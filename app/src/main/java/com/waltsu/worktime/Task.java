package com.waltsu.worktime;

/**
 * Task-object representing task-table row in database.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class Task {

    /**
     * Task's id.
     */
    public int id;

    /**
     * Description of the task.
     */
    public String description;

    /**
     * When task was done.
     */
    public String date;

    /**
     * By who task was done.
     */
    public String user;

    /**
     * How many hours did task take.
     */
    public String hours;

    /**
     * Task's category.
     */
    public String category;

    /**
     * Defines values to default.
     */
    public Task() {
        id = 0;
        description = "";
        date = "";
        user = "";
        hours = "";
        category = "";
    }

    /**
     * Defines values.
     *
     * @param id id of the task
     * @param date date when was completed
     * @param user done by who
     * @param category what category does task belong
     * @param description What kind of task
     * @param hours How long did it take
     */
    public Task(int id, String date, String user, String category,
                String description, String hours) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.user = user;
        this.hours = hours;
        this.category = category;
    }
}
