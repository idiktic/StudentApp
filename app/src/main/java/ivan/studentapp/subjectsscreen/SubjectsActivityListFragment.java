package ivan.studentapp.subjectsscreen;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * fragment to show the list of all Subjects
 *
 * @see SubjectsActivity
 */

public class SubjectsActivityListFragment extends ListFragment {

    // all subjects from database
    private ArrayList<Subject> subjects;
    private SubjectsAdapter subjectsAdapter;
    int myPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get subjects
        getDataFromDatabase();

        // set adapter
        subjectsAdapter = new SubjectsAdapter(getActivity(), subjects);
        setListAdapter(subjectsAdapter);
    }

    /**
     * get all subjects from subjects database
     */
    private void getDataFromDatabase() {
        SubjectsDBAdapter dbAdapter = new SubjectsDBAdapter(getActivity().getBaseContext());
        dbAdapter.openDatabase();
        subjects = dbAdapter.getAllSubjects();
        dbAdapter.closeDatabase();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchDetailActivity(position);
        myPosition = position;
    }

    /**
     * launch activity with event details
     *
     * @param position - position of clicked subject in the list
     * @see SubjectDetailActivity
     */
    public void launchDetailActivity(int position) {
        Subject subject = (Subject) getListAdapter().getItem(position);

        Intent DetailActivityIntent = new Intent(getActivity(), SubjectDetailActivity.class);

        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_NAME, subject.getName());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_ABB, subject.getAbbreviation());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_PROF, subject.getProfessor());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_CLASSES, subject.getClassesNumber());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_COLOR, subject.getColor());
        DetailActivityIntent.putExtra(SubjectsActivity.SUBJECT_ID, subject.getId());

        startActivity(DetailActivityIntent);
    }
}
