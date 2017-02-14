package com.pudchi.slidewarn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class hi_life_coupon extends Activity {

    WebView hi_life_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_life_coupon);

        hi_life_web = (WebView) findViewById(R.id.hilife_webView);
        hi_life_web.setWebChromeClient(new WebChromeClient());
        hi_life_web.setWebViewClient(new WebViewClientImpl());
        hi_life_web.getSettings().setBuiltInZoomControls(true);
        hi_life_web.getSettings().setJavaScriptEnabled(true);
        hi_life_web.loadUrl("http://www.hilife.com.tw/events_activity.aspx");

        DisplayMetrics dm_hi_life = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_hi_life);

        int width = dm_hi_life.widthPixels;
        float height = dm_hi_life.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (hi_life_web.canGoBack())
            {
                close_web = false;
                hi_life_web.goBack();
            }
            else
            {
                close_web = true;
            }

            event.startTracking();
            return close_web;
        }
        return super.onKeyDown(keyCode, event);
    }

    private final class WebViewClientImpl extends WebViewClient
    {
        public void WebViewClientImpl()
        {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if(url.compareTo("about:blank") == 0)
            {
                finish();
            }
            else if(url.endsWith(".mp4"))
            {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(i);
                finish();
            }
            return false;
        }
    }
}
