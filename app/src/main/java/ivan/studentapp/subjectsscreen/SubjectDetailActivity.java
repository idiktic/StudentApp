package ivan.studentapp.subjectsscreen;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ivan.studentapp.R;
import ivan.studentapp.mainscreen.MainActivity;

/**
 * activity that holds fragment to show details about subject
 *
 * @see SubjectViewFragment
 */

public class SubjectDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_detail_activity);

        createAndAddFragment();
    }

    /**
     * adding a fragment to activity
     */
    public void createAndAddFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SubjectViewFragment subjectViewFragment = new SubjectViewFragment();
        setTitle(R.string.subjects_details_title);
        fragmentTransaction.add(R.id.subjectDetailContainer, subjectViewFragment, "SUBJECT_VIEW_FRAGMENT");
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
