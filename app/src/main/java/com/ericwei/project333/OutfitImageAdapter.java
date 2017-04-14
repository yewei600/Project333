package com.ericwei.project333;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ericwei.project333.data.ClothesContract;

import java.util.concurrent.ExecutionException;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitImageAdapter extends BaseAdapter {
    private Context context;

    public OutfitImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return OutfitPickingData.getInstance().getOutfitArrayList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        try {
            byte[] imageData = new FetchImageDataTask().execute(OutfitPickingData.getInstance().getOutfitArrayList().get(i)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (view == null) {
            imageView = new ImageView(context);

        } else {
            imageView = (ImageView) view;
        }
        return imageView;
    }

    public class FetchImageDataTask extends AsyncTask<Integer, Object, byte[]> {

        @Override
        protected byte[] doInBackground(Integer... integers) {
            Cursor cursor = context.getContentResolver().query(ClothesContract.ClothesEntry.CONTENT_URI,
                    null,
                    ClothesContract.ClothesEntry.COLUMN_ID + " = '" + integers[0] + "'",
                    null,
                    ClothesContract.ClothesEntry.COLUMN_CATEGORY);

            return cursor.getBlob(cursor.getColumnIndex(ClothesContract.ClothesEntry.COLUMN_IMAGE));
        }
    }
}
