package com.ericwei.project333.clothes_tab;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ericwei.project333.R;
import com.ericwei.project333.data.ClothesContract.ClothesEntry;
import com.lyft.android.scissors.CropView;

import java.io.ByteArrayOutputStream;

public class AddClothesActivity extends AppCompatActivity {

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
                    saveItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private byte[] imageToByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void saveItem() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClothesEntry.COLUMN_CATEGORY, "tops");
        contentValues.put(ClothesEntry.COLUMN_SUBCATEGORY, "shirt");
        contentValues.put(ClothesEntry.COLUMN_IMAGE, imageToByte(itemImage));
        contentValues.put(ClothesEntry.COLUMN_ID, 1);

        Uri uri = getContentResolver().insert(ClothesEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Toast.makeText(this, "Added clothes item!", Toast.LENGTH_SHORT).show();
        }
    }
}
