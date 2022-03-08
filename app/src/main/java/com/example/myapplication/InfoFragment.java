package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.databinding.FragmentInfoBinding;
import com.example.myapplication.interfaces.OnPageTypeChange;


public class InfoFragment extends Fragment {

    private static InfoFragment infoFragment;
    private FragmentInfoBinding binding;
    private OnPageTypeChange onPageTypeChange;

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
        onPageTypeChange.typeToolbarChange("WEB VIEW");
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

    private void setupWebView() {
        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progressIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 웹뷰가 렌더링이 다 되었을 때 콜백 되는 메서드
                // HTML 뼈대, CSS , javascript
            }
        });

        webView.loadUrl("https://yts.mx/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}