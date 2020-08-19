package com.example.emovieapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.emovieapp.Constants;
import com.example.emovieapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class youtubeVideo extends YouTubeBaseActivity {
    String movieTrailer;
    @BindView(R.id.myPlayer)
    YouTubePlayerView trailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);
        ButterKnife.bind(this);
        movieTrailer = getIntent().getStringExtra("trailer");
        Animation animation = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, dpToPx(100, this));
        animation.setDuration(1000);
        animation.setFillEnabled(true);

        initializeTrailerPlayer();

    }

    private void initializeTrailerPlayer() {
        trailer.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(movieTrailer);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


}