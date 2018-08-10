package com.example.sandarumk.bakingapp.utils;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dinu on 9/17/17.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private final static String DELIMITER = "\\A";

    public static URL buildUrl(Context context) {
        URL url = null;
        try {
            url = new URL(JSON_URL);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(DELIMITER);

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

//    public static URL buildTrailerURL(Context context, String movieID){
//        URL url = null;
//        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
//                .appendQueryParameter(API_KEY, context.getString(R.string.movie_db_api_key))
//                .appendQueryParameter(LANUGUAGE, context.getString(R.string.language_en_us))
//                .appendPath(movieID)
//                .appendPath("videos")
//                .build();
//        try {
//            url = new URL(builtUri.toString());
//            Log.d(TAG, url.toString());
//        } catch (MalformedURLException e) {
//            Log.e(TAG, e.toString());
//        }
//        return url;
//    }

//    public static URL buildReviewURL(Context context, String movieID){
//        URL url = null;
//        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
//                .appendQueryParameter(API_KEY, context.getString(R.string.movie_db_api_key))
//                .appendQueryParameter(LANUGUAGE, context.getString(R.string.language_en_us))
//                .appendPath(movieID)
//                .appendPath("reviews")
//                .build();
//        try {
//            url = new URL(builtUri.toString());
//            Log.d(TAG, url.toString());
//        } catch (MalformedURLException e) {
//            Log.e(TAG, e.toString());
//        }
//        return url;
//    }
}
