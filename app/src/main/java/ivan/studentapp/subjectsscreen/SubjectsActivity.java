package ivan.studentapp.subjectsscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ivan.studentapp.R;
import ivan.studentapp.mainscreen.MainActivity;

/**
 * activity that holds fragment with list of subjects
 *
 * @see SubjectsActivityListFragment
 */
public class SubjectsActivity extends AppCompatActivity {

    // store intent extras
    public static final String SUBJECT_NAME = "ivan.studentapp.subjectsscreen.Subject Name";
    public static final String SUBJECT_ABB = "ivan.studentapp.subjectsscreen.Subject Abb";
    public static final String SUBJECT_PROF = "ivan.studentapp.subjectsscreen.Subject Prof";
    public static final String SUBJECT_CLASSES = "ivan.studentapp.subjectsscreen.Subject Classes";
    public static final String SUBJECT_COLOR = "ivan.studentapp.subjectsscreen.Subject Color";
    public static final String SUBJECT_ID = "ivan.studentapp.subjectsscreen.Subject Id";
    public static final String BUNDLE_FROM = "ivan.studentapp.subjectsscreen.subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment included in xml
        setContentView(R.layout.subjects_activity_main);

        // setting title
        setTitle(R.string.subjects_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflate menu
        inflater.inflate(R.menu.subjects_options_menu, menu);

        // visible
        menu.findItem(R.id.add).setVisible(true);

        // invisible
        menu.findItem(R.id.save).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.delete).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // start activity for creating subject
            case R.id.add:
                Intent i = new Intent(getApplicationContext(), SubjectNewActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @see MainActivity
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
