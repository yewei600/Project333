package com.ericwei.project333;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ericwei on 2017-04-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "increase day count!!!");

        SharedPreferences sharedPref1 = context.getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPref1.edit();
        editor1.putBoolean("today", false);
        editor1.commit();

        SharedPreferences sharedPref2 = context.getSharedPreferences("dayCount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putInt("dateCount", sharedPref2.getInt("dateCount", 0) + 1);
        editor2.commit();


    }
}