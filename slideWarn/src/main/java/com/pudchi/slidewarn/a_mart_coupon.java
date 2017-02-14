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

public class a_mart_coupon extends Activity {

    WebView a_mart_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_mart_coupon);

        a_mart_web = (WebView) findViewById(R.id.a_mart_webView);

        a_mart_web.setWebChromeClient(new WebChromeClient());
        a_mart_web.setWebViewClient(new WebViewClientImpl());
        a_mart_web.getSettings().setBuiltInZoomControls(true);
        a_mart_web.getSettings().setJavaScriptEnabled(true);
        a_mart_web.loadUrl("http://www.fe-amart.com.tw/sell_info.jsp");

        DisplayMetrics dm_amart = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_amart);

        int width = dm_amart.widthPixels;
        float height = dm_amart.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (a_mart_web.canGoBack())
            {
                close_web = false;
                a_mart_web.goBack();
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
