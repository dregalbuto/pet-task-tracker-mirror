package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import northeastern.is4300.pettasktracker.adapters.TaskCursorAdapter;
import northeastern.is4300.pettasktracker.data.TaskLoader;
import northeastern.is4300.pettasktracker.data.TaskRepository;

public class TimelineFragment extends Fragment {

    private TaskRepository taskRepository;
    private TaskLoader taskLoader;

    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);

        taskRepository = new TaskRepository(this.getContext());
        taskRepository.open();

        if (taskRepository.getTaskList().size() == 0) {
            taskLoader = new TaskLoader(this.getContext());
            taskLoader.addSomeTasks();
        }

        Cursor taskCursor = taskRepository.getTasksCursor();

        final ListView listView = (ListView) v.findViewById(R.id.home_tasks_listview);

        final TaskCursorAdapter tasksAdapter = new TaskCursorAdapter(getActivity(), taskCursor);
        listView.setAdapter(tasksAdapter);
        tasksAdapter.changeCursor(taskCursor);

        Button addTaskButton = (Button) v.findViewById(R.id.button_home_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), AddEditTaskActivity.class);
                myIntent.putExtra(GlobalVariables.KEY_TASK_ID, -1);
                startActivity(myIntent);
            }
        });

        return v;
    }
}
