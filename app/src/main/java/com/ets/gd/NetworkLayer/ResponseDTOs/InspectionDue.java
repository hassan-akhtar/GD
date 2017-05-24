package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/23/2017.
 * General Data
 */

public class InspectionDue extends RealmObject {

    private int Daily;
    private int Weekly;
    private int Monthly;
    private int Quarterly;
    private int SemiAnnual;
    private int Annual;
    private int FiveYears;
    private int SixYears;
    private int TenYears;
    private int TwelveYears;

    public int getDaily() {
        return Daily;
    }

    public void setDaily(int daily) {
        Daily = daily;
    }

    public int getWeekly() {
        return Weekly;
    }

    public void setWeekly(int weekly) {
        Weekly = weekly;
    }

    public int getMonthly() {
        return Monthly;
    }

    public void setMonthly(int monthly) {
        Monthly = monthly;
    }

    public int getQuarterly() {
        return Quarterly;
    }

    public void setQuarterly(int quarterly) {
        Quarterly = quarterly;
    }

    public int getSemiAnnual() {
        return SemiAnnual;
    }

    public void setSemiAnnual(int semiAnnual) {
        SemiAnnual = semiAnnual;
    }

    public int getAnnual() {
        return Annual;
    }

    public void setAnnual(int annual) {
        Annual = annual;
    }

    public int getFiveYears() {
        return FiveYears;
    }

    public void setFiveYears(int fiveYears) {
        FiveYears = fiveYears;
    }

    public int getSixYears() {
        return SixYears;
    }

    public void setSixYears(int sixYears) {
        SixYears = sixYears;
    }

    public int getTenYears() {
        return TenYears;
    }

    public void setTenYears(int tenYears) {
        TenYears = tenYears;
    }

    public int getTwelveYears() {
        return TwelveYears;
    }

    public void setTwelveYears(int twelveYears) {
        TwelveYears = twelveYears;
    }
}
