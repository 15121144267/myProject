package com.banshengyuan.feima.help;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lei.he on 2018/1/12.
 */

public class MyWebViewClient extends WebViewClient {
    private WebView mWebView;

    public MyWebViewClient(WebView webView) {
        mWebView = webView;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';" +
                "}" +
                "})()");
    }
}
