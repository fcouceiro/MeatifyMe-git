package com.ruthlessgames.meatifymebuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class SignInTask extends AsyncTask<String,Void,Boolean> {

	SignInListener mListener = null;
	String username = "";
	
	public SignInTask(SignInListener sL){
		mListener=sL;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = "";
		username = params[0];
		//adiciona o query
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("query","SELECT password FROM `users` WHERE username='"+params[0]+"';"));
				
				InputStream is = null;
				//http post
				try{
				        HttpClient httpclient = new DefaultHttpClient();
				        HttpPost httppost = new HttpPost("http://meatifyme-dropoff.ciki.me/query_frontend.php");
				        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				        HttpResponse response = httpclient.execute(httppost);
				        HttpEntity entity = response.getEntity();
				        is = entity.getContent();
				}catch(Exception e){
				        Log.e("log_tag", "Error in http connection "+e.toString());
				        mListener.signFailed(1);
				}
				
				//convert response to string
				try{
				        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				        StringBuilder sb = new StringBuilder();
				        String line = null;
				        while ((line = reader.readLine()) != null) {
				                sb.append(line + "\n");
				        }
				        is.close();
				 
				        result=sb.toString();
				}catch(Exception e){
				        Log.e("log_tag", "Error converting result "+e.toString());
			
				}
				
				try {
					JSONArray jArray = new JSONArray(result);
					
					if(jArray.length() == 1){
						JSONObject json_data = jArray.getJSONObject(0);
						if(json_data.get("password").toString().contains(params[1])){
							return true;
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) this.mListener.signSuccedd(username);
		else{
			
			this.mListener.signFailed(0);
		}
	}

}
