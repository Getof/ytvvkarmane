package ru.getof.ytvvkarmane.MainFragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.Components.AdsRadioData;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.RadioPlayer;
import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class FrLIVE extends Fragment {

    private Boolean
            flagDfm = false,
            flagComedy = false;
    private Boolean ser;
    private Intent playIntent;
    private ImageView adaptiveImageView;

    private ImageView plDfm,plComedy;


    public static FrLIVE newInstance(){
        FrLIVE fr = new FrLIVE();
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_radio,container,false);

        ser=isServiceRunning(getActivity());
        playIntent = new Intent(getActivity(), RadioPlayer.class);
        if (ser) getActivity().stopService(playIntent);

        plDfm = v.findViewById(R.id.playDfm);
        plComedy = v.findViewById(R.id.playComedy);
        adaptiveImageView = v.findViewById(R.id.ads_block_radio);
        
        LoadAdsRadio();
        

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("fDfm")){
                plDfm.setImageResource(R.drawable.radio_stop);
                flagDfm = true;
            }
            if (savedInstanceState.getBoolean("fComedy")){
                plComedy.setImageResource(R.drawable.radio_stop);
                flagComedy = true;
            }
        }

        plDfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagComedy) {
                    plComedy.setImageResource(R.drawable.ic_play_circle_outline);
                    getActivity().stopService(playIntent);
                    flagComedy = !flagComedy;
                }
                if (!flagDfm) {
                    plDfm.setImageResource(R.drawable.radio_stop);
                    playIntent.putExtra("stream", "http://162.210.196.145:19075");
                    getActivity().startService(playIntent);
                    flagDfm = !flagDfm;
                } else {
                    plDfm.setImageResource(R.drawable.ic_play_circle_outline);
                    getActivity().stopService(playIntent);
                    flagDfm = !flagDfm;
                }
            }
        });

        plComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagDfm) {
                    plDfm.setImageResource(R.drawable.ic_play_circle_outline);
                    getActivity().stopService(playIntent);
                    flagDfm = !flagDfm;
                }
                if (!flagComedy) {
                    plComedy.setImageResource(R.drawable.radio_stop);
                    playIntent.putExtra("stream", "http://130.185.144.199:38297");
                    getActivity().startService(playIntent);
                    flagComedy = !flagComedy;
                } else {
                    plComedy.setImageResource(R.drawable.ic_play_circle_outline);
                    getActivity().stopService(playIntent);
                    flagComedy = !flagComedy;
                }
            }
        });

        return v;
    }

    private void LoadAdsRadio() {
        RetrofitClient.getInstance()
                .getApi()
                .getAdsRadioData()
                .enqueue(new Callback<AdsRadioData>() {
                    @Override
                    public void onResponse(Call<AdsRadioData> call, Response<AdsRadioData> response) {
                        if (response.isSuccessful()) {
                            AdsList(response.body());
                        } else {
                            Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdsRadioData> call, Throwable t) {

                    }
                });
    }

    private void AdsList(AdsRadioData body) {
        Glide
                .with(this)
                .load(body.getAdsBannerRadio().get(0).getImageUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(adaptiveImageView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fDfm",flagDfm);
        outState.putBoolean("fComedy",flagComedy);
    }

    private static boolean isServiceRunning(Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo service : services) {
            if (".RadioPlayer".equals(service.service.getShortClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ser = isServiceRunning(getActivity());
        if (ser) getActivity().stopService(playIntent);
    }
}
