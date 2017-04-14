package com.ericwei.project333;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ericwei.project333.data.Item;

import java.util.ArrayList;

public class PickOutfitActivity extends AppCompatActivity implements PickedOutfitImageAdapter.ItemCardClickListener {

    private static final String TAG = PickOutfitActivity.class.getSimpleName();

    private ListView listView;
    private RecyclerView recyclerView;
    private String[] categoryNames;
    private static ArrayList<Integer> itemNumbers;
    private PickedOutfitImageAdapter imageAdapter;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()!!!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_outfit);

        listView = (ListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_pick_outfit);
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

        imageAdapter = new PickedOutfitImageAdapter(this, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "SAVE CLICKED!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveOutfit() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "len of pickingArray = " + OutfitPickingData.getInstance().getListLength());
        imageAdapter.setItemData(OutfitPickingData.getInstance().getOutfitItems());
    }

    @Override
    public void onItemClick(Item item) {

    }
}
