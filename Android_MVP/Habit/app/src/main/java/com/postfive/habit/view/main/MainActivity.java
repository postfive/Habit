package com.postfive.habit.view.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.adpater.PagerAdapter;
import com.postfive.habit.adpater.pager.SectionsPagerAdapter;
import com.postfive.habit.navigation.BottomNavigationViewHelper;
import com.postfive.habit.view.login.LoginActivity;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private MenuItem mMenuItem;
    private MenuItem mBottomNavigationMenu;

    @Override
    protected void onStart() {
        super.onStart();

        // db확인
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getHashKey(getApplicationContext());

/*
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
*/


        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_main);
        BottomNavigationViewHelper.removeShiftMode(mBottomNavigationView);

        // BottomNavigation 선택 리스터
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // Viewpager Adapter
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // viewpager
        mViewPager = (ViewPager)findViewById(R.id.viewpager_main);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mMenuItem = mBottomNavigationView.getMenu().getItem(0);

        sectionsPagerAdapter.setListItem(0);
        sectionsPagerAdapter.setListItem(1);
        sectionsPagerAdapter.setListItem(2);
        sectionsPagerAdapter.setListItem(3);

        sectionsPagerAdapter.notifyDataSetChanged();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.home :
                            mViewPager.setCurrentItem(0);
                            return true;
                        case R.id.search :
                            mViewPager.setCurrentItem(1);
                            return true;
                        case R.id.favorite :
                            mViewPager.setCurrentItem(2);
                            return true;
                        case R.id.setting :
                            mViewPager.setCurrentItem(3);
                            return true;
                        default :
                            break;

                    }
                    return false;
                }
            };

    private  ViewPager.OnPageChangeListener pageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if ( mMenuItem != null) {
                        mMenuItem.setChecked(false);
                    }
                    mMenuItem = mBottomNavigationView.getMenu().getItem(position);
                    mMenuItem.setChecked(true);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };

    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";
        String keyHash = null;

        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }

    }


    /* toolbar 붙이기 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_appbar, menu);
*/

        return super.onCreateOptionsMenu(menu);
    }

    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
/*        switch (item.getItemId()) {

            case android.R.id.home:
//                onClickHome();
                break;
            case R.id.btn_logout:
                logout();
                break;
            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    /* 커스텀 툴바 이용
    https://stablekernel.com/using-custom-views-as-menu-items/
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

       /* MenuItem alertMenuItem = menu.findItem(R.id.app_bar_switch_layout);
        RelativeLayout rootView = (RelativeLayout) alertMenuItem.getActionView();

        mSwitch = (Switch)rootView.findViewById(R.id.app_bar_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,TAG+ " Switch " + isChecked);

                if(isChecked)
                    alarm.setEnable(1);
                else
                    alarm.setEnable(0);
            }
        });*/

        return super.onPrepareOptionsMenu(menu);
    }

    private void logout(){
        if(LoginActivity.mFirebaseAuth == null){
            Toast.makeText(this,"로그인한 사용자가 아닙니다", Toast.LENGTH_SHORT);
        }else{
            LoginActivity.mFirebaseAuth.signOut();
        }

        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }
}
