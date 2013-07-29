package com.me.meatifyme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.me.meatifyme.MeatifyMe;

public class MainActivity extends AndroidApplication implements ActionResolver{
	MeatifyMe maingame;
	AdView adView;
	View gameView;
	
	private final int SHOW_TOAST = 0;
	private final int SHOW_ALERT = 1;
	private CharSequence TOAST_TEXT;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        RelativeLayout layout = new RelativeLayout(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        maingame =	new MeatifyMe(this);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        gameView = initializeForView(maingame , cfg);
        
        layout.addView(gameView);
        
        AdRequest adreq = new AdRequest();
        //adreq.addTestDevice("90E7CA239B2B610FB2242326D511B267");
        // Create and setup the AdMob view
        adView = new AdView(this, AdSize.BANNER, "a151b46c7661def"); // Put in your secret key here
        adView.loadAd(adreq);
        
        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
       	adParams.addRule(RelativeLayout.ALIGN_RIGHT);
       	
        layout.addView(adView,adParams);
        
        setContentView(layout);
    }

	protected Handler handler = new Handler(){
		@Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            case SHOW_TOAST:
            	Toast.makeText(adView.getContext(), TOAST_TEXT, Toast.LENGTH_SHORT);
            	break;
            	
            case SHOW_ALERT:
            	break;
            }
		}
	};

	
	@Override
	public void showShortToast(final CharSequence toastMessage) {
		// TODO Auto-generated method stub
		this.TOAST_TEXT = toastMessage;
		handler.sendEmptyMessage(SHOW_TOAST);
	}

	@Override
	public void showAlertBox(String alertBoxTitle, String alertBoxMessage,
			String alertBoxButtonText) {
		// TODO Auto-generated method stub
		
	}
}