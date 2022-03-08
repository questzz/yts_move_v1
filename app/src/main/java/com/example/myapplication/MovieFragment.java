package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.databinding.FragmentMovieBinding;
import com.example.myapplication.interfaces.OnPageTypeChange;
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.YtsData;
import com.example.myapplication.repository.MovieService;
import com.example.myapplication.utils.Define;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getName();
    // view Binding
    private FragmentMovieBinding binding;
    // 싱글톤 패턴
    private static MovieFragment movieFragment;
    private MovieAdapter movieAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MovieService service;

    private OnPageTypeChange onPageTypeChange;

    private MovieFragment(OnPageTypeChange onPageTypeChange) {
        this.onPageTypeChange = onPageTypeChange;
    }


    public static MovieFragment getInstance(OnPageTypeChange onPageTypeChange) {
        if (movieFragment == null) {
            movieFragment = new MovieFragment(onPageTypeChange);
        }
        return movieFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = MovieService.retrofit.create(MovieService.class);
        onPageTypeChange.typeToolbarChange(Define.PAGE_TITLE_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 플래그먼트에서 view binding 사용 방법
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        requestMoviesData();
    }

    private void initRecyclerView() {
        // 1. 어댑터
        // 2. 매니저
        // 3. 셋팅
        movieAdapter = new MovieAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.movieRecyclerView.setAdapter(movieAdapter);
        binding.movieRecyclerView.setLayoutManager(linearLayoutManager);
        binding.movieRecyclerView.hasFixedSize();
    }

    private void requestMoviesData() {
        service.repoContributors("rating", 1, 10)
        .enqueue(new Callback<YtsData>() {
            @Override
            public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                Log.d(TAG, "status code " + response.code());
                if (response.isSuccessful()) {
                    YtsData ytsData = response.body();
                    List<Movie> list = ytsData.getData().getMovies();
                    movieAdapter.addItems(list);


                    binding.progressIndicator.setVisibility(View.GONE);
                } else {
                   Log.d(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<YtsData> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}