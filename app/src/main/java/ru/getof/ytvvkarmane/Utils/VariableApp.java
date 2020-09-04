package ru.getof.ytvvkarmane.Utils;

import androidx.multidex.MultiDexApplication;

public class VariableApp extends MultiDexApplication {

    public static final String MRH_LOGIN="YuTV";
    public static final String MRH_PASS_1="QB0u5k2GuFGl74ytXpOw";
    public static final String MRH_PASS_2="PT0ITk3CI1U06wSWYuuT";


    @Override
    public void onCreate() {
        super.onCreate();
        /*AuthUser.initInstance();*/
    }
}
