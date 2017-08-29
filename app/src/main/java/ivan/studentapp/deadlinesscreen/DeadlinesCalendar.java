package ivan.studentapp.deadlinesscreen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import ivan.studentapp.R;

public class DeadlinesCalendar extends RelativeLayout {

    // 42 days to show on calendar
    private static final int DAYS_SHOW = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentMonth = Calendar.getInstance();

    // long press on cell event handling
    private EventHandler eventHandler = null;

    // pressed cell position
    float positionX, positionY;

    // internal
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // set of all events for updating calendar
    HashSet<Date> allEvents;

    public DeadlinesCalendar(Context context) {
        super(context);
    }

    public DeadlinesCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DeadlinesCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * inflate calendar layout
     */
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar, this);

        loadDateFormat(attrs);
        initUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.DeadlinesCalendar);

        try {
            // load date format
            dateFormat = ta.getString(R.styleable.DeadlinesCalendar_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            // to default otherwise
            ta.recycle();
        }
    }

    private void initUiElements() {
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.btn_calendar_previous);
        btnNext = (ImageView) findViewById(R.id.btn_calendar_next);
        txtDate = (TextView) findViewById(R.id.txt_calendar_date);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh calendar
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, 1);
                // populate month with events from database
                updateCalendar(allEvents);
            }
        });

        // subtract one month and refresh calendar
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth.add(Calendar.MONTH, -1);
                // populate month with events from database
                updateCalendar(allEvents);
            }
        });

        // long press on a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long press on cell and
                // pass a position of selected cell for query dialog
                if (eventHandler == null)
                    return false;
                positionX = cell.getX();
                positionY = cell.getY();
                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position), positionX, positionY);
                return true;
            }
        });
    }

    /**
     * show dates in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    /**
     * show dates in grid
     */
    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> calendarCells = new ArrayList<>();
        Calendar calendar = (Calendar) currentMonth.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (calendarCells.size() < DAYS_SHOW) {
            calendarCells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), calendarCells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentMonth.getTime()));
        header.setBackgroundColor(getResources().getColor(R.color.header));

        allEvents = events;
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date currentlySelected = getItem(15);
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.calendar_day, parent, false);

            TextView dateText = (TextView) view.findViewById(R.id.txt_day_cell);

            // if this day has an event, show event image
            if (eventDays != null) {
                for (Date eventDate : eventDays) {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year) {
                        ImageView eventImage = (ImageView) view.findViewById(R.id.img_day_cell);
                        eventImage.setVisibility(VISIBLE);
                    }
                }
            }

            // clear styling
            dateText.setTypeface(null, Typeface.NORMAL);
            dateText.setTextColor(getResources().getColor(R.color.calendar_regular));

            // if day is not current month color it grey, else it is today and color it blue and bold
            if (month != currentlySelected.getMonth() || year != currentlySelected.getYear()) {
                dateText.setTextColor(getResources().getColor(R.color.greyed_out));
                view.setEnabled(false);
                view.setOnLongClickListener(null);
            } else if (day == today.getDate() && month == today.getMonth() && year == today.getYear()) {
                dateText.setTypeface(null, Typeface.BOLD);
                dateText.setTextColor(getResources().getColor(R.color.today));
            }
            // set text
            dateText.setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    /**
     * assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * this interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Date date, float posX, float posY);
    }
}
