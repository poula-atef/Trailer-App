package com.example.emovieapp.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emovieapp.Adapters.MovieAdapter;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.Classes.VideoClass;
import com.example.emovieapp.Constants;
import com.example.emovieapp.FavoriteMovies;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.R;
import com.example.emovieapp.UI.MovieDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment implements MovieAdapter.onMovieClickListener {
    @BindView(R.id.fav_recc)
    RecyclerView favRecc;
    @BindView(R.id.no_fav)
    TextView noFav;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,view);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        List<MoviesClass.ResultsBean> movies = new ArrayList<>();
        FavoriteMovies favoriteMovies = new FavoriteMovies(getContext());
        movies = favoriteMovies.getAllMovies();

        if(movies.size() > 0){
            MovieAdapter adapter = new MovieAdapter(getContext(),this);
            adapter.setMovies(movies);

            favRecc.setLayoutManager(new GridLayoutManager(getContext(),3));
            favRecc.setAdapter(adapter);
            favRecc.setHasFixedSize(true);
            noFav.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMovieClick(MoviesClass.ResultsBean movie, CardView frontImage) {
        MovieViewModel trailerModelView = new MovieViewModel(movie.getId(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        trailerModelView.getVideoData();
        trailerModelView.getVideoMutableLiveData().observe(this, new Observer<List<VideoClass.ResultsBean>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(final List<VideoClass.ResultsBean> resultsBeans) {
                Intent intent = new Intent(getActivity(), MovieDetails.class);
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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), frontImage,
                        "front_image");

                startActivity(intent, options.toBundle());

            }
        });

    }
}