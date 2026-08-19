package com.technews.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
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

    // ✨ NEW: sync native status bar with the app's theme
    @JavascriptInterface
    public void setStatusBarDark(final boolean dark) {
        final Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Window window = activity.getWindow();
                window.setStatusBarColor(dark ? 0xFF000000 : 0xFFF5F5F7);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController c = window.getInsetsController();
                    if (c != null) {
                        if (dark) c.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                        else c.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                    }
                } else {
                    View decor = window.getDecorView();
                    int flags = decor.getSystemUiVisibility();
                    if (dark) flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    else flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    decor.setSystemUiVisibility(flags);
                }
            }
        });
    }
}
