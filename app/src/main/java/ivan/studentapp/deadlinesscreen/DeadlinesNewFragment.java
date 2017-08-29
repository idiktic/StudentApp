package ivan.studentapp.deadlinesscreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ivan.studentapp.R;
import ivan.studentapp.subjectsscreen.SubjectsActivity;
import ivan.studentapp.subjectsscreen.SubjectsDBAdapter;

/**
 * fragment for writing new and editing an event
 *
 * @see DeadlinesFragmentsManagerActivity
 */

public class DeadlinesNewFragment extends Fragment implements View.OnClickListener {

    // view fragment
    View viewFragment;

    // bundle that gets data from intent extras
    Bundle bundle;

    // list of all subjects for spinner
    private ArrayList<String> subjects;

    // spinners to choose subject and type of event
    Spinner spinnerSubject, spinnerType;

    // text viewvs to show selected option
    TextView txtSubject, txtDate, txtTime, txtType;

    // for writing note
    EditText editNote;

    // date from date picker
    Date dateFromDatePicker;

    // event to store data from bundle
    Event event;

    // helping string for naming month
    String mMonth;

    // numbers needed for dates
    int day, month, hours, minutes, year = 1900;

    public DeadlinesNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        assignUiElements(inflater, container);
        assignClickHandlers();

