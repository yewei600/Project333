package com.ericwei.project333.wardrobe_tab;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericwei.project333.ClothesImagesActivity;
import com.ericwei.project333.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WardrobeFragment extends Fragment implements CategoryCardsAdapter.CategoryCardClickListener {

    private static final String TAG = WardrobeFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CategoryCardsAdapter adapter;
    private List<String> categoryList;
    private String categoryArray[];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        categoryList = new ArrayList<>();

        categoryArray = getContext().getResources().getStringArray(R.array.clothes_category);

        initializeList();
    }

    public void initializeList() {
        for (int i = 0; i < categoryArray.length; i++) {
            categoryList.add(categoryArray[i]);
        }
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (categoryList.size() > 0 && recyclerView != null) {
            adapter = new CategoryCardsAdapter(categoryList, this, getContext());
            recyclerView.setAdapter(adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent intent = new Intent(getContext(), EditPictureActivity.class);
            intent.putExtra("Image", imageBitmap);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(int cardIndex) {
        Intent intent = new Intent(getContext(), ClothesImagesActivity.class);
        intent.putExtra("category", categoryArray[cardIndex]);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "updating Category Item count");
        adapter.updateCategoryItemCount();
    }
}
