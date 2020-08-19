package com.example.emovieapp.UI;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.emovieapp.Fragments.FavoriteFragment;
import com.example.emovieapp.Fragments.HomeFragment;
import com.example.emovieapp.R;
import com.example.emovieapp.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity  {


    private String last = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                Fragment selected = null;
                boolean trans = false;
                FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
                if(item.getItemId() == R.id.home && !last.equals("home")) {
                    selected = new HomeFragment();
                    trans=true;
                    transaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout);
                    last = "home";
                }


                else if(item.getItemId() == R.id.fav && !last.equals("fav")) {
                    selected = new FavoriteFragment();
                    trans=true;
                    last = "fav";
                    transaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout);

                }

                else if(item.getItemId() == R.id.search && !last.equals("search")) {
                    selected = new SearchFragment();
                    trans=true;
                    transaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout);
                    last = "search";
                }
                if(trans)
                    transaction.replace(R.id.container,selected).commit();


                return true;
            }
        });



    }




}