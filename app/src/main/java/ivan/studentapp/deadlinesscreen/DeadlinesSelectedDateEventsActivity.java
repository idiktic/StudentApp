package ivan.studentapp.deadlinesscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ivan.studentapp.R;

/**
 * activity to handle fragment for viewing all events on selected day
 * (selecting eye icon when day with event is long clicked)
 *
 * @see DeadlinesSelectedDateEventsListFragment
 */
public class DeadlinesSelectedDateEventsActivity extends AppCompatActivity {

    // bundle that gets data from intent extras
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fragment included in xml
        setContentView(R.layout.deadlines_selected_date_events_main);

        // proper format title date
        DateFormat df = SimpleDateFormat.getDateInstance();

        // setting title
        setTitle("> Events on " + df.format(getDate()));
    }

    /**
     * @return - new Date of selected day for title
     * fetches intent data about date of selected cell
     */
    private Date getDate() {
        // integers to store date from bundle
        int day, month, year;

        bundle = getIntent().getExtras();

        year = bundle.getInt(DeadlinesActivity.DATE_YEAR);
        day = bundle.getInt(DeadlinesActivity.DATE_DAY);
        month = bundle.getInt(DeadlinesActivity.DATE_MONTH);

        return new Date(year, month, day);
    }

    /**
     * @see DeadlinesActivity
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), DeadlinesActivity.class);
        startActivity(i);
        finish();
    }
}