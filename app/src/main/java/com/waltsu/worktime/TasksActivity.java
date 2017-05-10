package com.waltsu.worktime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activity for viewing tasks.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class TasksActivity extends AppCompatActivity {

    /**
     * Object containing tasks.
     */
    public TaskManager taskManager;

    /**
     * Headers for task-list.
     */
    public List<String> headers;

    /**
     * Content for list.
     */
    public HashMap<String, List<String>> data;

    /**
     * List's adapter for adding data.
     */
    public ExpandableListAdapter listAdapter;

    /**
     * List's view.
     */
    public ExpandableListView expListView;

    /**
     * Setups view.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        taskManager = AddTaskActivity.taskManager;
        setTitle(taskManager.getProject().name);

        expListView = (ExpandableListView) findViewById(R.id.taskslist);

        prepareListData();
        listAdapter = new ExpandableListAdapter(this, headers, data);
        expListView.setAdapter(listAdapter);
    }

    /**
     * Fetches new tasks on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();

        new GetTasksTask(this).execute();
    }

    /**
     * Remakes headers and content.
     */
    public void prepareListData() {
        List<Task> tasks = taskManager.getTasks();
        headers = new ArrayList<String>();
        data = new HashMap<String, List<String>>();
        int index = 0;

        for (Task task : tasks) {
            headers.add(task.date + ", " + task.hours + "h by " + task.user);
            List<String> content = new ArrayList<>();
            content.add("Date: " + task.date);
            content.add("User: " + task.user);
            content.add("Category: " + task.category);
            content.add("Description: " + task.description);
            content.add("Hours: " + task.hours);
            data.put(headers.get(index), content);
            index++;
        }
    }
}
