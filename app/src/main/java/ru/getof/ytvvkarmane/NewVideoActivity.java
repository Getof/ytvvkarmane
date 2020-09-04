package ru.getof.ytvvkarmane;


import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.Adapters.VideoGalleryAdapter;
import ru.getof.ytvvkarmane.Components.AdsData.AdsNVideoList;
import ru.getof.ytvvkarmane.Components.AdsNVideoData;
import ru.getof.ytvvkarmane.Components.VideoData.NewsList;
import ru.getof.ytvvkarmane.Components.VideoData.VideoDataYTV;

import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class NewVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String VIDEO_ID, TITLE_VIDEO_ID,VIDEO_DATE;
    private TextView titleVID, soderjVID;
    private RecyclerView rvGal;
    private List<NewsList> vDataList = new ArrayList<>();
    private List<AdsNVideoList> adsNVideoLists = new ArrayList<>();
    private VideoDataYTV vDataYTV = null;
    private VideoGalleryAdapter vAdapter;
    private NestedScrollView ns;
    public static final String API_KEY = "AIzaSyBe_TvodKulwLPymSvSQ8MuvBCwsAM0wP0";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayer ytbPlayer;
    private ImageView adaptiveImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        VIDEO_ID = getIntent().getStringExtra("vid");
        VIDEO_DATE = getIntent().getStringExtra("vdate");
        TITLE_VIDEO_ID = getIntent().getStringExtra("vtitle");
        YouTubePlayerView youTubePlayerView = findViewById(R.id.player_newvideo);
        titleVID = findViewById(R.id.titleVideo_id);
        soderjVID = findViewById(R.id.text_soderganye);
        ns = findViewById(R.id.nScrollView);
        ImageView imClose = findViewById(R.id.imageClose);
        imClose.setImageResource(R.drawable.ic_close_vidactivity);

        titleVID.setText(TITLE_VIDEO_ID);
        soderjVID.setText("Передача от "+VIDEO_DATE);
        youTubePlayerView.initialize(API_KEY, this);
        LoadAds();


        LoadList();

        rvGal = findViewById(R.id.rv_galery_video);

        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void LoadList() {
        RetrofitClient.getInstance()
                .getApi()
                .getVideoData()
                .enqueue(new Callback<VideoDataYTV>() {
                    @Override
                    public void onResponse(Call<VideoDataYTV> call, Response<VideoDataYTV> response) {
                        if (response.isSuccessful()) {
                            VideoList(response.body());
                        } else {
                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoDataYTV> call, Throwable t) {

                    }
                });
    }

    private void VideoList(final VideoDataYTV body) {
        vAdapter = new VideoGalleryAdapter(this,body.getNewsData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvGal.setLayoutManager(layoutManager);
        rvGal.setAdapter(vAdapter);
        /*rvGal.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return e.getAction() == MotionEvent.ACTION_MOVE;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/

        vAdapter.setOnItemClickListener(new VideoGalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ns.smoothScrollTo(0,0);
                playVideo(body.getNewsData().get(position).getId_video());
                titleVID.setText(body.getNewsData().get(position).getTitle_news());
                soderjVID.setText("Передача от "+body.getNewsData().get(position).getData_news());
            }
        });
    }

    private void LoadAds(){
        RetrofitClient.getInstance()
                .getApi()
                .getAdsNVideoData()
                .enqueue(new Callback<AdsNVideoData>() {
                    @Override
                    public void onResponse(Call<AdsNVideoData> call, Response<AdsNVideoData> response) {
                        if (response.isSuccessful()) {
                            AdsList(response.body());
                        } else {
                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdsNVideoData> call, Throwable t) {

                    }
                });
    }

    private void AdsList(AdsNVideoData body) {
        adaptiveImageView = findViewById(R.id.ads_block_pod_video);
        Glide
                .with(this)
                .load(body.getAdsBannerNVideo().get(0).getImageUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(adaptiveImageView);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            //youTubePlayer.setShowFullscreenButton(false);
            //youTubePlayer.setFullscreen(true);
            if (ytbPlayer == null) {
                ytbPlayer = youTubePlayer;
                ytbPlayer.cueVideo(VIDEO_ID);
            }
            //youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void playVideo(String videoID) {
        if (ytbPlayer != null) {
            ytbPlayer.cueVideo(videoID);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ytbPlayer.release();
    }



}
