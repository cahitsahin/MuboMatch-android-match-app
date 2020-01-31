package com.example.mubomatch.MoviesSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieSearch {

    private static String SEARCH_URL = "http://www.omdbapi.com/?s=title&apikey=e97a71f4";
    private static String SEARCH_URL_ID = "http://www.omdbapi.com/?i=id&apikey=e97a71f4";

    public static String searchMovieByTitle(String title) {
        return sendGetRequest(SEARCH_URL.replaceAll("title", title));
    }

    public static String searchMovieByID(String id) {
        return sendGetRequest(SEARCH_URL_ID.replaceAll("id", id));
    }

    private static String sendGetRequest(String requestUrl) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*///*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
            }
            buffer.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}

