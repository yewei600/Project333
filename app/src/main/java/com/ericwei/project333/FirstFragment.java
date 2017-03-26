package com.ericwei.project333;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ericwei on 2017-03-19.
 */

//http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

public class FirstFragment extends Fragment {

    CardView todayCard;
    CardView outfitsCard;
    CardView changeWardrobeCard;
    CardView scheduleCard;

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
        changeWardrobeCard = (CardView) view.findViewById(R.id.changeWardrobeCard);
        scheduleCard = (CardView) view.findViewById(R.id.scheduleCard);

        todayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayCardClicked();
            }
        });

        outfitsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changeWardrobeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWardrobeCardClicked();
            }
        });

        scheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    //check wardrobe, see what title should be set for the cardview: (add, change? )
    //SHOULD THIS OPTION BE IN A   NOT VERY ADVERTISED SPOT? (like the settings menu?)
    private void checkWardrobeStatus() {

    }


    private void todayCardClicked() {
        Intent intent = new Intent(getContext(), TodayOutfitActivity.class);
        startActivity(intent);
    }

    //    public void outfitsCardClicked() {
//        Intent intent = new Intent(getContext(), );
//        startActivity(intent);
//    }
//
    private void changeWardrobeCardClicked() {
        Intent intent = new Intent(getContext(), ChangeWardrobeActivity.class);
        startActivity(intent);
    }
//
//    public void scheduleCardClicked() {
//        Intent intent = new Intent(getContext(), );
//        startActivity(intent);
//    }
}
