package ivan.studentapp.subjectsscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * adapter for Subject database handling
 */

public class SubjectsDBAdapter {

    // database info
    private static final String DATABASE_NAME = "subjects";
    private static final int DATABASE_VERSION = 1;

    // subjects database intern
    public static final String SUBJECTS_TABLE = "subjects";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECTNAME = "name";
    public static final String COLUMN_SUBJECTABB = "abbreviation";
    public static final String COLUMN_PROFESSOR = "professor";
    public static final String COLUMN_CLASSES_NUM = "classesNum";
    public static final String COLUMN_COLOR = "color";

    // string to hold list of all columns
    private String[] allColumns = {
            COLUMN_ID,
            COLUMN_SUBJECTNAME,
            COLUMN_SUBJECTABB,
            COLUMN_PROFESSOR,
            COLUMN_CLASSES_NUM,
            COLUMN_COLOR};

    // sql command for creating table
    public static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + SUBJECTS_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SUBJECTNAME + " text, "
            + COLUMN_SUBJECTABB + " text, "
            + COLUMN_PROFESSOR + " text, "
            + COLUMN_CLASSES_NUM + " integer, "
            + COLUMN_COLOR + " text"
            + ");";

    private SQLiteDatabase sqlDB;
    private Context context;
    private SubjectsDBHelper subjectsDBHelper;

    public SubjectsDBAdapter(Context ctx) {
        context = ctx;
    }

    /**
     * method for opening database we are about to manipulate with
     *
     * @return
     * @throws android.database.SQLException
     */
    public SubjectsDBAdapter openDatabase() throws android.database.SQLException {
        subjectsDBHelper = new SubjectsDBHelper(context);
        sqlDB = subjectsDBHelper.getWritableDatabase();
        return this;
    }

    /**
     * method for closing database
     */
    public void closeDatabase() {
        subjectsDBHelper.close();
    }

    /**
     * get all subjects from database
     *
     * @return array list of subjects for list adapter
     */
    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        //grab all the information from subjects database
        Cursor cursor = sqlDB.query(SUBJECTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Subject subject = cursorToSubject(cursor);
            subjects.add(subject);
        }

        cursor.close();
        return subjects;
    }

    /**
     * get abbreviations of all subjects from database
     *
     * @return array list of events on specific day for list adapter
     */
    public ArrayList<String> getAllSubjectsAbbs() {
        ArrayList<String> subjectsAbbs = new ArrayList<String>();

        //grab all the information from subjects database
        Cursor cursor = sqlDB.query(SUBJECTS_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            // grab only abbreviations
            String subjectAbb = cursorToSubjectAbbOnly(cursor);
            subjectsAbbs.add(subjectAbb);
        }

        cursor.close();
        return subjectsAbbs;
    }

    /**
     * create new subject in database
     *
     * @param name
     * @param abbreviation
     * @param professor
     * @param classesNumber
     * @param color
     * @return
     */
    public Subject createSubject(String name,
                                 String abbreviation,
                                 String professor,
                                 int classesNumber,
                                 String color) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_SUBJECTNAME, name);
        values.put(COLUMN_SUBJECTABB, abbreviation);
        values.put(COLUMN_PROFESSOR, professor);
        values.put(COLUMN_CLASSES_NUM, classesNumber);
        values.put(COLUMN_COLOR, color);

        long insertId = sqlDB.insert(SUBJECTS_TABLE, null, values);

        Cursor cursor = sqlDB.query(SUBJECTS_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Subject newSubject = cursorToSubject(cursor);
        cursor.close();

        return newSubject;
    }

    /**
     * update existing subject in database
     *
     * @param idToUpdate
     * @param newName
     * @param newAbbreviation
     * @param newProfessor
     * @param newClassesNumber
     * @param newColor
     * @return
     */
    public long updateSubject(long idToUpdate,
                              String newName,
                              String newAbbreviation,
                              String newProfessor,
                              int newClassesNumber,
                              String newColor) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_SUBJECTNAME, newName);
        values.put(COLUMN_SUBJECTABB, newAbbreviation);
        values.put(COLUMN_PROFESSOR, newProfessor);
        values.put(COLUMN_CLASSES_NUM, newClassesNumber);
        values.put(COLUMN_COLOR, newColor);

        return sqlDB.update(SUBJECTS_TABLE, values, COLUMN_ID + " = " + idToUpdate, null);
    }

    /**
     * delete subject from database
     *
     * @param id - id of subject deleted
     * @return
     */
    public long deleteSubject(long id) {
        return sqlDB.delete(SUBJECTS_TABLE, COLUMN_ID + " = " + id, null);
    }

    /**
     * convert cursor object to subject object
     *
     * @param cursor
     * @return new subject from cursor
     */
    private Subject cursorToSubject(Cursor cursor) {
        Subject newSubject = new Subject(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getLong(0));
        return newSubject;
    }

    /**
     * grab only abbreviations
     *
     * @param cursor
     * @return
     */
    private String cursorToSubjectAbbOnly(Cursor cursor) {
        Subject newSubject = new Subject(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getLong(0));
        return newSubject.getAbbreviation();
    }

    private static class SubjectsDBHelper extends SQLiteOpenHelper {

        SubjectsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create subjects table
            db.execSQL(CREATE_TABLE_SUBJECTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(SubjectsDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + "\n Erasing data..");
            db.execSQL("DROP TABLE IF EXISTS " + SUBJECTS_TABLE);
            onCreate(db);
        }
    }
}
