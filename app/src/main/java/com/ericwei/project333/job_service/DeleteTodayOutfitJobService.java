package com.ericwei.project333.job_service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.ericwei.project333.data.OutfitContract;
import com.ericwei.project333.data.OutfitDbHelper;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by ericwei on 2017-04-16.
 */

public class DeleteTodayOutfitJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        OutfitDbHelper helper = new OutfitDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(OutfitContract.OutfitEntry.TABLE_NAME,
                OutfitContract.OutfitEntry.COLUMN_NAME + "=?",
                new String[]{"today"});

        SharedPreferences sharedPreferences = getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("today", false);
        editor.commit();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
