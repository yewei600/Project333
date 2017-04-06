package com.ericwei.project333.clothes_tab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.ericwei.project333.R;

import java.util.HashMap;
import java.util.List;

public class PickClothesCategoryActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail;
    private String bannerDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_clothes_category);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
