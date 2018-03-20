package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.JoinsRepository;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;
import northeastern.is4300.pettasktracker.data.User;

/**
 *
 */

public class TaskCursorAdapter extends CursorAdapter {

    private JoinsRepository joinsRepository;

    public TaskCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        joinsRepository = new JoinsRepository(context);
        joinsRepository.open();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView taskTitle = (TextView) view.findViewById(R.id.taskTitle);
        TextView taskTime = (TextView) view.findViewById(R.id.taskTime);
        Button taskUserButton = (Button) view.findViewById(R.id.taskUserButton);

        Task t = TaskRepository.cursorToTask(cursor);

        if (t != null) {

            User u = joinsRepository.getUserByTask(t);
            Pet p = joinsRepository.getPetByTask(t);

            String taskTitleString = TaskRepository.makeTaskTitle(p.getName(), t.getType());
            taskTitle.setText(taskTitleString);

            taskTime.setText(t.getTaskTime());

            taskUserButton.setText(u.getName());

        }
    }
}
