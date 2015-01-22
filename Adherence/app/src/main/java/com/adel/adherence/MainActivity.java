package com.adel.adherence;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


public class MainActivity extends ActionBarActivity {


    BluetoothDevice btdevice;
    BluetoothAdapter btadapter;
    final int BT_ENABLE_REQUEST = 1;
    final String BTSUCCESS_STR = "BTCONNECT_SUCCESS";
    final String BTFAILURE_STR = "BTCONNECT_FAILURE";

    final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    final String LOG_TAG = "Adherence: ";
    private Toast toaster;
    String btStatus = "Not Connected";
    TextView tv_btStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //setupBluetooth();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.bt_test, container, false);

            return rootView;
        }
    }

}




/* private void setupBluetooth(){
        btadapter = BluetoothAdapter.getDefaultAdapter();

        if (!(btadapter == null)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.bt_pair_title)
                    .setMessage("Pressing no will open up Bluetooth settings to pair.");
            builder.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return ;
                        }
                    });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent bt_pair = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivity(bt_pair);
                }
            });

            if(!btadapter.isEnabled()) {
                Intent enableBtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtintent, BT_ENABLE_REQUEST);
            }

            Set<BluetoothDevice> pairedDevices = btadapter.getBondedDevices();
            for(BluetoothDevice cur_dev : pairedDevices) {
                btdevice = cur_dev;
                break;
            }

            ConnectBT connect_Task = new ConnectBT();
            connect_Task.execute(btdevice);
        }

    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }
        public void run() {
            btadapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectBT extends AsyncTask<BluetoothDevice, Void, String> {

        private BluetoothSocket btSocket;
        private BluetoothDevice btDev;

        public String doInBackground(BluetoothDevice... btdev) {
            btDev = btdev[0];
            BluetoothSocket tmp = null;

            try {
                tmp = btDev.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Bluetooth connection failed");
            }
            btSocket = tmp;

            btadapter.cancelDiscovery();

            try{
                btSocket.connect();
            } catch(IOException e) {
                try{
                    btSocket.close();
                } catch (IOException g) {
                } finally {
                    return BTFAILURE_STR;
                }
            }

            return BTSUCCESS_STR;
        }

        protected void onPostExecute(String result) {
            btStatus = result;
        }

    }
//*/
