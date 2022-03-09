package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.databinding.FragmentMovieBinding;
import com.example.myapplication.interfaces.OnPageTypeChange;
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.YtsData;
import com.example.myapplication.repository.MovieService;
import com.example.myapplication.utils.Define;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getName();
    private FragmentMovieBinding binding; // view Binding
    private static MovieFragment movieFragment; // 싱글톤 패턴
    private MovieAdapter movieAdapter;
    private MovieService service;
    private final OnPageTypeChange onPageTypeChange;

    private int currentPageNumber = 1;
    private static final int DATA_LIMIT = 20;
    private List<Movie> movieList = new ArrayList<>();
    private boolean preventDuplicateScrollEvent = true;
    private boolean isFirstFragmentStart = true;


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
        if (isFirstFragmentStart) {
            requestMoviesData(currentPageNumber);
        } else {
            binding.progressIndicator.setVisibility(View.GONE);
        }

    }

    private void initRecyclerView() {
        // 1. 어댑터
        // 2. 매니저
        // 3. 셋팅
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.addItem(movieList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        final RecyclerView recyclerView = binding.movieRecyclerView;

        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (preventDuplicateScrollEvent) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                    if (lastVisibleItemPosition == itemTotalCount) {
                        if (currentPageNumber != 1) {
                            requestMoviesData(currentPageNumber);
                            preventDuplicateScrollEvent = false;
                        }
                    }
                }
            }
        });
    }

    private void requestMoviesData(int page) {
        String orderBy = "rating";
        service.repoContributors(orderBy, page, DATA_LIMIT)
                .enqueue(new Callback<YtsData>() {
                    @Override
                    public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                        if (response.isSuccessful()) {
                            YtsData ytsData = response.body();
                            // List<Movie> list = ytsData.getData().getMovies();
                            movieList = ytsData.getData().getMovies();
                            movieAdapter.addItem(ytsData.getData().getMovies());
                            currentPageNumber++; // 2
                            preventDuplicateScrollEvent = true;
                            isFirstFragmentStart = false;
                            binding.progressIndicator.setVisibility(View.GONE);
                        } else {
                            // assert 란 개발자들이 디버깅을 빠르게 하기윈한 도구
                            // 즉, 에러 검출용 코드이지 코드를 다 완성하고 동작할 때 돌아가는 함수가 아니다.
                            // log 보다 더 효율적으로 상용 될 수 있다.
                            // 컴파일 , 실재 앱을 배포 (컴파일러가 무시)
                            assert response.errorBody() != null;
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