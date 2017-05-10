package com.waltsu.worktime;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Async-task for adding new tasks to database.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class AddTaskTask extends AsyncTask<Task, Integer, HttpURLConnection> {

    /**
     * Parent activity
     */
    Activity activity;

    /**
     * Response code
     */
    int code = -1;

    /**
     * Defines values
     *
     * @param activity Parent activity
     */
    public AddTaskTask(Activity activity){
        this.activity = activity;
    }

    /**
     * Connects to the backend and tries to add task
     *
     * @param params Task to add as first element
     * @return httpURLConnection not used
     */
    @Override
    protected HttpURLConnection doInBackground(Task... params) {
        if (params.length == 0) {
            return null;
        }
        Task task = params[0];

        if (!android.text.TextUtils.isDigitsOnly(task.hours)) {
            code = -2;
            return null;
        }
        try {
            InputStream in = null;
            try {
                URL url = new URL("http://207.154.205.181/tasks");
                HttpURLConnection conn
                        = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                DataOutputStream wr
                        = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes("desc=" + task.description
                        + "&hours="+ task.hours
                        + "&category="
                        + URLEncoder.encode(task.category, "UTF-8")
                        + "&user=" + task.user
                        + "&projectId="
                        + BackendManager.taskManager.getProject().id
                        + "&date=" + task.date);
                wr.flush();
                wr.close();

                in = conn.getInputStream();

                int nextChar;
                String result = "";
                while((nextChar = in.read()) != -1) {
                    result += (char) nextChar;
                }

                JSONObject response = new JSONObject(result);
                code = response.getInt("code");
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
     * Gives toast to user depending on response given by backend
     *
     * @param httpURLConnection not used
     */
    @Override
    protected void onPostExecute(HttpURLConnection httpURLConnection) {
        super.onPostExecute(httpURLConnection);

        if (activity != null) {
            if (code == 201) {
                Toast toast = Toast.makeText(activity,
                        "Task added", Toast.LENGTH_SHORT);
                toast.show();

                ((TextView) activity.findViewById(R.id.user)).setText("");
                ((TextView) activity.findViewById(R.id.category)).setText("");
                ((TextView) activity.findViewById(R.id.desc)).setText("");
                ((TextView) activity.findViewById(R.id.hours)).setText("");
            } else if (code == 400) {
                Toast toast = Toast.makeText(activity,
                        "Adding task failed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (code == 500) {
                Toast toast = Toast.makeText(activity,
                        "Error connecting to server", Toast.LENGTH_SHORT);
                toast.show();
            } else if (code == -2) {
                Toast toast = Toast.makeText(activity,
                        "Hours has to be number", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
