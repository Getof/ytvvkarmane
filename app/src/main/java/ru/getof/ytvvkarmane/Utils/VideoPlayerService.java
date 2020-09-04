package ru.getof.ytvvkarmane.Utils;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ru.getof.ytvvkarmane.R;

public class VideoPlayerService implements AdsMediaSource.MediaSourceFactory {

    private final ImaAdsLoader adsLoader;
    private final DataSource.Factory dataSourceFactory;

    private SimpleExoPlayer player;
    private long contentPosition;

    public VideoPlayerService(Context context) {
        String adTag = "";
        adsLoader = new ImaAdsLoader(context, Uri.parse(adTag));
        dataSourceFactory =
                new DefaultDataSourceFactory(
                        context, Util.getUserAgent(context, context.getString(R.string.app_name)));
    }


    public void init(Context context, PlayerView playerView, String content_url) {
        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context);
        adsLoader.setPlayer(player);
        playerView.setPlayer(player);

        // This is the MediaSource representing the content media (i.e. not the ad).
        //String contentUrl = context.getString(R.string.content_url);
        MediaSource contentMediaSource = buildMediaSource(Uri.parse(content_url));

        // Compose the content media source into a new AdsMediaSource with both ads and content.
        MediaSource mediaSourceWithAds =
                new AdsMediaSource(
                        contentMediaSource, /* adMediaSourceFactory= */ this, adsLoader, playerView);

        // Prepare the player with the source.
        player.seekTo(contentPosition);
        player.prepare(mediaSourceWithAds);
        player.setPlayWhenReady(true);
    }

    public void reset() {
        if (player != null) {
            contentPosition = player.getContentPosition();
            player.release();
            player = null;
            adsLoader.setPlayer(null);
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
        adsLoader.release();
    }

    // AdsMediaSource.MediaSourceFactory implementation.

    @Override
    public MediaSource createMediaSource(Uri uri) {
        return buildMediaSource(uri);
    }

    @Override
    public int[] getSupportedTypes() {
        // IMA does not support Smooth Streaming ads.
        return new int[] {C.TYPE_DASH, C.TYPE_HLS, C.TYPE_OTHER};
    }

    // Internal methods.

    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }
}
