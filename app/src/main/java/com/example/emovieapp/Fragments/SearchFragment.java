package com.example.emovieapp.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.emovieapp.Adapters.MovieAdapter;
import com.example.emovieapp.Adapters.numbersAdapter;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.Classes.VideoClass;
import com.example.emovieapp.Constants;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.R;
import com.example.emovieapp.UI.MovieDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment implements MovieAdapter.onMovieClickListener, com.example.emovieapp.Adapters.numbersAdapter.onNumberClickListener {

    MovieAdapter adapter;
    MovieViewModel viewModel;
    List<Integer> numbers;
    String str;
    LifecycleOwner lifecycleOwner = this;
    MovieAdapter.onMovieClickListener listener = this;
    com.example.emovieapp.Adapters.numbersAdapter.onNumberClickListener numberListener = this;
    com.example.emovieapp.Adapters.numbersAdapter numbersAdapter;
    int currentPage = -1;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_rec)
    RecyclerView rec;
    @BindView(R.id.back_btn)
    ImageButton back;
    @BindView(R.id.forward_btn)
    ImageButton forward;
    @BindView(R.id.pages)
    RecyclerView numbers_rec;
    @BindView(R.id.not_found)
    TextView notFound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);

        setOnSearchTextValueChangedListener();
        return view;
    }

    private void setOnSearchTextValueChangedListener() {
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str = s.toString();
                if (str != null && !str.equals("")) {
                    viewModel = new MovieViewModel(Constants.API_KEY, Constants.LANGUAGE, str, Constants.PAGE, Constants.BASE_URL);
                    viewModel.startSearch();
                    viewModel.getSearchMovieMutableLiveData().observe(lifecycleOwner, new Observer<List<MoviesClass.ResultsBean>>() {
                        @Override
                        public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                            currentPage = -1;
                            adapter = new MovieAdapter(getContext(), listener);
                            adapter.setMovies(resultsBeans);
                            rec.setAdapter(adapter);
                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new GridLayoutManager(getContext(), 3));

                            numbers = new ArrayList<>();
                            int size = viewModel.getNumOfPages();
                            numbers.clear();
                            if (size > 0) {
                                for (int i = 1; i <= size; i++) {
                                    numbers.add(i);
                                }
                                numbers.set(0, -1);
                                if(numbers.size() > 1)
                                    forward.setVisibility(View.VISIBLE);
                                else
                                    forward.setVisibility(View.GONE);

                                currentPage = 1;
                                notFound.setVisibility(View.GONE);
                            } else {
                                back.setVisibility(View.GONE);
                                forward.setVisibility(View.GONE);
                                notFound.setVisibility(View.VISIBLE);
                            }
                            numbersAdapter = new numbersAdapter(numbers, numberListener, getContext());
                            numbers_rec.setLayoutManager(new GridLayoutManager(getContext(), 6));
                            numbers_rec.setHasFixedSize(true);
                            numbers_rec.setAdapter(numbersAdapter);
                        }
                    });

                    setBackButtonAction();

                    setForwardButtonAction();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setForwardButtonAction() {
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                viewModel = new MovieViewModel(Constants.API_KEY, Constants.LANGUAGE, str, currentPage, Constants.BASE_URL);
                viewModel.startSearch();
                viewModel.getSearchMovieMutableLiveData().observe(lifecycleOwner, new Observer<List<MoviesClass.ResultsBean>>() {
                    @Override
                    public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                        adapter = new MovieAdapter(getContext(), listener);
                        adapter.setMovies(resultsBeans);
                        rec.setAdapter(adapter);
                        rec.setHasFixedSize(true);
                        rec.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        if (numbers.size() > 0) {
                            for (int i = 0; i < numbers.size(); i++)
                                if (numbers.get(i) < 0) {
                                    numbers.set(i, numbers.get(i) * -1);
                                    break;
                                }
                            numbers.set(currentPage - 1, currentPage * -1);
                        }
                        if (currentPage == numbers.size())
                            forward.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        numbersAdapter = new numbersAdapter(numbers, numberListener, getContext());
                        numbers_rec.setLayoutManager(new GridLayoutManager(getContext(), 6));
                        numbers_rec.setHasFixedSize(true);
                        numbers_rec.setAdapter(numbersAdapter);
                    }
                });

            }
        });
    }

    private void setBackButtonAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                viewModel = new MovieViewModel(Constants.API_KEY, Constants.LANGUAGE, str, currentPage, Constants.BASE_URL);
                viewModel.startSearch();
                viewModel.getSearchMovieMutableLiveData().observe(lifecycleOwner, new Observer<List<MoviesClass.ResultsBean>>() {
                    @Override
                    public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                        adapter = new MovieAdapter(getContext(), listener);
                        adapter.setMovies(resultsBeans);
                        rec.setAdapter(adapter);
                        rec.setHasFixedSize(true);
                        rec.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        if (numbers.size() > 0) {
                            for (int i = 0; i < numbers.size(); i++)
                                if (numbers.get(i) < 0) {
                                    numbers.set(i, numbers.get(i) * -1);
                                    break;
                                }
                            numbers.set(currentPage - 1, currentPage * -1);
                        }
                        if (currentPage == 1)
                            back.setVisibility(View.GONE);
                        forward.setVisibility(View.VISIBLE);
                        numbersAdapter = new numbersAdapter(numbers, numberListener, getContext());
                        numbers_rec.setLayoutManager(new GridLayoutManager(getContext(), 6));
                        numbers_rec.setHasFixedSize(true);
                        numbers_rec.setAdapter(numbersAdapter);
                    }
                });

            }
        });
    }

