package com.example.da106g2_emp.tools;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationTask extends AsyncTask<String, Integer, String> {
    private final static String TAG = "CommunicationTask";
    private String url, outStr, requstMethod;

    public void setRequstMethod(String requstMethod) {
        this.requstMethod = requstMethod;
    }

    public CommunicationTask(String url, String outStr) {
        this.url = url;
        this.outStr = outStr;
    }

    @Override
    protected String doInBackground(String... strings) {
        return getRemoteData();
    }

    private String getRemoteData() {
        StringBuilder inStr = new StringBuilder();

        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            //此隱含式寫法可自動關閉con
            try (AutoCloseable conc = () -> con.disconnect();) {

                //設定HTTP參數
                con.setChunkedStreamingMode(0);
                con.setUseCaches(false);
                if (requstMethod == null || requstMethod.length() <= 0) {
                    con.setRequestMethod("POST");
                } else {
                    con.setRequestMethod("GET");
                }

                //設定資料進出
                con.setDoInput(true);
                con.setDoOutput("POST".equals(con.getRequestMethod()));

                con.setRequestProperty("charset", "UTF-8");

                if ("POST".equals(con.getRequestMethod())) {
                    //con讀取要傳送的資料
                    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));) {
                        bw.write(outStr);
                        Log.d(TAG, "output: ");
                    }
                }

                //讀取回傳，如=200時將資料讀取近來使用
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            inStr.append(line);
                        }
                    }
                } else {
                    Log.d(TAG, "回傳CODE: " + responseCode);
                }
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "格式錯誤：" + e.toString());
        } catch (IOException e) {
            Log.d(TAG, "IO錯誤：" + e.toString());
        } catch (Exception e) {
            Log.d(TAG, "倒大楣啦！錯誤：" + e.toString());
        }

        return inStr.toString();
    }
}
