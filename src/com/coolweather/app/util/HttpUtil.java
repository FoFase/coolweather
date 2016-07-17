package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					//填入apikey到HTTP header
					connection.setRequestProperty("apikey",  "7606f03963df8eb750a3bb37639dcebf");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.connect();//新加入代码
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));//以UTF-8解码
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					if(listener!=null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						listener.onError(e);
					}
				} finally {
					if(connection!=null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
