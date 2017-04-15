package com.ericwei.project333;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ericwei.project333.data.ClothesContract;
import com.ericwei.project333.data.Item;

public class ClothesImagesActivity extends AppCompatActivity implements ImageAdapter.ItemCardClickListener {

    private static final String TAG = ClothesImagesActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private String category;
    private Item[] items;
    private int[] itemIDs;
    private boolean isSelectingItems;
    private boolean showingSavedOutfitItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_images);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_item_images);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        imageAdapter = new ImageAdapter(this, this);
        recyclerView.setAdapter(imageAdapter);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        isSelectingItems = intent.getBooleanExtra("selecting", false);
        showingSavedOutfitItems = intent.getBooleanExtra("showingSavedOutfit", false);

        //grab the images data
        if (!showingSavedOutfitItems) {
            new FetchItemsDataTask().execute();
        } else {
            itemIDs = intent.getIntArrayExtra("itemids");
            getItemsByItemIDs(itemIDs);
        }
    }

    private void getItemsByItemIDs(int[] itemIDs) {

        Cursor cursor = getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                null,
                null,
                null,
                ClothesContract.ClothesEntry.COLUMN_CATEGORY);

        cursor.moveToFirst();

        //initializing the items array to the right length
        for (int i = 0; i < itemIDs.length; i++) {
            if (itemIDs[i] == 0) {
                items = new Item[i];
                break;
            }
        }

        int j = 0;

        //this logic has problem!!!!!  HOW I AM SEARCHING THE items(which are not sorted), against the cursor that points to items.
        for (int i = 0; i < cursor.getCount(); i++) {
            if (itemIDs[j] > 0) {
                if (cursor.getInt(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_ID)) == itemIDs[j]) {
                    byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_IMAGE));

                    items[j] = new Item(cursor.getString(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_CATEGORY)),
                            cursor.getString(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_SUBCATEGORY)),
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length),
                            cursor.getInt(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_ID))
                    );
                    j++;
                }
                cursor.moveToNext();
            } else {
                break;
            }
        }
        imageAdapter.setItemData(items);
    }

    @Override
    public void onItemClick(Item item) {
        Log.d(TAG, "in onItemClick!!()");
        Toast.makeText(this, "clicked item number " + item.getId(), Toast.LENGTH_SHORT).show();
        if (isSelectingItems) {
            OutfitPickingData.getInstance().appendOutfitItems(item);
            Toast.makeText(getApplicationContext(), "tapped!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public class FetchItemsDataTask extends AsyncTask<Object, Object, Cursor> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            Context context = ClothesImagesActivity.this;
            dialog = new ProgressDialog(context);  //can't use getApplicationContext()
            dialog.setMessage("Loading Movie Info...");
            dialog.setCancelable(false);
            //http://stackoverflow.com/questions/7811993/error-binderproxy45d459c0-is-not-valid-is-your-activity-running
            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Object... voids) {
            return getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                    null,
                    ClothesContract.ClothesEntry.COLUMN_CATEGORY + " = '" + category + "'",
                    null,
                    ClothesContract.ClothesEntry.COLUMN_CATEGORY);
        }

        @Override
        protected void onPostExecute(Cursor itemCursor) {
            int numItems = itemCursor.getCount();

            if (numItems > 0) {
                items = new Item[numItems];
                itemCursor.moveToFirst();

                for (int i = 0; i < numItems; i++) {
                    //the item image
                    byte[] imageBytes = itemCursor.getBlob(itemCursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_IMAGE));

                    items[i] = new Item(itemCursor.getString(itemCursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_CATEGORY)),
                            itemCursor.getString(itemCursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_SUBCATEGORY)),
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length),
                            itemCursor.getInt(itemCursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_ID))
                    );
                    itemCursor.moveToNext();
                }
                imageAdapter.setItemData(items);
            } else {
                Toast.makeText(getApplicationContext(), "You have no favorite movies!",
                        Toast.LENGTH_LONG).show();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(itemCursor);
        }
    }

//    public class FetchItemByItemIDsTask extends AsyncTask<int[], Object, Item[]> {
//
//        @Override
//        protected Item[] doInBackground(Integer[]... integers) {
//
//            Cursor cursor = getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
//                    null,
//                    null,
//                    null,
//                    ClothesContract.ClothesEntry.COLUMN_CATEGORY);
//
//            cursor.moveToFirst();
//            int j = 0;
//            for (int i = 0; i < cursor.getCount(); i++) {
//                if (cursor.getInt(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_ID)) == integers[j])
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Item[] items) {
//
//        }
//    }

}
