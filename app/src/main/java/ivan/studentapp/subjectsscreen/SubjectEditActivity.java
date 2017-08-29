package ivan.studentapp.subjectsscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ivan.studentapp.R;

/**
 * activity that holds fragment for editing subject
 * NOT IMPLEMENTED
 *
 * @see SubjectEditFragment
 */

public class SubjectEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_edit_activity);

        createEditFragment();
    }

    /**
     * adding a fragment to activity
     */
    public void createEditFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SubjectEditFragment subjectEditFragment = new SubjectEditFragment();
        setTitle(R.string.subject_edit_title);
        fragmentTransaction.add(R.id.subjectEditContainer, subjectEditFragment, "SUBJECT_EDIT_FRAGMENT");
        fragmentTransaction.commit();
    }
}
