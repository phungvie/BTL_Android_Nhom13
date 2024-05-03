package com.example.sonic.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sonic.fragment.HomeFragment;
import com.example.sonic.fragment.LibraryFragment;
import com.example.sonic.fragment.PremiumFragment;
import com.example.sonic.fragment.SearchFragment;
import com.example.sonic.myInterface.IToggle;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private IToggle mIToggle;
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);


    }

    public IToggle getmIToggle() {
        return mIToggle;
    }

    public void setmIToggle(IToggle mIToggle) {
        this.mIToggle = mIToggle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
//                return new SongFragment(new SongDTO(9, "bài hát 9", "", "/data/stream/HayTraoChoAnh-SonTungMTPSnoopDogg-6010660.mp3", null, ""));\
                HomeFragment viet01=new HomeFragment();
                viet01.setmIToggle(mIToggle);
                return viet01;
            case 1:
                SearchFragment viet02=new SearchFragment();
                viet02.setmIToggle(mIToggle);
                return viet02;
            case 2:
                LibraryFragment viet03=new LibraryFragment();
                viet03.setmIToggle(mIToggle);
                return viet03;
            case 3:
                return new PremiumFragment();
            default:
                HomeFragment viet0=new HomeFragment();
                viet0.setmIToggle(mIToggle);
                return viet0;


        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
