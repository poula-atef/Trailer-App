package com.example.emovieapp.MVVM_API;

import com.example.emovieapp.Classes.ActorClass;
import com.example.emovieapp.Classes.ActorMoviesClass;
import com.example.emovieapp.Classes.CastClass;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.Classes.VideoClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIClass {
        @GET("/3/movie/{category}")
        Call<MoviesClass> getListOfMovies(
                @Path("category") String category,
                @Query("api_key") String apiKey,
                @Query("language") String language,
                @Query("page") int page
        );

        @GET("/3/movie/{id}/videos")
        Call<VideoClass> getMovieTrailer(@Path("id") int id,
                                         @Query("api_key") String apiKey,
                                         @Query("language") String language);


        @GET("/3/movie/{id}/credits")
        Call<CastClass> getMovieCast(@Path("id") int id,
                                     @Query("api_key") String apiKey);


        @GET("/3/person/{id}")
        Call<ActorClass> getActorInformation(
                @Path("id") int id,
                @Query("api_key") String apiKey,
                @Query("language") String language
        );

        @GET("/3/find/{id}")
        Call<ActorMoviesClass> getActorMovies(
                @Path("id") String id,
                @Query("api_key") String apiKey,
                @Query("language") String language,
                @Query("external_source") String external_source
        );

        @GET("/3/search/movie")
        Call<MoviesClass> getSearchMovies(
                @Query("api_key") String apiKey,
                @Query("language") String language,
                @Query("query") String query,
                @Query("page") int page
        );

        @GET("/3/movie/{id}/{category}")
        Call<MoviesClass> getSpecialMovieCast(@Path("id") int id,
                                              @Path("category") String category,
                                              @Query("api_key") String apiKey,
                                              @Query("language") String language,
                                              @Query("page") int page
        );
}
