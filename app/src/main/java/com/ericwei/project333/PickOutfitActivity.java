package com.ericwei.project333;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.ericwei.project333.data.OutfitContract;
import com.ericwei.project333.data.OutfitDbHelper;

import java.util.ArrayList;

public class PickOutfitActivity extends AppCompatActivity {

    private static final String TAG = PickOutfitActivity.class.getSimpleName();

    private ListView listView;
    private GridView gridView;
    private String[] categoryNames;
    private static ArrayList<Integer> itemNumbers;
    private PickedOutfitImageAdapter imageAdapter;
    private Button saveButton;
    private Toolbar toolbar;
    private boolean pickingTodayOutfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()!!!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_outfit);

        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridView);

        toolbar = (Toolbar) findViewById(R.id.saveOutfit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        categoryNames = getResources().getStringArray(R.array.clothes_category);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, categoryNames);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PickOutfitActivity.this, ClothesImagesActivity.class);
                intent.putExtra("category", categoryNames[i]);
                intent.putExtra("selecting", true);
                startActivity(intent);
            }
        });

        imageAdapter = new PickedOutfitImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        Intent intent = getIntent();
        pickingTodayOutfit = intent.getBooleanExtra("pickingtodaynew", false);
    }

    private void saveOutfit(String outfitName) {
        OutfitDbHelper helper = new OutfitDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(OutfitContract.OutfitEntry.COLUMN_NAME, outfitName);

        for (int i = 0; i < OutfitPickingData.getInstance().getListLength(); i++) {
            switch (i) {
                case 0:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT1, OutfitPickingData.getInstance().getOutfitNumbers().get(0));
                    break;
                case 1:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT2, OutfitPickingData.getInstance().getOutfitNumbers().get(1));
                    break;
                case 2:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT3, OutfitPickingData.getInstance().getOutfitNumbers().get(2));
                    break;
                case 3:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT4, OutfitPickingData.getInstance().getOutfitNumbers().get(3));
                    break;
                case 4:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT5, OutfitPickingData.getInstance().getOutfitNumbers().get(4));
                    break;
                case 5:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT6, OutfitPickingData.getInstance().getOutfitNumbers().get(5));
                    break;
                case 6:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT7, OutfitPickingData.getInstance().getOutfitNumbers().get(6));
                    break;
                case 7:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT8, OutfitPickingData.getInstance().getOutfitNumbers().get(7));
                    break;
                case 8:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT9, OutfitPickingData.getInstance().getOutfitNumbers().get(8));
                    break;
                case 9:
                    contentValues.put(OutfitContract.OutfitEntry.COLUMN_OUTFIT10, OutfitPickingData.getInstance().getOutfitNumbers().get(9));
                    break;
                default:
                    Log.d(TAG, "error putting contentValues in");
            }
        }
        long id = db.insert(OutfitContract.OutfitEntry.TABLE_NAME, null, contentValues);
        if (id > 0) {
            Log.d(TAG, "saved successful! outfit== " + outfitName);
        } else {
            Log.d(TAG, "outfit not saved");
        }
        OutfitPickingData.getInstance().getOutfitNumbers().clear();
        OutfitPickingData.getInstance().getOutfitBitmaps().clear();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pick_outfit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (pickingTodayOutfit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PickOutfitActivity.this);

                builder.setTitle("Use this outfit for today?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveOutfit("today");
                        SharedPreferences sharedPreferences = getSharedPreferences("todayOutfit", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("today", true);
                        editor.commit();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                //else?
                AlertDialog.Builder builder = new AlertDialog.Builder(PickOutfitActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.edittext_dialog, null);
                builder.setView(dialogView);

                final EditText editText = (EditText) dialogView.findViewById(R.id.et_dialog);
                builder.setMessage("Enter Outfit name below:");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveOutfit(editText.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "len of pickingArray = " + OutfitPickingData.getInstance().getListLength());
        imageAdapter.notifyDataSetChanged();
    }
}
