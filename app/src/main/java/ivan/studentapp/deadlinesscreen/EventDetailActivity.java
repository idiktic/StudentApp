package ivan.studentapp.deadlinesscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ivan.studentapp.R;

/**
 * activity to handle fragment for viewing event details
 *
 * @see EventViewFragment
 */
public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_activity);

        // setting title
        setTitle(R.string.event_details_title);

        createAndAddFragment();
    }

    /**
     * adding a fragment to activity
     */
    public void createAndAddFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        EventViewFragment eventViewFragment = new EventViewFragment();
        fragmentTransaction.add(R.id.eventDetailContainer, eventViewFragment, "EVENT_VIEW_FRAGMENT");
        fragmentTransaction.commit();
    }

    /**
     * @see DeadlinesEvents
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), DeadlinesEvents.class);
        startActivity(i);
        finish();
    }
}