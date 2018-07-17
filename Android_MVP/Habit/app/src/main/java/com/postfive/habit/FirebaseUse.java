package com.postfive.habit;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUse {
    private static String Uid;

    private static FirebaseAuth mFirebaseAuth =  FirebaseAuth.getInstance();
    private static FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
    private static FirebaseDatabase mDatabase;
    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static FirebaseUser getUser() {
        if (mFirebaseAuth == null) {
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
        }
        return mFirebaseUser;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (mFirebaseAuth == null) {
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mFirebaseAuth;
    }

}
