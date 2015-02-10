package com.adel.adherenceui.pickers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Adel on 2/6/15.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateDialogListener mListener;
    private int activatedViewId;
    public static String RECEIVING_VIEW_ID = "RECEIVING_VIEW_ID";

    public interface OnDateDialogListener{
        public void onDateSet(DatePicker view, int year, int month, int day, int _activatedViewId);
    }


    public DatePickerFragment() {
        activatedViewId = getArguments().getInt(RECEIVING_VIEW_ID);
    }

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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDateDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnDateDialogListener");
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        mListener.onDateSet(view, year, month, day, activatedViewId);
    }
}