package ivan.studentapp.todoscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import butterknife.OnClick;
import ivan.studentapp.mainscreen.MainActivity;
import ivan.studentapp.R;

/**
 * activity to show list of to do tasks
 * loaded from text file
 */

public class toDoActivity extends Activity {

    // list of tasks
    private ToDoList tasks;
    // adapter for tasks
    private TodoTaskAdapter tasksAdapter;

    // set folder name
    private final String _folderName = "Tasks";

    // list to show to do tasks
    ListView list;

    // priority of task
    String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo);

        list = (ListView) findViewById(R.id.listTodo);

        tasks = new ToDoList();
        tasksAdapter = new TodoTaskAdapter(this, R.id.editTextTask, tasks);
        list.setAdapter(tasksAdapter);
        loadData();
    }

    @OnClick (R.id.buttonPlus)
    public void showDialog(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(toDoActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.todo_dialog_add, null);

        // init ui elements
        Button mButton = (Button) mView.findViewById(R.id.addButton);
        final EditText mEdit = (EditText) mView.findViewById(R.id.editTextTask);
        final EditText mDate = (EditText) mView.findViewById(R.id.editTextDate);
        final EditText mSubject = (EditText) mView.findViewById(R.id.editTextSubject);
        final EditText mComment = (EditText) mView.findViewById(R.id.editTextComment);

        // setting and showing dialog
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        final RadioGroup radio = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        radio.check(R.id.radioButton2);
        priority = "normal";

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);

                // check which radio button was clicked
                switch(radioButton.getId()) {
                    case R.id.radioButton1:
                        priority = "high";
                        break;
                    case R.id.radioButton2:
                        priority = "normal";
                        break;
                    case R.id.radioButton3:
                        priority = "low";
                        break;
                }
            }
        });

        // add button click listener
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String txt = mEdit.getText().toString();
                String date = mDate.getText().toString();
                String subject = mSubject.getText().toString();
                String comment = mComment.getText().toString();

                if(!txt.isEmpty()){
                    // if all other empty
                    if(date.isEmpty() && subject.isEmpty() && comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, date, subject, comment, priority));
                    // all empty but date
                    if(!date.isEmpty() && subject.isEmpty() && comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, date, "", "", priority));
                    // all empty but subject
                    if(date.isEmpty() && !subject.isEmpty() && comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, "", subject, "", priority));
                    // all empty but comment
                    if(date.isEmpty() && subject.isEmpty() && !comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, "", "", comment, priority));
                    // only comment empty
                    if(!date.isEmpty() && !subject.isEmpty() && comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, date, subject, "", priority));
                    // only date empty
                    if(date.isEmpty() && !subject.isEmpty() && !comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, "", subject, comment, priority));
                    // only subject empty
                    if(!date.isEmpty() && subject.isEmpty() && !comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, date, "", comment, priority));
                    // all filled
                    if(!date.isEmpty() && !subject.isEmpty() && !comment.isEmpty())
                        tasksAdapter.add(new TodoTask(txt, false, date, subject, comment, priority));

                    Toast.makeText(toDoActivity.this, "Successfully added!", Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                }
                else {
                    Toast.makeText(toDoActivity.this, "Input not valid", Toast.LENGTH_SHORT).show();
                }

                mEdit.setText("");
                mSubject.setText("");
                mComment.setText("");
            }
        });
    }


    /**
     * load data from text file
     * @see ToDoList
     */
    public void loadData() {
        try {
            tasks.LoadFromFile(_folderName);
        } catch (IOException e) {
            // todo_subjectsscreen Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * on pause save everything
     */
    protected void onPause() {
        try {
            tasks.SaveToFile(_folderName);
        } catch (IOException e) {
            // todo_subjectsscreen Auto-generated catch block
            e.printStackTrace();
        }
        super.onPause();
    }

    /**
     * @see MainActivity
     */
    @Override
    public void onBackPressed(){
        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }










}
