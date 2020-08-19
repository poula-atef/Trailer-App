package com.example.emovieapp.UI;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emovieapp.Classes.ActorClass;
import com.example.emovieapp.Classes.ActorMoviesClass;
import com.example.emovieapp.Constants;
import com.example.emovieapp.Adapters.MovieAdapter;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.R;
import com.example.emovieapp.Classes.VideoClass;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorProfile extends AppCompatActivity implements MovieAdapter.onActorMovieClickListener {


    String imgURL;
    int actorId;
    MovieViewModel ActorViewModel, moviesViewModel;
    MovieAdapter adapter;
    LifecycleOwner lifecycleOwner = this;
    Context context = this;
    MovieAdapter.onActorMovieClickListener listener = this;

    @BindView(R.id.name_title)
    TextView actorNameTitle;
    @BindView(R.id.actor_name)
    TextView actorName;
    @BindView(R.id.age_title)
    TextView actorAgeTitle;
    @BindView(R.id.actor_age)
    TextView actorAge;
    @BindView(R.id.actor_intro_title)
    TextView actorIntroTitle;
    @BindView(R.id.actor_intro)
    TextView actorIntro;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.front_img)
    ImageView actorImage;
    @BindView(R.id.front_card)
    CardView image;
    @BindView(R.id.rec_movies)
    RecyclerView movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_profile);
        ButterKnife.bind(this);

        imgURL = getIntent().getStringExtra("imgURL");
        actorId = getIntent().getIntExtra("actorId", 0);

        Glide.with(this).load(imgURL).into(actorImage);

        ActorViewModel = new MovieViewModel(actorId, Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        ActorViewModel.getActorData();
        ActorViewModel.getActorMutableLiveData().observe(this, new Observer<ActorClass>() {
            @Override
            public void onChanged(ActorClass actorClass) {
                if (actorClass != null) {
                    if (actorClass.getName() != null && !actorClass.getName().equals(""))
                        actorName.setText(actorClass.getName());
                    else {
                        actorName.setVisibility(View.GONE);
                        actorNameTitle.setVisibility(View.GONE);

                    }
                    if (actorClass.getBirthday() != null && !actorClass.getBirthday().equals("")) {
                        String age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(actorClass.getBirthday().substring(0, 4)));
                        actorAge.setText(age);
                    } else {
                        actorAgeTitle.setVisibility(View.GONE);
                        actorAge.setVisibility(View.GONE);
                    }
                    if (actorClass.getBiography() != null && !actorClass.getBiography().equals(""))
                        actorIntro.setText(actorClass.getBiography());
                    else {
                        actorIntroTitle.setVisibility(View.GONE);
                        actorIntro.setVisibility(View.GONE);
                    }
                    if (actorClass.getImdb_id() != null && !actorClass.getImdb_id().equals("")) {
                        moviesViewModel = new MovieViewModel(actorClass.getImdb_id(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
                        moviesViewModel.getActorMoviesData();
                        moviesViewModel.getActorMoviesMutableLiveData().observe(lifecycleOwner, new Observer<List<ActorMoviesClass.PersonResultsBean.KnownForBean>>() {
                            @Override
                            public void onChanged(List<ActorMoviesClass.PersonResultsBean.KnownForBean> knownForBeans) {
                                if (knownForBeans.size() > 0) {
                                    adapter = new MovieAdapter(knownForBeans, "actorMovies", context, listener);
                                    movies.setLayoutManager(new GridLayoutManager(context, 3));
                                    movies.setHasFixedSize(true);
                                    movies.setAdapter(adapter);
                                } else {
                                    movieTitle.setVisibility(View.GONE);
                                    movies.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        movieTitle.setVisibility(View.GONE);
                        movies.setVisibility(View.GONE);
                    }
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorProfile.this, MovieImage.class);
                intent.putExtra("imgURL", imgURL);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActorProfile.this, image,
                        "front_image");

                startActivity(intent, options.toBundle());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActorMovieClick(final ActorMoviesClass.PersonResultsBean.KnownForBean movie, final CardView frontImage) {

        final Context context = this;

        MovieViewModel trailerModelView = new MovieViewModel(movie.getId(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        trailerModelView.getVideoData();
        trailerModelView.getVideoMutableLiveData().observe(this, new Observer<List<VideoClass.ResultsBean>>() {
            @Override
            public void onChanged(final List<VideoClass.ResultsBean> resultsBeans) {
                Intent intent = new Intent(ActorProfile.this, MovieDetails.class);
                intent.putExtra("front", movie.getPoster_path());
                intent.putExtra("back", movie.getBackdrop_path());
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("view", movie.getOverview());
                intent.putExtra("id", movie.getId());
                intent.putExtra("rate", (int) (movie.getVote_average() * 10));
                if (resultsBeans == null || resultsBeans.size() == 0)
                    intent.putExtra("trailer", "none");
                else
                    intent.putExtra("trailer", resultsBeans.get(0).getKey());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActorProfile.this, frontImage,
                        "front_image");

                startActivity(intent, options.toBundle());
            }
        });
    }
}