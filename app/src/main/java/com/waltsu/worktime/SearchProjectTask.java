package com.waltsu.worktime;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Async-task for searching project with identifier.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class SearchProjectTask
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
     * Response object.
     */
    JSONObject response;

    /**
     * Project's identifier.
     */
    String identifier;

    /**
     * Defines values.
     *
     * @param activity Parent activity
     */
    public SearchProjectTask(Activity activity){
        this.activity = activity;
    }

    /**
     * Connects to backend and tries to find project with identifier.
     *
     * @param params Identifier as first element
     * @return httpURLConnection not used
     */
    @Override
    protected HttpURLConnection doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        if (params[0].equals("")) {
            code = -2;
            return null;
        }

        identifier = params[0];

        try {
            InputStream in = null;
            try {
                URL url = new URL(
                        "http://207.154.205.181/projects/" + identifier);
                HttpURLConnection conn
                        = (HttpURLConnection) url.openConnection();
                in = conn.getInputStream();

                int nextChar;
                String result = "";
                while((nextChar = in.read()) != -1) {
                    result += (char) nextChar;
                }


                response = new JSONObject(result);
                code = response.getInt("code");
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            code = 404;
        }

        return null;
    }

    /**
     * Navigates to project's addTask-activity or creates new project.
     *
     * @param httpURLConnection not used
     */
    @Override
    protected void onPostExecute(HttpURLConnection httpURLConnection) {
        super.onPostExecute(httpURLConnection);

        if (activity != null) {
            if (code == 200) {
                Intent i = new Intent(activity, AddTaskActivity.class);
                try {
                    JSONObject data
                            = response.getJSONArray("data").getJSONObject(0);
                    i.putExtra("id", data.getInt("id"));
                    i.putExtra("name", data.getString("identifier"));
                    Toast toast = Toast.makeText(activity,
                            "Project found", Toast.LENGTH_SHORT);
                    toast.show();

                    activity.startActivity(i);
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(activity,
                            "Something wrong with this project",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    System.out.println(e);
                }
            } else if (code == 404) {
                new AddProjectTask(activity).execute(identifier);
            } else if (code == 500) {
                Toast toast = Toast.makeText(activity,
                        "Error connecting to server", Toast.LENGTH_SHORT);
                toast.show();
            } else if (code == -2) {
                Toast toast = Toast.makeText(activity,
                        "Identifier can't be empty", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
