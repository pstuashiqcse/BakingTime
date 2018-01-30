package com.codelab.bakingtime.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.StepAdapter;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;


public class RecipeDetailsFragment extends Fragment {

    private TextView title, description;
    //private VideoView videoView;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private ArrayList<StepsModel> arrayList;
    private int index;

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
        //videoView = rootView.findViewById(R.id.video_player);
        simpleExoPlayerView = rootView.findViewById(R.id.video_player);

        loadData();

        return rootView;
    }

    private void loadData() {

        String titleText = arrayList.get(index).getShortDescription();
        String descriptionText = arrayList.get(index).getDescription();
        String videoUrl = arrayList.get(index).getVideoURL();

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

        /*if (videoUrl != null && !videoUrl.isEmpty()) {
            MediaController mc = new MediaController(getActivity());
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri video = Uri.parse(videoUrl);
            videoView.setMediaController(mc);
            videoView.setVideoURI(video);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
        } else {
            videoView.setVisibility(View.GONE);
        }*/

        if (videoUrl != null && !videoUrl.isEmpty()) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            simpleExoPlayerView.setPlayer(player);

            player.setPlayWhenReady(true);
            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory("baking-time")).
                    createMediaSource(uri);
            player.prepare(mediaSource, true, false);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
        /* TODO: buffer load, progress counter, espresso, content provider to store recipe data, homescreen widget - ingredient list */
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
