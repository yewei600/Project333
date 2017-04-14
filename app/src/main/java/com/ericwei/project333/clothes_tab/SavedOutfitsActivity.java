package com.ericwei.project333.clothes_tab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ericwei.project333.R;

public class SavedOutfitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_outfits);

        Toast.makeText(getApplicationContext(), "In the SavedOutfitsActivity!!!()", Toast.LENGTH_SHORT).show();
    }
}
