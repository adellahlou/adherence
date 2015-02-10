package com.adel.adherenceui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyDevicesFragment extends Fragment {

    private ListView mListView;
    private ArrayList<AdherenceDevice> mAdherenceDevices;
    private ArrayAdapter<String> mDeviceNameAdapter;
    private ArrayAdapter<String> mDeviceStatusAdapter;

    private String deviceDataFileName = "com.adel.adherenceui.PREFERENCE_DEVICE_DATA";
    private static final String TAG_DEVICES = "devices";
    private static final String TAG_DEVICENAME = "deviceName";
    private static final String TAG_LASTREFILLED = "lastRefilled";
    private static final String TAG_EXPECTEDNEXTREFILL = "expectedNextRefill";
    private static final String TAG_LASTCONNECTED = "lastConnected" ;

    public MyDevicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAdherenceDevices = loadSavedDevices();
        if(mAdherenceDevices == null) {
            mAdherenceDevices = new ArrayList<AdherenceDevice>();
        }


        View rootView = inflater.inflate(R.layout.fragment_mydevices, container, false);
        return rootView;
    }

    private ArrayList<AdherenceDevice> loadSavedDevices(){

        File deviceDataFile = new File(getActivity().getFilesDir(), deviceDataFileName);
        ArrayList<AdherenceDevice> ret = null;

        if (deviceDataFile.exists()){
            StringBuilder jsonBuilder;
            String current;
            try {
                BufferedReader buf = new BufferedReader(new FileReader(deviceDataFile));

                jsonBuilder = new StringBuilder((int) deviceDataFile.length());

                while((current = buf.readLine()) != null){
                    jsonBuilder.append(current);
                }

                if(jsonBuilder.length() == 0)
                    ret = null;
                else
                    ret = parseFileJSON(jsonBuilder.toString());

                buf.close();
            } catch (FileNotFoundException e) {
                Log.i(MainActivity.APP_TAG, e.toString());
            }
            catch (IOException e){
                Log.e(MainActivity.APP_TAG, e.toString());
            }
        }

        return ret;
    }

    private ArrayList<AdherenceDevice> parseFileJSON(String rawDeviceData){
        ArrayList<AdherenceDevice> ret = new ArrayList<AdherenceDevice>();
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        try {
            JSONObject fullJSON = new JSONObject(rawDeviceData);

            JSONArray devices = fullJSON.getJSONArray(TAG_DEVICES);

            for(int i = 0; i < devices.length(); i++) {
                JSONObject device = devices.getJSONObject(i);

                String deviceName = device.getString(TAG_DEVICENAME);
                Date lastRefilled = format.parse(device.getString(TAG_LASTREFILLED));
                Date expectedNextRefill = format.parse(device.getString(TAG_EXPECTEDNEXTREFILL));
                Date lastConnected = format.parse(device.getString(TAG_LASTCONNECTED));

                ret.add(new AdherenceDevice(deviceName,
                        expectedNextRefill,
                        lastRefilled,
                        lastConnected));
            }
        } catch (JSONException e) {
            Log.e(MainActivity.APP_TAG, e.toString());
        } catch (ParseException e){
            Log.e(MainActivity.APP_TAG, e.toString());
        }

        if(ret.size() == 0)
            return null;
        else
            return ret;
    }

    private void writeSavedDevices() {

    }
}