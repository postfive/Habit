package com.postfive.habit.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.UserSettingValue;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitKit;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.Unit;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.noti.HabitNoti;
import com.postfive.habit.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String PREFS_NAME = "Init";

    private UserHabitRespository mUserHabitRespository;
    private HabitRespository mHabitRespository;

    UserSettingValue mUserSettingValue;

    private Button mBtnLookAround;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUserSettingValue = new UserSettingValue(this);

        // 앱 최초 실행 여부 확인
        if(mUserSettingValue.init()) {
            // 디비 초기화
            new CheckTypesTask().execute();
            connectDB();
            populateWithTestData();
        }else{
//            new CheckTypesTask().execute();

            Intent lookAround = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(lookAround);
            finish();
        }

        new HabitNoti(this).Alarm();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                LoginActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();

            Intent lookAround = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(lookAround);
            finish();
            super.onPostExecute(result);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 연결 끊기
        //disconnectDB();
    }

    private void connectDB(){

        mUserHabitRespository = new UserHabitRespository(getApplication());
        mHabitRespository = new HabitRespository(getApplication());
    }

    private void disconnectDB(){


        mUserHabitRespository.destroyInstance();
        mHabitRespository.destroyInstance();
    }



    private void populateWithTestData()
    {
        Log.d(TAG, "DB TEST  초기화 시작");
        // 설정 값 있을때는 그냥 종료

        // 다 지우고
        mHabitRespository.deleteAll();


        Unit unitLiquid1 = new Unit(Unit.LIQUID_UNIT, "L");
        Unit unitLiquid2 = new Unit(Unit.LIQUID_UNIT, "mL");


        Unit countUnit = new Unit(Unit.COUNT_UNIT, "회");

        Unit timeUnit1 = new Unit(Unit.TIME_UNIT, "분");
        Unit timeUnit2 = new Unit(Unit.TIME_UNIT, "시");

        Unit setUnit = new Unit(Unit.SET_UNIT, "Set");

        Unit walkUnit1 = new Unit(Unit.WALK_UNIT, "걸음");
        Unit walkUnit2 = new Unit(Unit.WALK_UNIT, "층");
        Unit walkUnit3 = new Unit(Unit.WALK_UNIT, "계단");

        // 유명인 set

        Habit bicycle = new Habit(1, Habit.HEALTH_CATEGORY, "자전거타기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 1, 1, 6, "blue", R.drawable.ic_bicycle);
        Habit run     = new Habit(2, Habit.HEALTH_CATEGORY, "달리기 하기", Unit.COUNT_UNIT, Habit.NIGHT_TIME, 1, 1, 6, "blue", R.drawable.ic_run);
        Habit walk    = new Habit(3, Habit.HEALTH_CATEGORY, "만보 걷기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 1, 1, 6, "blue", R.drawable.ic_walking);
        Habit yoga    = new Habit(4, Habit.HEALTH_CATEGORY, "요가 하기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 1, 1, 6, "blue", R.drawable.ic_yoga);

        Habit fruits     = new Habit(5, Habit.EAT_CATEGORY, "아침에 사과 먹기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 1, 1, 6, "blue", R.drawable.ic_apple);
        Habit dryfruits  = new Habit(6, Habit.EAT_CATEGORY, "견과류먹기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 5, 1, 6, "blue", R.drawable.ic_dry_fruits);
        Habit drinkwater = new Habit(7, Habit.EAT_CATEGORY, "물마시기", Unit.LIQUID_UNIT, Habit.ALLDAY_TIME, 10, 1, 6, "blue", R.drawable.ic_water);

        List<Habit> habitli = new ArrayList<>();
        habitli.add(bicycle);
        habitli.add(run);
        habitli.add(walk);
        habitli.add(yoga);
        habitli.add(fruits);
        habitli.add(dryfruits);
        habitli.add(drinkwater);

        mHabitRespository.insertAllHabit(habitli);

        CelebHabitMaster celebHabitmaster = new CelebHabitMaster("박보람",1,"박보람의 40kg \n다이어트 습관", "75kg에서 40kg로 만드는 방법","박보람의 40kg 만드는\n다이어트 라이프스타일", "박보람이 알려주는 라이프스타일 팁으로\n다이어트의 기초를 탄탄히 다져보세요!","img_parkboram_list.jpg", R.drawable.img_parkboram_title, R.drawable.famous_detail_parkboram);
        CelebHabitMaster celebHabitmaster2 = new CelebHabitMaster("김종국",2,"김종국의 군살없는\n라이프스타일", "최강의 트레이닝", "...",  "...","img_kimjongkuk_list.jpg", R.drawable.img_kimjongkuk_title,1);
        CelebHabitMaster celebHabitmaster3 = new CelebHabitMaster("안영이",3,"미생 안영이처럼\n똑똑하게 회사생활하기", "처음 회사생활 시작하는 신입사원 추천", "...",  "...", "img_ahnyoungi.jpg", R.drawable.img_ahnyoungi_title,1);
        CelebHabitMaster celebHabitmaster4 = new CelebHabitMaster("안영이",4,"미생 안영이처럼\n똑똑하게 회사생활하기", "처음 회사생활 시작하는 신입사원 추천", "...",  "...","img_ahnyoungi.jpg", R.drawable.famous_list,1);


        mHabitRespository.insertCelebHabitMaster(celebHabitmaster);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster2);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster3);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster4);

        CelebHabitDetail celebHabitd1 = new CelebHabitDetail(1, 1,Habit.MORNING_TIME,  7, "박보람 습관", "공복에 아메리카노 마시기", "", "박보람은 아침 유산소 운동 전\n" +
                                            "공복에 아메리카노를 한잔씩 마셔요.\n" +
                                            "카페인이 지방 연소에 윤활유 역할을 한다고해요."
                                            ,"07:00", 124, 1, 1, Unit.COUNT_UNIT, "회", "img_parkboram_detail_1.png", "blue", R.drawable.ic_water, R.drawable.img_parkboram_detail_1);

        CelebHabitDetail celebHabitd2 = new CelebHabitDetail(1, 2, Habit.MORNING_TIME, 2, "박보람 습관", "유산소 운동하기", "", "또한 공복의 유산소 운동은 \n" +
                "체내에 축적되어 있던 탄수화물을\n" +
                "긴 수면시간 동안 천천히 소모시키게 된\n" +
                "상태이기 때문에 음식 섭취를 하기 전인 오전 공복에 유산소를 할 경우 체지방을 끌어내어\n" +
                "활동 에너지로 사용하기 때문에 효과가 좋습니다.\n" +
                "\n" +
                "월,수,금 -  달리기\n" +
                "화,목 - 맨손 체조","07:30",124, 20, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_1.png", "blue", R.drawable.ic_water, R.drawable.img_parkboram_detail_2);

        CelebHabitDetail celebHabitd3 = new CelebHabitDetail(1, 3, Habit.AFTERNOON_TIME, 2, "박보람 습관", "계단오르기\n" +
                "(발 뒷꿈치부터) 한칸씩, 두칸씩 ~"
                , "(박보람)\n" +
                "계단을 생활화하면서 \n" +
                "조금씩 강도를 높이기로 했어요.\n" +
                "3층 왔다갔다 했던 걸 5층으로 늘려봤어요.\n" +
                "\n" +
                "사실 5층까지는 조금 땀 나다가? 괜찮더라구요.\n" +
                "\n" +
                "그래서 운동강도를 높이고자\n" +
                "20층까지 도전해봤어요!\n" +
                "\n" +
                "그렇게 20층을 1~10층은 한칸씩, 11~20층은 두칸씩\n" +
                "오르니 2주차부터는 효과가 있더라구요."
                ,"계단 오르기는 \n" +
                "무산소 운동과 유산소 운동 효과를 \n" +
                "모두 갖고 있기 때문에 \n" +
                "신체 기능 향상과 체력 유지에 효과적입니다.", "15:00",84, 20, 1, Unit.WALK_UNIT,"층", "img_parkboram_detail_2.png", "blue", R.drawable.ic_dry_fruits, R.drawable.img_parkboram_detail_3);

        CelebHabitDetail celebHabitd4 = new CelebHabitDetail(1, 4, Habit.NIGHT_TIME, 3, "박보람습관", "근력운동 하기", "", "런지 30회 3세트\n" +
                "1. 두 다리는 골반너비로 벌리고 \n" +
                "손은 허리에 두고 섭니다.\n" +
                "2. 오른쪽 발을 앞으로 어깨너비 두배 정도로 벌리고\n" +
                "동시에 왼발은 뒷꿈치를 세워 정면을 보고 섭니다.\n" +
                "3. 상체는 똑바로 편 상태로 앞에 둔 오른쪽 무릎을\n" +
                "90도로 굽히며 왼쪽 무릎은 바닥에 \n" +
                "닿기 전까지 내려갑니다.\n" +
                "4.복부에 단단히 힘을 주고 허벅지 힘으로 일어나고\n" +
                "왼쪽과 번갈아서 수행합니다.\n" +
                "\n" +
                "푸쉬업 10회 3세트\n" +
                "\n" +
                "플랭크 30초 2세트","20:00",254, 20, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_3.png", "blue", R.drawable.ic_walking, R.drawable.img_parkboram_detail_4);

        CelebHabitDetail celebHabitd5 = new CelebHabitDetail(1, 5,  Habit.NIGHT_TIME,3, "박보람습관", "저녁은 닭가슴살 쉐이크", ""
                , "닭가슴살 1개 + 삶은 달걀 흰자 2개 + \n" +
                "미니 양배추 1개 + 아몬드 3알 + 바나나1개","20:00",254, 1, 1,Unit.COUNT_UNIT, "회", "img_parkboram_detail_3.png", "blue", R.drawable.ic_walking, R.drawable.img_parkboram_detail_5);



        mHabitRespository.insertCelebHabitDetail(celebHabitd1);
        mHabitRespository.insertCelebHabitDetail(celebHabitd2);
        mHabitRespository.insertCelebHabitDetail(celebHabitd3);
        mHabitRespository.insertCelebHabitDetail(celebHabitd4);
        mHabitRespository.insertCelebHabitDetail(celebHabitd5);

        List<CelebHabitKit> celebHabitKits = new ArrayList<>();
        celebHabitKits.add(new CelebHabitKit(1, "투썸 아메리카노\n모바일 상품권", "#요즘 날씨엔 뜨아죠", R.drawable.img_tta));
        celebHabitKits.add(new CelebHabitKit(1, "아임 닭 1kg", "#저녁에 먹는 \n닭가슴살 쉐킷쉐킷", R.drawable.img_chi));
        celebHabitKits.add(new CelebHabitKit(1, "올리브영 \n바디쇼 요가메트", "#오늘밤 나와 함께 근력운동 \n헛둘 헛둘 메트", R.drawable.img_mat));
        celebHabitKits.add(new CelebHabitKit(1, "닌자 블랜디드\n모바일 상품권", "#다갈아 시...", R.drawable.img_mix));

        mHabitRespository.insertHabitKit(celebHabitKits);

        List<Unit> unitList = new ArrayList<>();
        unitList.add(unitLiquid1);
        unitList.add(unitLiquid2);
        unitList.add(countUnit);
        unitList.add(timeUnit1);
        unitList.add(timeUnit2);
        unitList.add(setUnit);
        unitList.add(walkUnit1);
        unitList.add(walkUnit2);
        unitList.add(walkUnit3);
        mHabitRespository.insertUnit(unitList);

        Log.d(TAG, "DB TEST 초기화 종료 ");

        disconnectDB();
    }

}
