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

public class ok_coupon extends Activity {

    WebView ok_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_coupon);

        ok_web = (WebView) findViewById(R.id.ok_webView);
        ok_web.setWebChromeClient(new WebChromeClient());
        ok_web.setWebViewClient(new WebViewClientImpl());
        ok_web.getSettings().setBuiltInZoomControls(true);
        ok_web.getSettings().setJavaScriptEnabled(true);
        ok_web.loadUrl("http://www.okmart.com.tw/promotion_reference");

        DisplayMetrics dm_ok = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_ok);

        int width = dm_ok.widthPixels;
        float height = dm_ok.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (ok_web.canGoBack())
            {
                close_web = false;
                ok_web.goBack();
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
