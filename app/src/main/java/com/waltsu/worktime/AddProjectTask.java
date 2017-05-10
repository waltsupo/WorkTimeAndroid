package com.waltsu.worktime;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Async-task for adding new project to database.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class AddProjectTask
        extends AsyncTask<String, Integer, HttpURLConnection> {

    /**
     * Parent activity.
     */
    Activity activity;

    /**
     * Response code.
     */
    int code = -1;

    /**
     * Project's identifier.
     */
    String identifier;

    /**
     * Project's id.
     */
    int newId;

    /**
     * Defines values.
     *
     * @param activity Parent activity
     */
    public AddProjectTask(Activity activity){
        this.activity = activity;
    }

    /**
     * Connects to the backend and tries to add new project.
     *
     * @param params Array containing new identifier as first element
     * @return httpURLConnection, not used
     */
    @Override
    protected HttpURLConnection doInBackground(String... params) {

        identifier = params[0];
        try {
            InputStream in = null;
            try {
                URL url = new URL("http://207.154.205.181/projects");
                HttpURLConnection conn
                        = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                DataOutputStream wr
                        = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes("name=Project&date=" + df.format(new Date())
                        + "&identifier=" + identifier);
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
                newId = response.getJSONObject("data").getInt("id");
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            code = 400;
        }

        return null;
    }

    /**
     * Shows toasts or moves to new activity.
     *
     * @param httpURLConnection not used
     */
    @Override
    protected void onPostExecute(HttpURLConnection httpURLConnection) {
        super.onPostExecute(httpURLConnection);

        if (activity != null) {
            if (code == 201) {
                Toast toast = Toast.makeText(activity,
                        "Project created", Toast.LENGTH_SHORT);
                toast.show();

                Intent i = new Intent(activity, AddTaskActivity.class);
                i.putExtra("id", newId);
                i.putExtra("name", identifier);
                activity.startActivity(i);
            } else if (code == 400) {
                Toast toast = Toast.makeText(activity,
                        "Invalid identifier", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
