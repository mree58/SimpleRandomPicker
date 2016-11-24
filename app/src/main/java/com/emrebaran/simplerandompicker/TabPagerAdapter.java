package com.emrebaran.simplerandompicker;

/**
 * Created by mree on 24.11.2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NumbersFragment tab1 = new NumbersFragment();
                return tab1;
            case 1:
                LettersFragment tab2 = new LettersFragment();
                return tab2;
            case 2:
                CustomFragment tab3 = new CustomFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}