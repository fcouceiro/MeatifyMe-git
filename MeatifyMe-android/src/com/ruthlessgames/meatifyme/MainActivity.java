package com.ruthlessgames.meatifyme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.me.meatifyme.R;
import com.ruthlessgames.meatifyme.ActionResolver;
import com.ruthlessgames.meatifyme.MeatifyMe;

public class MainActivity extends AndroidApplication implements ActionResolver,OnClickListener, OnCheckedChangeListener{
	
	MeatifyMe maingame;
	AdView adView;
	View gameView;
	View mainmenu_view;
	View niveis_view;
	View loading_view;
	RelativeLayout main_layout;
	
	Animation flash_anim_btn;
	int start_click_count = 0;
	
	private final int SHOW_TOAST = 0;
	private final int SHOW_ALERT = 1;
	private CharSequence TOAST_TEXT;
	private final int SHOW_MAINMENU = 2;
	private final int HIDE_MAINMENU = 3;
	private final int SHOW_NIVEIS = 4;
	private final int SHOW_LOADING = 5;
	private final int DO_THE_FLASHY_THING = 6;
	private boolean niveis_set = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        main_layout = new RelativeLayout(this);
        
        
        //create hard stuff
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        maingame =	new MeatifyMe(this);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
      
        gameView = initializeForView(maingame , cfg);
        
        main_layout.addView(gameView);
        
        
        
        AdRequest adreq = new AdRequest();
        //adreq.addTestDevice("90E7CA239B2B610FB2242326D511B267");
        
        // Create and setup the AdMob view
        adView = new AdView(this, AdSize.BANNER, "a151fe9e9ceb083"); // Put in your secret key here
        adView.loadAd(adreq);
        
        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
       	adParams.addRule(RelativeLayout.ALIGN_RIGHT);
       	
       
        
       	//pop the ui
       	this.pop_UI();
       	
    	main_layout.addView(adView,adParams);
		
        setContentView(main_layout);
    }

	private void pop_UI(){
		//mainmenu
		LayoutInflater inflater = getLayoutInflater();
		mainmenu_view = inflater.inflate(R.layout.main, main_layout, false);
		mainmenu_view.setVisibility(View.GONE);
		
		((CheckBox) mainmenu_view.findViewById(R.id.checkSound)).setOnCheckedChangeListener(this);
		((Button) mainmenu_view.findViewById(R.id.btnCampanha)).setOnClickListener(this);
		((Button) mainmenu_view.findViewById(R.id.btnNiveis)).setOnClickListener(this);
		
		main_layout.addView(mainmenu_view);
		
		//niveis
		niveis_view = inflater.inflate(R.layout.niveis_layout, main_layout,false);
		niveis_view.setVisibility(View.GONE);
		((Button) niveis_view.findViewById(R.id.btnPlayNiveis)).setOnClickListener(this);
		main_layout.addView(niveis_view);
		
		//loading
		loading_view = inflater.inflate(R.layout.laoding_layout, main_layout,false);
		loading_view.setVisibility(View.GONE);
		
		main_layout.addView(loading_view);
		
		//flashy button
		flash_anim_btn = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
	    flash_anim_btn.setDuration(500); // duration - half a second
	    flash_anim_btn.setInterpolator(new LinearInterpolator()); // do not alter flash_anim_btn rate
	    flash_anim_btn.setRepeatCount(Animation.INFINITE); // Repeat flash_anim_btn infinitely
	    flash_anim_btn.setRepeatMode(Animation.REVERSE);
	    Button btnStart = (Button)this.loading_view.findViewById(R.id.btnStartGame);
	    btnStart.setVisibility(View.GONE);
		btnStart.setOnClickListener(this);
		flash_anim_btn.reset();
		btnStart.startAnimation(flash_anim_btn);
	}
	
	protected Handler handler = new Handler(){
		@Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            case SHOW_TOAST:
            	Toast.makeText(mainmenu_view.getContext(), TOAST_TEXT, Toast.LENGTH_LONG).show();
            	break;
            	
            case SHOW_ALERT:
            	break;
            case SHOW_MAINMENU:
            	//gameView.setVisibility(View.GONE);
            	adView.setVisibility(View.VISIBLE);
            	mainmenu_view.setVisibility(View.VISIBLE);
            	niveis_view.setVisibility(View.GONE);
            	loading_view.setVisibility(View.GONE);
            	break;
            case HIDE_MAINMENU:
            	//gameView.setVisibility(View.VISIBLE);
            	adView.setVisibility(View.GONE);
            	mainmenu_view.setVisibility(View.GONE);
            	niveis_view.setVisibility(View.GONE);
            	loading_view.setVisibility(View.GONE);
            	break;
            case SHOW_NIVEIS:
            	((Spinner)niveis_view.findViewById(R.id.spinnerCustomLevels)).setAdapter(new ArrayAdapter<String>(niveis_view.getContext(),
                        android.R.layout.simple_spinner_item, maingame.nomes_levels_custom));
            	mainmenu_view.setVisibility(View.GONE);
            	niveis_view.setVisibility(View.VISIBLE);
            	break;
            case SHOW_LOADING:
            	loading_view.setVisibility(View.VISIBLE);
            	break;
            case DO_THE_FLASHY_THING:
            	ProgressBar pbar = (ProgressBar) loading_view.findViewById(R.id.pbarLoading);
            	pbar.setVisibility(View.GONE);
    			Button btnStart = (Button)loading_view.findViewById(R.id.btnStartGame);
    			btnStart.setVisibility(View.VISIBLE);
    			
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

	@Override
	public void showMainMenu() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(SHOW_MAINMENU);
	}
	
	//input stuff
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.btnCampanha:
			handler.sendEmptyMessage(HIDE_MAINMENU);
			maingame.startLevel(0);
			break;
		case R.id.btnNiveis:
			handler.sendEmptyMessage(SHOW_NIVEIS);
			break;
		case R.id.btnPlayNiveis:
			handler.sendEmptyMessage(HIDE_MAINMENU);
			Spinner spinner = (Spinner) niveis_view.findViewById(R.id.spinnerCustomLevels);
			maingame.startCustomLevel(spinner.getSelectedItemPosition());
			break;
		case R.id.btnStartGame:
			if(this.start_click_count > 5)
			{
				maingame.gotoMainMenu();
			}
			else
			this.start_click_count++;
			
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.checkSound){
			if(arg1)
			Toast.makeText(mainmenu_view.getContext(), "We've some good damn sound!", Toast.LENGTH_LONG).show();
			
			maingame.setSound(arg1);
		}
		
	}
	
	@Override
	public void onBackPressed (){
		if(niveis_view.getVisibility() == View.VISIBLE)
		{
			handler.sendEmptyMessage(SHOW_MAINMENU);
		}
		
		Log.v("BACK", "PRESSED"); 
	}

	@Override
	public void backPressed() {
		// TODO Auto-generated method stub
		onBackPressed();
	}

	@Override
	public void updateProgressLoading(float prog) {
		// TODO Auto-generated method stub
		ProgressBar pbar = (ProgressBar) loading_view.findViewById(R.id.pbarLoading);
		pbar.setProgress((int)(prog * 100));
		
		if(pbar.getProgress() == pbar.getMax())
		{
			handler.sendEmptyMessage(DO_THE_FLASHY_THING);
		}
	}

	@Override
	public void showLoading() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(SHOW_LOADING);
	}
}