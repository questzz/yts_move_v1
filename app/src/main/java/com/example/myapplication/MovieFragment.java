package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentMovieBinding;


public class MovieFragment extends Fragment {

    // view Binding
    private FragmentMovieBinding binding;

    // 싱글톤 패턴
    private static MovieFragment movieFragment;

    private MovieFragment() {
        // Required empty public constructor
    }


    public static MovieFragment getInstance() {
        if (movieFragment == null) {
            movieFragment = new MovieFragment();
        }
        return movieFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 플래그먼트에서 view binding 사용 방법
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}