package ivan.studentapp.subjectsscreen;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ivan.studentapp.R;
import ivan.studentapp.deadlinesscreen.DeadlinesActivity;
import ivan.studentapp.deadlinesscreen.DeadlinesFragmentsManagerActivity;
import ivan.studentapp.deadlinesscreen.EventsDBAdapter;

/**
 * fragment for viewing selected subject details
 *
 * @see SubjectDetailActivity
 */
public class SubjectViewFragment extends Fragment {

    // intent for fetching extras
    Intent intent;

    // variable to hold data from intent
    Subject subject;

    // view for this fragment
    View viewFragment;

    //UI controls
    TextView tvAbb, tvName, tvProf;

    public SubjectViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        intent = getActivity().getIntent();
        // getting subject data from intent
        fetchSubjectData();

        // initialize user controls
        initUiControls(inflater, container);

        return viewFragment;
    }

    /**
     * fetching data from extras and
     * creating new Subject with that info
     */
    private void fetchSubjectData() {

        subject = new Subject(intent.getExtras().getString(SubjectsActivity.SUBJECT_NAME),
                intent.getExtras().getString(SubjectsActivity.SUBJECT_ABB),
                intent.getExtras().getString(SubjectsActivity.SUBJECT_PROF),
                intent.getExtras().getInt(SubjectsActivity.SUBJECT_CLASSES),
                intent.getExtras().getString(SubjectsActivity.SUBJECT_COLOR),
                intent.getExtras().getLong(SubjectsActivity.SUBJECT_ID));
    }

    /**
     * inflate the layout for this fragment
     * @param inflater
     * @param container
     */
    private void initUiControls(LayoutInflater inflater, ViewGroup container) {
        viewFragment = inflater.inflate(R.layout.subject_view_fragment, container, false);

        tvAbb = (TextView) viewFragment.findViewById(R.id.tv_detailAbbreviation);
        tvName = (TextView) viewFragment.findViewById(R.id.tv_detailName);
        tvProf = (TextView) viewFragment.findViewById(R.id.tv_detailProf);

        // setting values to text views
        tvAbb.setText(subject.getAbbreviation());
        tvName.setText(subject.getName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.subjects_options_menu, menu);

        // visible
        menu.findItem(R.id.delete).setVisible(true);
        menu.findItem(R.id.new_event).setVisible(true);

        // invisible
        menu.findItem(R.id.save).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // start add new event to this subject
            case R.id.new_event:
                addNewEvent();
                return true;
            // start edit subject activity
            case R.id.edit:
                editSubject();
                return true;
            // delete subject
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.subjects_details_menu_delete_dialog_title)
                        .setMessage(R.string.subjects_details_menu_delete_dialog_text)
                        .setPositiveButton(R.string.subjects_details_menu_delete_dialog_buttonYes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // first delete from database all events of subject
                                deleteEvents();
                                // then delete subject from database
                                deleteSubject();
                                // refresh subjects list activity
                                startSubjectListActivity();
                            }
                        })
                        .setNegativeButton(R.string.subjects_details_menu_delete_dialog_buttonCancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete)
                        .show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * delete a subject data from database
     * first delete all events with that subject abb
     * and then delete subject from database
     */
    public void deleteEvents() {
        EventsDBAdapter eventsDBAdapter = new EventsDBAdapter(getActivity().getBaseContext());
        eventsDBAdapter.openDatabase();
        eventsDBAdapter.deleteAllEventsOfSubject(intent.getExtras().getString(SubjectsActivity.SUBJECT_ABB));
        eventsDBAdapter.closeDatabase();
    }

    public void deleteSubject() {
        SubjectsDBAdapter subjectsDBAdapter = new SubjectsDBAdapter(getActivity().getBaseContext());
        subjectsDBAdapter.openDatabase();
        subjectsDBAdapter.deleteSubject(intent.getExtras().getLong(SubjectsActivity.SUBJECT_ID));
        subjectsDBAdapter.closeDatabase();
    }

    /**
     * adding data to intent
     *
     * @param intent - intent to fill with data
     * @param from   - string with info about activity caller
     */
    public void addExtras(Intent intent, String from) {
        intent.putExtra(SubjectsActivity.SUBJECT_NAME, subject.getName());
        intent.putExtra(SubjectsActivity.SUBJECT_ABB, subject.getAbbreviation());
        intent.putExtra(SubjectsActivity.SUBJECT_PROF, subject.getProfessor());
        intent.putExtra(SubjectsActivity.SUBJECT_CLASSES, subject.getClassesNumber());
        intent.putExtra(SubjectsActivity.SUBJECT_COLOR, subject.getColor());
        intent.putExtra(SubjectsActivity.SUBJECT_ID, intent.getExtras().getLong(SubjectsActivity.SUBJECT_ID));
        intent.putExtra(DeadlinesActivity.BUNDLE_FROM, from);
    }

    /**
     * start new event activity
     *
     * @see DeadlinesFragmentsManagerActivity
     * @see ivan.studentapp.deadlinesscreen.DeadlinesNewFragment
     */
    public void addNewEvent() {
        Intent addnewEvent = new Intent(getActivity(), DeadlinesFragmentsManagerActivity.class);
        //add extras to intent
        addExtras(addnewEvent, "subjects with subject abb");
        startActivity(addnewEvent);
    }

    /**
     * start edit subject activity
     *
     * @see SubjectEditActivity
     * @see SubjectEditFragment
     */
    public void editSubject() {
        Intent EditActivityIntent = new Intent(getActivity(), SubjectEditActivity.class);
        //add extras to intent
        addExtras(EditActivityIntent, "subject edit");
        startActivity(EditActivityIntent);
    }

    public void startSubjectListActivity() {
        Intent intent = new Intent(getContext(), SubjectsActivity.class);
        startActivity(intent);
    }

}
