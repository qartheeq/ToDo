package com.maya.todo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import eu.epitech.todolist.R;

/**
 * Created by noboud_n on 10/01/2017.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textView = getActivity().findViewById(R.id.dueDateLabel);
        StringBuilder formattedDate = new StringBuilder().append(year)
                .append("-")
                .append(month + 1 < 10 ? "0" + (month + 1) : month + 1)
                .append("-")
                .append(day + 1 < 10 ? "0" + (day + 1) : day);
        textView.setText(formattedDate);
    }
}
