package com.ericwei.project333.clothes_tab;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ericwei.project333.ClothesImagesActivity;
import com.ericwei.project333.PickOutfitActivity;
import com.ericwei.project333.R;
import com.ericwei.project333.data.OutfitContract;
import com.ericwei.project333.data.OutfitDbHelper;

//case :
//        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_OUTFIT1));
//        imageBitmaps[] =BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        imageID[] = cursor.getInt(cursor.getColumnIndex(OutfitContract.OutfitEntry.))
//        break;

public class SavedOutfitsActivity extends AppCompatActivity {

    private static final String TAG = SavedOutfitsActivity.class.getSimpleName();
    private boolean pickingTodaysOutfit;
    private String[] outfitNames;
    private Toolbar toolbar;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_outfits);

        toolbar = (Toolbar) findViewById(R.id.savedOutfits_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        pickingTodaysOutfit = intent.getBooleanExtra("pickingtoday", false);

        ListView listView = (ListView) findViewById(R.id.outfits_list);
        outfitNames = getSavedOutfits();

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, outfitNames);
        listView.setAdapter(listAdapter);
        listView.setEmptyView(findViewById(R.id.empty_tv));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int clickIndex, long l) {

                if (pickingTodaysOutfit) {
                    new AlertDialog.Builder(SavedOutfitsActivity.this)
                            .setTitle("Select this outfit for today?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    selectOutfitForToday(clickIndex);
                                }
                            })
                            .setNeutralButton("View Outfit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //opening the ClothesImagesActivity
                                    openClothesImagesActivity(clickIndex);
                                }
                            })
                            .show();
                } else {
                    openClothesImagesActivity(clickIndex);
                }
            }
        });
    }

    private void selectOutfitForToday(int outfitIndex) {
        //get the outfit number
        Log.d(TAG, "selectOutfitForToday!!!! yay");
        OutfitDbHelper helper = new OutfitDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        int[] outfitNumbers = getSelectedOutfit(outfitNames[outfitIndex]);
        ContentValues contentValues = new ContentValues();
        contentValues.put(OutfitContract.OutfitEntry.COLUMN_NAME, outfitNames[outfitIndex]);

        for (int i = 0; i < outfitNumbers.length; i++) {
            switch (i) {
                case 0:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT1, outfitNumbers[0]);
                    break;
                case 1:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT2, outfitNumbers[1]);
                    break;
                case 2:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT3, outfitNumbers[2]);
                    break;
                case 3:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT4, outfitNumbers[3]);
                    break;
                case 4:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT5, outfitNumbers[4]);
                    break;
                case 5:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT6, outfitNumbers[5]);
                    break;
                case 6:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT7, outfitNumbers[6]);
                    break;
                case 7:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT8, outfitNumbers[7]);
                    break;
                case 8:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT9, outfitNumbers[8]);
                    break;
                case 9:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT10, outfitNumbers[9]);
                    break;
                default:
                    Log.d(TAG, "error putting contentValues in");
            }
            long id = db.insert(OutfitContract.OutfitEntry.TABLE_NAME, null, contentValues);
            if (id > 0) {
                Log.d(TAG, "saved successful! outfit== " + outfitNames[outfitIndex]);

                SharedPreferences sharedPreferences = getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("today", true);
                editor.commit();
            } else {
                Log.d(TAG, "outfit not saved");
            }
        }
    }

    private void openClothesImagesActivity(int outfitIndex) {
        int[] itemIDs = getSelectedOutfit(outfitNames[outfitIndex]);
        Intent intent = new Intent(SavedOutfitsActivity.this, ClothesImagesActivity.class);
        intent.putExtra("showingSavedOutfit", true);
        intent.putExtra("itemids", itemIDs);
        startActivity(intent);
    }

    private String[] getSavedOutfits() {
        OutfitDbHelper helper = new OutfitDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(OutfitContract.OutfitEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                OutfitContract.OutfitEntry.COLUMN_NAME);

        Log.d(TAG, "the number of outfits == " + String.valueOf(cursor.getCount()));

        String[] outfitNames = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            outfitNames[i] = cursor.getString(cursor.getColumnIndex(OutfitContract.OutfitEntry.COLUMN_NAME));
            cursor.moveToNext();
        }
        return outfitNames;
    }

    private int[] getSelectedOutfit(String outfitName) {
        OutfitDbHelper helper = new OutfitDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(OutfitContract.OutfitEntry.TABLE_NAME,
                null,
                OutfitContract.OutfitEntry.COLUMN_NAME + "=?",
                new String[]{outfitName},
                null,
                null,
                null);

        Log.d(TAG, "cursor has columns =" + cursor.getColumnCount());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saved_outfits_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_outfit) {
            Intent intent = new Intent(SavedOutfitsActivity.this, PickOutfitActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }
}
