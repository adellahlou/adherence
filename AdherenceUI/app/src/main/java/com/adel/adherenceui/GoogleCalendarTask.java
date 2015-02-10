package com.adel.adherenceui;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.temboo.core.TembooSession;
import com.temboo.Library.Google.Calendar.


/**
 * Created by Adel on 2/8/15.
 */
public class GoogleCalendarTask extends AsyncTask<Void, Void, String> {

    private TextView mTextView;

    public GoogleCalendarTask(TextView view) {
        this.mTextView = view;
    }

    @Override
    protected String doInBackground(Void... args) {
        try {
            TembooSession session = new TembooSession("astronam","myFirstApp", "ef524bcc2e02489fab08191708245210");
        } catch (Exception e) {

        }

        return null;
    }

        protected void onPostExecute(String result) {
            try {

            } catch(Exception e) {
                Log.e(this,getClass().toString(), e.getMessage());
            }
        }

}
