package com.ericwei.project333.job_service;

import android.content.Context;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by ericwei on 2017-04-16.
 */

public class ScheduleJobUtilities {

    private static final int REMINDER_INTERVAL_MINUTES = 5;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String DELETE_JOB_TAG = "outfit_delete_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleDeleteTodayOutfit(Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job deleteOutfitJob = dispatcher.newJobBuilder()
                .setService(DeleteTodayOutfitJobService.class)
                .setTag(DELETE_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(deleteOutfitJob);

        sInitialized = true;
    }
}
