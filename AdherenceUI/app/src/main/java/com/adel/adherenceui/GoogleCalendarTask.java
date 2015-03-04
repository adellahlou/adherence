package com.adel.adherenceui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.temboo.Library.Google.Calendar.CreateCalendar;
import com.temboo.core.TembooSession;


/**
 * Created by Adel on 2/8/15.
 */
public class GoogleCalendarTask extends AsyncTask<Void, Void, CreateCalendar.CreateCalendarResultSet> {

    private TextView mTextView;
    private Context mContext;

    public GoogleCalendarTask(TextView view) {
        this.mTextView = view;
    }

    public GoogleCalendarTask() {

    }

    public GoogleCalendarTask(Context context)
    {
        mContext = context;
    }


    @Override
    protected CreateCalendar.CreateCalendarResultSet doInBackground(Void... args) {
        try {
            //TembooSession session = new TembooSession("astronam","myFirstApp", "ef524bcc2e02489fab08191708245210");

            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            TembooSession session = new TembooSession("kobzajj", "myFirstApp", "43cd68c01c4145eab346c5050063a458");

            CreateCalendar createCalendarChoreo = new CreateCalendar(session);

            // Get an InputSet object for the choreo
            CreateCalendar.CreateCalendarInputSet createCalendarInputs = createCalendarChoreo.newInputSet();

            // Set inputs
            createCalendarInputs.set_ClientSecret("_ixrY6_PlFy8TtgYv5XdIjur");
            createCalendarInputs.set_RefreshToken("1/ZsoJOXCFOhMylRdwBTeOLaZVfxB3xvBNufU2TUgz38YMEudVrK5jSpoR30zcRFq6");
            createCalendarInputs.set_ClientID("869535631383-01ffjklss196llsc13o0q3ursusiabic.apps.googleusercontent.com");
            createCalendarInputs.set_Title("1ADHERENCE_EVENTS");

            // Execute Choreo
            CreateCalendar.CreateCalendarResultSet createCalendarResults = createCalendarChoreo.execute(createCalendarInputs);
            return createCalendarResults;

        } catch (Exception e) {
            // if an exception occurred, log it
            Log.e(this.getClass().toString(), e.getMessage());
        }

        return null;
    }

        protected void onPostExecute(CreateCalendar.CreateCalendarResultSet result) {
            try {

                Toast.makeText(mContext, "SUCCESSFULLY ADDED CALENDAR!", Toast.LENGTH_SHORT).show();

            } catch(Exception e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }

}
