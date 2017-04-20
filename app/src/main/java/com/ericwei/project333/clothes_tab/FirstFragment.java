package com.ericwei.project333.clothes_tab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ericwei.project333.AlarmReceiver;
import com.ericwei.project333.ClothesImagesActivity;
import com.ericwei.project333.PickOutfitActivity;
import com.ericwei.project333.R;
import com.ericwei.project333.data.OutfitContract;
import com.ericwei.project333.data.OutfitDbHelper;

import java.util.Calendar;

/**
 * Created by ericwei on 2017-03-19.
 */

//http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

public class FirstFragment extends Fragment {
    private static final String TAG = FirstFragment.class.getSimpleName();

    CardView todayCard;
    CardView outfitsCard;
    Button startButton;
    TextView progressTextView;
    AlarmManager alarmManager;
    PendingIntent alarmIntent;

    SharedPreferences dayCountSharedPref;
    SharedPreferences.Editor dayCountEditor;

    SharedPreferences dateSharedPref;
    SharedPreferences.Editor dateEditor;

    SharedPreferences startedSharedPref;
    SharedPreferences.Editor startedEditor;

    public FirstFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        dayCountSharedPref = getContext().getSharedPreferences("dayCount", Context.MODE_PRIVATE);
        dayCountEditor = dayCountSharedPref.edit();
        if (!dayCountSharedPref.contains("dayCount")) {
            dayCountEditor.putInt("dayCount", 0);
            dayCountEditor.commit();
        }

        dateSharedPref = getContext().getSharedPreferences("date", Context.MODE_PRIVATE);
        dateEditor = dateSharedPref.edit();

        startedSharedPref = getContext().getSharedPreferences("started", Context.MODE_PRIVATE);
        startedEditor = startedSharedPref.edit();
        if (!startedSharedPref.contains("started")) {
            startedEditor.putBoolean("started", false);
            startedEditor.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        todayCard = (CardView) view.findViewById(R.id.todayCard);
        outfitsCard = (CardView) view.findViewById(R.id.outfitsCard);
        startButton = (Button) view.findViewById(R.id.start_button);

        if (startedSharedPref.getBoolean("started", false)) {
            startButton.setVisibility(View.INVISIBLE);
        }

        progressTextView = (TextView) view.findViewById(R.id.tv_date_count);

        //set the day count textview
        updateDayCountTextView();

        todayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayCardClicked();
            }
        });

        outfitsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outfitsCardClicked();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiate the alarm manager
                startedEditor.putBoolean("started", true);
                startedEditor.commit();

                Calendar cal = Calendar.getInstance();
                int todayDate = cal.get(Calendar.DAY_OF_MONTH);
                dateEditor.putInt("date", todayDate);
                dateEditor.commit();

                startButton.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDayCountTextView();
    }

    private void updateDayCountTextView() {
        int dayCount = 0;

        //if the challenge has started
        if (startedSharedPref.getBoolean("started", false)) {
            //get current day
            Calendar calendar = Calendar.getInstance();
            int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);

            if (dateSharedPref.getInt("date", 0) != todayNumber) {
                dayCount = dayCountSharedPref.getInt("date", 0) + 1;
                dayCountEditor.putInt("date", dayCount);
                dayCountEditor.commit();
            }
        }
        progressTextView.setText(dayCount + "/90 days");

    }

    private void todayCardClicked() {
        //check here if today's outfit exists or not
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
        Boolean todayOutfitExists = sharedPrefs.getBoolean("today", false);
        if (todayOutfitExists) {
            Toast.makeText(getContext(), "Exists!!", Toast.LENGTH_SHORT).show();
            int[] todaysOutfit = getTodaysOutfit();
            Intent intent = new Intent(getContext(), ClothesImagesActivity.class);
            intent.putExtra("showingSavedOutfit", true);
            intent.putExtra("itemids", todaysOutfit);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Today's outfit don't exist!", Toast.LENGTH_SHORT).show();

            new AlertDialog.Builder(getContext())
                    .setTitle("Pick Today's Outfit")
                    .setMessage("New Outfit or Saved Outfit?")
                    .setNegativeButton("new outfit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getContext(), PickOutfitActivity.class);
                            intent.putExtra("pickingtodaynew", true);
                            startActivity(intent);
                        }
                    })
                    .setPositiveButton("saved outfits", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getContext(), SavedOutfitsActivity.class);
                            intent.putExtra("pickingtoday", true);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    private int[] getTodaysOutfit() {
        OutfitDbHelper helper = new OutfitDbHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(OutfitContract.OutfitEntry.TABLE_NAME,
                null,
                OutfitContract.OutfitEntry.COLUMN_NAME + "=?",
                new String[]{"today"},
                null,
                null,
                null);

        int[] imageID = new int[cursor.getColumnCount() - 1];
        cursor.moveToFirst();

        for (int i = 0; i < imageID.length; i++) {
            switch (i) {
                case 0:
                    imageID[0] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT1));
                    break;
                case 1:
                    imageID[1] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT2));
                    break;
                case 2:
                    imageID[2] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT3));
                    break;
                case 3:
                    imageID[3] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT4));
                    break;
                case 4:
                    imageID[4] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT5));
                    break;
                case 5:
                    imageID[5] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT6));
                    break;
                case 6:
                    imageID[6] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT7));
                    break;
                case 7:
                    imageID[7] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT8));
                    break;
                case 8:
                    imageID[8] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT9));
                    break;
                case 9:
                    imageID[9] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT10));
                    break;
                default:
                    Log.d(TAG, "error getting item ID");
            }
        }
        return imageID;
    }

    public void outfitsCardClicked() {
        Intent intent = new Intent(getContext(), SavedOutfitsActivity.class);
        startActivity(intent);
    }

}
