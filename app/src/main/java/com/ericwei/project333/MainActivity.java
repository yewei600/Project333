package com.ericwei.project333;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ericwei.project333.clothes_tab.FirstFragment;
import com.ericwei.project333.job_service.ScheduleJobUtilities;
import com.ericwei.project333.profile_tab.SettingsFragment;
import com.ericwei.project333.wardrobe_tab.WardrobeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ItemAddedBroadcastReceiver receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate!!!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Item");
        tabLayout.getTabAt(1).setText("26/33");
        tabLayout.getTabAt(2).setText("Settings");

        intentFilter = new IntentFilter();
        receiver = new ItemAddedBroadcastReceiver();

        intentFilter.addAction(getString(R.string.action_item_added));
        registerReceiver(receiver, intentFilter);

        ScheduleJobUtilities.scheduleDeleteTodayOutfit(this);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume!!!");
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment(), "ONE");
        adapter.addFragment(new WardrobeFragment(), "TWO");
        adapter.addFragment(new SettingsFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }

    private class ItemAddedBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(getString(R.string.action_item_added))) {
                Log.d(TAG, "update the ITEM COUNT!!!!");
                tabLayout.getTabAt(1).setText("2");
            }

        }
    }

}
