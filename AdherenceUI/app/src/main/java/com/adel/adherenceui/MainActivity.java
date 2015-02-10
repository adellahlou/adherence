package com.adel.adherenceui;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.adel.adherenceui.pickers.DatePickerFragment;

import java.util.Date;

/* Get SHA1 Auth key
* keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
 */

public class MainActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener, DatePickerFragment.OnDateDialogListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;

    public static String APP_TAG = "ADHERENCE_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public  void  onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PreferenceManager.setDefaultValues( this, R.xml.preferences, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_instructions):
                break;
            case (R.id.action_about):
                break;
            case (R.id.action_legal):
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        ScheduleFragment scheduleFragment;
        MyDevicesFragment myDevicesFragment;
        SettingsFragment mSettingsFragment;

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            scheduleFragment = new ScheduleFragment();
            myDevicesFragment= new MyDevicesFragment();
            mSettingsFragment = new SettingsFragment();
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return scheduleFragment;
                case 1:
                    return myDevicesFragment;
                case 2:
                    return  mSettingsFragment;
                default:
                    return new MyDevicesFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case (0):
                    return "Schedule";
                case (1):
                    return "My Devices";
                case (2):
                    return "Settings";
                default:
                    return "Nada";
            }
        }
    }




    public void showDatePicker(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(DatePickerFragment.RECEIVING_VIEW_ID, v.getId());
        dateFragment.setArguments(args);
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getCalendarCaller(View v) {
        mAppSectionsPagerAdapter.scheduleFragment.getAdherenceCalendar();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day, int activatedViewId) {
        Toast.makeText(this,"Date Set", Toast.LENGTH_LONG).show();
        Date newDate = new Date(year, month, day);
        getSupportFragmentManager().findFragmentById(mAppSectionsPagerAdapter.getItem(0).getId()).getView().findViewById(activatedViewId);
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {}
}




