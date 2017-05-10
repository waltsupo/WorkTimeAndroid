package com.waltsu.worktime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manager to help with backend.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class BackendManager {

    /**
     * Object containing data about tasks.
     */
    public static TaskManager taskManager;

    /**
     * Sets new taskManager.
     *
     * @param newManager new taskManager
     */
    public static void setTaskManager(TaskManager newManager) {
        taskManager = newManager;
    }

    /**
     * Creates tasks-object from GET-response.
     *
     * @param response Response as a string
     * @throws JSONException if response is invalid
     */
    public static void getTasks(JSONObject response) throws JSONException {
        JSONArray data = response.getJSONArray("data");
        taskManager.emptyTasks();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Task task = new Task();
            task.category = object.getString("category");
            task.description = object.getString("description");
            task.date = object.getString("date");
            task.hours = object.getString("hours");
            task.id = object.getInt("id");
            task.user = object.getString("user");

            taskManager.addTask(task);
        }
    }
}
