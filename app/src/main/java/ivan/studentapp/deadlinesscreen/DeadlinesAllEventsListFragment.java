package ivan.studentapp.deadlinesscreen;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ivan.studentapp.subjectsscreen.Subject;
import ivan.studentapp.subjectsscreen.SubjectDetailActivity;
import ivan.studentapp.subjectsscreen.SubjectsActivity;
import ivan.studentapp.subjectsscreen.SubjectsAdapter;
import ivan.studentapp.subjectsscreen.SubjectsDBAdapter;

/**
 * fragment to show the list of all Events
 */

public class DeadlinesAllEventsListFragment extends ListFragment {

    // all events from database
    private ArrayList<Event> events;
    private EventsAdapter eventsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get events
        getDataFromDatabase();

        eventsAdapter = new EventsAdapter(getActivity(), events);
        setListAdapter(eventsAdapter);

        // set divider
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        getListView().setDividerHeight(1);

    }

    /**
     * get all events from events database
     */
    private void getDataFromDatabase() {
        EventsDBAdapter dbAdapter = new EventsDBAdapter(getActivity().getBaseContext());
        dbAdapter.openDatabase();
        events = dbAdapter.getAllEvents();
        dbAdapter.closeDatabase();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchEventDetailActivity(position);
    }

    /**
     * launch activity with event details
     *
     * @param position - number of clicked event in the list
     * @see EventDetailActivity
     */
    public void launchEventDetailActivity(int position) {
        Event event = (Event) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), EventDetailActivity.class);

        intent.putExtra(DeadlinesActivity.EVENT_SUBJECT, event.getName());
        intent.putExtra(DeadlinesActivity.EVENT_DATE, event.getDate());
        intent.putExtra(DeadlinesActivity.EVENT_TYPE, event.getType());
        intent.putExtra(DeadlinesActivity.EVENT_NOTE, event.getNote());
        intent.putExtra(DeadlinesActivity.EVENT_ID, event.getId());

        startActivity(intent);
    }
}
