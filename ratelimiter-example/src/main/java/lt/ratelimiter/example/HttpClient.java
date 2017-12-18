package lt.ratelimiter.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author leitao.
 * @time: 2017/12/18  15:54
 * @version: 1.0
 * @description:
 **/
public class HttpClient {

    public  static void  main(String[] args) throws Exception{
        String serverUrl = "http://localhost:8080/rate";
        StringBuffer paramsBuffer = new StringBuffer();
        for (int i =0;i<1000;i++){
            invoker(serverUrl,paramsBuffer.toString());
        }
    }

    public static String invoker (String serverUrl, String args) throws Exception {
        URL url = new URL(serverUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //拼接入参
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setConnectTimeout(10000);
        con.setReadTimeout(60000);
        OutputStream out = null;
        StringBuilder rspBuffer = new StringBuilder();
        try {
            out = con.getOutputStream();
            out.write(args.toString().getBytes());
            out.flush();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;
                try {
                    while (null != (line = br.readLine())) {
                        rspBuffer.append(line);
                    }
                } catch (IOException e) {
                    throw new IOException(e.getMessage(), e);
                } finally {
                    if (null != in) {
                        in.close();
                    }
                    if (null != br) {
                        br.close();
                    }
                }
            } else {
                throw new Exception("请求响应的状态码" + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
        return rspBuffer.toString();
    }
}
