package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import northeastern.is4300.pettasktracker.AddEditTaskActivity;
import northeastern.is4300.pettasktracker.GlobalVariables;
import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.JoinsRepository;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserRepository;

/**
 *
 */

public class TaskCursorAdapter extends CursorAdapter {

    private JoinsRepository joinsRepository;
    private UserRepository userRepository;
    private Context myContext;
    private long taskId;

    public TaskCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.myContext = context;
        joinsRepository = new JoinsRepository(context);
        joinsRepository.open();
        userRepository = new UserRepository(context);
        userRepository.open();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final View myView = view;

        TextView taskTitle = (TextView) view.findViewById(R.id.taskTitle);
        TextView taskTime = (TextView) view.findViewById(R.id.taskTime);
        final Button taskUserButton = (Button) view.findViewById(R.id.taskUserButton);

        final Task t = TaskRepository.cursorToTask(cursor);

        if (t != null) {

            User u = joinsRepository.getUserByTask(t);
            Pet p = joinsRepository.getPetByTask(t);

            String taskTitleString = TaskRepository.makeTaskTitle(p.getName(), t.getType());
            taskTitle.setText(taskTitleString);

            taskTime.setText(t.getTaskTime());

            taskUserButton.setText(u.getName());

        }

        taskUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(context, view);
                ArrayList<String> users = userRepository.getUserNamesList();
                for (String name : users) {
                    if (!name.equals(taskUserButton.getText())) {
                        menu.getMenu().add(name);
                    }
                }
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        joinsRepository.updateTaskUser(t, userRepository.getUserByName(menuItem.toString()));
                        notifyDataSetChanged();
                        return true;
                    }
                });
            }
        });

        ImageButton pencilButton = (ImageButton) view.findViewById(R.id.pencilButton);
        pencilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(myContext, AddEditTaskActivity.class);
                if (t != null) {
                    myIntent.putExtra(GlobalVariables.KEY_TASK_ID, t.getId());
                }
                myContext.startActivity(myIntent);
            }
        });
    }
}
