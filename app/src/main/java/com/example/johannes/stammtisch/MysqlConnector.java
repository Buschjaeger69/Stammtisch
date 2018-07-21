package com.example.johannes.stammtisch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Johannes on 20.07.2018.
 */

public class MysqlConnector extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String datei = params[0];

        URL url = null;
        try {
            url = new URL("http://192.168.100.57/Wizard/"+datei);

        } catch (MalformedURLException e) {
            Log.d("Fehler ","hi");
            e.printStackTrace();
        }
        // Verbindung aufbauen.
        URLConnection conn = null;
        Log.e("Fehler ",url.toString());
        try {
            conn = url.openConnection();
        } catch (IOException e) {

            e.printStackTrace();
        }
        conn.setDoOutput(true);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }



        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sb.toString();



    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

}
