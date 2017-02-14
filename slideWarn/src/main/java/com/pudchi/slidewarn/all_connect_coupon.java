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

public class all_connect_coupon extends Activity {

    WebView all_connect_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_connect_coupon);

        all_connect_web = (WebView) findViewById(R.id.all_connect_webView);
        all_connect_web.setWebChromeClient(new WebChromeClient());
        all_connect_web.setWebViewClient(new WebViewClientImpl());
        all_connect_web.getSettings().setBuiltInZoomControls(true);
        all_connect_web.getSettings().setJavaScriptEnabled(true);
        all_connect_web.loadUrl("http://www.pxmart.com.tw/px/edm.px");

        DisplayMetrics dm_allconnect = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_allconnect);

        int width = dm_allconnect.widthPixels;
        float height = dm_allconnect.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (all_connect_web.canGoBack())
            {
                close_web = false;
                all_connect_web.goBack();
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
