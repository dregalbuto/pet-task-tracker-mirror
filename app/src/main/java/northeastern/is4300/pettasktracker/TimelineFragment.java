package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TimelineFragment extends Fragment {

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

        // TODO implement Tasks listView

        /* Set up Edit Task button */
        Button editTask1 = (Button) v.findViewById(R.id.pencil1);
        editTask1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), EditTaskActivity.class);
                startActivity(myIntent);
            }
        });

        /* Set up Edit Task button */
        Button editTask2 = (Button) v.findViewById(R.id.pencil2);
        editTask2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), EditTaskActivity.class);
                startActivity(myIntent);
            }
        });

        /* Set up Edit Task button */
        Button editTask3 = (Button) v.findViewById(R.id.pencil3);
        editTask3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), EditTaskActivity.class);
                startActivity(myIntent);
            }
        });

        /* Set up Edit Task button */
        Button editTask4 = (Button) v.findViewById(R.id.pencil4);
        editTask4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), EditTaskActivity.class);
                startActivity(myIntent);
            }
        });

        /* Set up Add Task button */
        // TODO button consistency
        Button addTaskButton = (Button) v.findViewById(R.id.button_add_task_main);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(myIntent);
            }
        });

        return v;
    }
}
