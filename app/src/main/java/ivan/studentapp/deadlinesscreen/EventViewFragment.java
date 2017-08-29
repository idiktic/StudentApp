package ivan.studentapp.deadlinesscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ivan.studentapp.R;

/**
 * fragment for viewing selected event details
 */

public class EventViewFragment extends Fragment {

    // bundle that gets data from intent extras
    Bundle bundle;

    // variable to store event created from bundle
    Event event;

    // view for this fragment
    View viewFragment;

    //UI controls
    TextView txtSubject, txtDate, txtType, txtNote;

    public EventViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // getting event data from bundle
        fetchEventData();

        // initialize user controls
        initUiControls(inflater, container);

        return viewFragment;
    }

    /**
     * fetching data from extras and
     * creating new Event with that info
     */
    private void fetchEventData() {
        bundle = getActivity().getIntent().getExtras();
        event = new Event(bundle.getString(DeadlinesActivity.EVENT_SUBJECT),
                bundle.getString(DeadlinesActivity.EVENT_TYPE),
                bundle.getString(DeadlinesActivity.EVENT_NOTE),
                bundle.getString(DeadlinesActivity.EVENT_DATE),
                bundle.getLong(DeadlinesActivity.EVENT_ID));
    }

    /**
     * inflate the layout for this fragment
     *
     * @param inflater
     * @param container
     */
    private void initUiControls(LayoutInflater inflater, ViewGroup container) {
        View viewFragment = inflater.inflate(R.layout.event_view_fragment, container, false);

        txtSubject = (TextView) viewFragment.findViewById(R.id.tv_details_event_subject);
        txtDate = (TextView) viewFragment.findViewById(R.id.tv_details_event_date);
        txtType = (TextView) viewFragment.findViewById(R.id.tv_details_event_type);
        txtNote = (TextView) viewFragment.findViewById(R.id.tv_details_event_note);

        // setting values to text views
        txtSubject.setText(event.getName());
        txtDate.setText(event.getDate());
        txtType.setText(event.getType());
        txtNote.setText(event.getNote());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.deadlines_options_menu, menu);

        //Visible items
        menu.findItem(R.id.event_edit).setVisible(true);
        menu.findItem(R.id.event_delete).setVisible(true);

        // Invisible items
        menu.findItem(R.id.add_event).setVisible(false);
        menu.findItem(R.id.all_events).setVisible(false);
        menu.findItem(R.id.event_save).setVisible(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // edit event
            case R.id.event_edit:
                Intent intent = new Intent(getActivity().getBaseContext(), DeadlinesFragmentsManagerActivity.class);
                intent.putExtra(DeadlinesActivity.EVENT_SUBJECT, bundle.getString(DeadlinesActivity.EVENT_SUBJECT));
                intent.putExtra(DeadlinesActivity.EVENT_TYPE, bundle.getString(DeadlinesActivity.EVENT_TYPE));
                intent.putExtra(DeadlinesActivity.EVENT_NOTE, bundle.getString(DeadlinesActivity.EVENT_NOTE));
                intent.putExtra(DeadlinesActivity.EVENT_DATE, bundle.getString(DeadlinesActivity.EVENT_DATE));
                intent.putExtra(DeadlinesActivity.EVENT_ID, bundle.getLong(DeadlinesActivity.EVENT_ID));
                intent.putExtra(DeadlinesActivity.BUNDLE_FROM, "event details full");
                startActivity(intent);
                return true;

            // delete event
            case R.id.event_delete:
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Event")
                        .setMessage("Are you sure you want to delete that event")
                        .setPositiveButton(R.string.subjects_details_menu_delete_dialog_buttonYes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete from database
                                EventsDBAdapter eventsDBAdapter = new EventsDBAdapter(getActivity().getBaseContext());
                                eventsDBAdapter.openDatabase();
                                eventsDBAdapter.deleteEvent(event.getId());
                                eventsDBAdapter.closeDatabase();

                                startAllEventsListActivity();
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
     * @see DeadlinesEvents
     */
    public void startAllEventsListActivity() {
        Intent i = new Intent(getActivity().getBaseContext(), DeadlinesEvents.class);
        startActivity(i);
    }
}
