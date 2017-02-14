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

public class seven_coupon extends Activity {

    WebView seven_web;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (seven_web.canGoBack())
            {
                close_web = false;
                seven_web.goBack();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_coupon);

        seven_web = (WebView) findViewById(R.id.seven_webView);
        seven_web.setWebChromeClient(new WebChromeClient());
        seven_web.setWebViewClient(new WebViewClientImpl());
        seven_web.getSettings().setBuiltInZoomControls(true);
        seven_web.getSettings().setJavaScriptEnabled(true);
        seven_web.loadUrl("http://www.7-11.com.tw/special/index.asp?item=Event");

        DisplayMetrics dm_seven = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_seven);

        int width = dm_seven.widthPixels;
        float height = dm_seven.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));

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
