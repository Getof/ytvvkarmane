package ru.getof.ytvvkarmane.AppInterfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.getof.ytvvkarmane.Components.AdsHomeData;
import ru.getof.ytvvkarmane.Components.AdsNVideoData;
import ru.getof.ytvvkarmane.Components.AdsRadioData;
import ru.getof.ytvvkarmane.Components.RunId;
import ru.getof.ytvvkarmane.Components.RunList;
import ru.getof.ytvvkarmane.Components.VideoData.VideoDataYTV;
import ru.getof.ytvvkarmane.Components.WeatherData;

public interface Api {

    @GET("vkarmane/php_utils/newvideo.json")
    Call<VideoDataYTV> getVideoData();

    @GET("vkarmane/php_utils/ads_request.php")
    Call<AdsHomeData> getAdsHomeData();

    @GET("vkarmane/php_utils/ads_request_nvideo.php")
    Call<AdsNVideoData> getAdsNVideoData();

    @GET("vkarmane/php_utils/ads_request_radio.php")
    Call<AdsRadioData> getAdsRadioData();

    @GET("vkarmane/php_utils/run_request.php")
    Call<RunList> getRunListData();

    @GET("robokassa/pre_order.php?")
    Call<RunId> getRunId(@Query("sum") String sumRun, @Query("message") String textRun, @Query("days") String daysRun);

    @GET("vkarmane/php_utils/weather.json")
    Call<WeatherData> getWeather();

}
