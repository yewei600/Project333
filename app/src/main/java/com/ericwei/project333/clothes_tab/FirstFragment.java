package com.ericwei.project333.clothes_tab;

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
import android.widget.Toast;

import com.ericwei.project333.ClothesImagesActivity;
import com.ericwei.project333.PickOutfitActivity;
import com.ericwei.project333.R;
import com.ericwei.project333.data.OutfitContract;
import com.ericwei.project333.data.OutfitDbHelper;

/**
 * Created by ericwei on 2017-03-19.
 */

//http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

public class FirstFragment extends Fragment {
    private static final String TAG = FirstFragment.class.getSimpleName();

    CardView todayCard;
    CardView outfitsCard;

    public FirstFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        todayCard = (CardView) view.findViewById(R.id.todayCard);
        outfitsCard = (CardView) view.findViewById(R.id.outfitsCard);

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

        return view;
    }

    //check wardrobe, see what title should be set for the cardview: (add, change? )
    //SHOULD THIS OPTION BE IN A   NOT VERY ADVERTISED SPOT? (like the settings menu?)
    private void checkWardrobeStatus() {

    }


    private void todayCardClicked() {
        //check here if today's outfit exists or not
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
        if (sharedPrefs.getBoolean("today", false)) {
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
