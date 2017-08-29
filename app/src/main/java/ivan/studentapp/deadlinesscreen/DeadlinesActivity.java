package ivan.studentapp.deadlinesscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.Date;
import java.util.HashSet;

import ivan.studentapp.R;
import ivan.studentapp.mainscreen.MainActivity;

/**
 * main activity for Deadlines, has calendar and events
 *
 * @see DeadlinesCalendar
 * @see EventsDBAdapter
 */
public class DeadlinesActivity extends AppCompatActivity {

    // all events from database
    HashSet<Date> events;

    // calendar pop-out query and tollbar layout
    RelativeLayout calendarQueryBackground, calendarQuery, calendarToolbar;

    // calendar grid
    GridView calendarGrid;

    // calendar pop-out query buttons
    ImageButton btnSeeEvents, btnAddEvent;

    // calendar instance
    DeadlinesCalendar calendar;

    // store intent extras
    public static final String DATE_DAY = "ivan.studentapp.deadlinesscreen.Date Day";
    public static final String DATE_MONTH = "ivan.studentapp.deadlinesscreen.Date Month";
    public static final String DATE_YEAR = "ivan.studentapp.deadlinesscreen.Date Year";
    public static final String EVENT_SUBJECT = "ivan.studentapp.deadlinesscreen.Event Subject";
    public static final String EVENT_DATE = "ivan.studentapp.deadlinesscreen.Event Date";
    public static final String EVENT_TYPE = "ivan.studentapp.deadlinesscreen.Event Type";
    public static final String EVENT_NOTE = "ivan.studentapp.deadlinesscreen.Event Note";
    public static final String EVENT_ID = "ivan.studentapp.deadlinesscreen.Event Id";
    public static final String BUNDLE_FROM = "ivan.studentapp.deadlinesscreen.deadlines";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);

        setTitle(R.string.events_main_title);

        // init controls
        initUiElements();

        //get all events from databse
        getDataFromDatabase();

        // update calendar and set listeners
        setCalendarData();
    }

    /**
     * initialize controls
     */
    private void initUiElements() {
        calendar = ((DeadlinesCalendar) findViewById(R.id.calendar_view));
        calendarToolbar = (RelativeLayout) calendar.findViewById(R.id.calendar_toolbar);
        calendarGrid = (GridView) calendar.findViewById(R.id.calendar_grid);
        calendarQuery = (RelativeLayout) calendar.findViewById(R.id.calendar_querry_layer);
        calendarQueryBackground = (RelativeLayout) calendar.findViewById(R.id.querry_dialog_background);
        btnAddEvent = (ImageButton) calendarQuery.findViewById(R.id.deadlines_img_add_event);
        btnSeeEvents = (ImageButton) calendarQuery.findViewById(R.id.deadlines_img_see_events);
    }

    /**
     * fetch data from Events database
     *
     * @see EventsDBAdapter
     */
    private void getDataFromDatabase() {
        // hash set for events
        events = new HashSet<>();

        // get all events from database
        EventsDBAdapter dbAdapter = new EventsDBAdapter(getApplication().getBaseContext());
        dbAdapter.openDatabase();
        events = dbAdapter.getAllDates();
        dbAdapter.closeDatabase();
    }

    /**
     * set data onto calendar
     */
    private void setCalendarData() {

        // fill calendar with events
        calendar.updateCalendar(events);

        // assign event handler
        calendar.setEventHandler(new DeadlinesCalendar.EventHandler() {
            @Override
            public void onDayLongPress(Date date, float positionX, float positionY) {
                // proper query position inside calendar
                positionQuery(date, positionX, positionY);
                // set listeners for calendar actions and behaviour
                setListeners(date);
            }
        });
    }

    /**
     * onLongClick on calendar cell shows query
     * for adding event to that specific day
     * or see all events on that specific day
     *
     * @param date      - date of day clicked
     * @param positionX - X coordinates of cell clicked, used for query positioning
     * @param positionY - Y coordinates of cell clicked, used for query positioning
     */
    private void positionQuery(Date date, float positionX, float positionY) {

        // hide see icon
        btnSeeEvents.setVisibility(View.GONE);

        // position X and change background accordingly
        if (positionX == 0.0) {
            calendarQueryBackground.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.dialog_background_left));
            calendarQuery.setX(positionX + 10);
        } else if (positionX > 500.0) {
            calendarQueryBackground.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.dialog_background_right));
            calendarQuery.setX(positionX - 80);
        } else {
            calendarQueryBackground.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.dialog_background_middle));
            calendarQuery.setX(positionX - 33);
        }

        // position Y
        calendarQuery.setY(positionY + 110);

        // show see icon, if selected date has events
        if (events != null) {
            for (Date eventDate : events) {
                if (eventDate.getDate() == date.getDate() &&
                        eventDate.getMonth() == date.getMonth() &&
                        eventDate.getYear() == date.getYear()) {
                    btnSeeEvents.setVisibility(View.VISIBLE);
                }
            }
        }

        // set grid transparency
        calendarGrid.setAlpha(0.5f);

        showQuery();
    }

    private void showQuery() {
        // show query on long date click
        calendarQuery.setVisibility(View.VISIBLE);
        calendarQuery.bringToFront();
    }

    private void hideQuery() {
        calendarQuery.setVisibility(View.INVISIBLE);
        btnSeeEvents.setVisibility(View.GONE);
        calendarGrid.setAlpha(1);
    }

    /**
     * onClick and onTouch listeners for UI elements
     * for proper calendar functioning
     *
     * @param date
     */
    private void setListeners(final Date date) {
        // hide query and show calendar
        calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideQuery();
            }
        });

        // hide query and show calendar
        calendar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideQuery();
                return true;
            }
        });

        // goto new event activity
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deadlinesNewActivity = new Intent(getApplicationContext(), DeadlinesFragmentsManagerActivity.class);
                addDataToIntent(deadlinesNewActivity, date, "calendar with date");
                startActivity(deadlinesNewActivity);
                finish();
            }
        });

        // goto see events on selected date activity
        btnSeeEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deadlinesSeeEventsOnSelectedDate = new Intent(getApplicationContext(), DeadlinesSelectedDateEventsActivity.class);
                addDataToIntent(deadlinesSeeEventsOnSelectedDate, date, "deadlines");
                startActivity(deadlinesSeeEventsOnSelectedDate);
                finish();
            }
        });
    }

    /**
     * Put extras to intent
     * with information "from" to determine
     * which activity to open
     *
     * @param intent - intent to be filled with data
     * @param date   - data to be put into intent
     * @param from   - name of activity which started this intent
     */
    private void addDataToIntent(Intent intent, Date date, String from) {
        intent.putExtra(DeadlinesActivity.DATE_DAY, date.getDate());
        intent.putExtra(DeadlinesActivity.DATE_MONTH, date.getMonth());
        intent.putExtra(DeadlinesActivity.DATE_YEAR, date.getYear());
        intent.putExtra(DeadlinesActivity.BUNDLE_FROM, from);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deadlines_options_menu, menu);

        /* Visible items */
        menu.findItem(R.id.add_event).setVisible(true);
        menu.findItem(R.id.all_events).setVisible(true);

        /* Invisible items */
        menu.findItem(R.id.event_edit).setVisible(false);
        menu.findItem(R.id.event_delete).setVisible(false);
        menu.findItem(R.id.event_save).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.add_event:
                i = new Intent(getApplicationContext(), DeadlinesFragmentsManagerActivity.class);
                i.putExtra(DeadlinesActivity.BUNDLE_FROM, "calendar empty");
                startActivity(i);
                finish();
                return true;
            case R.id.all_events:
                Intent deadlinesAllActivity = new Intent(getApplicationContext(), DeadlinesEvents.class);
                startActivity(deadlinesAllActivity);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // hide query on back press if shown or else go to main activity
        if (calendarQuery.isShown()) {
            hideQuery();
        } else {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
