package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ActivityDetailBinding;
import com.example.myapplication.models.Movie;
import com.example.myapplication.utils.Define;

public class DetailActivity extends AppCompatActivity {

    private final static String TAG = DetailActivity.class.getName();
    private ActivityDetailBinding binding;
    private BottomSheetFragment bottomSheetFragment;
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

        bottomSheetFragment = new BottomSheetFragment(movie);

    }

    private void addEventListener() {
        binding.showContentButton.setOnClickListener(v -> {
            addFragment();
        });
    }

    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 잘 알려진 버그 out 시 애니메이션 처리 안됨
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack("aaa");
        transaction.replace(binding.bottomSheetContainer.getId(), bottomSheetFragment);
        transaction.commit();
    }
}