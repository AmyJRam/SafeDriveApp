package com.example.torus.safedriveapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TORUS on 10/5/2017.
 */

public class PreferenceDetails {
    String GUARDIAN_NAME_KEY = "Guardian";
    String GUARDIAN_PHONE_KEY = "GuardianPhone";
    String SPEED_KEY = "Speed";
    String CHECK_SAFETY_KEY = "CheckSafety";
    String SMS_KEY = "SMS";
    String PREFERENCE_NAME = "UserDetails";
    String guardian_name;
    String guardian_phone;
    String speed;
    boolean check_safety;
    boolean sms;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    PreferenceDetails(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public String getGuardian_phone() {
        return sharedPreferences.getString(GUARDIAN_PHONE_KEY, null);
    }

    public void setGuardian_phone(String guardian_phone) {
        this.guardian_phone = guardian_phone;
        editor.putString(GUARDIAN_PHONE_KEY, guardian_phone);
        editor.commit();
    }


    public String getGuardian_name() {
        return sharedPreferences.getString(GUARDIAN_NAME_KEY, null);
    }

    public void setGuardian_name(String guardian_name) {
        this.guardian_name = guardian_name;
        editor.putString(GUARDIAN_NAME_KEY, guardian_name);
        editor.commit();
    }

    public String getSpeed() {
        return sharedPreferences.getString(SPEED_KEY, null);
    }

    public void setSpeed(String speed) {
        this.speed = speed;
        editor.putString(SPEED_KEY, speed);
        editor.commit();
    }

    public boolean isCheck_safety() {
        return sharedPreferences.getBoolean(CHECK_SAFETY_KEY, false);

    }

    public void setCheck_safety(boolean check_safety) {

        this.check_safety = check_safety;
        editor.putBoolean(SPEED_KEY, check_safety);
        editor.commit();
    }


    public boolean isSms() {
        return sharedPreferences.getBoolean(SMS_KEY, false);
    }

    public void setSms(boolean sms) {
        this.sms = sms;
        editor.putBoolean(SMS_KEY, sms);
        editor.commit();
    }
}
