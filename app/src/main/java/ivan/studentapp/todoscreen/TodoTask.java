package ivan.studentapp.todoscreen;

/**
 * class to hold to do task data
 */

public class TodoTask {
    private String text;
    private Boolean isDone;
    private String date;
    private String subject;
    private String comment;
    private String priority;

    public TodoTask(String text, Boolean isDone, String date, String subject, String comment, String priority) {
        super();
        this.text = text;
        this.isDone = isDone;
        this.date = date;
        this.subject = subject;
        this.comment = comment;
        this.priority = priority;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", this.text, this.isDone, this.date, this.subject, this.comment, this.priority);
    }


}
