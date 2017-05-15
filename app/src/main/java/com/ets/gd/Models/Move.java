package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/12/2017.
 * General Data
 */

public class Move {


    private int locID;
    private int cuID;


    public Move() {
    }

    public Move(int locID, int cuID) {
        this.locID = locID;
        this.cuID = cuID;
    }

    public int getLocID() {
        return locID;
    }

    public void setLocID(int locID) {
        this.locID = locID;
    }

    public int getCuID() {
        return cuID;
    }

    public void setCuID(int cuID) {
        this.cuID = cuID;
    }
}
