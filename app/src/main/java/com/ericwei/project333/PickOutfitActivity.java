package com.ericwei.project333;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickOutfitActivity extends AppCompatActivity {

    private static final String TAG = PickOutfitActivity.class.getSimpleName();

    private ListView listView;
    private GridView gridView;
    private String[] categoryNames;
    private static ArrayList<Integer> itemNumbers;
    private OutfitImageAdapter imageAdapter;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()!!!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_outfit);

        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridView);
        saveButton = (Button) findViewById(R.id.toolbar_save_button);

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

        imageAdapter = new OutfitImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "SAVE CLICKED!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveOutfit() {

    }

    ///////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pick_outfit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(getApplicationContext(), "save button clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
    ///////////////////

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "len of pickingArray = " + OutfitPickingData.getInstance().getListLength());
        imageAdapter.notifyDataSetChanged();
    }
}
