package ivan.studentapp.deadlinesscreen;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * fragment for viewing all events on selected day
 *
 * @see DeadlinesSelectedDateEventsActivity
 */

public class DeadlinesSelectedDateEventsListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // list adapter for events
        EventsAdapter eventsAdapter;

        // set adapter with data from database
        eventsAdapter = new EventsAdapter(getActivity(), getEvents());
        setListAdapter(eventsAdapter);

        // set list divider
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        getListView().setDividerHeight(1);

    }

    /**
     * fetches events from database
     *
     * @return array list of all events on selected date
     */
    private ArrayList<Event> getEvents() {
        // list of events on selected day/date
        ArrayList<Event> events;

        // database adapter
        EventsDBAdapter dbAdapter = new EventsDBAdapter(getActivity().getBaseContext());

        dbAdapter.openDatabase();

        // get all events on selected day
        events = dbAdapter.getAllEventsOnDay(getDate());

        dbAdapter.closeDatabase();
        return events;
    }

    /**
     * format date from intent extras for database search
     *
     * @return string with formatted date
     */
    private String getDate() {
        // integers to store date from bundle
        int day, month, year;
        // selected date formatted for database
        String selectedDate;
        // bundle that gets data from intent extras
        Bundle bundle;

        // get info from extras
        bundle = getActivity().getIntent().getExtras();

        // assign values from bundle
        year = 1900 + bundle.getInt(DeadlinesActivity.DATE_YEAR);
        day = bundle.getInt(DeadlinesActivity.DATE_DAY);
        month = bundle.getInt(DeadlinesActivity.DATE_MONTH) + 1;

        // create string
        selectedDate = year + "-" + month + "-" + day;

        return selectedDate;
    }

    /**
     * this option is disabled here
     *
     * @param l
     * @param v
     * @param position
     * @param id
     * @see DeadlinesAllEventsListFragment - see event details only from here
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //launchEventDetailActivity(position);
    }
/*
    public void launchEventDetailActivity (int position) {
        Event event = (Event) getListAdapter().getItem(position);

        Intent intent = new Intent (getActivity(), EventDetailActivity.class);

        intent.putExtra(DeadlinesActivity.EVENT_SUBJECT, event.getName());
        intent.putExtra(DeadlinesActivity.EVENT_DATE, event.getDate());
        intent.putExtra(DeadlinesActivity.EVENT_TYPE, event.getType());
        intent.putExtra(DeadlinesActivity.EVENT_NOTE, event.getNote());
        intent.putExtra(DeadlinesActivity.EVENT_ID, event.getId());
        intent.putExtra(DeadlinesActivity.BUNDLE_FROM, "events on day");

        startActivity(intent);
    }
    */
}
