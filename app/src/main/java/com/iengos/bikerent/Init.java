package com.iengos.bikerent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.util.HashMap;


/**
 * Created by Simone on 10/07/2016.
 */
public class Init extends AppCompatActivity {

    Handler timeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_init);

        /// Set gif in webView
        WebView webView = (WebView) findViewById(R.id.gif_web_view);
        webView.loadUrl("file:///android_asset/logo.gif");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // Wait 3s
        timeHandler = new Handler();
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openLogin = new Intent(Init.this, PresentationSlides.class);
                startActivity(openLogin);
            }
        }, 3000);
    }
}