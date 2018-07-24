package com.postfive.habit.view.celeb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.adpater.celebdetaillist.celeblist.CelebDetailRecyclerViewAdapter;
import com.postfive.habit.adpater.celeblist.CelebRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.main.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CelebActivity extends AppCompatActivity  {

    private static final String TAG = "CelebActivity";
    private TextView textView_title;
    private ImageView imageViewTitle;
    private Button Buttonsave;
    private EditText editTextResolution;

    private RecyclerView mRecyclerViewCelebDetailList;
    private CelebDetailRecyclerViewAdapter mCelebRecyclerViewAdapter;
    private HabitRespository mHabitRespository;
    private List<CelebHabitDetail> mCelebHabitDetailList;
    private CelebHabitMaster mCelebHabitMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celeb);

        connectCelebDB();
        processIntent();
        componentInit();

    }

    private void connectCelebDB(){
        mHabitRespository = new HabitRespository(getApplication());
    }


    private void disconnectDB(){
        mHabitRespository.destroyInstance();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        processIntent();
    }

    private void processIntent() {
        Intent receivedIntent = getIntent();
        int celebCode = receivedIntent.getIntExtra("celebcode", 0);
//        int celebCode = 0;

        if(celebCode == 0){
            finish();
        }
        readCelebDetail(celebCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        componentInit();
        setComponent();
    }

    private void setComponent() {

        imageViewTitle.setImageDrawable(assignImage(mCelebHabitMaster.getImg()));
        String resoution = mCelebHabitMaster.getTitle();

        CharSequence charSequence = resoution;
        getSupportActionBar().setTitle(charSequence);

        ViewGroup.LayoutParams imageViewTitleLayoutParams = imageViewTitle.getLayoutParams();

        imageViewTitleLayoutParams.height = getResources().getDisplayMetrics().heightPixels/3;
        imageViewTitleLayoutParams.width = getResources().getDisplayMetrics().widthPixels;

        Log.d(TAG, "mCelebHabitDetailList size " + mCelebHabitDetailList.size());
        mCelebRecyclerViewAdapter.setAllHabit(mCelebHabitDetailList);

    }

    private void readCelebDetail(int celebCode){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.isTerminated();
        mCelebHabitDetailList = mHabitRespository.getCelebHabit(celebCode);
        mCelebHabitMaster     =  mHabitRespository.getCelebHabitMater(celebCode);

        disconnectDB();
        if(mCelebHabitDetailList.size() < 0) {
            finish();
        }
        if(mCelebHabitMaster == null) {
            finish();
        }

    }

    private void componentInit(){
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_celeb);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        actionBar.setDisplayShowTitleEnabled(false);

        imageViewTitle = (ImageView)findViewById(R.id.image_celeb_title);

        mCelebRecyclerViewAdapter = new CelebDetailRecyclerViewAdapter();

        mRecyclerViewCelebDetailList = (RecyclerView)findViewById(R.id.recyclerview_celeb_detail_list);

        mRecyclerViewCelebDetailList.setAdapter(mCelebRecyclerViewAdapter);
        mRecyclerViewCelebDetailList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCelebDetailList.setNestedScrollingEnabled(false);


        Buttonsave = (Button)findViewById(R.id.btn_habit_save);


        editTextResolution = (EditText)findViewById(R.id.edittext_user_resolution);

        editTextResolution.setHint(mCelebHabitMaster.getResolution());
    }

    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onClickHome();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // 뒤로
    private void onClickHome(){
        /*Intent intent = new Intent(this, MainActivity.class);
        //setResult(RESULT_CANCELED, intent);
        startActivity(intent);*/
        finish();
    }


    private Drawable assignImage(String imgUri){
        InputStream inputStream = null;
        Drawable img = null;

        try{
            Log.d(TAG, "imgUri "+ imgUri.toString());
            inputStream = this.getResources().getAssets().open(imgUri);
            img = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


    public void onClickSave(View v){
        String strResolution = editTextResolution.getText().toString();


        if(strResolution.length() < 1){
            Toast.makeText(this,"다짐 입력을 하지 않으셨습니다.", Toast.LENGTH_LONG).show();
//            final boolean[] isOk = {false};
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // 제목셋팅
            alertDialogBuilder.setTitle("다짐을 입력하지 않으셨습니다!");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("습관시작 전\n다짐을 입력해주세요")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            editTextResolution.requestFocus();
                        }
                    })
                    .setNegativeButton("그냥시작하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            setHabit();
                        }

                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else{
            setHabit();
        }
    }

    private void setHabit() {
        Toast.makeText(this,"저장!", Toast.LENGTH_LONG).show();;
        List<UserHabitDetail> userHabitDetailList  = new ArrayList<>();
        List<UserHabitState> userHabitStateList  = new ArrayList<>();
        int userDetailIdx = 0;
        int userStatedaypriority = 0 ;
        int userStateseq = 0 ;


        //connectCelebDB();


        for(int i = 0 ; i < 4 ;i++) {

            List<CelebHabitDetail> celebTimeTmp  = new ArrayList<>();
            for(int k = 0 ; k < mCelebHabitDetailList.size() ; k ++){
                CelebHabitDetail tmp = mCelebHabitDetailList.get(k);
                if(tmp.getTime() == i){
                    celebTimeTmp.add(tmp);
                }
            }


            Log.d(TAG, "for i "+ Integer.toString(i) );
            for (int j = 0; j < celebTimeTmp.size(); j++) {
//                user detail 습관 넣기
                CelebHabitDetail tmp = celebTimeTmp.get(j);
                //Log.d(TAG,  tmp.getTime() +"/"+ tmp.getPriority() +"/"+ tmp.getHabitcode() +"/"+  tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );

                if (tmp == null)
                    continue;

                Log.d(TAG, "for j "+ Integer.toString(j) );
                userDetailIdx++;
                UserHabitDetail usrtmp = new UserHabitDetail(userDetailIdx, tmp);
                userHabitDetailList.add(usrtmp);
                //Log.d(TAG,   usrtmp.getTime() +"/"+  usrtmp.getPriority() +"/"+ usrtmp.getHabitcode() +"/"+usrtmp.getName() +"/"+ usrtmp.getGoal() +"/"+ usrtmp.getDaysum() +"/"+ usrtmp.getFull() +"/"+ usrtmp.getUnit() );

                // user state 습관 넣기
                for (int dayofweek = 1; dayofweek < 8; dayofweek++) {
                    //Log.d(TAG, "for k "+ Integer.toString(k) );

                    if ((tmp.getDaysum() & (1 << dayofweek)) > 0) {
                        userStateseq++;
                        UserHabitState statetmp = new UserHabitState(userStateseq, dayofweek, usrtmp);
                        userHabitStateList.add(statetmp);

                    }
                }
            }

            celebTimeTmp = null;

        }
        UserHabitRespository mUserHabitRespository = new UserHabitRespository(getApplication());


        mUserHabitRespository.insertAllUserHabit(userHabitDetailList, userHabitStateList);

        mUserHabitRespository.destroyInstance();
        Toast.makeText(this,"저장! 완료", Toast.LENGTH_LONG).show();;
        //disconnectDB();
    }
}
