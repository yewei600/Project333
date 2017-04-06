package com.ericwei.project333.wardrobe_tab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ericwei.project333.R;
import com.lyft.android.scissors.CropView;

public class EditPictureActivity extends AppCompatActivity {

    private static final String TAG = EditPictureActivity.class.getSimpleName();

    CropView cropView;
    Button submitButton;
    Bitmap itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        cropView = (CropView) findViewById(R.id.crop_view);
        submitButton = (Button) findViewById(R.id.submit);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemImage = (Bitmap) bundle.get("Image");
        cropView.setImageBitmap(itemImage);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(EditPictureActivity.this, WardrobeExpandableListActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
