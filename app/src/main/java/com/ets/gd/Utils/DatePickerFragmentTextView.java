package com.ets.gd.Utils;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.ets.gd.Fragments.InspectionDatesFragment;
import com.ets.gd.R;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFragmentTextView extends DialogFragment implements DatePickerDialog.OnDateSetListener {


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
        TextView tvDate = (TextView) getActivity().findViewById(InspectionDatesFragment.viewID);
        month = month+1;
        tvDate.setText(day + "/" + month + "/" + year);
        month = month-1;
        diff = new Date(year-1900, month, day).getTime()/1000.0;
        Log.e( "onDateSet: ", ""+diff);


    }
}
