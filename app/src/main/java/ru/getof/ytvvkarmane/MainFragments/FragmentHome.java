package ru.getof.ytvvkarmane.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.Adapters.AdsHomeAdapter;
import ru.getof.ytvvkarmane.Adapters.ContentHomeAdapter;
import ru.getof.ytvvkarmane.Components.AdsHomeData;
import ru.getof.ytvvkarmane.Components.VideoData.NewsList;
import ru.getof.ytvvkarmane.Components.VideoData.VideoDataYTV;
import ru.getof.ytvvkarmane.NewVideoActivity;
import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class FragmentHome extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private RecyclerView rv;
    private ViewPager vpAds;
    private AdsHomeAdapter adsHomeAdapter;
    private ProgressBar progressBar;
    private List<NewsList> newsDataList = new ArrayList<>();
    private VideoDataYTV videoDataYTV = null;
    private ContentHomeAdapter videoAdapter;

    public static FragmentHome newInstance() {
        FragmentHome fragmentHome = new FragmentHome();
        return fragmentHome;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LoadVideo();
        progressBar = view.findViewById(R.id.progressBar);

        rv = view.findViewById(R.id.rvContent);

        LoadAds();
        vpAds = view.findViewById(R.id.vp_Ads);

        return view;
    }

    private void LoadVideo() {

        RetrofitClient.getInstance()
                .getApi()
                .getVideoData()
                .enqueue(new Callback<VideoDataYTV>() {
                    @Override
                    public void onResponse(Call<VideoDataYTV> call, Response<VideoDataYTV> response) {
                        if (response.isSuccessful()) {
                            VideoReady(response.body());
                        } else {
                            Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoDataYTV> call, Throwable t) {

                    }
                });

    }

    private void VideoReady(final VideoDataYTV vd) {
        videoAdapter = new ContentHomeAdapter(getActivity(),vd.getNewsData());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        //rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(new ContentHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(getActivity(), NewVideoActivity.class);
                intent.putExtra("vid", vd.getNewsData().get(position).getId_video());
                intent.putExtra("vtitle", vd.getNewsData().get(position).getTitle_news());
                intent.putExtra("vdate", vd.getNewsData().get(position).getData_news());
                startActivity(intent);
            }
        });


    }

    private void LoadAds(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        RetrofitClient.getInstance()
                .getApi()
                .getAdsHomeData()
                .enqueue(new Callback<AdsHomeData>() {
                    @Override
                    public void onResponse(Call<AdsHomeData> call, Response<AdsHomeData> response) {
                        if (response.isSuccessful()) {
                            AdsReady(response.body());
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            adsHomeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(),response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdsHomeData> call, Throwable t) {

                    }

                });

    }

    private void AdsReady(AdsHomeData body) {
        adsHomeAdapter = new AdsHomeAdapter(getActivity(),body.getAdsBannerHome());
        vpAds.setAdapter(adsHomeAdapter);
    }
}
