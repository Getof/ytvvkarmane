package ru.getof.ytvvkarmane.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.getof.ytvvkarmane.MainFragments.FrLIVE;
import ru.getof.ytvvkarmane.MainFragments.FragmentChat;
import ru.getof.ytvvkarmane.MainFragments.FragmentHome;
import ru.getof.ytvvkarmane.MainFragments.FragmentRun;

public class FragmentHomeAdapter extends FragmentPagerAdapter {


    private String[] tabs;
    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public FragmentHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentHome.newInstance();
            case 1:
                return FrLIVE.newInstance();
            case 2:
                return FragmentRun.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    /*@NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }*/
}