        return viewFragment;
    }

    /**
     * assigning elements, click handlers and setting
     * necessary data into elements
     *
     * @param inflater
     * @param container
     */
    public void assignUiElements(LayoutInflater inflater, ViewGroup container) {
        viewFragment = inflater.inflate(R.layout.deadlines_new_fragment, container, false);
        spinnerSubject = (Spinner) viewFragment.findViewById(R.id.spinner_subject);
        spinnerType = (Spinner) viewFragment.findViewById(R.id.spinner_type);
        txtSubject = (TextView) viewFragment.findViewById(R.id.textView7);
        txtDate = (TextView) viewFragment.findViewById(R.id.txt_deadline_date);
        txtTime = (TextView) viewFragment.findViewById(R.id.txt_deadline_time);
        txtType = (TextView) viewFragment.findViewById(R.id.txt_deadline_type);
        editNote = (EditText) viewFragment.findViewById(R.id.edit_deadline_note);

        bundle = getActivity().getIntent().getExtras();
        String from = bundle.getString(DeadlinesActivity.BUNDLE_FROM);
        setData(from);

    }

    /**
     * assigning click handlers
     */
    public void assignClickHandlers() {
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
    }

    /**
     * setting data to text views
     * depending on which intent called this activity
     *
     * @param from - string that stores info about this activity caller
     */
    public void setData(String from) {

        if (from.contains("calendar with date")) {
            year = year + bundle.getInt(DeadlinesActivity.DATE_YEAR);
            day = bundle.getInt(DeadlinesActivity.DATE_DAY);
            month = bundle.getInt(DeadlinesActivity.DATE_MONTH);
            convertMonthToString(month);

            setSubjectSpinner("");
            setTypeSpinner("");
            txtDate.setText(day + " " + mMonth + ", " + year);
        } else if (from.contains("event details full")) {
            event = new Event(bundle.getString(DeadlinesActivity.EVENT_SUBJECT),
                    bundle.getString(DeadlinesActivity.EVENT_TYPE),
                    bundle.getString(DeadlinesActivity.EVENT_NOTE),
                    bundle.getString(DeadlinesActivity.EVENT_DATE),
                    bundle.getLong(DeadlinesActivity.EVENT_ID));

            String abb = bundle.getString(DeadlinesActivity.EVENT_SUBJECT);
            String type = bundle.getString(DeadlinesActivity.EVENT_TYPE);
            setSubjectSpinner(abb);
            spinnerSubject.setVisibility(View.GONE);
            setTypeSpinner(type);
            setTypeSpinner(event.getType());
            Date date;
            date = formatDate(event.getDate());
            year = year + date.getYear();
            day = date.getDate();
            month = date.getMonth();
            convertMonthToString(month);
            txtDate.setText(day + " " + mMonth + " " + year);
            editNote.setText(event.getNote());

        } else if (from.contains("subjects with subject abb")) {
            String abb = bundle.getString(SubjectsActivity.SUBJECT_ABB);

            setSubjectSpinner(abb);
            spinnerSubject.setVisibility(View.GONE);
            setTypeSpinner("");
        } else {
            setSubjectSpinner("");
            setTypeSpinner("");
        }
    }

    /**
     * setting subject abbreviation either from bundle string
     * or from spinner adapter
     *
     * @param subjectAbb - string to hold value for subject abbreviation
     */
    public void setSubjectSpinner(String subjectAbb) {
        //fetching subject names from database as string
        SubjectsDBAdapter dbAdapter = new SubjectsDBAdapter(getActivity().getBaseContext());
        dbAdapter.openDatabase();
        subjects = dbAdapter.getAllSubjectsAbbs();
        dbAdapter.closeDatabase();

        //putting string values into adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.my_simple_spinner_item, subjects);

        txtSubject.setText(subjectAbb);

        //setting up spinner
        spinnerSubject.setAdapter(adapter);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                txtSubject.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtSubject.setText("");
            }
        });
    }

    /**
     * setting event type data either from bundle string
     * or from spinner adapter
     *
     * @param type - string to hold value of event type
     */
    public void setTypeSpinner(String type) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapter);

        // set spinner initial selection to be type got from intent, if there is such
        if (!type.equals(null)) {
            int spinnerPosition = adapter.getPosition(type);
            spinnerType.setSelection(spinnerPosition);
        }
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtType.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * checking inputs from text boxes
     */
    public void checkNewEvent() {
        String name, type, note, sDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //if date is entered, save event into database
        if (txtDate.getText().
                toString().
                equals("") || txtSubject.getText().toString().equals(""))
            Toast.makeText(getActivity(), R.string.event_warning, Toast.LENGTH_SHORT).show();
        else {
            if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).contains("calendar with date")) {
                Date date = new Date(year - 1900, month, day);
                //formatting date for database
                sDate = sdf.format(date);
            } else {
                sDate = sdf.format(dateFromDatePicker);
            }

            name = txtSubject.getText().toString();
            type = txtType.getText().toString();
            note = editNote.getText().toString();
            saveNewEventToDatabase(name, type, note, sDate);
            startCalendarActivity();
        }
    }

    /**
     * saving new event into database
     *
     * @param name - abbreviation of subject to be stored into database
     * @param type - type of event to be stored into database
     * @param note - event note to be stored into database
     * @param date - event date to be stored into database
     */
    public void saveNewEventToDatabase(String name, String type, String note, String date) {
        EventsDBAdapter eventsDBAdapter = new EventsDBAdapter(getActivity().getBaseContext());
        eventsDBAdapter.openDatabase();
        eventsDBAdapter.createEvent(name, type, note, date);
        eventsDBAdapter.closeDatabase();
        Toast.makeText(getActivity().getBaseContext(), R.string.event_create, Toast.LENGTH_SHORT).show();
    }

    /**
     * checking inputs from text boxes
     */
    public void checkUpdateEvent() {
        long id;
        String name, type, note, sDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        id = event.getId();

        Date date;


        //Date date = new Date(event.getDate());
        //if date is entered, save event into database
        if (txtDate.getText().
                toString().
                equals("") || txtSubject.getText().toString().equals(""))
            Toast.makeText(getActivity(), R.string.event_warning, Toast.LENGTH_SHORT).show();
        else {
           /* if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).contains("calendar with date")) {
                Date date = new Date(year - 1900, month, day);
                //formatting date for database
                sDate = sdf.format(date);
            } else {
                */
           /* }*/
            date = formatDateFromTxt(txtDate.getText().toString());
            sDate = sdf.format(date);
            name = txtSubject.getText().toString();
            type = txtType.getText().toString();
            note = editNote.getText().toString();
            updateEventInDatabase(id, name, type, note, sDate);
            startCalendarActivity();
        }
    }

    /**
     * updating event in database
     *
     * @param id   - id of event to be updated
     * @param name - abbreviation of subject to be updated
     * @param type - type of event to be updated
     * @param note - event note to be updated
     * @param date - event date to be updated
     */
    public void updateEventInDatabase(long id, String name, String type, String note, String date) {
        EventsDBAdapter eventsDBAdapter = new EventsDBAdapter(getActivity().getBaseContext());
        eventsDBAdapter.openDatabase();
        eventsDBAdapter.updateEvent(id, name, type, note, date);
        eventsDBAdapter.closeDatabase();
        Toast.makeText(getActivity().getBaseContext(), R.string.event_update, Toast.LENGTH_SHORT).show();
    }

    /**
     * handling proper dates formats
     *
     * @param dateToFormat - string to format into date
     */
    public Date formatDateFromTxt(String dateToFormat) { //12 Jan 2017
        String[] fomatted = dateToFormat.split(" ");

        int year = Integer.valueOf(fomatted[2]);
        year = -1900 + year;
        String mMonth = (fomatted[1]);
        int month = convertMonth(mMonth) - 1;
        int day = Integer.valueOf(fomatted[0]);

        return new Date(year, month, day);
    }

    public Date formatDate(String dateToFormat) {       // 2017-05-29
        String[] fomatted = dateToFormat.split("-");

        int year = Integer.valueOf(fomatted[0]);
        year = -1900 + year;
        int month = Integer.valueOf(fomatted[1]);
        month = month - 1;
        int day = Integer.valueOf(fomatted[2]);

        return new Date(year, month, day);
    }

    /**
     * proper month naming
     *
     * @param month - string to convert into number
     * @return number of month
     */
    public int convertMonth(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return 0;
        }
    }

    /**
     * proper month numbering
     *
     * @param month - number to convert into string
     * @return abbreviation of month
     */
    public void convertMonthToString(int month) {
        switch (month) {
            case 0:
                mMonth = "Jan";
                break;
            case 1:
                mMonth = "Feb";
                break;
            case 2:
                mMonth = "Mar";
                break;
            case 3:
                mMonth = "Apr";
                break;
            case 4:
                mMonth = "May";
                break;
            case 5:
                mMonth = "Jun";
                break;
            case 6:
                mMonth = "Jul";
                break;
            case 7:
                mMonth = "Aug";
                break;
            case 8:
                mMonth = "Sep";
                break;
            case 9:
                mMonth = "Oct";
                break;
            case 10:
                mMonth = "Nov";
                break;
            case 11:
                mMonth = "Dec";
                break;
        }
    }

    /**
     * refreshing deadlines main page
     *
     * @see DeadlinesActivity
     */
    public void startCalendarActivity() {
        Intent i = new Intent(getActivity().getBaseContext(), DeadlinesActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //setting the date
            case R.id.txt_deadline_date:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.deadlines_date, null);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                final DatePicker datePicker = (DatePicker) mView.findViewById(R.id.deadline_date_picker);
                Button btnOKDate = (Button) mView.findViewById(R.id.deadline_date_btn_ok);
                Button btnCancelDate = (Button) mView.findViewById(R.id.deadline_date_btn_cancel);

                btnOKDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateFromDatePicker = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                        DateFormat df = SimpleDateFormat.getDateInstance();
                        txtDate.setText(df.format(dateFromDatePicker));
                        dialog.cancel();
                    }
                });

                btnCancelDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;

