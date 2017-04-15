package com.ericwei.project333.clothes_tab;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ericwei.project333.ClothesImagesActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_outfits);

        ListView listView = (ListView) findViewById(R.id.outfits_list);
        final String[] outfitNames = getSavedOutfits();

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, outfitNames);
        listView.setAdapter(listAdapter);
        listView.setEmptyView(findViewById(R.id.empty_tv));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int[] itemIDs = getSelectedOutfit(outfitNames[i]);
                Intent intent = new Intent(SavedOutfitsActivity.this, ClothesImagesActivity.class);
                intent.putExtra("showingSavedOutfit", true);
                intent.putExtra("itemids", itemIDs);
                startActivity(intent);
            }
        });
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
}
