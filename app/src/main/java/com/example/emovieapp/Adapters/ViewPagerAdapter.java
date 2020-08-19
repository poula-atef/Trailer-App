package com.example.emovieapp.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.emovieapp.Constants;
import com.example.emovieapp.MVVM_API.MovieViewModel;
import com.example.emovieapp.Classes.MoviesClass;
import com.example.emovieapp.R;
import com.example.emovieapp.UI.MovieDetails;
import com.example.emovieapp.Classes.VideoClass;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    List<MoviesClass.ResultsBean>movies;
    Context context;
    LifecycleOwner lifecycleOwner;
    Activity activity;
    public ViewPagerAdapter(List<MoviesClass.ResultsBean> movies, Context context,LifecycleOwner lifecycleOwner,Activity activity) {
        this.movies = movies;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if(movies == null)
            return 0;
        return movies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)(context.getSystemService(context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.view_pager_element,null);

        final ImageView img = view.findViewById(R.id.movie_image);
        final TextView tv = view.findViewById(R.id.movie_name);

        tv.setText(movies.get(position).getTitle());
        Glide.with(view)
                .asBitmap()
                .load(Constants.IMAGE_BASE_URL+movies.get(position).getPoster_path())
                .into(new BitmapImageViewTarget(img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });

        final MoviesClass.ResultsBean movie = movies.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MovieViewModel trailerModelView = new MovieViewModel(movie.getId(),Constants.BASE_URL,Constants.API_KEY,Constants.LANGUAGE);
                trailerModelView.getVideoData();
                trailerModelView.getVideoMutableLiveData().observe(lifecycleOwner, new Observer<List<VideoClass.ResultsBean>>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(final List<VideoClass.ResultsBean> resultsBeans) {
                        Intent intent = new Intent(context, MovieDetails.class);
                        intent.putExtra("front",movie.getPoster_path());
                        intent.putExtra("back",movie.getBackdrop_path());
                        intent.putExtra("title",movie.getTitle());
                        intent.putExtra("id",movie.getId());
                        intent.putExtra("view",movie.getOverview());
                        intent.putExtra("rate",(int)(movie.getVote_average()*10));
                        if (resultsBeans == null || resultsBeans.size() == 0)
                            intent.putExtra("trailer","none");
                        else
                            intent.putExtra("trailer",resultsBeans.get(0).getKey());
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,img,
                                "front_image");

                        context.startActivity(intent,options.toBundle());

                    }
                });
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
