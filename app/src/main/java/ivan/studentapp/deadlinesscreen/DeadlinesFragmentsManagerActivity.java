package ivan.studentapp.deadlinesscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ivan.studentapp.R;
import ivan.studentapp.subjectsscreen.Subject;
import ivan.studentapp.subjectsscreen.SubjectDetailActivity;
import ivan.studentapp.subjectsscreen.SubjectsActivity;

/**
 * activity to manage which fragment to open
 */

public class DeadlinesFragmentsManagerActivity extends AppCompatActivity {

    // bundle to save extras from intent
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadlines_new_activity);
        bundle = getIntent().getExtras();
        createEditFragment();
    }

    /**
     * creating and commiting a fragment
     */
    public void createEditFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DeadlinesNewFragment deadlinesNewFragment = new DeadlinesNewFragment();

        setTitle(R.string.new_event_title);
        fragmentTransaction.add(R.id.deadlines_new_container, deadlinesNewFragment, "DEADLINES_NEW_FRAGMENT");
        fragmentTransaction.commit();
    }

    /**
     * adding data to intent
     *
     * @param intent - intent to fill with data
     */
    public void addEventContentToIntent(Intent intent) {
        intent.putExtra(DeadlinesActivity.EVENT_SUBJECT, bundle.getString(DeadlinesActivity.EVENT_SUBJECT));
        intent.putExtra(DeadlinesActivity.EVENT_TYPE, bundle.getString(DeadlinesActivity.EVENT_TYPE));
        intent.putExtra(DeadlinesActivity.EVENT_NOTE, bundle.getString(DeadlinesActivity.EVENT_NOTE));
        intent.putExtra(DeadlinesActivity.EVENT_DATE, bundle.getString(DeadlinesActivity.EVENT_DATE));
        intent.putExtra(DeadlinesActivity.EVENT_ID, bundle.getString(DeadlinesActivity.EVENT_ID));
    }

    /**
     * adding data to intent
     *
     * @param intent - intent to fill with data
     */
    public void addSubjectContentToIntent(Intent intent) {
        intent.putExtra(SubjectsActivity.SUBJECT_NAME, bundle.getString(SubjectsActivity.SUBJECT_NAME));
        intent.putExtra(SubjectsActivity.SUBJECT_ABB, bundle.getString(SubjectsActivity.SUBJECT_ABB));
        intent.putExtra(SubjectsActivity.SUBJECT_PROF, bundle.getString(SubjectsActivity.SUBJECT_PROF));
        intent.putExtra(SubjectsActivity.SUBJECT_CLASSES, bundle.getString(SubjectsActivity.SUBJECT_CLASSES));
        intent.putExtra(SubjectsActivity.SUBJECT_COLOR, bundle.getString(SubjectsActivity.SUBJECT_COLOR));
        intent.putExtra(SubjectsActivity.SUBJECT_ID, bundle.getLong(SubjectsActivity.SUBJECT_ID));
    }

    /**
     * @see DeadlinesEvents
     * @see EventDetailActivity
     * @see SubjectDetailActivity
     * @see DeadlinesActivity
     */
    @Override
    public void onBackPressed() {
        Intent intent;

        if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).equals("all events empty")) {
            intent = new Intent(getApplicationContext(), DeadlinesEvents.class);
        } else if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).equals("event details full")) {
            intent = new Intent(getApplicationContext(), EventDetailActivity.class);
            addEventContentToIntent(intent);
        } else if (bundle.getString(DeadlinesActivity.BUNDLE_FROM).equals("subjects with subject abb")) {
            intent = new Intent(getApplicationContext(), SubjectDetailActivity.class);
            addSubjectContentToIntent(intent);
        } else {
            intent = new Intent(getApplicationContext(), DeadlinesActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
