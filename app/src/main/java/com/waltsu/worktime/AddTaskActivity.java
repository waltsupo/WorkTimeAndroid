package com.waltsu.worktime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity for adding new tasks or navigating to tasks-activity.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class AddTaskActivity extends AppCompatActivity {

    /**
     * TaskManager that contains all information
     */
    public static TaskManager taskManager;

    /**
     * Setups activity.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskManager = new TaskManager(
                new Project(getIntent().getExtras().getInt("id"),
                getIntent().getExtras().getString("name")));
        BackendManager.setTaskManager(taskManager);
        setTitle(taskManager.getProject().name);
    }

    /**
     * Adds new task to current project.
     *
     * @param view View that activated method
     */
    public void addTask(View view) {
        String user
                = ((TextView) findViewById(R.id.user)).getText().toString();
        String category
                = ((TextView) findViewById(R.id.category)).getText().toString();
        String desc
                = ((TextView) findViewById(R.id.desc)).getText().toString();
        String hours
                = ((TextView) findViewById(R.id.hours)).getText().toString();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Task task = new Task(-1, df.format(new Date()),
                user, category, desc, hours);
        new AddTaskTask(this).execute(task);
    }

    /**
     * Navigates to tasks-activity.
     *
     * @param view View that activated method
     */
    public void seeTasks(View view) {
        Intent i = new Intent(AddTaskActivity.this, TasksActivity.class);
        startActivity(i);
    }
}
