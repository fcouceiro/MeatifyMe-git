package com.ruthlessgames.meatifymebuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.IntBuffer;

import yuku.iconcontextmenu.IconContextMenu;
import yuku.iconcontextmenu.IconContextMenu.IconContextItemSelectedListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;

public class MainActivity extends AndroidApplication implements OnClickListener,TabListener,AndroidBridge,OnMenuItemClickListener,OnCheckedChangeListener{
	
	RelativeLayout main_container;
	Button popup_but;
	View gameview;
	View menu_view;
	View export_view;
	MeatifyMeBuilder editor;
	IconContextMenu popup;
	PopupMenu level_popup;
	AlertDialog.Builder alert;
	
	int idTabA = 0;
	int idTabB = 1;
	int idTabC = 2;
	
	ActionBar act_bar;
	int SELECTED_BLOCK_TYPE = 1;
	int SELECTED_LEVEL_STYLE = 4;
	int SELECTED_LEVEL_TYPE = 1;
	
	boolean BORDER_ON = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        main_container = new RelativeLayout(this);
        act_bar = getActionBar();
        act_bar.setDisplayShowTitleEnabled(false);
        act_bar.setDisplayUseLogoEnabled(false);
        act_bar.setDisplayShowHomeEnabled(false);                              
        act_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabA = act_bar.newTab().setText("Editor").setTabListener(this).setTag(idTabA);
        ActionBar.Tab tabB = act_bar.newTab().setText("Export").setTabListener(this).setTag(idTabB);
        ActionBar.Tab tabC = act_bar.newTab().setText("Settings").setTabListener(this).setTag(idTabC);
        
       
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        //gameview
        editor = new MeatifyMeBuilder(this);
        gameview  = initializeForView(editor , cfg);
        
       menu_view = getLayoutInflater().inflate(R.layout.main, main_container,false);
       popup_but = (Button) menu_view.findViewById(R.id.teste_id);
       popup_but.setOnClickListener(this);

       Drawable d = getResources().getDrawable(blIds[1]);
		popup_but.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
		
        main_container.addView(gameview);
        main_container.addView(menu_view);
        
        popup  = new IconContextMenu(main_container.getContext(), R.menu.popup);
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
        
   
        Button level_popup_but = (Button) menu_view.findViewById(R.id.btnLevel);
        level_popup_but.setOnClickListener(this);
        
        level_popup = new PopupMenu(this, level_popup_but);
        level_popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = level_popup.getMenuInflater();
        inflater.inflate(R.menu.level_popup, level_popup.getMenu());
        
        ToggleButton border_toggle = (ToggleButton) menu_view.findViewById(R.id.btnBorder);
        border_toggle.setOnCheckedChangeListener(this);

        
        alert  = new AlertDialog.Builder(this);                 
        alert.setTitle("Level name");  
        alert.setMessage("Enter name:");                

         // Set an EditText view to get user input   
         final EditText input = new EditText(this); 
         alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int whichButton) {  
                String value = input.getText().toString();
                editor.name = value.trim();
                return;                  
               }  
             });  

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    return;   
                }
            });
                
        
        //export view
        
        export_view = getLayoutInflater().inflate(R.layout.export_layout, main_container,false);
        export_view.setVisibility(View.GONE);
        
        ((Button)export_view.findViewById(R.id.btnUpload)).setOnClickListener(this);
      

        main_container.addView(export_view);
        //settings view
		
		 setContentView(main_container);
        
		 act_bar.addTab(tabA);
	        act_bar.addTab(tabB);
	        act_bar.addTab(tabC);
	       
    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.teste_id:
			popup.show();
			break;
		case R.id.btnLevel:
			level_popup.show();
			break;
		case R.id.btnUpload:
			new Connector().execute(editor.save());
			break;
		}
	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		if(arg0.getTag()== (Object)idTabA)
		{
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gameview.setVisibility(View.VISIBLE);
					menu_view.setVisibility(View.VISIBLE);
					export_view.setVisibility(View.GONE);
				}});
		}
		else if(arg0.getTag()== (Object)idTabB)
		{
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					gameview.setVisibility(View.GONE);
					menu_view.setVisibility(View.GONE);
					
					String type_text = "";
					switch(SELECTED_LEVEL_TYPE){
					case 1:
						type_text = getString(R.string.strType1);
						break;
					case 2:
						type_text = getString(R.string.strType2);
						break;
					case 3:
						type_text = getString(R.string.strType3);
						break;
					}
					
					String style_text = "";
					switch(SELECTED_LEVEL_STYLE){
					case 1:
						style_text = getString(R.string.strStyle1);
						break;
					case 2:
						style_text = getString(R.string.strStyle2);
						break;
					case 3:
						style_text = getString(R.string.strStyle3);
						break;
					case 4:
						style_text = getString(R.string.strStyle4);
						break;
					}
					
					//update view items
					((TextView)export_view.findViewById(R.id.labelLevelName)).setText("Level name: " + editor.name);
					((TextView)export_view.findViewById(R.id.labelLevelType)).setText("Level type: " + type_text);
					((TextView)export_view.findViewById(R.id.labelLevelStyle)).setText("Level style: " + style_text);
					((TextView)export_view.findViewById(R.id.labelBlockTotal)).setText("Block total: " + editor.blocks_rep.size());
				
					export_view.setVisibility(View.VISIBLE);
				}});
		}
		else{
			
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gameview.setVisibility(View.GONE);
					menu_view.setVisibility(View.GONE);
					export_view.setVisibility(View.GONE);
				}});
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	int blIds[] = { 0,R.drawable.bl1,R.drawable.bl3,R.drawable.bl3,R.drawable.bl4,R.drawable.bl5,R.drawable.bl6,R.drawable.bl7,R.drawable.bl8,R.drawable.bl9,R.drawable.bl10,R.drawable.bl11,R.drawable.bl12};
	@Override
	public int getSelectedBlockType() {
		// TODO Auto-generated method stub
		return this.SELECTED_BLOCK_TYPE;
	}



	@Override
	public void showToast(final CharSequence a) {
		// TODO Auto-generated method stub
		this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(gameview.getContext(), a, Toast.LENGTH_LONG).show();
			}});
	}


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.Style_item1:
			this.SELECTED_LEVEL_STYLE = 1;
			break;
		case R.id.Style_item2:
			this.SELECTED_LEVEL_STYLE = 2;
			break;
		case R.id.Style_item3:
			this.SELECTED_LEVEL_STYLE = 3;
			break;
		case R.id.Style_item4:
			this.SELECTED_LEVEL_STYLE = 4;
			break;
		case R.id.Type_item1:
			this.SELECTED_LEVEL_TYPE = 1;
			break;
		case R.id.Type_item2:
			this.SELECTED_LEVEL_TYPE = 2;
			break;
		case R.id.Type_item3:
			this.SELECTED_LEVEL_TYPE = 3;
			break;
		case R.id.btnName:
			alert.show();
			break;
		}
		
		editor.setStyle(SELECTED_LEVEL_STYLE);
		editor.setType(SELECTED_LEVEL_TYPE);
		return false;
	}


	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		this.BORDER_ON = arg1;
		this.editor.setBorder(BORDER_ON,this.SELECTED_BLOCK_TYPE);
	}
	
}