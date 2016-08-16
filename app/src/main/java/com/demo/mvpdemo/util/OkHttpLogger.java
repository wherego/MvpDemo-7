package com.demo.mvpdemo.util;

import android.util.Log;

import com.demo.mvpdemo.app.MyApp;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Create by zf 16/8/16
 */
public class OkHttpLogger implements HttpLoggingInterceptor.Logger {
    private static final int MAX_LOG_LENGTH = 4000;

    @Override
    public void log(String message) {
        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                Log.d(MyApp.LOG, message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }
}
