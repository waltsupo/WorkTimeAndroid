package com.waltsu.worktime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Activity for joining or creating project.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class ProjectActivity extends AppCompatActivity {

    /**
     * Setups activity.
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
    }

    /**
     * Joins or creates a project depending on if project already exists.
     *
     * @param view that activated method
     */
    public void selectProject(View view) {
        String identifier = ((TextView) findViewById(R.id.projectIdentifier))
                .getText().toString();
        new SearchProjectTask(ProjectActivity.this).execute(identifier);
    }
}
