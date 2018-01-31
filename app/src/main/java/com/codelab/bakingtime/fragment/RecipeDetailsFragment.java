package com.codelab.bakingtime.fragment;

import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;


public class RecipeDetailsFragment extends Fragment {

    private TextView title, description;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private ImageView thumbView;

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
        simpleExoPlayerView = rootView.findViewById(R.id.video_player);
        thumbView = rootView.findViewById(R.id.thumb_view);

        loadData();

        setRetainInstance(true);

        return rootView;
    }

    private void loadData() {

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

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }


/*    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            releasePlayer();
        }
    }*/


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
    
}
