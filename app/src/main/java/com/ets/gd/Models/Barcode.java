package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/12/2017.
 * General Data
 */

public class Barcode {

    private String message;
    private String task;


    public Barcode() {
    }

    public Barcode(String message, String task) {
        this.message = message;
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
