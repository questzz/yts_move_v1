package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ActivityDetailBinding;
import com.example.myapplication.models.Movie;
import com.example.myapplication.utils.Define;

public class DetailActivity extends AppCompatActivity {

    private final static String TAG = DetailActivity.class.getName();
    private ActivityDetailBinding binding;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            movie = (Movie) getIntent().getSerializableExtra(Define.PARAM_MOVIE_OBJ);
            initData();
            addEventListener();
        }

    }

    private void initData() {
        binding.titleTextView.setText(movie.getTitle());
        binding.yearTextView.setText( "제작년도: " + movie.getYear() + "년도");
        binding.runTimeTextView.setText("상영시간 : " + movie.getRuntime() + "분");

        Glide.with(this)
                .load(movie.getMediumCoverImage())
                .into(binding.moviePoster);

        Glide.with(this)
                .load(movie.getBackgroundImage())
                .into(binding.backgroundImageView);

    }

    private void addEventListener() {
        binding.showContentButton.setOnClickListener(v -> {
            Log.d(TAG, "button clicked!");
        });
    }
}