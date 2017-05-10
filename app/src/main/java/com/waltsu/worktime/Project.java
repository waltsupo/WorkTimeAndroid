package com.waltsu.worktime;

/**
 * Project-object representing project-table row in database.
 *
 * @author Valtteri Poutanen valtteri.poutanen@hotmail.com
 * @version 2017.0511
 * @since 1.8
 */
public class Project {

    /**
     * Project's id
     */
    public int id;

    /**
     * Project's name
     */
    public String name;

    /**
     * Defines default values.
     */
    public Project() {
        id = 0;
        name = "";
    }

    /**
     * Defines values.
     *
     * @param id project's id
     * @param name project's name
     */
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
