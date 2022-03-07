package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.models.YtsData;
import com.example.myapplication.repository.MovieService;
import com.example.myapplication.utils.FragmentType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // 1. viewBind 사용하기
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰 바인딩 설정 방법 (Activity)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intData();
        addBottomNavigationListener();
        replaceFragment(FragmentType.MOVIE);
    }

    private void intData() {

    }

    private void replaceFragment(FragmentType type) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == FragmentType.MOVIE) {
            fragment = MovieFragment.getInstance();
        } else {
            fragment = InfoFragment.getInstance();
        }
        transaction.replace(binding.mainContainer.getId(), fragment);
        transaction.commit();
    }


    private void addBottomNavigationListener() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_1:
                    replaceFragment(FragmentType.MOVIE);
                    break;
                case R.id.page_2:
                    replaceFragment(FragmentType.INFO);
                    break;
            }
            return true;
        });
    }
}