package com.example.sandarumk.bakingapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.activities.RecipeStepDetailActivity;
import com.example.sandarumk.bakingapp.activities.RecipeStepListActivity;
import com.example.sandarumk.bakingapp.model.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a single RecipeStep detail screen.
 * This fragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment {

    public static final String ARG_ITEM = "item";

    private SimpleExoPlayer player;
    @BindView(R.id.view_player)
    PlayerView playerView;
    private Dialog mFullScreenDialog;
    @BindView(R.id.image_full_screen)
    ImageView mFullScreenIcon;
    @BindView(R.id.step_detail_container)
    ViewGroup stepContainerView;
    private ViewGroup.LayoutParams playerLayoutParams;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private boolean mExoPlayerFullscreen;

    private RecipeStep mItem;
    private Unbinder unbinder;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    public RecipeStepDetailFragment() {
    }

    public static RecipeStepDetailFragment newInstance(RecipeStep step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM)) {
            mItem = getArguments().getParcelable(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (mItem != null) {
            TextView text = rootView.findViewById(R.id.recipestep_detail);
            text.setText(mItem.getDescription());

            mFullScreenIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mExoPlayerFullscreen) {
                        closeFullscreenDialog();
                    } else {
                        openFullscreenDialog();
                    }
                }
            });

            if (mItem.getVideoURL() != null && !mItem.getVideoURL().isEmpty()) {
                playerView.setVisibility(View.VISIBLE);
            } else {
                playerView.setVisibility(View.GONE);
            }
            initFullscreenDialog();
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            openFullscreenDialog();
        }
    }

    private void initializePlayer() {
        if (mItem != null && mItem.getVideoURL() != null && !mItem.getVideoURL().isEmpty()) {
            String url = mItem.getVideoURL();
            if (player == null) {
                TrackSelection.Factory adaptiveTrackSelectionFactory =
                        new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this.getContext()),
                        new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
                playerView.setPlayer(player);
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playbackPosition);
            }
            MediaSource mediaSource = buildMediaSource(Uri.parse(url));
            player.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        Context context = this.getContext();
        if (context != null) {
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "bakingTime"), BANDWIDTH_METER);
            ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
            return factory.createMediaSource(uri);
        }
        return null;
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        playerLayoutParams = playerView.getLayoutParams();
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen_skrink);
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        playerView.setLayoutParams(playerLayoutParams);
        stepContainerView.addView(playerView, 0);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageResource(R.drawable.ic_fullscreen_expand);
    }

}
