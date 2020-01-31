package com.example.mubomatch.BookSearch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookSearch {

    private static String SEARCH_BOOK = "https://www.googleapis.com/books/v1/volumes?q=quilting";
    private static String SEARCH_BOOK_ID = "https://www.googleapis.com/books/v1/volumes/id";

    public static String searchBookByTitle(String title) {
        return sendGetRequest(SEARCH_BOOK.replaceAll("quilting", title));
    }

    public static String searchBookByID(String id) {
        return sendGetRequest(SEARCH_BOOK_ID.replaceAll("id", id));
    }

    private static String sendGetRequest(String requestUrl) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
