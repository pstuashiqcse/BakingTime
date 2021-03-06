package com.codelab.bakingtime.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


public class RecipeDetailsFragment extends Fragment {

    private TextView title, description;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private ImageView thumbView;

    private ArrayList<StepsModel> arrayList;
    private int index = 0;

    private long position = 0;
    private static final String POSITION_KEY = "position";

    private boolean shouldAutoPlay;
    private static final String AUTO_PLAY = "auto_play";

    public RecipeDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            arrayList = getArguments().getParcelableArrayList(Constants.KEY_STEPS);
            index = getArguments().getInt("index");
        }
        title = rootView.findViewById(R.id.title);
        description = rootView.findViewById(R.id.description);
        simpleExoPlayerView = rootView.findViewById(R.id.video_player);
        thumbView = rootView.findViewById(R.id.thumb_view);

        restoreBundleData(savedInstanceState);

        return rootView;
    }

    private void loadData() {

        if (arrayList != null && !arrayList.isEmpty()) {
            String titleText = arrayList.get(index).getShortDescription();
            String descriptionText = arrayList.get(index).getDescription();
            String videoUrl = arrayList.get(index).getVideoURL();
            String thumbUrl = arrayList.get(index).getThumbnailURL();

            if (titleText != null && !titleText.isEmpty()) {
                title.setText(titleText);
            } else {
                title.setVisibility(View.GONE);
            }

            if (descriptionText != null && !descriptionText.isEmpty()) {
                description.setText(descriptionText);
            } else {
                description.setVisibility(View.GONE);
            }

            if (videoUrl != null && !videoUrl.isEmpty()) {
                player = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(getActivity()),
                        new DefaultTrackSelector(), new DefaultLoadControl());

                simpleExoPlayerView.setPlayer(player);

                player.setPlayWhenReady(false);
                Uri uri = Uri.parse(videoUrl);
                MediaSource mediaSource = new ExtractorMediaSource.Factory(
                        new DefaultHttpDataSourceFactory("baking-time")).
                        createMediaSource(uri);
                player.prepare(mediaSource, true, false);

                if (position > 0 || shouldAutoPlay) {
                    player.setPlayWhenReady(shouldAutoPlay);
                    player.seekTo(position);
                }

                Log.e("Visible", "play");

            } else {
                simpleExoPlayerView.setVisibility(View.GONE);
            }

            if (thumbUrl != null && !thumbUrl.isEmpty()) {
                Glide.with(getActivity())
                        .load(thumbUrl)
                        .into(thumbView);
            } else {
                thumbView.setVisibility(View.GONE);
            }
        }
    }

    private void releasePlayer() {
        saveBundleData();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            loadData();
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
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBundleData();
        outState.putLong(POSITION_KEY, position);
        outState.putBoolean(AUTO_PLAY, shouldAutoPlay);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreBundleData(savedInstanceState);
    }

    private void saveBundleData() {
        if(player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            position = player.getCurrentPosition();
        }
    }

    private void restoreBundleData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(POSITION_KEY);
            shouldAutoPlay = savedInstanceState.getBoolean(AUTO_PLAY);

        }
    }
}
