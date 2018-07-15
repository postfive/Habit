package com.postfive.habit.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.postfive.habit.R;
import com.postfive.habit.view.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private static final int GOOGLE_SIGN_IN = 9001;

    // 구글
    private SignInButton mSigninBtn;

    private Button mBtnLookAround;

    // Firebase auth
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseUser mFirebaseUser;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions mGoogleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // 로그인 여부 확인
        if ( mFirebaseUser == null ) {
            // 로그인 안되어있을때
            Toast.makeText(this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
        }else{

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // google-services.json의 클라이언트 id
                .requestEmail()
                .build();

        initComponent();
    }

    private void initComponent() {
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        // [START initialize_auth]
        mFirebaseAuth = FirebaseAuth.getInstance();
        // Google 로그인 버튼
        mSigninBtn = (SignInButton) findViewById(R.id.btn_sign_google);
        mBtnLookAround = (Button)findViewById(R.id.btn_look_around);

        mSigninBtn.setOnClickListener(this);
        mBtnLookAround.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_sign_google :
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
                break;
            case R.id.btn_look_around :
                Intent lookAround = new Intent(this, MainActivity.class);
                startActivity(lookAround);
            default :
                break;
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(LoginActivity.this, "인증실패",Toast.LENGTH_LONG).show();
            }
        }
    }


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());


        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        Task<AuthResult> authResultTask = mFirebaseAuth.signInWithCredential(credential);
//        Toast.makeText(LoginActivity.this, "cc", Toast.LENGTH_LONG).show();

        authResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                /*FirebaseUser firebaseUser = authResult.getUser();
                Toast.makeText(AuthActitivy.this, firebaseUser.getEmail(),Toast.LENGTH_LONG).show();
                firebaseUser.getEmail();*/
                Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
