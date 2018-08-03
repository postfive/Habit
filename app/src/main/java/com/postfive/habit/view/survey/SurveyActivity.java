package com.postfive.habit.view.survey;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.postfive.habit.R;
import com.postfive.habit.view.main.MainActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    private Spinner mSpinnerJob;
    private ToggleButton mBtnWoman;
    private ToggleButton mBtnMan;
    private DiscreteSeekBar mDiscreteSeekBar;

    private String mGender = null;
    private String mJob    = null;
    private int mAge       = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        List<String> jobList = new ArrayList<>();
        jobList.add("직업을 선택하세요");
        jobList.add("회사원");
        jobList.add("프리랜서");
        jobList.add("자영업");
        jobList.add("학생");

        // spinner;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner, jobList );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinnerJob = (Spinner)findViewById(R.id.spinner_job);
        mSpinnerJob.setAdapter(arrayAdapter);
        mSpinnerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                    mJob = (String)mSpinnerJob.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerJob.setId(0);


        mBtnWoman = (ToggleButton)findViewById(R.id.togglebtn_woman);
        mBtnMan = (ToggleButton)findViewById(R.id.togglebtn_man);
        mDiscreteSeekBar = (DiscreteSeekBar)findViewById(R.id.discreteseekbar);
        mDiscreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mAge=seekBar.getProgress();
            }
        });
    }

    public void onClickGender(View v){

        switch(v.getId()){
            case R.id.togglebtn_woman :
                mBtnWoman.setChecked(true);
                mBtnMan.setChecked(false);
                mGender = "woman";
                break;
            case R.id.togglebtn_man :
                mBtnWoman.setChecked(false);
                mBtnMan.setChecked(true);
                mGender = "man";
                break;
            default :
                mBtnWoman.setChecked(false);
                mBtnMan.setChecked(false);
                mGender = null;
                break;
        }
    }

    public void onClickSaveSurvey(View v){

        if(mGender == null){
            Toast.makeText(this,"성별을 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mJob == null){
            Toast.makeText(this,"직업을 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mAge < 0) {
            Toast.makeText(this,"나이대를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
