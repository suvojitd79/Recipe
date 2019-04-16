package com.example.recipe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe.Adapter.RecipeVideoAdapter;
import com.example.recipe.R;
import com.example.recipe.RecipeDetails;
import com.example.recipe.Retrofit.Response;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class Vid_1 extends Fragment {

    private Context context;
    private PlayerView playerView;
    private ProgressBar buffering;
    private SimpleExoPlayer simpleExoPlayer;
    private int currentPlaying = -1;
    private TextView vid_description;

    public void releasePlayer() {

        if (simpleExoPlayer != null) {

            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void setVid_description(String description){

        vid_description.setText(description);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.vid_1, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.video_recyclerView);
        playerView = v.findViewById(R.id.media);
        buffering = v.findViewById(R.id.media_progress);
        vid_description = v.findViewById(R.id.video_description);
        RecipeVideoAdapter recipeVideoAdapter = new RecipeVideoAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recipeVideoAdapter);
        Response response = ((RecipeDetails) context).getResponse();
        recipeVideoAdapter.update(response.getSteps());
        return v;
    }


    //exo player
    public void playVideo(Bundle data) {

        if(simpleExoPlayer != null && currentPlaying != data.getInt("position"))
            releasePlayer();

        //already playing
        if (simpleExoPlayer != null && currentPlaying == data.getInt("position")) {
            simpleExoPlayer.setPlayWhenReady(data.getBoolean("isPlay"));
            return;
        }

        currentPlaying = data.getInt("position");
        setVid_description(data.getString("video_description"));

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        //set up the exo player
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(factory);

        simpleExoPlayer
                = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(simpleExoPlayer);
        playerView.setUseController(true);


        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        buffering.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_ENDED:
                        simpleExoPlayer.seekTo(0);
                        break;
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_READY:
                        buffering.setVisibility(View.GONE);
                        break;

                }


            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


        DataSource.Factory factory1 = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "media"));

        //source
        MediaSource mediaSource
                = new ExtractorMediaSource.Factory(factory1)
                .createMediaSource(Uri.parse(data.getString("url")));

        simpleExoPlayer.prepare(mediaSource);

        simpleExoPlayer.setPlayWhenReady(data.getBoolean("isPlay"));

    }


}
