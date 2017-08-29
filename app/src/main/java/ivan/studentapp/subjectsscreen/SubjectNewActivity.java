package ivan.studentapp.subjectsscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ivan.studentapp.R;

/**
 * activity that holds fragment for creating new subject
 *
 * @see SubjectNewFragment
 */

public class SubjectNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_new_activity);

        createEditFragment();
    }

    /**
     * adding a fragment to activity
     */
    public void createEditFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SubjectNewFragment subjectNewFragment = new SubjectNewFragment();
        setTitle(R.string.subject_add_title);
        fragmentTransaction.add(R.id.subjectNewContainer, subjectNewFragment, "SUBJECT_EDIT_FRAGMENT");
        fragmentTransaction.commit();
    }

    /**
     * @see SubjectsActivity
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), SubjectsActivity.class);
        startActivity(i);
        finish();
    }
}
