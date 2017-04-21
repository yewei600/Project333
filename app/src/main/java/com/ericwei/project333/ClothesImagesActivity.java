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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private boolean isSelectingTodaysOutfit;
    private Toolbar toolbar;

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
        isSelectingTodaysOutfit = intent.getBooleanExtra("selectingTodayOutfit", false);

        //grab the images data
        if (!showingSavedOutfitItems) {
            new FetchItemsDataTask().execute();
        } else {
            itemIDs = intent.getIntArrayExtra("itemids");
            getItemsByItemIDs(itemIDs);
        }
    }

    private void getItemsByItemIDs(int[] itemIDs) {
        //initializing the items array to the right length
        for (int i = 0; i < itemIDs.length; i++) {
            if (itemIDs[i] == 0) {
                items = new Item[i];
                break;
            }
        }
        int j = 0;
        //querying each clothes item
        for (int i = 0; i < itemIDs.length; i++) {
            if (itemIDs[i] > 0) {
                Cursor cursor = getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                        null,
                        ClothesContract.ClothesEntry.COLUMN_ID + " = '" + itemIDs[i] + "'",
                        null,
                        null);

                cursor.moveToFirst();
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_IMAGE));

                items[j] = new Item(cursor.getString(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_SUBCATEGORY)),
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length),
                        cursor.getInt(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_ID)));

                cursor.close();
                j++;
            } else {
                break;
            }
        }
        imageAdapter.setItemData(items);
    }

    @Override
    public void onItemClick(Item item) {
        Log.d(TAG, "in onItemClick!!()");
        if (isSelectingItems) {
            if (!OutfitPickingData.getInstance().getOutfitNumbers().contains(item.getId())) {
                OutfitPickingData.getInstance().appendOutfitNumber(item.getId());
                OutfitPickingData.getInstance().appendOutfitBitmap(item.getItemImage());
                Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Item already added", Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(getApplicationContext(), "You have no " + category + " saved!",
                        Toast.LENGTH_SHORT).show();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(itemCursor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clothes_images_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_select_for_today) {
            Toast.makeText(getApplicationContext(), "select for today!!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
