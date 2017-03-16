package com.example.admin.ca2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPManager {

    // A URI is passed in from MovieModel
    // the content at this URI is returned
    public static String getData(String uri) {

        BufferedReader reader = null;

        // if all goes well the code in the try block is executed successfully
        try {
            URL url = new URL(uri);

            // open a connection to that URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //used to hold the string read in from the file
            StringBuilder sb = new StringBuilder();

            //Users to read the stream of characters read in from the file
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            //holds a line of characters
            String line;

            // the reader will continue to read in from the input stream till everything is read in
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            // convert sb to a strign and return the lot the MainActivity for output to the UI
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // Finally is important to avoid leaks
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

}

