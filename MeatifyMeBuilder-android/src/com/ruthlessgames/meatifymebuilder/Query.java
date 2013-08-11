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

public class Query extends AsyncTask<String,Void,String[]>{
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	@Override
	protected void onPreExecute(){
		
		
	}
	
	@Override
	protected String[] doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String result[] = arg0;
		
		//adiciona o query
		nameValuePairs.add(new BasicNameValuePair("query",arg0[0]));
		
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
		 
		        result[0]=sb.toString();
		}catch(Exception e){
		        Log.e("log_tag", "Error converting result "+e.toString());
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result[]) {
		//parse json data
		if(result.length > 1){ 
		try{
		        JSONArray jArray = new JSONArray(result[0]);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                
		                //print expected results
		                for(int j=1;j<result.length;j++)
		               Log.i(result[1],""+ json_data.get(result[1]));
		              
		        }
		}catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
		}
		
		Log.i("FROM MYSQL",result[0]);
    }

}
