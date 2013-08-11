package com.ruthlessgames.meatifymebuilder;

import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FileUpload extends AsyncTask<FileToUpload,Integer,Boolean>{

	ProgressBar pBar;
	String username = "";
	
	public FileUpload(String username,ProgressBar progress_bar){
		pBar = progress_bar;
		this.username = username;
	}
	
	@Override
	protected void onPreExecute(){
		pBar.setIndeterminate(true);
		pBar.setEnabled(true);
		pBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected Boolean doInBackground(FileToUpload... arg0) {
		// TODO Auto-generated method stub
		
		FTPClient mFtpClient = new FTPClient();
		boolean debug_bool[] = new boolean[arg0.length];
		try{
			
			mFtpClient.connect("185.28.21.15");
			
			if( mFtpClient.isConnected()){
				if (mFtpClient.login("u860682274", "fcouceiro94")) {
					Log.d("LOGIN", "SUCCESSFUL");
					
					if(mFtpClient.changeWorkingDirectory(username));
					for(int i=0;i<arg0.length;i++){
						if(mFtpClient.storeFile(arg0[i].nome + ".xml", arg0[i].ficheiro)){
							debug_bool[i] = true;
							Log.d("UPLOAD", "SUCCESSFUL - " + arg0[i].nome);
						}
						else{
							debug_bool[i] = false;
							Log.d("UPLOAD", "FAILED");
						}
					}
				}
				else Log.d("LOGIN", "FAILED");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i=0;i<arg0.length;i++){
			if(!debug_bool[i]) return false;
		}
		
		return true;
	}
	
	@Override
	 protected void onProgressUpdate(Integer... progress) {
        
    }
	
	@Override
	protected void onPostExecute(Boolean result) {
		pBar.setVisibility(View.GONE);
		Toast.makeText(pBar.getContext(), result ? "Done" : "An error ocurred", Toast.LENGTH_SHORT).show();
       Log.d("COMPLETED TASK","RETURNED: " + (result ? "true" : "false"));
    }
}
