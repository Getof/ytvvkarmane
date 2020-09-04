package ru.getof.ytvvkarmane.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.getof.ytvvkarmane.AppInterfaces.Api;

public class RetrofitClient {

    private static final String BASE_URL = "http://xn--80aeb5cm2e.xn--p1ai/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

}
