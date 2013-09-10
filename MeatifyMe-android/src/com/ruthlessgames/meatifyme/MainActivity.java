package com.ruthlessgames.meatifyme;

import yuku.iconcontextmenu.IconContextMenu;
import yuku.iconcontextmenu.IconContextMenu.IconContextItemSelectedListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
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
import com.lamerman.*;
import com.me.meatifyme.R;
import com.ruthlessgames.meatifyme.ActionResolver;
import com.ruthlessgames.meatifyme.MeatifyMe;

public class MainActivity extends AndroidApplication implements ActionResolver,OnClickListener, OnCheckedChangeListener{
	
	
	MeatifyMe maingame;
	AdView adView;
	View gameView;
	View mainmenu_view;
	View loading_view;
	View ingame_view;
	Button popup_but;
	PopupMenu niveis_popup;
	
	RelativeLayout main_layout;
	IconContextMenu popup;
	IconContextMenu niveis_icon_popup_menu;
	
	Animation flash_anim_btn;
	
	private int SELECTED_BLOCK_TYPE= 1;
	private final int SHOW_TOAST = 0;
	private final int SHOW_ALERT = 1;
	private CharSequence TOAST_TEXT;
	private final int SHOW_MAINMENU = 2;
	private final int HIDE_MAINMENU = 3;
	private final int SHOW_LOADING = 5;
	private final int DO_THE_FLASHY_THING = 6;
	private final int SHOW_INGAME_VIEW = 7;
	private final int PICK_FILE = 8;
	
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
		
		niveis_icon_popup_menu = new IconContextMenu(main_layout.getContext(),R.menu.popup);
		niveis_icon_popup_menu.setOnIconContextItemSelectedListener(new IconContextItemSelectedListener(){

			@Override
			public void onIconContextItemSelected(MenuItem item, Object info) {
				// TODO Auto-generated method stub

				if(item.getTitle() == "Cancel"){
					niveis_icon_popup_menu.dismiss();
				}
				else{
					handler.sendEmptyMessage(HIDE_MAINMENU);
					
					for(int i=0;i<maingame.nomes_levels_custom.length;i++){
						if(maingame.nomes_levels_custom[i].contains(item.getTitle())){
							maingame.startCustomLevel(i);
						}
					}
					handler.sendEmptyMessage(SHOW_INGAME_VIEW);
				}
			}});
		
		niveis_popup = new PopupMenu(main_layout.getContext(),((Button)mainmenu_view.findViewById(R.id.btnNiveis)));
		niveis_popup.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				if(arg0.getItemId() == R.id.np1){
					niveis_icon_popup_menu.show();
				}
				else if(arg0.getItemId() == R.id.np2){
					Intent intent = new Intent(getBaseContext(), FileDialog.class);
	                intent.putExtra(FileDialog.START_PATH, "/sdcard/Download");
	                
	                intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_OPEN);
	                
	                //can user select directories or not
	                intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
	                
	                //alternatively you can set file filter
	                intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "xml" });
	                
	                startActivityForResult(intent, PICK_FILE);
				}
				else if(arg0.getItemId() == R.id.np3){
					String url = "http://meatifyme-dropoff.ciki.me/";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
				return false;
			}
			
		});
		getMenuInflater().inflate(R.menu.niveis, niveis_popup.getMenu());
		
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
		
		//ingame ui
		ingame_view = inflater.inflate(R.layout.ingame_layout, main_layout,false);
		ingame_view.setVisibility(View.GONE);
		main_layout.addView(ingame_view);
		popup_but = (Button)ingame_view.findViewById(R.id.btnBlockType);
		popup_but.setOnClickListener(this);
		popup  = new IconContextMenu(main_layout.getContext(), R.menu.popup);
	        popup.setOnIconContextItemSelectedListener(new IconContextItemSelectedListener() {

				@Override
				public void onIconContextItemSelected(MenuItem arg0, Object info) {
					// TODO Auto-generated method stub
					if(arg0.getOrder() == 13) return;
					
					SELECTED_BLOCK_TYPE = arg0.getOrder();
					
					 Drawable d = getResources().getDrawable(blIds[SELECTED_BLOCK_TYPE]);
					popup_but.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
				}
		       });
	}
	
	public synchronized void onActivityResult(final int requestCode,
            int resultCode, final Intent data) {

            if (resultCode == Activity.RESULT_OK) {

                    if (requestCode == PICK_FILE) {
                            String filePath = data.getStringExtra(FileDialog.RESULT_PATH);
                            this.maingame.new_custom_level(filePath);
                            this.setNiveisPopupMenu();
                    }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            	Log.d("PICK_FILE", "Canceled by user");
            }

    }
	
	protected Handler handler = new Handler(){
		@Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            case SHOW_INGAME_VIEW:
            	if(ingame_view.getVisibility() == View.GONE)
            	ingame_view.setVisibility(View.VISIBLE);
            	else ingame_view.setVisibility(View.GONE);
            	break;
            case SHOW_TOAST:
            	Toast.makeText(mainmenu_view.getContext(), TOAST_TEXT, Toast.LENGTH_LONG).show();
            	break;
            	
            case SHOW_ALERT:
            	break;
            case SHOW_MAINMENU:
            	//gameView.setVisibility(View.GONE);
            	adView.setVisibility(View.VISIBLE);
            	mainmenu_view.setVisibility(View.VISIBLE);
            	loading_view.setVisibility(View.GONE);
            	ingame_view.setVisibility(View.GONE);
            	break;
            case HIDE_MAINMENU:
            	//gameView.setVisibility(View.VISIBLE);
            	adView.setVisibility(View.GONE);
            	mainmenu_view.setVisibility(View.GONE);
            	loading_view.setVisibility(View.GONE);
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
			maingame.setScreen(maingame.campanha);
			maingame.campanha.start();
			handler.sendEmptyMessage(SHOW_INGAME_VIEW);
			break;
		case R.id.btnNiveis:
			niveis_popup.show();
			break;
		case R.id.btnStartGame:
			maingame.gotoMainMenu();
			break;
		case R.id.btnBlockType:
			popup.show();
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
	
	int blIds[] = { 0,R.drawable.bl1,R.drawable.bl2,R.drawable.bl3,R.drawable.bl4,R.drawable.bl5,R.drawable.bl6,R.drawable.bl7,R.drawable.bl8,R.drawable.bl9,R.drawable.bl10,R.drawable.bl11,R.drawable.bl12};

	@Override
	public int getBlockType() {
		// TODO Auto-generated method stub
		return this.SELECTED_BLOCK_TYPE;
	}

	@Override
	public void setNiveisPopupMenu() {
		// TODO Auto-generated method stub
		Menu menu = niveis_icon_popup_menu.getMenu();
		menu.clear();
		for(int i=0;i<maingame.nomes_levels_custom.length;i++){
			menu.add(maingame.nomes_levels_custom[i].replace(".xml", ""));
    	}
		menu.add("Cancel");
	}
}