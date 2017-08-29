package ivan.studentapp.subjectsscreen;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ivan.studentapp.deadlinesscreen.Event;
import ivan.studentapp.deadlinesscreen.EventsAdapter;
import ivan.studentapp.deadlinesscreen.EventsDBAdapter;

/**
 * fragment to show the list of all Events inside Subject details
 */

public class SubjectEventsListFragment extends ListFragment {

    // all events from database
    private ArrayList<Event> events;
    private EventsAdapter eventsAdapter;

    // bundle to fetch event data from extras
    Bundle bundle;

    // event variable to store data from extras
    Event event;

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
        bundle = getActivity().getIntent().getExtras();
        event = new Event();
        event.setName(bundle.getString(SubjectsActivity.SUBJECT_ABB));

        EventsDBAdapter dbAdapter = new EventsDBAdapter(getActivity().getBaseContext());

        dbAdapter.openDatabase();
        events = dbAdapter.getAllEventsOfSubject(event.getName());
        dbAdapter.closeDatabase();
    }

    /**
     * OPTION DISABLED
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //launchDetailActivity(position);
    }
/*
    public void launchDetailActivity (int position) {

        Subject subject = (Subject) getListAdapter().getItem(position);
        Intent DetailActivityIntent = new Intent (getActivity(), SubjectDetailActivity.class);
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_NAME, subject.getName());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_ABB, subject.getAbbreviation());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_PROF, subject.getProfessor());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_CLASSES, subject.getClassesNumber());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_COLOR, subject.getColor());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_ID, subject.getId());

        startActivity(DetailActivityIntent);
    }*/
}
