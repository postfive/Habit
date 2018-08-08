package com.postfive.habit.view.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.postfive.habit.view.survey.SurveyActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String PREFS_NAME = "Init";

    private UserHabitRespository mUserHabitRespository;
    private HabitRespository mHabitRespository;
    private boolean isFinish = false;
    UserSettingValue mUserSettingValue;

    private ImageView mImageView;
    private LinearLayout mBtnKakao;
    private LinearLayout mBtnFacebook;
    private LinearLayout mBtnGoogle;
    private LinearLayout mBtnNoLogin;
    private TextView mTextviewForgetAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Log.d(TAG, "언제?1");

        mUserSettingValue = new UserSettingValue(getApplication());

        mImageView = (ImageView)findViewById(R.id.imageview_login_bg);
        mBtnKakao = (LinearLayout)findViewById(R.id.btn_login_kakao);
        mBtnFacebook = (LinearLayout)findViewById(R.id.btn_login_facebook);
        mBtnGoogle = (LinearLayout)findViewById(R.id.btn_login_google);
        mBtnNoLogin = (LinearLayout)findViewById(R.id.btn_no_login);
        mTextviewForgetAccount = (TextView)findViewById(R.id.textview_forget_account);

        // 앱 최초 실행 여부 확인
        if(mUserSettingValue.init()) {



            // 디비 초기화
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
            new CheckTypesTask(progressBar).execute();


        mBtnKakao.setOnClickListener(this);
        mBtnFacebook.setOnClickListener(this);
        mBtnGoogle.setOnClickListener(this);
        mBtnNoLogin.setOnClickListener(this);
        mTextviewForgetAccount.setOnClickListener(this);

        }else{
//            mImageView.setVisibility(View.INVISIBLE);
            mBtnKakao.setVisibility(View.INVISIBLE);
            mBtnFacebook.setVisibility(View.INVISIBLE);
            mBtnGoogle.setVisibility(View.INVISIBLE);
            mBtnNoLogin.setVisibility(View.INVISIBLE);
            mTextviewForgetAccount.setVisibility(View.INVISIBLE);

            Log.d(TAG, "언제?2");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("init",1);
            startActivity(intent);
            finish();

            Log.d(TAG, "언제?3");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.textview_forget_account){
            Toast.makeText(this,"계정찾기", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
        finish();
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressBar progressBar;

        public CheckTypesTask(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                connectDB();
                populateWithTestData();
                while(!isFinish) {
                    Log.d(TAG,"DB TEST 진행중");
                    Thread.sleep(500);
                }

                Log.d(TAG,"DB TEST 끝");
            } catch (Exception e){

            }
            /*catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.INVISIBLE);
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

        Habit bicycle = new Habit(1, Habit.HEALTH_CATEGORY, "자전거타기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_bicycle);
        Habit run     = new Habit(2, Habit.HEALTH_CATEGORY, "달리기 하기", Unit.COUNT_UNIT, Habit.NIGHT_TIME, 0, 1, 0, "blue", R.drawable.ic_run);
        Habit walk    = new Habit(3, Habit.HEALTH_CATEGORY, "만보 걷기", Unit.WALK_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_walking);
        Habit yoga    = new Habit(4, Habit.HEALTH_CATEGORY, "요가 하기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 0, 1, 0, "blue", R.drawable.ic_yoga);

        Habit fruits     = new Habit(5, Habit.EAT_CATEGORY, "아침에 사과 먹기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 0, 1, 0, "blue", R.drawable.ic_apple);
        Habit dryfruits  = new Habit(6, Habit.EAT_CATEGORY, "견과류먹기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_dry_fruits);
        Habit drinkwater = new Habit(7, Habit.EAT_CATEGORY, "물마시기", Unit.LIQUID_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_water);
        Habit drinktea   = new Habit(8, Habit.EAT_CATEGORY, "차마시기", Unit.LIQUID_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_tea);
        Habit hi         = new Habit(9, Habit.SOCIAL_LIFE_CATEGORY, "인사하기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_greet);
        Habit write      = new Habit(10, Habit.SOCIAL_LIFE_CATEGORY, "필기하기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_listup);
        Habit cleanup    = new Habit(11, Habit.SOCIAL_LIFE_CATEGORY, "정리하기", Unit.COUNT_UNIT, Habit.ALLDAY_TIME, 0, 1, 0, "blue", R.drawable.ic_cleanup);
        Habit wakeup    = new Habit(12, Habit.SOCIAL_LIFE_CATEGORY, "기상하기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 0, 1, 0, "blue", R.drawable.ic_clock);
        Habit mind       = new Habit(13, Habit.MIND_CATEGORY, "명상하기", Unit.COUNT_UNIT, Habit.MORNING_TIME, 0, 1, 0, "blue", R.drawable.ic_meditation);

        List<Habit> habitli = new ArrayList<>();
        habitli.add(bicycle);
        habitli.add(run);
        habitli.add(walk);
        habitli.add(yoga);
        habitli.add(fruits);
        habitli.add(dryfruits);
        habitli.add(drinkwater);
        habitli.add(drinktea);
        habitli.add(hi);
        habitli.add(write);
        habitli.add(cleanup);
        habitli.add(wakeup);
        habitli.add(mind);

        mHabitRespository.insertAllHabit(habitli);

        CelebHabitMaster celebHabitmaster2 = new CelebHabitMaster("스티브잡스",1,"전 세계를 바꾼 스티브 잡스, \n혁신을 위한 라이프스타일", "스티브잡스 라이프스타일", "전 세계 라이프스타일을 바꾼 \n스티브 잡스, \n혁신을 위한 라이프스타일",  "스티브잡스는 현대 IT 산업의 선구자였습니다.\n" +
                "그의 단순함에 대한 철학과 창의성은 Mac PC, iPod,\n" +
                "iPhone, iPad까지 완벽하고 압도적인 성공을 통해\n" +
                "Apple 社를 세계시장의 정상에 서게 하였습니다\n" +
                "잡스는 사람들에게 기술을 통한 아름다움, 견고함,\n" +
                "그리고 효율성에 대한 기준을 심어주었죠.\n" +
                "\n" +
                "스티브잡스의 자서전과 인터뷰,\n" +
                "여러 많은 자료를 수집해 그의 평소 생활패턴을\n" +
                "재구성해 우리 일상에서 '잡스처럼' 내면을 중시하는\n" +
                "첫번째 라이프스타일 프로젝트를 준비했습니다!","img_kimjongkuk_list.jpg", R.drawable.famous_list_04, R.drawable.famous_detail);
        CelebHabitMaster celebHabitmaster = new CelebHabitMaster("박보람",2,"박보람의 40kg \n다이어트 습관", "75kg에서 40kg로 만드는 방법","박보람의 40kg 만드는\n다이어트 라이프스타일", "박보람이 알려주는 라이프스타일 팁으로\n다이어트의 기초를 탄탄히 다져보세요!","img_parkboram_list.jpg", R.drawable.img_parkboram_title, R.drawable.famous_detail_parkboram);
        CelebHabitMaster celebHabitmaster3 = new CelebHabitMaster("김종국",3,"김종국의 군살없는\n라이프스타일", "최강의 트레이닝", "...",  "...","img_kimjongkuk_list.jpg", R.drawable.img_kimjongkuk_title,1);
        CelebHabitMaster celebHabitmaster4 = new CelebHabitMaster("안영이",4,"미생 안영이처럼\n똑똑하게 회사생활하기", "처음 회사생활 시작하는 신입사원 추천", "...",  "...", "img_ahnyoungi.jpg", R.drawable.img_ahnyoungi_title,1);


        mHabitRespository.insertCelebHabitMaster(celebHabitmaster2);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster3);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster4);

        CelebHabitDetail celebHabitd1 = new CelebHabitDetail(2, 1,Habit.MORNING_TIME,  7, "아메리카노 마시기", "공복에 아메리카노 마시기", "", "박보람은 아침 유산소 운동 전\n" +
                                            "공복에 아메리카노를 한잔씩 마셔요.\n" +
                                            "카페인이 지방 연소에 윤활유 역할을 한다고해요."
                                            ,"07:00", 124, 1, 1, Unit.COUNT_UNIT, "회", "img_parkboram_detail_1.png", "blue", R.drawable.ic_water, R.drawable.img_parkboram_detail_1);

        CelebHabitDetail celebHabitd2 = new CelebHabitDetail(2, 2, Habit.MORNING_TIME, 2, "유산소 운동하기", "유산소 운동하기", "", "또한 공복의 유산소 운동은 \n" +
                "체내에 축적되어 있던 탄수화물을\n" +
                "긴 수면시간 동안 천천히 소모시키게 된\n" +
                "상태이기 때문에 음식 섭취를 하기 전인 오전 공복에 유산소를 할 경우 체지방을 끌어내어\n" +
                "활동 에너지로 사용하기 때문에 효과가 좋습니다.\n" +
                "\n" +
                "월,수,금 -  달리기\n" +
                "화,목 - 맨손 체조","07:30",124, 20, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_1.png", "blue", R.drawable.ic_walking, R.drawable.img_parkboram_detail_2);

        CelebHabitDetail celebHabitd3 = new CelebHabitDetail(2, 3, Habit.AFTERNOON_TIME, 2, "계단오르기", "계단오르기"
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
                "신체 기능 향상과 체력 유지에 효과적입니다.", "15:00",84, 20, 1, Unit.WALK_UNIT,"층", "img_parkboram_detail_2.png", "blue", R.drawable.ic_run, R.drawable.img_parkboram_detail_3);

        CelebHabitDetail celebHabitd4 = new CelebHabitDetail(2, 4, Habit.NIGHT_TIME, 3, "근력운동 하기", "근력운동 하기", "", "런지 30회 3세트\n" +
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
                "플랭크 30초 2세트","20:00",254, 20, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_3.png", "blue", R.drawable.ic_run, R.drawable.img_parkboram_detail_4);

        CelebHabitDetail celebHabitd5 = new CelebHabitDetail(2, 5,  Habit.NIGHT_TIME,3, "닭가슴살 쉐이크 먹기", "닭가슴살 쉐이크 먹기", ""
                , "닭가슴살 1개 + 삶은 달걀 흰자 2개 + \n" +
                "미니 양배추 1개 + 아몬드 3알 + 바나나1개","20:00",254, 1, 1,Unit.COUNT_UNIT, "회", "img_parkboram_detail_3.png", "blue", R.drawable.ic_water, R.drawable.img_parkboram_detail_5);



        mHabitRespository.insertCelebHabitDetail(celebHabitd1);
        mHabitRespository.insertCelebHabitDetail(celebHabitd2);
        mHabitRespository.insertCelebHabitDetail(celebHabitd3);
        mHabitRespository.insertCelebHabitDetail(celebHabitd4);
        mHabitRespository.insertCelebHabitDetail(celebHabitd5);


        CelebHabitDetail celebHabitd21 = new CelebHabitDetail(1, 1,Habit.MORNING_TIME,  11, "일어나기", "일어나기", "", "1999년, 한 인터뷰에서 스티브잡스는\n" +
                "자신이 아주 아침형 인간이라고 말했습니다.\n" +
                "매일 아침 6시에 일어나고\n" +
                "6시 15분이면 최고 효율을 낼 수 있을 정도로\n" +
                "아침을 깨끗한 정신으로 맞이했다고 합니다."
                ,"06:00", 124, 1, 1, Unit.COUNT_UNIT, "회", "img_parkboram_detail_1.png", "blue", R.drawable.ic_clock, R.drawable.sub_jobs_1);

        CelebHabitDetail celebHabitd22 = new CelebHabitDetail(1, 2, Habit.MORNING_TIME, 2, "필기하기", "하루 계획 하기", "\"지난 33년 동안, 매일 아침 거울을 보며 \n" +
                "자신에게 질문했습니다. \n" +
                "만약 오늘이 내 인생 마지막 날이라면, \n" +
                "오늘 내가 하려는 이 일을 하려고 할까? \n" +
                "만약 아니라는 대답이 며칠 연속으로 나온다면, \n" +
                "뭔가 변화가 필요하다는 것을 깨닫게 되죠\""
                , "또한 공복의 유산소 운동은 \n" +
                "'나도 곧 죽겠지'라는 사실을 항상 명심한다면\n" +
                "인생에 있어서 중차대한 결정을 내려야 할 때 \n" +
                "큰 도움이 됩니다.\n" +
                "왜냐면 대부분의 것들이 외부의 기대치나, \n" +
                "실패 혹은 창피에 대한 두려움과 같이\n" +
                "죽음앞에선 \n" +
                "한낱 아무것도 아닌 것들일 뿐이기 때문이죠.\n" +
                "\n" +
                "죽음 앞에선 스티브잡스도 당신도 평등합니다.\n" +
                "아침에 업무 시작 전,\n" +
                "나의 목표를 생각하보며\n" +
                "오늘 할 일을 적어보고 어떻게 진행 할 지 \n" +
                "하나하나 머릿속으로\n" +
                "생각해보고 작성하는 시간을 가져보세요.","07:00",124, 1, 1, Unit.COUNT_UNIT,"회", "img_parkboram_detail_1.png", "blue", R.drawable.ic_listup, R.drawable.sub_jobs_2);

        CelebHabitDetail celebHabitd23 = new CelebHabitDetail(1, 3, Habit.AFTERNOON_TIME, 2, "산책하기", "산책하기"
                , ""
                ,"잡스는 시간이 날 때 마다\n" +
                "복잡한 생각을 정리하기 위해 산책을 했다고 합니다.\n" +
                "\n" +
                "\"절대 책을 가져갈 생각 마세요. \n" +
                "산책의 목적은\n" +
                "머릿속을 쉬게하는겁니다. \n" +
                "그래서 산책하는동안 어떤 생각도\n" +
                "하지 않아야 하는 대신 \n" +
                "당신 주변에 있는 사물들에 정신을 집중하세요.\n" +
                "\n" +
                "하루 한번씩은, 복잡한 머리를 정리하기 위해\n" +
                "아무생각없이 산책한번씩 해봐요!", "16:00",254, 20, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_2.png", "blue", R.drawable.ic_walking, R.drawable.sub_jobs_3);

        CelebHabitDetail celebHabitd24 = new CelebHabitDetail(1, 4, Habit.NIGHT_TIME, 3, "수다떨기", "마음 맞는 사람들과 차 한잔과 수다떨기", "", "Apple이 전 세계적인 성공을 이룰 수 있었던\n" +
                "가장 큰 이유는 바로 \n" +
                "스티브잡스의 기발한 상상력이 녹아든 제품 입니다.\n" +
                "\n" +
                "많은 성공한 사업가들은 창의적인 아이디어를 위해 많은 사람들과 대화를 나눴는데\n" +
                "스티브 잡스의 경우 스티브 워즈니악과 팀쿡이 바로 그 상대였습니다.\n" +
                "두 사람은 매일같이 일과 관련되거나 전혀 관련없는 이야기를 나누며\n" +
                "서로 다른 의견을 가지는 부분들에서 항상 새로운 것을 발견하였다고 합니다.\n" +
                "\n" +
                "여러분도 차 한잔과 함께 마음 맞는 사람과 \n" +
                "수다를 떨면서 나와 다른 생각을 통해 \n" +
                "새로운 발견을 할 수 있을것입니다.","16:00",124, 10, 1, Unit.TIME_UNIT,"분", "img_parkboram_detail_3.png", "blue", R.drawable.ic_tea, R.drawable.sub_jobs_4);

        CelebHabitDetail celebHabitd25 = new CelebHabitDetail(1, 5,  Habit.NIGHT_TIME,3, "명상하기", "하루를 마무리하며 명상하기", "\"가만히 앉아서 내면을 들여다보면 \n" +
                "마음에 평온이 찾아오고 \n" +
                "현재의 순간이 한없이 확장되는 것이 느껴집니다\n" +
                "이것이 바로 마음의 수양이며, 지속적으로 훈련해야하는 것입니다.\""
                , "잡스는 청소년 시절, \n" +
                "자신의 정체성에 대해 끊임없이 고민을 했고,\n" +
                "대학에 들어간 후에는 동양, 불교 문화와 \n" +
                "연관된 영성과 깨달음에 관한\n" +
                "책들을 탐독하며 \n" +
                "친구들과 명상모임에 참여하기도 했습니다.\n" +
                "\n" +
                "특히 인도여행에서 동양사상과 수행에 매료되었고, 위와 같은 말을 남겼습니다.","20:00",124, 20, 1,Unit.TIME_UNIT, "분", "img_parkboram_detail_3.png", "blue", R.drawable.ic_meditation, R.drawable.sub_jobs_5);



        mHabitRespository.insertCelebHabitDetail(celebHabitd21);
        mHabitRespository.insertCelebHabitDetail(celebHabitd22);
        mHabitRespository.insertCelebHabitDetail(celebHabitd23);
        mHabitRespository.insertCelebHabitDetail(celebHabitd24);
        mHabitRespository.insertCelebHabitDetail(celebHabitd25);


        List<CelebHabitKit> celebHabitKits = new ArrayList<>();
        celebHabitKits.add(new CelebHabitKit(2, "투썸 아메리카노\n모바일 상품권", "#요즘 날씨엔 뜨아죠", R.drawable.img_tta));
        celebHabitKits.add(new CelebHabitKit(2, "아임 닭 1kg", "#저녁에 먹는 \n닭가슴살 쉐킷쉐킷", R.drawable.img_chi));
        celebHabitKits.add(new CelebHabitKit(2, "올리브영 \n바디쇼 요가메트", "#오늘밤 나와 함께 근력운동 \n헛둘 헛둘 메트", R.drawable.img_mat));
        celebHabitKits.add(new CelebHabitKit(2, "닌자 블랜디드\n모바일 상품권", "#다갈아 시...", R.drawable.img_mix));
        celebHabitKits.add(new CelebHabitKit(1, "자명종 시계\n", "#아침일찍 일어나기", R.drawable.product_jobs_1));
        celebHabitKits.add(new CelebHabitKit(1, "몰스킨 \n다이어리", "#오늘 할일 목표 적기", R.drawable.product_jobs_2));
        celebHabitKits.add(new CelebHabitKit(1, "스티브잡스 자서전\n", "#자극받기 #영감받기", R.drawable.product_jobs_3));
        celebHabitKits.add(new CelebHabitKit(1, "스타벅스 말차", "#차 한잔의 여유 #수다 필수품", R.drawable.product_jobs_4));

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

        disconnectDB();

        Log.d(TAG, "DB TEST 초기화 종료 ");
        isFinish = true;
    }

}
