package ivan.studentapp.mainscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import ivan.studentapp.R;
import ivan.studentapp.deadlinesscreen.DeadlinesActivity;
import ivan.studentapp.subjectsscreen.SubjectsActivity;
import ivan.studentapp.todoscreen.toDoActivity;


/**
 * StudentApp main activity
 */

public class MainActivity extends Activity {


    Button buttonTodo, buttonSubjects, btnDeadlines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // init ui elements
        buttonTodo = (Button) findViewById(R.id.buttonTodo);
        buttonSubjects = (Button) findViewById(R.id.buttonSubjects);
        btnDeadlines = (Button) findViewById(R.id.btnDeadlines);

        // set listeners
        buttonTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goTodo();
            }
            });
        buttonSubjects.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goSubjects();
            }
            });
        btnDeadlines.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goDeadlines();
            }
            });

}

    /**
     * @see toDoActivity
     */
    public void goTodo(){
        Intent i = new Intent(getApplicationContext(), toDoActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * @see SubjectsActivity
     */
    public void goSubjects(){
        Intent i = new Intent(getApplicationContext(), SubjectsActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * @see DeadlinesActivity
     */
    public void goDeadlines(){
        Intent i = new Intent(getApplicationContext(), DeadlinesActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
}
