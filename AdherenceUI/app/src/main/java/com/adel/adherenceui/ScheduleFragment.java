package com.adel.adherenceui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.ArrayList;


public class ScheduleFragment extends Fragment {


    //private OnFragmentInteractionListener mListener;
    public final String calendarTag = "1ADHERENCE_EVENTS";

    public ArrayList<String> eventNames = new ArrayList<String>();
    public ArrayList<Long> eventTimes = new ArrayList<Long>();
    public ArrayList<String> eventTimeZones = new ArrayList<String>();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    EditText startDate;
    EditText endDate;

    CalendarView mCalendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_schedule, container, false);
        startDate = (EditText) myFragmentView.findViewById(R.id.datepicker_start);
        endDate = (EditText) myFragmentView.findViewById(R.id.datepicker_end);
        mCalendarView= (CalendarView) myFragmentView.findViewById(R.id.calendar);

        return myFragmentView;
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DatePickerListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;



    public void getAdherenceCalendar() {

        boolean calendarExists = false;
        long calID = 0;
        int accountCounter = 0;
        Cursor cur = null;
        ContentResolver cr = getActivity().getContentResolver();
        Uri uri_calendars = CalendarContract.Calendars.CONTENT_URI;
        Uri uri_events = CalendarContract.Events.CONTENT_URI;

        //Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        String myAccountType = "com.google";
        Account[] accounts = AccountManager.get(getActivity()).getAccountsByType(myAccountType);


        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?))";

        String[] selectionArgs = new String[] {accounts[0].name,
                                                myAccountType};


        //cur = cr.query(uri, null, null, null, null);
        cur = cr.query(uri_calendars, null, selection, selectionArgs, null);
        //cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        //cur.moveToFirst();

        while(cur.moveToNext()) {
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            String nameTest = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.NAME));
            calID = cur.getLong(cur.getColumnIndex(CalendarContract.Calendars._ID));
            String test3 = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));
            String test4 = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_TYPE));
            String test5 = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.OWNER_ACCOUNT));

            if (nameTest.equals(calendarTag))
            {
                calendarExists = true;
                break;
            }

            Log.d(MainActivity.APP_TAG, calID + " " + displayName + " " + accountName + " " + ownerName);
            //Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
        }
        cur.close();


        //CONTINUE FROM HERE - NEED DO DO ANOTHER QUERY TO GET ALL EVENTS FROM THE ADHERENCE CALENDAR
        if (calendarExists)
        {
            ArrayList<Long> calendarIDs = new ArrayList<Long>();
            Cursor eventCursor = null;

            String eventSelection = CalendarContract.Events.CALENDAR_ID + " = ?";

            String[] eventSelectionArgs = new String[] {new String(Long.toString(calID))};

            //eventCursor = cr.query(uri_events, null, null, null, null);
            eventCursor = cr.query(uri_events, null, eventSelection, eventSelectionArgs, null);

            while (eventCursor.moveToNext())
            {
                calendarIDs.add( new Long(eventCursor.getLong(eventCursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID))));
                eventNames.add(eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.TITLE)));
                eventTimes.add(new Long(eventCursor.getLong(eventCursor.getColumnIndex(CalendarContract.Events.DTSTART))));
                eventTimeZones.add(eventCursor.getString(eventCursor.getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE)));

            }
        }

        else
        {
            //INSERT CODE TO TELL THE USER THEY NEED TO CREATE A CALENDAR BECAUSE IT DOESN'T EXIST

            createAdherenceCalendar();
        }



    }

    public void createAdherenceCalendar()
    {
        new GoogleCalendarTask(getActivity()).execute();
    }


}
