package ivan.studentapp.deadlinesscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ivan.studentapp.R;

/**
 * adapter class for listing events
 */

public class EventsAdapter extends ArrayAdapter<Event> {

    private static class ViewHolder {
        TextView subject;
        TextView type;
        TextView date;
        TextView note;
    }

    public EventsAdapter(Context context,
                         ArrayList<Event> events) {
        super(context, 0, events);
    }

    // ui controls
    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get event item from list
        final Event event = getItem(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.deadlines_one_event, parent, false);

            // init ui elements
            viewHolder.subject = (TextView) convertView.findViewById(R.id.tv_deadlines_event_subject);
            viewHolder.type = (TextView) convertView.findViewById(R.id.tv_deadlines_event_type);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_deadlines_event_date);
            viewHolder.note = (TextView) convertView.findViewById(R.id.tv_deadlines_event_note);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setting values to ui elements
        viewHolder.subject.setText(event.getName());
        viewHolder.type.setText(event.getType());
        viewHolder.date.setText(event.getDate());
        viewHolder.note.setText(event.getNote());

        return convertView;
    }
}
