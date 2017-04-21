package com.ericwei.project333;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ericwei.project333.data.ClothesContract;
import com.ericwei.project333.data.ClothesDbHelper;

public class ItemDetailViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_view);

        toolbar = (Toolbar) findViewById(R.id.detailItem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        itemID = intent.getIntExtra("ItemID", -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_detail_item_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemByID(itemID);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItemByID(int id) {
        ClothesDbHelper dbHelper = new ClothesDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (itemID < 0) {
            Toast.makeText(getApplicationContext(), "Error deleting item!", Toast.LENGTH_LONG).show();
        }
        int row = db.delete(ClothesContract.ClothesEntry.TABLE_NAME, ClothesContract.ClothesEntry.COLUMN_ID + "=?", new String[]{String.valueOf(itemID)});
        if (row == 1) {
            Toast.makeText(getApplicationContext(), "Sucessfully deleted item!", Toast.LENGTH_LONG).show();
        }

    }
}
