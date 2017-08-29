package ivan.studentapp.deadlinesscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * adapter for Events database handling
 */

public class EventsDBAdapter {

    // database info
    private static final String DATABASE_NAME = "events";
    private static final int DATABASE_VERSION = 1;

    // events database intern
    public static final String EVENTS_TABLE = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EVENTNAME = "name";
    public static final String COLUMN_EVENTTYPE = "type";
    public static final String COLUMN_EVENTNOTE = "note";
    public static final String COLUMN_DATE = "date";

    // string to hold list of all columns
    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_EVENTNAME,
            COLUMN_EVENTTYPE,
            COLUMN_EVENTNOTE,
            COLUMN_DATE};

    // sql command for creating table
    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + EVENTS_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_EVENTNAME + " text, "
            + COLUMN_EVENTTYPE + " text, "
            + COLUMN_EVENTNOTE + " text, "
            + COLUMN_DATE + " text"
            + ");";

    private SQLiteDatabase sqlDB;
    private Context context;
    private EventsDBHelper eventsDBHelper;

    public EventsDBAdapter(Context ctx) {
        context = ctx;
    }

    /**
     * method for opening database we are about to manipulate with
     *
     * @return
     * @throws android.database.SQLException
     */
    public EventsDBAdapter openDatabase() throws android.database.SQLException {
        eventsDBHelper = new EventsDBHelper(context);
        sqlDB = eventsDBHelper.getWritableDatabase();
        return this;
    }

    /**
     * method for closing database
     */
    public void closeDatabase() {
        eventsDBHelper.close();
    }

    /**
     * get all events from database
     *
     * @return array list of events for list adapter
     */
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<Event>();

        //grab all the information from events database
        Cursor cursor = sqlDB.query(EVENTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Event event = cursorToEvent(cursor);
            events.add(event);
        }
        cursor.close();
        return events;
    }

    /**
     * get all events from database on specific day
     *
     * @return array list of events on specific day for list adapter
     */
    public ArrayList<Event> getAllEventsOnDay(String sDate) {
        ArrayList<Event> eventsOnDay = new ArrayList<Event>();
        Date dateFromCalendar, dateFromDatabase;

        //format date from String to Date
        dateFromCalendar = formatDate(sDate);

        //grab all the information from events database
        Cursor cursor = sqlDB.query(EVENTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Event event = cursorToEvent(cursor);
            //format event's date from String to Date
            dateFromDatabase = formatDate(event.getDate());
            //if Date selected from Calendar is equal
            // to date from database add it to the list
            if (dateFromDatabase.getDate() == dateFromCalendar.getDate() &&
                    dateFromDatabase.getMonth() == dateFromCalendar.getMonth() &&
                    dateFromDatabase.getYear() == dateFromCalendar.getYear())
                eventsOnDay.add(event);
        }
        cursor.close();
        return eventsOnDay;
    }

    /**
     * get all events from database of specific subject
     *
     * @return array list of events of specific subject for list adapter
     */
    public ArrayList<Event> getAllEventsOfSubject(String subjectAbb) {
        ArrayList<Event> eventsOfSubject = new ArrayList<Event>();

        //grab all the information from events database
        Cursor cursor = sqlDB.query(EVENTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Event event = cursorToEvent(cursor);
            // where abbreviation is equal to
            // events name (which is subject abbreviation as well)
            if (event.getName().equals(subjectAbb))
                eventsOfSubject.add(event);
        }
        cursor.close();
        return eventsOfSubject;
    }

    /**
     * get all dates formatted from events database
     *
     * @return set of all dates as Date
     */
    public HashSet<Date> getAllDates() {
        HashSet<Date> dates = new HashSet<Date>();

        //grab all the information from events database
        Cursor cursor = sqlDB.query(EVENTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Event event = cursorToEvent(cursor);
            Date date;
            date = formatDate(event.getDate());
            dates.add(date);
        }

        cursor.close();
        return dates;
    }

    /**
     * converting cursor object to Event object
     *
     * @param cursor
     * @return new Event from cursor
     */
    private Event cursorToEvent(Cursor cursor) {
        Event newEvent = new Event(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getLong(0));
        return newEvent;
    }

    /**
     * format date from database
     *
     * @param dateToFormat
     * @return new Date formatted from string
     */
    public Date formatDate(String dateToFormat) {
        String[] fomatted = dateToFormat.split("-");

        int year = Integer.valueOf(fomatted[0]);
        year = -1900 + year;
        int month = Integer.valueOf(fomatted[1]);
        month = month - 1;
        int day = Integer.valueOf(fomatted[2]);

        return new Date(year, month, day);
    }

    /**
     * create new event in database
     *
     * @param name - subject abbreviation
     * @param type - type of event
     * @param note - event comment
     * @param date - event date
     * @return new Event added to database
     */
    public Event createEvent(String name,
                             String type,
                             String note,
                             String date) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_EVENTNAME, name);
        values.put(COLUMN_EVENTTYPE, type);
        values.put(COLUMN_EVENTNOTE, note);
        values.put(COLUMN_DATE, date);

        long insertId = sqlDB.insert(EVENTS_TABLE, null, values);

        Cursor cursor = sqlDB.query(EVENTS_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();

        return newEvent;
    }

    /**
     * update existing Event in database
     *
     * @param idToUpdate
     * @param newName
     * @param newType
     * @param newNote
     * @param newDate
     * @return id of updated event in database
     */
    public long updateEvent(long idToUpdate,
                            String newName,
                            String newType,
                            String newNote,
                            String newDate) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_EVENTNAME, newName);
        values.put(COLUMN_EVENTTYPE, newType);
        values.put(COLUMN_EVENTNOTE, newNote);
        values.put(COLUMN_DATE, newDate);

        return sqlDB.update(EVENTS_TABLE, values, COLUMN_ID + " = " + idToUpdate, null);
    }

    /**
     * deleting Event from database
     *
     * @param id
     * @return id of deleted object
     */
    public long deleteEvent(long id) {
        return sqlDB.delete(EVENTS_TABLE, COLUMN_ID + " = " + id, null);
    }

    /**
     * when subject is deleted, all events are deleted too
     *
     * @param subject
     * @return
     */
    public long deleteAllEventsOfSubject(String subject) {
        String query = COLUMN_EVENTNAME + " = " + "\'" + subject + "\';";
        return sqlDB.delete(EVENTS_TABLE, query, null);
    }

    private static class EventsDBHelper extends SQLiteOpenHelper {

        EventsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create subjects table
            db.execSQL(CREATE_TABLE_EVENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(EventsDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + "\n Erasing data..");
            db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE);
            onCreate(db);
        }
    }
}
