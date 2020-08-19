package com.example.emovieapp.MVVM_API;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emovieapp.Classes.ActorClass;
import com.example.emovieapp.Classes.ActorMoviesClass;
import com.example.emovieapp.Classes.CastClass;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.Classes.VideoClass;
import com.example.emovieapp.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private int MovieID;
    private MutableLiveData<List<MoviesClass.ResultsBean>> MovieMutableLiveData;
    private MutableLiveData<List<MoviesClass.ResultsBean>> SearchMovieMutableLiveData;
    private MutableLiveData<List<VideoClass.ResultsBean>> VideoMutableLiveData;
    private MutableLiveData<List<CastClass.CastBean>> CastMutableLiveData;
    private MutableLiveData<List<ActorMoviesClass.PersonResultsBean.KnownForBean>> ActorMoviesMutableLiveData;
    private MutableLiveData<ActorClass> ActorMutableLiveData;
    private String BASE_URL;
    private String CATEGORY;

    private String LANGUAGE;
    private String QUERY;
    private String API_KEY;
    private String EXTERNAL_ID;
    private int PAGE;
    private int numOfPages;

    public MutableLiveData<ActorClass> getActorMutableLiveData() {
        return ActorMutableLiveData;
    }

    public MutableLiveData<List<MoviesClass.ResultsBean>> getMovieMutableLiveData() {
        return MovieMutableLiveData;
    }

    public MutableLiveData<List<MoviesClass.ResultsBean>> getSearchMovieMutableLiveData() {
        return SearchMovieMutableLiveData;
    }

    public MutableLiveData<List<VideoClass.ResultsBean>> getVideoMutableLiveData() {
        return VideoMutableLiveData;
    }

    public MutableLiveData<List<CastClass.CastBean>> getCastMutableLiveData() {
        return CastMutableLiveData;
    }

    public MutableLiveData<List<ActorMoviesClass.PersonResultsBean.KnownForBean>> getActorMoviesMutableLiveData() {
        return ActorMoviesMutableLiveData;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public MovieViewModel(String BASE_URL, String CATEGORY, String LANGUAGE, String API_KEY, int PAGE) {
        this.BASE_URL = BASE_URL;
        this.CATEGORY = CATEGORY;
        this.LANGUAGE = LANGUAGE;
        this.API_KEY = API_KEY;
        this.PAGE = PAGE;
    }

    public MovieViewModel(String BASE_URL, String CATEGORY, String LANGUAGE, String API_KEY, int PAGE,int id) {
        this.BASE_URL = BASE_URL;
        this.CATEGORY = CATEGORY;
        this.LANGUAGE = LANGUAGE;
        this.API_KEY = API_KEY;
        this.PAGE = PAGE;
        this.MovieID = id;
    }

    public MovieViewModel(int id,String BASE_URL, String API_KEY, String LANGUAGE) {
        this.MovieID = id;
        this.BASE_URL = BASE_URL;
        this.API_KEY = API_KEY;
        this.LANGUAGE = LANGUAGE;
    }


    public MovieViewModel(int id,String BASE_URL, String API_KEY) {
        this.MovieID = id;
        this.BASE_URL = BASE_URL;
        this.API_KEY = API_KEY;
    }

    public MovieViewModel(String API_KEY,String LANGUAGE,String QUERY,int PAGE,String BASE_URL) {
        this.BASE_URL = BASE_URL;
        this.API_KEY = API_KEY;
        this.PAGE = PAGE;
        this.LANGUAGE = LANGUAGE;
        this.QUERY = QUERY;
    }

    public MovieViewModel(String EXTERNAL_ID,String BASE_URL, String API_KEY, String LANGUAGE) {
        this.EXTERNAL_ID = EXTERNAL_ID;
        this.BASE_URL = BASE_URL;
        this.API_KEY = API_KEY;
        this.LANGUAGE = LANGUAGE;
    }


    private void getMovieFromAPI(){
        MovieMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<MoviesClass> list = api.getListOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        list.enqueue(new Callback<MoviesClass>() {
            @Override
            public void onResponse(Call<MoviesClass> call, Response<MoviesClass> response) {
                MoviesClass myResult = response.body();
                MovieMutableLiveData.setValue(myResult.getResults());
            }

            @Override
            public void onFailure(Call<MoviesClass> call, Throwable t) {
            }
        });

    }

    private void getTrailerFromAPI(){
        VideoMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<VideoClass> list = api.getMovieTrailer(MovieID,API_KEY,LANGUAGE);
        list.enqueue(new Callback<VideoClass>() {
            @Override
            public void onResponse(Call<VideoClass> call, Response<VideoClass> response) {
                VideoClass myResult = response.body();
                VideoMutableLiveData.setValue(myResult.getResults());
            }

            @Override
            public void onFailure(Call<VideoClass> call, Throwable t) {
            }
        });

    }

    private void getCastFromAPI(){
        CastMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<CastClass> list = api.getMovieCast(MovieID,API_KEY);
        list.enqueue(new Callback<CastClass>() {
            @Override
            public void onResponse(Call<CastClass> call, Response<CastClass> response) {
                CastClass myResult = response.body();
                CastMutableLiveData.setValue(myResult.getCast());
            }

            @Override
            public void onFailure(Call<CastClass> call, Throwable t) {
            }
        });

    }

    private void getActorInformationFromAPI(){
        ActorMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<ActorClass> list = api.getActorInformation(MovieID,API_KEY,LANGUAGE);
        list.enqueue(new Callback<ActorClass>() {
            @Override
            public void onResponse(Call<ActorClass> call, Response<ActorClass> response) {
                ActorClass myResult = response.body();
                ActorMutableLiveData.setValue(myResult);
            }

            @Override
            public void onFailure(Call<ActorClass> call, Throwable t) {
            }
        });

    }

    private void getActorMoviesFromAPI(){
        ActorMoviesMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<ActorMoviesClass> list = api.getActorMovies(EXTERNAL_ID,API_KEY,LANGUAGE, Constants.EXTERNAL_SOURCE);
        list.enqueue(new Callback<ActorMoviesClass>() {
            @Override
            public void onResponse(Call<ActorMoviesClass> call, Response<ActorMoviesClass> response) {
                ActorMoviesClass actorMoviesClass = response.body();
                ActorMoviesMutableLiveData.setValue(actorMoviesClass.getPerson_results().get(0).getKnown_for());
            }

            @Override
            public void onFailure(Call<ActorMoviesClass> call, Throwable t) {

            }
        });

    }

    private void getSearchMovieFromAPI(){
        SearchMovieMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<MoviesClass> list = api.getSearchMovies(API_KEY,LANGUAGE,QUERY,PAGE);
        list.enqueue(new Callback<MoviesClass>() {
            @Override
            public void onResponse(Call<MoviesClass> call, Response<MoviesClass> response) {
                MoviesClass myResult = response.body();
                numOfPages = myResult.getTotal_pages();
                SearchMovieMutableLiveData.setValue(myResult.getResults());
            }

            @Override
            public void onFailure(Call<MoviesClass> call, Throwable t) {
            }
        });

    }

    private void getSpecialFromAPI(){
        MovieMutableLiveData = new MutableLiveData<>();
        Retrofit builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        APIClass api = builder.create(APIClass.class);
        Call<MoviesClass> list = api.getSpecialMovieCast(MovieID,CATEGORY, API_KEY, LANGUAGE, PAGE);
        list.enqueue(new Callback<MoviesClass>() {
            @Override
            public void onResponse(Call<MoviesClass> call, Response<MoviesClass> response) {
                MoviesClass myResult = response.body();
                MovieMutableLiveData.setValue(myResult.getResults());
            }

            @Override
            public void onFailure(Call<MoviesClass> call, Throwable t) {
            }
        });

    }

    public void getMoviesData(){
        getMovieFromAPI();
    }

    public void getVideoData(){
        getTrailerFromAPI();
    }

    public void getCastData(){
        getCastFromAPI();
    }

    public void getActorData(){
        getActorInformationFromAPI();
    }

    public void getActorMoviesData(){
        getActorMoviesFromAPI();
    }

    public void startSearch(){
        getSearchMovieFromAPI();
    }

    public void getSpecialMovies(){ getSpecialFromAPI(); }
}
