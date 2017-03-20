package com.ets.gd.Models;

/**
 * Created by hakhtar on 3/20/2017.
 * General Data
 */

public class Info {

    private String taskType;
    private String compName;

    public Info(String taskType, String compName) {
        this.taskType = taskType;
        this.compName = compName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
}
