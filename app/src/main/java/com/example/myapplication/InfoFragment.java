package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentInfoBinding;


public class InfoFragment extends Fragment {

    private static InfoFragment infoFragment;
    private FragmentInfoBinding binding;

    // 싱글톤 패턴 적용
    private InfoFragment() {

    }

    public static InfoFragment getInstance() {
        if (infoFragment == null) {
            infoFragment = new InfoFragment();
        }
        return infoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}