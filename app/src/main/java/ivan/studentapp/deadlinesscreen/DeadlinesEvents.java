package ivan.studentapp.deadlinesscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ivan.studentapp.R;

/**
 * activity which holds fragment for the list of all Events
 *
 * @see DeadlinesAllEventsListFragment
 */

public class DeadlinesEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadlines_events_main);

        setTitle(R.string.all_events_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deadlines_options_menu, menu);

        // Visible items
        menu.findItem(R.id.add_event).setVisible(true);

        // Invisible items
        menu.findItem(R.id.event_edit).setVisible(false);
        menu.findItem(R.id.event_delete).setVisible(false);
        menu.findItem(R.id.all_events).setVisible(false);
        menu.findItem(R.id.event_save).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.add_event:
                i = new Intent(getApplicationContext(), DeadlinesFragmentsManagerActivity.class);
                i.putExtra(DeadlinesActivity.BUNDLE_FROM, "all events empty");
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), DeadlinesActivity.class);
        startActivity(i);
        finish();
    }
}

