package com.adel.adherence;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import java.io.FileOutputStream;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;



/**
 * Created by Adel on 1/14/15.
 */
public class BluetoothInterfacer {

    BluetoothAdapter mbadapter;
    static final int REQUEST_ENABLE_BT = 100;

    private final BroadcastReceiver btreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)) {

            }
        }
    }


    public BluetoothInterfacer() throws Exception {
        mbadapter = BluetoothAdapter.getDefaultAdapter();
        if(mbadapter == null) {
            throw new Exception("No Bluetooth support");
        }
    }

    public void connect(Activity currentActivity) {
        if(!mbadapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            currentActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT );
            return ;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_ENABLE_BT && resultCode == )
    }


    public void disconnect() {

    }

    public boolean connected() {
        return false;
    }

    public boolean received() {
        return false;
    }

    public void savePicture(byte[] ) {
        String filename = generateFilenName();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

        } catch (Exception e) {

        }
    }


}
