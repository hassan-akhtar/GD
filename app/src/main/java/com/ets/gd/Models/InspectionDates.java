package com.ets.gd.Models;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 3/27/2017.
 * General Data
 */

public class InspectionDates  extends RealmObject implements Serializable {
    private  String daily;
    private  String weekly;
    private  String monthly;
    private  String quaterly;
    private  String semiAnnual;
    private  String annual;
    private  String fiveYear;
    private  String sixYear;
    private  String tenYear;
    private  String twelveYear;

    public InspectionDates(){

    }

    public InspectionDates(String daily, String weekly, String monthly, String quaterly, String semiAnnual, String annual, String fiveYear, String sixYear, String tenYear, String twelveYear) {
        this.daily = daily;
        this.weekly = weekly;
        this.monthly = monthly;
        this.quaterly = quaterly;
        this.semiAnnual = semiAnnual;
        this.annual = annual;
        this.fiveYear = fiveYear;
        this.sixYear = sixYear;
        this.tenYear = tenYear;
        this.twelveYear = twelveYear;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getQuaterly() {
        return quaterly;
    }

    public void setQuaterly(String quaterly) {
        this.quaterly = quaterly;
    }

    public String getSemiAnnual() {
        return semiAnnual;
    }

    public void setSemiAnnual(String semiAnnual) {
        this.semiAnnual = semiAnnual;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getFiveYear() {
        return fiveYear;
    }

    public void setFiveYear(String fiveYear) {
        this.fiveYear = fiveYear;
    }

    public String getSixYear() {
        return sixYear;
    }

    public void setSixYear(String sixYear) {
        this.sixYear = sixYear;
    }

    public String getTenYear() {
        return tenYear;
    }

    public void setTenYear(String tenYear) {
        this.tenYear = tenYear;
    }

    public String getTwelveYear() {
        return twelveYear;
    }

    public void setTwelveYear(String twelveYear) {
        this.twelveYear = twelveYear;
    }
}
