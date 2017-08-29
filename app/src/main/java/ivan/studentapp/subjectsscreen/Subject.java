package ivan.studentapp.subjectsscreen;

/**
 * class to hold Subject info
 */

public class Subject {
    private long _id;
    private String name;
    private String abbreviation;
    private String professor;
    private int classesNumber;
    private String color;

    public Subject() {
    }

    public Subject(String name, String abbreviation, String professor, int classesNumber, String color, long _id) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.professor = professor;
        this.classesNumber = classesNumber;
        this.color = color;
        this._id = _id;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getClassesNumber() {
        return classesNumber;
    }

    public void setClassesNumber(int classesNumber) {
        this.classesNumber = classesNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
