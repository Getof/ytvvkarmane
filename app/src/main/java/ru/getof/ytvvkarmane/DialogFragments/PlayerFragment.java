package ru.getof.ytvvkarmane.DialogFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.exoplayer2.ui.PlayerView;

import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.VideoPlayerService;

public class PlayerFragment extends DialogFragment {

    private String url_video;
    private PlayerView playerView;
    private VideoPlayerService player;

    public static PlayerFragment newInstance(){
        PlayerFragment f = new PlayerFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url_video = getArguments().getString("videoUrl");
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_player, container, false);
        playerView = v.findViewById(R.id.player_view);
        player = new VideoPlayerService(getActivity());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        player.init(getActivity(), playerView, url_video);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.reset();
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        player.release();
    }
}
