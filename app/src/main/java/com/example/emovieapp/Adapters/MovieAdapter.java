package com.example.emovieapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emovieapp.Classes.ActorMoviesClass;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    List<MoviesClass.ResultsBean>movies;
    List<ActorMoviesClass.PersonResultsBean.KnownForBean>actorMovies;
    Context context;
    String type;
    onMovieClickListener listener;
    onActorMovieClickListener actorListener;
    public MovieAdapter(List<ActorMoviesClass.PersonResultsBean.KnownForBean> actorMovies,String type,Context context,onActorMovieClickListener actorListener) {
        this.actorMovies = actorMovies;
        this.type = type;
        this.context = context;
        this.actorListener = actorListener;
    }
    public MovieAdapter(Context context, onMovieClickListener listener){
        this.context = context;
        this.listener = listener;
    }



    public MovieAdapter(){}


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_element,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if(type == null)
        {
        holder.rating_layout.setVisibility(View.VISIBLE);

        int rating = ((int)(movies.get(position).getVote_average()*10));

        if(rating>=70){
            holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.more_than_70));
        }
        else {
            if (rating >= 40) {
                holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.less_than_70));
            }
            else{
                holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.less_than_40));
            }
        }

        holder.rating_pb.setProgress(rating,true);

        holder.rating_tv.setText(String.valueOf(rating));
        holder.tv.setText(movies.get(position).getTitle());
        Glide.with(context).load(IMAGE_BASE_URL+movies.get(position).getPoster_path()).into(holder.im);
        }
        else{
            holder.rating_layout.setVisibility(View.VISIBLE);

            int rating = ((int)(actorMovies.get(position).getVote_average()*10));

            if(rating>=70){
                holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.more_than_70));
            }
            else {
                if (rating >= 40) {
                    holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.less_than_70));
                }
                else{
                    holder.rating_pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.less_than_40));
                }
            }

            holder.rating_pb.setProgress(rating,true);

            holder.rating_tv.setText(String.valueOf(rating));
            holder.tv.setText(actorMovies.get(position).getTitle());
            Glide.with(context).load(IMAGE_BASE_URL+actorMovies.get(position).getPoster_path()).into(holder.im);
        }
    }

    @Override
    public int getItemCount() {
        if(type == null) {
            if (movies == null)
                return 0;
            return movies.size();
        }
        else{
            if (actorMovies == null)
                return 0;
            return actorMovies.size();
        }
    }



    public void setMovies(List<MoviesClass.ResultsBean> movies) {
        this.movies = movies;
    }

    public interface onMovieClickListener{
        public void onMovieClick(MoviesClass.ResultsBean movie, CardView frontImage);
    }

    public interface onActorMovieClickListener{
        public void onActorMovieClick(ActorMoviesClass.PersonResultsBean.KnownForBean movie, CardView frontImage);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;
        ImageView im;
        View view;
        CardView rec_card;
        FrameLayout rating_layout;
        TextView rating_tv;
        ProgressBar rating_pb;
        public MovieViewHolder(@NonNull View view) {
            super(view);
            tv = view.findViewById(R.id.tv);
            im = view.findViewById(R.id.im);
            rec_card = view.findViewById(R.id.rec_card);
            rating_layout = view.findViewById(R.id.rating_layout);
            rating_pb = view.findViewById(R.id.rating_pb);
            rating_tv = view.findViewById(R.id.rating_tv);
            this.view = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(type == null)
                listener.onMovieClick(movies.get(getLayoutPosition()),rec_card);
            else
                actorListener.onActorMovieClick(actorMovies.get(getLayoutPosition()),rec_card);

        }
    }
}
