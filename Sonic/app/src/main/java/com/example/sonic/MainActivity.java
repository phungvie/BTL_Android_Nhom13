package com.example.sonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonic.adapter.MyViewPagerAdapter;
import com.example.sonic.databinding.ActivityMainBinding;
import com.example.sonic.fragment.LibraryFragment;
import com.example.sonic.model.UserDTO;
import com.example.sonic.myInterface.IToggle;
import com.example.sonic.network.remote.RetrofitClientToken;
import com.example.sonic.sharedPreferences.DataLocalManager;
import com.google.android.material.navigation.NavigationView;

import java.util.Optional;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//kiểm tra xem đã đăng nhập chưa
        String token = DataLocalManager.getToken();
        if (token.isEmpty()||token==null) {
            Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(mIntent);
        }


//không cho vuốt chuyển trang trong viewPager2
        binding.viewPager2.setUserInputEnabled(false);
        //hiẹue ứng chuyển trang
        //  binding.viewPager2.setPageTransformer(new ZoomOutPageTransformer());


//cài ActionBarDrawerToggle
        DrawerLayout mDrawerLayout = binding.drawerLayout;
        Toolbar mToolbar = binding.toolbar;

        setSupportActionBar(mToolbar);

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


//đặt tên cho NavigationView
        NavigationView mNavigationView = binding.navView;
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);
        TextView mTextViewNav = headerView.findViewById(R.id.textViewNameNavHeaderMain);
        Optional<UserDTO> OptionalUserDTO = Optional.ofNullable(DataLocalManager.getUserDTO());
        if (OptionalUserDTO.isPresent()) {
            mTextViewNav.setText(OptionalUserDTO.get().getName());
        } else {
            mTextViewNav.setText("null");
        }


//khở tạo MyViewPagerAdapter và truyền hàm xử lí ActionBarDrawerToggle cho các fragment
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        myViewPagerAdapter.setmIToggle( new IToggle() {
            //hàm tắt ActionBarDrawerToggle và đặt icon nút Toggle
            @Override
            public void TurnOnTheBackButton() {
                toggle.setDrawerIndicatorEnabled(false);
                toggle.setHomeAsUpIndicator(R.drawable.ic_left);

            }

            @Override
            public void TurnOnToggle() {
            }

            //khi fragment còn 1 trang cuối cùng thì sẽ quay về view_pager_2 và bật núttoggle
            @Override
            public void setToggle(LibraryFragment libraryFragment) {
                toggle.setToolbarNavigationClickListener(v -> {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        onBackPressed();
                        findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                        findViewById(R.id.fragment_container).setVisibility(View.GONE);
                        toggle.setDrawerIndicatorEnabled(true);
                        if(libraryFragment!=null){
                            libraryFragment.vietLoadLib();
                        }
                    } else {
                        onBackPressed();
                    }
                });
            }
        });

        binding.viewPager2.setAdapter(myViewPagerAdapter);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_home) {
                findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                findViewById(R.id.fragment_container).setVisibility(View.GONE);
                toggle.setDrawerIndicatorEnabled(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
// Loại bỏ tất cả các fragment hiện tại khỏi FrameLayout
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
// Commit thay đổi
                fragmentTransaction.commit();

                binding.viewPager2.setCurrentItem(0);
                binding.toolbar.setTitle(R.string.home);
            } else if (id == R.id.bottom_search) {
                findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                findViewById(R.id.fragment_container).setVisibility(View.GONE);
                toggle.setDrawerIndicatorEnabled(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// Loại bỏ tất cả các fragment hiện tại khỏi FrameLayout
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
// Commit thay đổi
                fragmentTransaction.commit();

                binding.viewPager2.setCurrentItem(1);
                binding.toolbar.setTitle(R.string.search);
            } else if (id == R.id.bottom_library) {
                findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                findViewById(R.id.fragment_container).setVisibility(View.GONE);
                toggle.setDrawerIndicatorEnabled(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// Loại bỏ tất cả các fragment hiện tại khỏi FrameLayout
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
// Commit thay đổi
                fragmentTransaction.commit();

                binding.viewPager2.setCurrentItem(2);
                binding.toolbar.setTitle(R.string.lib);
            } else if (id == R.id.bottom_premium) {
                findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                findViewById(R.id.fragment_container).setVisibility(View.GONE);
                toggle.setDrawerIndicatorEnabled(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// Loại bỏ tất cả các fragment hiện tại khỏi FrameLayout
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
// Commit thay đổi
                fragmentTransaction.commit();

                binding.viewPager2.setCurrentItem(3);
                binding.toolbar.setTitle(R.string.premium);
            }
            return true;
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigation.getMenu().findItem(R.id.bottom_search).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigation.getMenu().findItem(R.id.bottom_library).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavigation.getMenu().findItem(R.id.bottom_premium).setChecked(true);
                        break;
                }
            }
        });

        //

//        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý sự kiện ở đây
//                onBackPressed();
//            }
//        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bottom_inf_user) {
//            binding.viewPager2.setCurrentItem(4);

            Intent mIntent = new Intent(this, InfUserActivity.class);
            startActivity(mIntent);

        } else {
            if (id == R.id.bottom_logout) {
                DataLocalManager.setToken(null);
                DataLocalManager.setUserDTO(null);
                RetrofitClientToken.DestroyRetrofit();

                Intent mIntent = new Intent(this, LoginActivity.class);
                startActivity(mIntent);

            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean doubleBackToExitPressedOnce = false;
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            exitApp();
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Ấn back một lần nữa để thoát", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000); // Thời gian cho phép ấn back lần thứ hai
//    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (binding.viewPager2.getVisibility() == View.VISIBLE) {
                if (doubleBackToExitPressedOnce) {
                    exitApp();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Ấn back một lần nữa để thoát.", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000); // Thời gian cho phép ấn back lần thứ hai
            } else {
                if(getSupportFragmentManager().getBackStackEntryCount() == 1){
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    findViewById(R.id.view_pager_2).setVisibility(View.VISIBLE);
                    findViewById(R.id.fragment_container).setVisibility(View.GONE);
                    toggle.setDrawerIndicatorEnabled(true);
                    super.onBackPressed();
                }else{
                    super.onBackPressed();
                }

            }
        }

    }

    private void exitApp() {
        // Kết thúc tất cả các Activity và thoát khỏi ứng dụng
        finishAffinity();
    }
}

