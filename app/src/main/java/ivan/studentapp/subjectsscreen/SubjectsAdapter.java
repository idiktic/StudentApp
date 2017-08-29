package ivan.studentapp.subjectsscreen;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ivan.studentapp.R;

/**
 * adapter class for listing subjects
 */

public class SubjectsAdapter extends ArrayAdapter<Subject> {

    private static class ViewHolder {
        TextView name;
        TextView abb;
        TextView color;
    }

    public SubjectsAdapter(Context context,
                           ArrayList<Subject> subjects) {
        super(context, 0, subjects);
    }

    // ui controls
    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get subject item from list
        final Subject subject = getItem(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.subjects_one_subject, parent, false);

            // init ui elements
            viewHolder.color = (TextView) convertView.findViewById(R.id.tv_color);
            viewHolder.abb = (TextView) convertView.findViewById(R.id.tv_abbreviation);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setting values to ui elements
        viewHolder.abb.setText(subject.getAbbreviation());
        viewHolder.name.setText(subject.getName());
        setSubjectColor(subject.getColor());

        return convertView;
    }

    /**
     * selecting color from database
     *
     * @param color - string from database
     */
    public void setSubjectColor(String color) {
        switch (color) {
            case "black":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_black));
                break;
            case "blue":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_blue));
                break;
            case "blueDark":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_bluedark));
                break;
            case "blueDarkest":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_bluedarkest));
                break;
            case "green":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_green));
                break;
            case "greenDark":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_greendark));
                break;
            case "greenDarkest":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_greendarkest));
                break;
            case "grey":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_grey));
                break;
            case "indigo":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_indigo));
                break;
            case "orange":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_orange));
                break;
            case "purple":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_purple));
                break;
            case "purpleDark":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_purpledark));
                break;
            case "red":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_red));
                break;
            case "white":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_white));
                break;
            case "yellow":
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_yellow));
                break;
            default:
                viewHolder.color.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle));
                break;
        }
    }
}
