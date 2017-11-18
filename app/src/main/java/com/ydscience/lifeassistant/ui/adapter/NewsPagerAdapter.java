package com.ydscience.lifeassistant.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.ydscience.lifeassistant.ui.fragment.PagerNewsFragment;
import com.ydscience.lifeassistant.utils.MyLog;

import java.util.List;

/**
 * Created by ydscience on 2017/6/12.
 */

public class NewsPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> pagerTitle;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;
    public NewsPagerAdapter(FragmentManager fm,List<String> titleLists,List<Fragment> list) {
        super(fm);
        this.mFragmentManager = fm;
        this.pagerTitle = titleLists;
        this.mFragmentList = list;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return pagerTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitle.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}

