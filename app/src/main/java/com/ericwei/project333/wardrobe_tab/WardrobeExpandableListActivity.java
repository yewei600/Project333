package com.ericwei.project333.wardrobe_tab;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ericwei.project333.MainActivity;
import com.ericwei.project333.R;
import com.ericwei.project333.data.ClothesContract;
import com.ericwei.project333.data.ClothesDbHelper;

import java.io.ByteArrayOutputStream;

public class WardrobeExpandableListActivity extends AppCompatActivity {

    private static final String TAG = WardrobeExpandableListActivity.class.getSimpleName();

    private WardrobeExpandableListAdapter listAdapter;
    private ExpandableListView expandableListView;
    private String[] listTitles;
    private String[][] listDetails;
    private Bitmap itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        populateListContent();

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemImage = (Bitmap) bundle.get("Image");

        listAdapter = new WardrobeExpandableListAdapter(this, listTitles, listDetails);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                saveItem(listTitles[i], listDetails[i][i1]);

                Intent intent2 = new Intent(WardrobeExpandableListActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                finish();
                return true;
            }
        });

        Toast.makeText(this, "Hello ExpandableList act!", Toast.LENGTH_SHORT).show();
    }

    private void populateListContent() {
        listTitles = getApplicationContext().getResources().getStringArray(R.array.clothes_category);

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.clothes_subcategory);
        int n = ta.length();
        listDetails = new String[n][];
        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                listDetails[i] = res.getStringArray(id);
            } else {
                // something wrong with the XML
            }
        }
        ta.recycle(); // Important!
    }

    private byte[] imageToByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void saveItem(String category, String subcategory) {
        if (getDatabaseRowCount() < 33) {
            ContentValues contentValues = new ContentValues();
            long itemNumber = getNextRowID();
            contentValues.put(ClothesContract.ClothesEntry.COLUMN_CATEGORY, category);
            contentValues.put(ClothesContract.ClothesEntry.COLUMN_SUBCATEGORY, subcategory);
            contentValues.put(ClothesContract.ClothesEntry.COLUMN_IMAGE, imageToByte(itemImage));
            contentValues.put(ClothesContract.ClothesEntry.COLUMN_ID, itemNumber);

            Uri uri = getContentResolver().insert(ClothesContract.ClothesEntry.CONTENT_URI, contentValues);
            if (uri != null) {
                Toast.makeText(this, "Added clothes " + category + " " + subcategory + " " + itemNumber, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "You already have 33 items!", Toast.LENGTH_SHORT).show();
        }
    }

    private long getDatabaseRowCount() {
        ClothesDbHelper dbHelper = new ClothesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, ClothesContract.ClothesEntry.TABLE_NAME);
        Log.d(TAG, "database number of rows ==" + cnt);
        db.close();
        return cnt;
    }

    private long getNextRowID() {
        SharedPreferences rowIDSharedPref = getSharedPreferences("rowID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = rowIDSharedPref.edit();

        int nextID = rowIDSharedPref.getInt("nextRowID", 0) + 1;
        editor.putInt("nextRowID", nextID);
        editor.commit();

        return nextID;
    }

}
