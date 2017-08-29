package ivan.studentapp.deadlinesscreen;

/**
 * class to hold data for one event
 * _name - subject abbreviation
 * _type - hardcoded types (Exam, Homework, Seminar, Other)
 * _note - custom comment about event
 * _date - date
 */

public class Event {
    private long _id;
    private String _name;
    private String _type;
    private String _note;
    private String _date;

    public Event() {
    }

    public Event(String name, String type, String note, String date, long id) {
        this._name = name;
        this._type = type;
        this._note = note;
        this._date = date;
        this._id = id;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }


    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String note) {
        this._note = note;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        this._date = date;
    }

}
