package com.ericwei.project333.wardrobe_tab;

import android.content.ContentValues;
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

import com.ericwei.project333.R;
import com.ericwei.project333.data.ClothesContract;
import com.ericwei.project333.data.ClothesDbHelper;

import java.io.ByteArrayOutputStream;

public class WardrobeExpandableListActivity extends AppCompatActivity {

    private static final String TAG = WardrobeExpandableListActivity.class.getSimpleName();

    private CategoryExpandableListAdapter listAdapter;
    private ExpandableListView expandableListView;
    private String[] listTitles;
    private String[][] listDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        populateListContent();

        listAdapter = new CategoryExpandableListAdapter(this, listTitles, listDetails);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getApplicationContext(), listDetails[i][i1] + " clicked", Toast.LENGTH_SHORT).show();
                return false;
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

    private void saveItem() {
        ContentValues contentValues = new ContentValues();
        long itemNumber = getDatabaseRowCount() + 1;
        contentValues.put(ClothesContract.ClothesEntry.COLUMN_CATEGORY, "tops");
        contentValues.put(ClothesContract.ClothesEntry.COLUMN_SUBCATEGORY, "shirt");
        // contentValues.put(ClothesContract.ClothesEntry.COLUMN_IMAGE, imageToByte(itemImage));
        contentValues.put(ClothesContract.ClothesEntry.COLUMN_ID, itemNumber);

        Uri uri = getContentResolver().insert(ClothesContract.ClothesEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Toast.makeText(this, "Added clothes item number" + itemNumber, Toast.LENGTH_SHORT).show();
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

}