//    private void initializeToolBar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.search_toolBar);
//        view.setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    @Override
    public void onMovieClick(final MoviesClass.ResultsBean movie, final CardView frontImage) {

        MovieViewModel trailerModelView = new MovieViewModel(movie.getId(), Constants.BASE_URL, Constants.API_KEY, Constants.LANGUAGE);
        trailerModelView.getVideoData();
        trailerModelView.getVideoMutableLiveData().observe(this, new Observer<List<VideoClass.ResultsBean>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChanged(List<VideoClass.ResultsBean> resultsBeans) {
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

    @Override
    public void onClickListener(final int number) {
        currentPage = numbers.get(number);
        viewModel = new MovieViewModel(Constants.API_KEY, Constants.LANGUAGE, str, currentPage, Constants.BASE_URL);
        viewModel.startSearch();
        viewModel.getSearchMovieMutableLiveData().observe(this, new Observer<List<MoviesClass.ResultsBean>>() {
            @Override
            public void onChanged(List<MoviesClass.ResultsBean> resultsBeans) {
                adapter = new MovieAdapter(getContext(), listener);
                adapter.setMovies(resultsBeans);
                rec.setAdapter(adapter);
                rec.setHasFixedSize(true);
                rec.setLayoutManager(new GridLayoutManager(getContext(), 3));
                if (numbers.size() > 0) {
                    for (int i = 0; i < numbers.size(); i++)
                        if (numbers.get(i) < 0) {
                            numbers.set(i, numbers.get(i) * -1);
                            break;
                        }
                    numbers.set(number, (number + 1) * -1);
                }
                if (currentPage == numbers.size()) {
                    forward.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                } else if (currentPage == 1) {
                    back.setVisibility(View.GONE);
                    forward.setVisibility(View.VISIBLE);
                } else {
                    forward.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                }
                numbersAdapter = new numbersAdapter(numbers, numberListener, getContext());
                numbers_rec.setLayoutManager(new GridLayoutManager(getContext(), 6));
                numbers_rec.setHasFixedSize(true);
                numbers_rec.setAdapter(numbersAdapter);
            }
        });
    }


}