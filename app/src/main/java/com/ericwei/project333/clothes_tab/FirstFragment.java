package com.ericwei.project333.clothes_tab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ericwei.project333.PickOutfitActivity;
import com.ericwei.project333.R;
import com.ericwei.project333.ViewOutfitActivity;

/**
 * Created by ericwei on 2017-03-19.
 */

//http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

public class FirstFragment extends Fragment {

    CardView todayCard;
    CardView outfitsCard;

    public FirstFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        todayCard = (CardView) view.findViewById(R.id.todayCard);
        outfitsCard = (CardView) view.findViewById(R.id.outfitsCard);

        todayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayCardClicked();
            }
        });

        outfitsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outfitsCardClicked();
            }
        });

        return view;
    }

    //check wardrobe, see what title should be set for the cardview: (add, change? )
    //SHOULD THIS OPTION BE IN A   NOT VERY ADVERTISED SPOT? (like the settings menu?)
    private void checkWardrobeStatus() {

    }


    private void todayCardClicked() {
        //check here if today's outfit exists or not
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("today", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        if (!sharedPrefs.contains("outfit")) {
            Toast.makeText(getContext(), "Today's outfit don't exist!", Toast.LENGTH_SHORT).show();

            new AlertDialog.Builder(getContext())
                    .setTitle("Pick Today's Outfit")
                    .setMessage("New Outfit or Saved Outfit?")
                    .setNegativeButton("new outfit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getContext(), PickOutfitActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setPositiveButton("saved outfits", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getContext(), SavedOutfitsActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();

        } else {
            Toast.makeText(getContext(), "Exists!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ViewOutfitActivity.class);
            startActivity(intent);
        }

    }

    public void outfitsCardClicked() {
        Intent intent = new Intent(getContext(), SavedOutfitsActivity.class);
        startActivity(intent);
    }
}
