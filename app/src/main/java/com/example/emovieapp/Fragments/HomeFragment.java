package com.example.emovieapp.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.emovieapp.Adapters.MovieAdapter;
import com.example.emovieapp.Adapters.ViewPagerAdapter;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.Classes.VideoClass;
import com.example.emovieapp.Constants;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.R;
import com.example.emovieapp.UI.MovieDetails;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements MovieAdapter.onMovieClickListener{

    MovieViewModel popular_viewModel, upcoming_viewModel, top_viewModel;
    ViewPagerAdapter viewAdapter;
    int mainSliderSize = 0;
    LifecycleOwner lifecycleOwner = this;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.popular_title)
    TextView popular_title;
    @BindView(R.id.rec_popular)
    RecyclerView popular;
    @BindView(R.id.top_title)
    TextView top_title;
    @BindView(R.id.rec_top)
    RecyclerView top;

    boolean pageContinue = false;

    public HomeFragment(){
        pageContinue = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        setPopularRecyclerViewMovies();

        setTabViewMovies();

        setTopRatedRecyclerViewMovies();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClick(final MoviesClass.ResultsBean movie, final CardView frontImage) {

        MovieViewModel trailerModelView = new MovieViewModel(movie.getId(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        trailerModelView.getVideoData();
        trailerModelView.getVideoMutableLiveData().observe(this, new Observer<List<VideoClass.ResultsBean>>() {
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


    private void setPopularRecyclerViewMovies() {
        popular_viewModel = new MovieViewModel(Constants.BASE_URL, Constants.CATEGORY_POPULAR, Constants.LANGUAGE, Constants.API_KEY, Constants.PAGE);
        popular_viewModel.getMoviesData();
        final MovieAdapter adapter = new MovieAdapter(getContext(), this);
        popular_viewModel.getMovieMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                adapter.setMovies(resultsBeans);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(RecyclerView.HORIZONTAL);
                popular.setLayoutManager(manager);
                popular.setHasFixedSize(true);
                popular.setAdapter(adapter);
                popular_title.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);

            }
        });
    }

    private void setTopRatedRecyclerViewMovies() {
        top_viewModel = new MovieViewModel(Constants.BASE_URL, Constants.CATEGORY_TOP, Constants.LANGUAGE, Constants.API_KEY, Constants.PAGE);
        top_viewModel.getMoviesData();
        final MovieAdapter adapter = new MovieAdapter(getContext(), this);
        top_viewModel.getMovieMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                adapter.setMovies(resultsBeans);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(RecyclerView.HORIZONTAL);
                top.setLayoutManager(manager);
                top.setHasFixedSize(true);
                top.setAdapter(adapter);
                top_title.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setTabViewMovies() {

        final Timer timer = new Timer();
        upcoming_viewModel = new MovieViewModel(Constants.BASE_URL, Constants.CATEGORY_UPCOMING, Constants.LANGUAGE, Constants.API_KEY, Constants.PAGE);
        upcoming_viewModel.getMoviesData();
        upcoming_viewModel.getMovieMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                List<MoviesClass.ResultsBean> tabList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    tabList.add(resultsBeans.get(i));
                }
                viewAdapter = new ViewPagerAdapter(tabList, getContext(), lifecycleOwner, getActivity());
                pager.setAdapter(viewAdapter);
                mainSliderSize = tabList.size();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(pageContinue){
                            getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pager.getCurrentItem() < mainSliderSize - 1)
                                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                                else
                                    pager.setCurrentItem(0);
                            }
                        });}
                    }
                }, 4000, 6000);
            }
        });
        tab.setupWithViewPager(pager);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pageContinue = false;
    }
}