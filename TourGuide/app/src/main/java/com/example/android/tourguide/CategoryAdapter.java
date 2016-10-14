package com.example.android.tourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Aadam on 9/29/2016.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TopSpotsFragment();
        } else if (position == 1) {
            return new IndoorsFragment();
        } else if (position == 2) {
            return new OutdoorsFragment();
        } else {
            return new HistoricalFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.top_spots);
        } else if (position == 1) {
            return mContext.getString(R.string.indoors);
        } else if (position == 2) {
            return mContext.getString(R.string.outdoors);
        } else {
            return mContext.getString(R.string.historical);
        }
    }
}