/*            //setting the time, NOT IMPLEMENTED YET
            case R.id.txt_deadline_time:
                AlertDialog.Builder nBuilder = new AlertDialog.Builder(getActivity());
                View nView = getActivity().getLayoutInflater().inflate(R.layout.deadlines_time, null);

                nBuilder.setView(nView);
                final AlertDialog nDialog = nBuilder.create();
                nDialog.show();


                final TimePicker timePicker = (TimePicker) nView.findViewById(R.id.deadline_time_picker);
                Button btnOKTime = (Button) nView.findViewById(R.id.deadline_time_btn_ok);
                Button btnCancelTime = (Button) nView.findViewById(R.id.deadline_time_btn_cancel);

                btnOKTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hours = timePicker.getCurrentHour();
                        minutes = timePicker.getCurrentMinute();
                        txtTime.setText("" + hours + ":" + minutes);
                        nDialog.cancel();
                    }
                });

                btnCancelTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nDialog.cancel();
                    }
                });
                break;*/
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.deadlines_options_menu, menu);

        /* Visible items */
        menu.findItem(R.id.event_save).setVisible(true);


        /* Invisible items */
        menu.findItem(R.id.event_edit).setVisible(false);
        menu.findItem(R.id.event_delete).setVisible(false);
        menu.findItem(R.id.all_events).setVisible(false);
        menu.findItem(R.id.add_event).setVisible(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.event_save:
                // if it is update fragment, check inputs
                if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).contains("event details full"))
                    checkUpdateEvent();
                else
                    // if it is new fragment, check inputs
                    checkNewEvent();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
