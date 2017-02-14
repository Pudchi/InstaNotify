package com.pudchi.slidewarn;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendGCM extends AsyncTask<String, Void, Integer> {
    private ProgressDialog mDialog;
    private Context mContext;



    public SendGCM(Context context) {
        mDialog = new ProgressDialog(context);
        mDialog.setTitle("發送緊急通知");
        mDialog.setMessage("發送通知中!");
        mDialog.setCancelable(true);
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        int status = -1;
        try {
            URL url = new URL("https://android.googleapis.com/gcm/send");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key = " + MainActivity.SERVER_API_KEY);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bufferedWriter.write(params[0]);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();





            connection.connect();
            status = connection.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    protected void onPostExecute(Integer statuscode) {
        if (statuscode != 200)
        {
            Toast.makeText(mContext, "發送失敗，請檢查你的 SERVER_API_KEY 是否有設定正確", Toast.LENGTH_LONG).show();
        }
        mDialog.cancel();
        Toast.makeText(mContext, "發送通知成功!", Toast.LENGTH_SHORT).show();
        super.onPostExecute(statuscode);
    }
}


    /*public static void post(String apiKey, Content content) {

        try{

            // 1. URL
            URL url = new URL("https://android.googleapis.com/gcm/send");

            // 2. Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 3. Specify POST method
            conn.setRequestMethod("POST");

            // 4. Set the headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key="+apiKey);

            conn.setDoOutput(true);

            // 5. Add JSON data into POST request body

            //`5.1 Use Jackson object mapper to convert Contnet object into JSON
            ObjectMapper mapper = new ObjectMapper();
            // 5.2 Get connection output stream
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            // 5.3 Copy Content "JSON" into
            mapper.writeValue(wr, content);
            // 5.4 Send the request
            wr.flush();

            // 5.5 close
            wr.close();

            // 6. Get the response
            int responseCode = conn.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 7. Print result
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/