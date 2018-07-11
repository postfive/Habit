package com.postfive.habit.view.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.Snackbar;
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
import android.view.View;
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.adpater.PagerAdapter;
import com.postfive.habit.view.login.LoginActivity;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getHashKey(getApplicationContext());

        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Habit"));
        tabLayout.addTab(tabLayout.newTab().setText("통계"));
        tabLayout.addTab(tabLayout.newTab().setText("추천"));
        tabLayout.addTab(tabLayout.newTab().setText("설정"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_appbar_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
//                onClickHome();
                break;
            case R.id.btn_logout:
                logout();
                break;
            default:
                break;
        }
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

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
