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

public class carrefour_coupon extends Activity {

    WebView carrefour_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrefour_coupon);

        carrefour_web.setWebViewClient(new WebViewClient());
        carrefour_web.loadUrl("http://www.carrefour.com.tw/");

        DisplayMetrics dm_carrefour = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_carrefour);

        int width = dm_carrefour.widthPixels;
        float height = dm_carrefour.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            boolean close_web;
            if (carrefour_web.canGoBack())
            {
                close_web = false;
                carrefour_web.goBack();
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
