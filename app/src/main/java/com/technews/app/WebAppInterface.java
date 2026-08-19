package com.technews.app;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    private final Context context;

    WebAppInterface(Context context) { this.context = context; }

    @JavascriptInterface
    public void openArticle(String url, String title, String source) {
        Intent i = new Intent(context, ReaderActivity.class);
        i.putExtra("url", url);
        i.putExtra("title", title);
        i.putExtra("source", source);
        context.startActivity(i);
    }

    @JavascriptInterface
    public void shareArticle(String url, String title) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(share, "Share article"));
    }
}
