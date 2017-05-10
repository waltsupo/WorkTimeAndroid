package com.waltsu.worktime;

import java.util.ArrayList;
import java.util.List;

/**
 * Object containing task and project- information
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class TaskManager {

    /**
     * Current project.
     */
    private Project project;

    /**
     * Current project's tasks.
     */
    private List<Task> tasks;

    /**
     * Defines values.
     *
     * @param project project
     */
    public TaskManager(Project project) {
        this.project = project;
        tasks = new ArrayList<>();
    }

    /**
     * Returns current project.
     *
     * @return current project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Add's task to current project
     *
     * @param task new task
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Return's tasks
     *
     * @return current project's tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Empties tasks.
     */
    public void emptyTasks() { tasks.clear(); }
}
