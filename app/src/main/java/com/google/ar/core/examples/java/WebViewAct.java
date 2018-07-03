package com.google.ar.core.examples.java;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.ar.core.examples.java.augmentedimage.R;

public class WebViewAct extends AppCompatActivity {

    private WebView wv;
    private ProgressBar pb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initTasks();
    }

    private void initTasks() {
        findViews();
        getIntData();
        setupWebView();
    }

    private void getIntData() {
        if (getIntent() != null)
            url = getIntent().getStringExtra(getString(R.string.int__web_view_act__url));
    }

    private void findViews() {
        wv = findViewById(R.id.wv);
        pb = findViewById(R.id.pb);
    }

    private void setupWebView() {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                pb.setVisibility(View.GONE);
            }
        });
        wv.loadUrl(url);

    }
}
