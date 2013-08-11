package com.ruthlessgames.meatifymebuilder;

import org.apache.commons.net.ftp.FTPClient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CreateUserRemoteDir extends AsyncTask<String,Void,Boolean>{

	ProgressDialog pDialog;
	Dialog signDialog;
	
	public CreateUserRemoteDir(ProgressDialog c,Dialog d){
		pDialog = c;
		signDialog = d;
	}
	
	@Override
	protected void onPreExecute(){
		pDialog.setMessage("Creating remote directory.");
	}
	
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		FTPClient mFtpClient = new FTPClient();
		
		try{
			
			mFtpClient.connect("185.28.21.15");
			
			if( mFtpClient.isConnected()){
				if (mFtpClient.login("u860682274", "fcouceiro94")) {
					Log.d("LOGIN", "SUCCESSFUL");
					if(mFtpClient.makeDirectory(arg0[0])){
						return true;
					}
					else return false;
				}
				else Log.d("LOGIN", "FAILED");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		pDialog.dismiss();
		
		if(result){
			Toast.makeText(pDialog.getContext(), "Successfully signed up", Toast.LENGTH_LONG).show();
			signDialog.dismiss();
		}
		else Toast.makeText(pDialog.getContext(), "An error occurred. Make sure you have internet access.", Toast.LENGTH_LONG).show();
    }
}
