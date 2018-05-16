package ro.atoming.bakingapp.ui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ro.atoming.bakingapp.R;
import ro.atoming.bakingapp.models.RecipeStep;

/**
 * Created by Bogdan on 5/10/2018.
 */

public class RecipeStepDetailFragment extends Fragment {
    private RecipeStep mRecipeStep ;
    private TextView mDescription;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mExoPlayerView;
    private String mVideoUrl;

    public RecipeStepDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_step_player,container,false);
        mDescription = view.findViewById(R.id.step_description);
        mExoPlayerView = view.findViewById(R.id.playerView);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getBundleExtra("currentStep");
        if (bundle!=null){
            mRecipeStep = bundle.getParcelable("step");
        }
        mDescription.setText(mRecipeStep.getDescription());
        mVideoUrl = mRecipeStep.getVideoUrl();
        initializaPlayer(Uri.parse(mVideoUrl));
        return view;
    }

    private void initializaPlayer (Uri mediaUri){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector,loadControl);
        mExoPlayerView.setPlayer(mExoPlayer);

        String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri,new DefaultDataSourceFactory(getActivity(),userAgent),
                new DefaultExtractorsFactory(),null,null);
        mExoPlayer.prepare(mediaSource);
    }
}
