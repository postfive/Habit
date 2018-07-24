package com.postfive.habit.view.main;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.UserSettingValue;
import com.postfive.habit.navigation.BottomNavigationViewHelper;
import com.postfive.habit.view.celeblist.CelebListFragment;
import com.postfive.habit.view.login.LoginActivity;
import com.postfive.habit.view.myhabits.MyHabitsFragment;
import com.postfive.habit.view.setting.SettingFragment;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getHashKey(getApplicationContext());

//        Toast.makeText(this, " "+Integer.toString(UserSettingValue.getAfternoonPushHour()) , Toast.LENGTH_LONG).show();
//        Toast.makeText(this, " "+UserSettingValue.getResolutionValue() , Toast.LENGTH_LONG).show();

        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_main);
        BottomNavigationViewHelper.removeShiftMode(mBottomNavigationView);

        // BottomNavigation 선택 리스터
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        loadFragment(new MyHabitsFragment());

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment == null){
            return false;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.home :
                            fragment = new MyHabitsFragment();
                            break;
                        case R.id.favorite :
                            fragment = new CelebListFragment();
                            break;
                        case R.id.setting :
                            fragment = new SettingFragment();
                            break;
                        default :
                            break;

                    }
                    return loadFragment(fragment);
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
        return super.onCreateOptionsMenu(menu);
    }

    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    /* 커스텀 툴바 이용
    https://stablekernel.com/using-custom-views-as-menu-items/
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
