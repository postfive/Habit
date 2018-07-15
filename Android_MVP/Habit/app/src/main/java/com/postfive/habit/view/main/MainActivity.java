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
import com.postfive.habit.navigation.BottomNavigationViewHelper;
import com.postfive.habit.view.celeblist.CelebListFragment;
import com.postfive.habit.view.login.LoginActivity;
import com.postfive.habit.view.myhabits.MyHabitsFragment;
import com.postfive.habit.view.setting.SettingFragment;
import com.postfive.habit.view.statistics.StatisticsFragment;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private MenuItem mMenuItem;

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
                        case R.id.search :
                            fragment = new StatisticsFragment();
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
