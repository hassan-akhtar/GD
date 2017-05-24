package com.ets.gd.Utils;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ets.gd.FireBug.ViewInformation.InspectionDatesFragment;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFragmentEditText extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public static Double diff;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        diff = 0.0;
        EditText tvDate = (EditText) getActivity().findViewById(InspectionDatesFragment.viewID);
        month = month+1;
        String monthStr = "" + month;
        String dayStr = "" + day;
        if (monthStr.length() == 1) {
            monthStr = "0" + month;
        }
        if (dayStr.length() == 1) {
            dayStr = "0" + day;
        }
        tvDate.setTextColor(Color.BLACK);
        tvDate.setText("" + monthStr + "/" + dayStr + "/" + year);
        month = month-1;
        diff = new Date(year-1900, month, day).getTime()/1000.0;
        Log.e( "onDateSet: ", ""+diff);


    }
}
