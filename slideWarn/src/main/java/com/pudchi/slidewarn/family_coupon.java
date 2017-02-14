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

public class family_coupon extends Activity {

    WebView family_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_coupon);

        family_web = (WebView) findViewById(R.id.family_webView);
        family_web.setWebChromeClient(new WebChromeClient());
        family_web.setWebViewClient(new WebViewClientImpl());
        family_web.getSettings().setBuiltInZoomControls(true);
        family_web.getSettings().setJavaScriptEnabled(true);
        family_web.loadUrl("http://www.family.com.tw/marketing/event.aspx");


        DisplayMetrics dm_family = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_family);

        int width = dm_family.widthPixels;
        float height = dm_family.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (family_web.canGoBack())
            {
                close_web = false;
                family_web.goBack();
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
