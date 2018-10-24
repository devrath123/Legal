package com.example.devrathrathee.legal.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.devrathrathee.legal.views.TodayCasesFragment;
import com.example.devrathrathee.legal.views.TomorrowCasesFragment;
import com.example.devrathrathee.legal.views.WeeklyCasesFragment;

import java.util.ArrayList;
import java.util.List;

public class CasesPagerAdapter extends FragmentPagerAdapter {

    List<String> pageTitleList = new ArrayList<>();

     public CasesPagerAdapter(FragmentManager fragmentManager, List<String> pageTitleList){
        super(fragmentManager);
        this.pageTitleList.addAll(pageTitleList);
     }

    @Override
    public Fragment getItem(int position) {

         switch (position){
             case 0 : return TodayCasesFragment.newInstance();
             case 1 : return TomorrowCasesFragment.newInstance();
             case 2 : return WeeklyCasesFragment.newInstance();
         }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitleList.get(position);
    }
}
