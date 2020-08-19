package com.example.emovieapp.UI;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.emovieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieImage extends AppCompatActivity {
    String movieImageUrl;
    @BindView(R.id.front_img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_image);
        ButterKnife.bind(this);

        movieImageUrl = getIntent().getStringExtra("imgURL");

        Glide.with(this).load(movieImageUrl).into(img);
    }
}