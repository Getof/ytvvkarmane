package ru.getof.ytvvkarmane.Utils;

public class AuthUser {

    private static AuthUser mInstance;
    private Boolean isUser;

    public static void initInstance(){
        if (mInstance == null){
            mInstance = new AuthUser();
        }
    }

    public static AuthUser getInstance(){
        return mInstance;
    }

    private AuthUser(){
        isUser = false;
    }

    public Boolean getUser() {
        return isUser;
    }

    public void setUser(Boolean user) {
        isUser = user;
    }
}
