package com.example.emovieapp.UI;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emovieapp.Adapters.CastAdapter;
import com.example.emovieapp.Classes.CastClass;
import com.example.emovieapp.Constants;
import com.example.emovieapp.Adapters.MovieAdapter;
import com.example.emovieapp.FavoriteMovies;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.R;
import com.example.emovieapp.Classes.VideoClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity implements CastAdapter.OnActorClickListener, MovieAdapter.onMovieClickListener {

    String movieTrailer;
    String movieTitle, movieFrontImageURL, movieBackImageURL, movieOverView;
    int movieRate;
    FavoriteMovies favoriteMovies;

    Context context = this;
    CastAdapter.OnActorClickListener listener = this;
    @BindView(R.id.play_movie)
    FloatingActionButton fab;
    @BindView(R.id.front_img)
    ImageView front;
    @BindView(R.id.front_card)
    CardView front_card;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cast)
    RecyclerView cast;
    @BindView(R.id.sim_tv)
    TextView sim_tv;
    @BindView(R.id.similar)
    RecyclerView similar;
    @BindView(R.id.recc_tv)
    TextView recc_tv;
    @BindView(R.id.recc)
    RecyclerView recc;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.rating_pb_detail)
    ProgressBar rate_pb;
    @BindView(R.id.rating_tv)
    TextView rate_tv;
    @BindView(R.id.desc_tv)
    TextView description;
    @BindView(R.id.back_img)
    ImageView back;
    @BindView(R.id.like_btn)
    ImageButton like;

    private boolean animate = true;
    private int PAGE = 1;
    private int movieId;
    private MovieAdapter.onMovieClickListener movieListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        movieId = getIntent().getIntExtra("id", 0);

        favoriteMovies = new FavoriteMovies(this);

        boolean isLiked = favoriteMovies.isLiked(movieId);

        if(isLiked){
            like.setBackground(getResources().getDrawable(R.drawable.like_it));
            like.setContentDescription("liked");
        }


        fillCastRecyclerView();

        fillSimilarRecyclerView();

        fillReccomendationRecyclerView();

        setHelperVariables();

        setOnFrontImageClickListener();

        setInnerRateBar();

        setMovieData();

        setFloatingActionButtonAction();

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnPressLikeButton();
            }
        });


    }


    private void setOnPressLikeButton() {
        like.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadeout));

        if(like.getContentDescription().equals("disliked")){
            like.setBackground(getResources().getDrawable(R.drawable.like_it));
            like.setContentDescription("liked");
            favoriteMovies.addNewMovie(movieId,movieTitle,movieFrontImageURL,movieRate,movieBackImageURL,movieOverView);
        }
        else {
            like.setBackground(getResources().getDrawable(R.drawable.favorite));
            like.setContentDescription("disliked");
            favoriteMovies.removeMovie(movieTitle);
        }
        like.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));

    }

    private void setFloatingActionButtonAction() {
        fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.back_animation));

        if (movieTrailer.equals("none"))
            fab.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (animate) {
                    Animation animation = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, dpToPx(100, context));
                    animation.setDuration(1000);
                    animation.setFillEnabled(true);
                    sv.startAnimation(animation);
                }
                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);

                final FrameLayout back = findViewById(R.id.back_frame);
                back.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        sv.setLayoutParams(params);
                        final Intent intent = new Intent(MovieDetails.this, youtubeVideo.class);
                        intent.putExtra("trailer", movieTrailer);
                        final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetails.this, back,
                                "back_trailer");
                        animate = false;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent, options.toBundle());
                            }
                        }, 5);
                    }
                }, 1000);

            }
        });
    }

    private void setMovieData() {
        Glide.with(this).load(Constants.IMAGE_BASE_URL + movieFrontImageURL).into(front);
        Glide.with(this).load(Constants.IMAGE_BASE_URL + movieBackImageURL).into(back);
        title.setText(movieTitle);
        description.setText(movieOverView);
    }

    private void setInnerRateBar() {
        rate_tv.setText(String.valueOf(movieRate));
        if (movieRate < 70) {
            if (movieRate >= 40) {
                rate_pb.setProgressDrawable(getResources().getDrawable(R.drawable.less_than_70));
            } else {
                rate_pb.setProgressDrawable(getResources().getDrawable(R.drawable.less_than_40));
            }
        }
        rate_pb.setProgress(movieRate);
    }

    private void setOnFrontImageClickListener() {
        front_card.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, MovieImage.class);
                intent.putExtra("imgURL", Constants.IMAGE_BASE_URL + movieFrontImageURL);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetails.this, front_card,
                        "front_image");

                startActivity(intent, options.toBundle());
            }
        });
    }

    private void setHelperVariables() {
        Intent intent = getIntent();
        movieTitle = intent.getStringExtra("title");
        movieFrontImageURL = intent.getStringExtra("front");
        movieBackImageURL = intent.getStringExtra("back");
        movieOverView = intent.getStringExtra("view");
        movieTrailer = intent.getStringExtra("trailer");
        movieRate = intent.getIntExtra("rate", 0);
    }

    private void fillReccomendationRecyclerView() {
        MovieViewModel reccViewModel = new MovieViewModel(Constants.BASE_URL, Constants.CATEGORY_RECCOMENDATION, Constants.LANGUAGE, Constants.API_KEY, PAGE, movieId);
        reccViewModel.getSpecialMovies();
        reccViewModel.getMovieMutableLiveData().observe(this, new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                MovieAdapter movieAdapter = new MovieAdapter(context, movieListener);
                movieAdapter.setMovies(resultsBeans);
                recc.setAdapter(movieAdapter);
                recc.setHasFixedSize(true);
                LinearLayoutManager mngr1 = new LinearLayoutManager(context);
                mngr1.setOrientation(RecyclerView.HORIZONTAL);
                recc.setLayoutManager(mngr1);
                if (resultsBeans.size() == 0) {
                    recc.setVisibility(View.GONE);
                    recc_tv.setVisibility(View.GONE);
                }
            }
        });

    }

    private void fillSimilarRecyclerView() {
        MovieViewModel similarViewModel = new MovieViewModel(Constants.BASE_URL, Constants.CATEGORY_SIMILAR, Constants.LANGUAGE, Constants.API_KEY, PAGE, movieId);
        similarViewModel.getSpecialMovies();
        similarViewModel.getMovieMutableLiveData().observe(this, new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                MovieAdapter movieAdapter = new MovieAdapter(context, movieListener);
                movieAdapter.setMovies(resultsBeans);
                similar.setAdapter(movieAdapter);
                similar.setHasFixedSize(true);
                LinearLayoutManager mngr2 = new LinearLayoutManager(context);
                mngr2.setOrientation(RecyclerView.HORIZONTAL);
                similar.setLayoutManager(mngr2);
                if (resultsBeans.size() == 0) {
                    similar.setVisibility(View.GONE);
                    sim_tv.setVisibility(View.GONE);
                }
            }
        });

    }

    private void fillCastRecyclerView() {
        MovieViewModel castViewModel = new MovieViewModel(movieId, Constants.BASE_URL, Constants.API_KEY);
        castViewModel.getCastData();
        castViewModel.getCastMutableLiveData().observe(this, new Observer<List<CastClass.CastBean>>() {
            @Override
            public void onChanged(List<CastClass.CastBean> castBeans) {
                CastAdapter castAdapter = new CastAdapter(castBeans, context, listener);
                cast.setAdapter(castAdapter);
                cast.setHasFixedSize(true);
                LinearLayoutManager mngr = new LinearLayoutManager(context);
                mngr.setOrientation(RecyclerView.HORIZONTAL);
                cast.setLayoutManager(mngr);
            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActorClick(CastClass.CastBean actor, ImageView image) {
        Intent intent = new Intent(MovieDetails.this, ActorProfile.class);
        intent.putExtra("imgURL", Constants.IMAGE_BASE_URL + actor.getProfile_path());
        intent.putExtra("actorId", actor.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetails.this, image,
                "front_image");

        startActivity(intent, options.toBundle());
    }

    @Override
    protected void onResume() {
        final FrameLayout back = findViewById(R.id.back_frame);
        back.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));

        super.onResume();
    }

    @Override
    public void onMovieClick(final MoviesClass.ResultsBean movie, final CardView frontImage) {
        MovieViewModel trailerModelView = new MovieViewModel(movie.getId(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        trailerModelView.getVideoData();
        trailerModelView.getVideoMutableLiveData().observe(this, new Observer<List<VideoClass.ResultsBean>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(final List<VideoClass.ResultsBean> resultsBeans) {
                Intent intent = new Intent(MovieDetails.this, MovieDetails.class);
                intent.putExtra("front", movie.getPoster_path());
                intent.putExtra("back", movie.getBackdrop_path());
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("id", movie.getId());
                intent.putExtra("view", movie.getOverview());
                intent.putExtra("rate", (int) (movie.getVote_average() * 10));
                if (resultsBeans == null || resultsBeans.size() == 0)
                    intent.putExtra("trailer", "none");
                else
                    intent.putExtra("trailer", resultsBeans.get(0).getKey());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetails.this, frontImage,
                        "front_image");

                startActivity(intent, options.toBundle());

            }
        });

    }
}