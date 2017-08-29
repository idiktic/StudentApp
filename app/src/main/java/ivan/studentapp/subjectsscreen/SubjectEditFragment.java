package ivan.studentapp.subjectsscreen;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import ivan.studentapp.R;

/**
 * fragment for editing subject
 * NOT IMPLEMENTED
 *
 * @see SubjectEditActivity
 */
public class SubjectEditFragment extends Fragment implements View.OnClickListener {

    Intent intent;
    Subject subject;
    // view for this fragment
    View viewFragment;

    //UI controls
    TextView tvAbb, tvName, tvProf;
    AlertDialog dialog;

    EditText etName, etAbb, etProf, etClass;
    Button btnColor;
    String color;
    long id;

    public SubjectEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        intent = getActivity().getIntent();

        // getting subject data from intent
        fetchSubjectData();

        // initialize user controls
        initUiControls(inflater, container);

        return viewFragment;
    }

    /**
     * filling created subject object with info passed from intent extras
     */
    private void fetchSubjectData() {
        subject = new Subject();

        subject.setName(intent.getExtras().getString(SubjectsActivity.SUBJECT_NAME));
        subject.setAbbreviation(intent.getExtras().getString(SubjectsActivity.SUBJECT_ABB));
        subject.setProfessor(intent.getExtras().getString(SubjectsActivity.SUBJECT_PROF));
        subject.setClassesNumber(intent.getExtras().getInt(SubjectsActivity.SUBJECT_CLASSES));
        subject.setColor(intent.getExtras().getString(SubjectsActivity.SUBJECT_COLOR));
        subject.setId(intent.getExtras().getLong(SubjectsActivity.SUBJECT_ID));
    }

    /**
     * inflate the layout for this fragment
     *
     * @param inflater
     * @param container
     */
    private void initUiControls(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.subject_edit_fragment, container, false);

        //initializing ui controls
        etName = (EditText) viewFragment.findViewById(R.id.subject_edit_name_et);
        etAbb = (EditText) viewFragment.findViewById(R.id.subject_edit_abbreviation_et);
        etProf = (EditText) viewFragment.findViewById(R.id.subject_edit_professor_et);
        etClass = (EditText) viewFragment.findViewById(R.id.subject_edit_classes_et);
        btnColor = (Button) viewFragment.findViewById(R.id.subject_edit_color_btn);
        btnColor.setOnClickListener(this);

        //filling widgets with information about subject to edit
        etName.setText(subject.getName());
        etAbb.setText(subject.getAbbreviation());
        etProf.setText(subject.getProfessor());
        etClass.setText("" + subject.getClassesNumber());
        color = subject.getColor();
        id = subject.getId();

        setColorPickerButtonColor(color);
    }


    public void setColorPickerButtonColor(String color) {
        //selecting color based on data from subject to edit
        switch (color) {
            case "black":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_black));
                break;
            case "blue":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_blue));
                break;
            case "blueDark":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_bluedark));
                break;
            case "blueDarkest":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_bluedarkest));
                break;
            case "green":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_green));
                break;
            case "greenDark":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_greendark));
                break;
            case "greenDarkest":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_greendarkest));
                break;
            case "grey":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_grey));
                break;
            case "indigo":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_indigo));
                break;
            case "orange":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_orange));
                break;
            case "purple":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_purple));
                break;
            case "purpleDark":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_purpledark));
                break;
            case "red":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_red));
                break;
            case "white":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_white));
                break;
            case "yellow":
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle_yellow));
                break;
            default:
                btnColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_circle));
                break;
        }
    }

    public void showColorPicker() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.subject_color_picker, null);

        Button colorBlack = (Button) mView.findViewById(R.id.black);
        colorBlack.setOnClickListener(this);
        Button colorBlue = (Button) mView.findViewById(R.id.blue);
        colorBlue.setOnClickListener(this);
        Button colorBlueDark = (Button) mView.findViewById(R.id.blue_dark);
        colorBlueDark.setOnClickListener(this);
        Button colorBlueDarkest = (Button) mView.findViewById(R.id.blue_darkest);
        colorBlueDarkest.setOnClickListener(this);
        Button colorGreen = (Button) mView.findViewById(R.id.green);
        colorGreen.setOnClickListener(this);
        Button colorGreenDark = (Button) mView.findViewById(R.id.green_dark);
        colorGreenDark.setOnClickListener(this);
        Button colorGreenDarkest = (Button) mView.findViewById(R.id.green_darkest);
        colorGreenDarkest.setOnClickListener(this);
        Button colorGrey = (Button) mView.findViewById(R.id.grey);
        colorGrey.setOnClickListener(this);
        Button colorIndigo = (Button) mView.findViewById(R.id.indigo);
        colorIndigo.setOnClickListener(this);
        Button colorOrange = (Button) mView.findViewById(R.id.orange);
        colorOrange.setOnClickListener(this);
        Button colorPurple = (Button) mView.findViewById(R.id.purple);
        colorPurple.setOnClickListener(this);
        Button colorPurpleDark = (Button) mView.findViewById(R.id.purple_dark);
        colorPurpleDark.setOnClickListener(this);
        Button colorRed = (Button) mView.findViewById(R.id.red);
        colorRed.setOnClickListener(this);
        Button colorWhite = (Button) mView.findViewById(R.id.white);
        colorWhite.setOnClickListener(this);
        Button colorYellow = (Button) mView.findViewById(R.id.yellow);
        colorYellow.setOnClickListener(this);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subject_edit_color_btn:
                showColorPicker();
                break;
            case R.id.black:
                dialog.cancel();
                color = "black";
                setColorPickerButtonColor(color);
                break;
            case R.id.blue:
                dialog.cancel();
                color = "blue";
                setColorPickerButtonColor(color);
                break;
            case R.id.blue_dark:
                dialog.cancel();
                color = "blueDark";
                setColorPickerButtonColor(color);
                break;
            case R.id.blue_darkest:
                dialog.cancel();
                color = "blueDarkest";
                setColorPickerButtonColor(color);
                break;
            case R.id.green:
                dialog.cancel();
                color = "green";
                setColorPickerButtonColor(color);
                break;
            case R.id.green_dark:
                dialog.cancel();
                color = "greenDark";
                setColorPickerButtonColor(color);
                break;
            case R.id.green_darkest:
                dialog.cancel();
                color = "greenDarkest";
                setColorPickerButtonColor(color);
                break;
            case R.id.grey:
                dialog.cancel();
                color = "grey";
                setColorPickerButtonColor(color);
                break;
            case R.id.indigo:
                dialog.cancel();
                color = "indigo";
                setColorPickerButtonColor(color);
                break;
            case R.id.orange:
                dialog.cancel();
                color = "orange";
                setColorPickerButtonColor(color);
                break;
            case R.id.purple:
                dialog.cancel();
                color = "purple";
                setColorPickerButtonColor(color);
                break;
            case R.id.purple_dark:
                dialog.cancel();
                color = "purpleDark";
                setColorPickerButtonColor(color);
                break;
            case R.id.red:
                dialog.cancel();
                color = "red";
                setColorPickerButtonColor(color);
                break;
            case R.id.white:
                dialog.cancel();
                color = "white";
                setColorPickerButtonColor(color);
                break;
            case R.id.yellow:
                dialog.cancel();
                color = "yellow";
                setColorPickerButtonColor(color);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.subjects_options_menu, menu);
        menu.findItem(R.id.save).setVisible(true);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.delete).setVisible(false);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveEditedSubject();
                startSubjectListActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveEditedSubject() {
        String name, abb, prof;
        int classes;
        name = etName.getText().toString();
        abb = etAbb.getText().toString();
        prof = etProf.getText().toString();
        if (etClass.getText().toString().equals(""))
            classes = 0;
        else classes = Integer.parseInt(etClass.getText().toString());

        SubjectsDBAdapter subjectsDBAdapter = new SubjectsDBAdapter(getActivity().getBaseContext());
        subjectsDBAdapter.openDatabase();
        subjectsDBAdapter.updateSubject(id, name, abb, prof, classes, color);
        subjectsDBAdapter.closeDatabase();
    }

    public void startSubjectListActivity() {
        Intent intent = new Intent(getContext(), SubjectsActivity.class);
        startActivity(intent);
    }
}
