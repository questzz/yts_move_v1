package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.databinding.FragmentInfoBinding;
import com.example.myapplication.interfaces.OnPageTypeChange;
import com.example.myapplication.utils.Define;


public class InfoFragment extends Fragment {

    private static InfoFragment infoFragment;
    private FragmentInfoBinding binding;
    private final OnPageTypeChange onPageTypeChange;
    private OnBackPressedCallback onBackPressedCallback;

    // 싱글톤 패턴 적용
    private InfoFragment(OnPageTypeChange onPageTypeChange) {
        this.onPageTypeChange = onPageTypeChange;
    }

    public static InfoFragment getInstance(OnPageTypeChange onPageTypeChange) {
        if (infoFragment == null) {
            infoFragment = new InfoFragment(onPageTypeChange);
        }
        return infoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPageTypeChange.typeToolbarChange("Stub!");
        fragmentBackPressCustom();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progressIndicator.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(Define.WEB_VIEW_URL);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    // 프래그먼에서 뒤로가기 이벤트 커스텀 하기
    private void fragmentBackPressCustom() {
        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               Log.d("TAG", "stub!");
            }
        };
        requireActivity().
                getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackPressedCallback.remove();
    }

}