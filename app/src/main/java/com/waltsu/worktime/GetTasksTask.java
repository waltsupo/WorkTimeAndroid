package com.waltsu.worktime;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Async-task for getting tasks from database.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class GetTasksTask extends AsyncTask<URL, Integer, HttpURLConnection> {

    /**
     * Parent activity.
     */
    Activity activity;

    /**
     * Defines values.
     *
     * @param activity parent activity
     */
    public GetTasksTask(Activity activity){
        this.activity = activity;
    }

    /**
     * Connects to backend and tries to get tasks.
     *
     * @param params not used
     * @return not used
     */
    @Override
    protected HttpURLConnection doInBackground(URL... params) {
        try {
            InputStream in = null;
            try {
                URL url = new URL("http://207.154.205.181/tasks/"
                        + BackendManager.taskManager.getProject().id);
                HttpURLConnection conn
                        = (HttpURLConnection) url.openConnection();
                in = conn.getInputStream();

                int nextChar;
                String result = "";
                while((nextChar = in.read()) != -1) {
                    result += (char) nextChar;
                }

                JSONObject response = new JSONObject(result);
                BackendManager.getTasks(response);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Modifies UI to show correct tasks.
     *
     * @param httpURLConnection not used
     */
    @Override
    protected void onPostExecute(HttpURLConnection httpURLConnection) {
        super.onPostExecute(httpURLConnection);

        if (activity != null) {
            TasksActivity tasksActivity = (TasksActivity) activity;
            tasksActivity.prepareListData();
            tasksActivity.listAdapter
                    = new ExpandableListAdapter(tasksActivity,
                    tasksActivity.headers,
                    tasksActivity.data);
            tasksActivity.expListView.setAdapter(tasksActivity.listAdapter);
        }
    }
}
