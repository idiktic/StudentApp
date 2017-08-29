package ivan.studentapp.todoscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ivan.studentapp.R;

public class TodoTaskAdapter extends ArrayAdapter<TodoTask> {

    private LayoutInflater inflater;

    public TodoTaskAdapter(Context context, int textViewResourceId,
                           List<TodoTask> objects) {
        super(context, textViewResourceId, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get to do task item from list
        final TodoTask todoTask = this.getItem(position);

        // inflate if not already
        if (convertView == null)
            convertView = this.inflater.inflate(R.layout.todo_task, null);

        final Context context = this.inflater.getContext();

        // init ui elements
        final TextView tvText = (TextView) convertView.findViewById(R.id.taskText);
        final TextView tvDateText = (TextView) convertView.findViewById(R.id.dateText);
        final TextView tvSubjectText = (TextView) convertView.findViewById(R.id.subjectText);
        final TextView tvCommentText = (TextView) convertView.findViewById(R.id.commentText);
        RelativeLayout rlTask = (RelativeLayout) convertView.findViewById(R.id.taskLayout);
        ImageView ivDone = (ImageView) convertView.findViewById(R.id.isDone);
        Button btnDelete = (Button) convertView.findViewById(R.id.buttonDelete);

        //set task data
        tvText.setText(todoTask.getText());
        tvDateText.setText(todoTask.getDate());
        if (todoTask.getDate() != null)

            if (todoTask.getSubject() != null)
                tvSubjectText.setText(todoTask.getSubject());
        if (todoTask.getComment() != null)
            tvCommentText.setText(todoTask.getComment());

        //change done/undone image
        if (todoTask.isDone()) {
            ivDone.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.done));
        } else {
            ivDone.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.undone));
        }

        ivDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                todoTask.setIsDone(!todoTask.isDone());
                notifyDataSetChanged();
            }
        });

        //change background color
        String s = todoTask.getPriority().toLowerCase();
        switch (s) {
            case "high":
                rlTask.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.high_priority));
                break;
            case "normal":
                rlTask.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.normal_priority));
                break;
            case "low":
                rlTask.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.low_priority));
                break;
        }


        //delete task
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                remove(todoTask);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                notifyDataSetChanged();
            }
        });

        // edit task
        rlTask.setOnLongClickListener(new View.OnLongClickListener() {
            //radi al ne brisanje nego treba edit!!!!!!!

            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                final View mView = LayoutInflater.from(context).inflate(R.layout.todo_dialog_edit, null);

                final EditText mEdit = (EditText) mView.findViewById(R.id.editTextTask);
                final EditText mDate = (EditText) mView.findViewById(R.id.editTextDate);
                final EditText mSubject = (EditText) mView.findViewById(R.id.editTextSubject);
                final EditText mComment = (EditText) mView.findViewById(R.id.editTextComment);

                Button mButton = (Button) mView.findViewById(R.id.saveButton);

                mEdit.setText(todoTask.getText());
                mDate.setText(todoTask.getDate());
                mSubject.setText(todoTask.getSubject());
                mComment.setText(todoTask.getComment());

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                // save edited task
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txt = mEdit.getText().toString();
                        String date = mDate.getText().toString();
                        String subject = mSubject.getText().toString();
                        String comment = mComment.getText().toString();

                        if (!txt.isEmpty()) {
                            todoTask.setText(txt);

                            todoTask.setDate(date);

                            todoTask.setSubject(subject);

                            todoTask.setComment(comment);
                            dialog.cancel();
                        } else
                            Toast.makeText(context, "Task name cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
        return convertView;
    }
}
