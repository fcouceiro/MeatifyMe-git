package com.me.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.*;


public class LevelSelector extends UI{


	MeatifyMe maingame;
	
	Vector2 bsize = new Vector2(Gdx.graphics.getWidth() / 3,Gdx.graphics.getHeight() /14);
	Vector2 center = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() / 2);
	
	public LevelSelector(MeatifyMe main)
	{
		super(main.batch,main.font,false);
		maingame = main;
	
        table.setBackground(new TextureRegionDrawable(Textures.main_bg));
        
        
		this.populateLevels();
		
		//botao back
		final TextButton button2 = new TextButton("Back", StylesManager.skin);
		button2.setBounds(center.x + center.x /2 - bsize.x/2, Gdx.graphics.getHeight()/20, bsize.x, bsize.y);
		button2.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	               	
	                return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	if(x < button2.getWidth() && x >0 && y<button2.getHeight() && y > 0)
	        	maingame.setScreen(maingame.mainmenu);
	        }
		});
		
		table.addActor(button2);
	}
	
	public void populateLevels()
	{
		int counter = 0;
		
		//popular a tabela com botoes
		for(final Level lev:maingame.levels)
		{
			final TextButton button1 = new TextButton(lev.name.toString(), StylesManager.skin);
			button1.setBounds(center.x - bsize.x, 3* bsize.y + 1.2f*bsize.y*counter, bsize.x, bsize.y);

			button1.addListener(new InputListener() {
		        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		               	
		                return true;
		        }
		        
		        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		        	if(x < button1.getWidth() && x >0 && y<button1.getHeight() && y > 0){
		        		maingame.setScreen(lev);
		        	}
		        	
		        }
			});
			
			table.addActor(button1);
			counter++;
		}
	}
	
	public void populateCustomLevels()
	{
		int counter = 0;
		
		//popular a tabela com botoes
		for(final Level lev:maingame.levels_custom)
		{
			final TextButton button1 = new TextButton(lev.name.toString(), StylesManager.skin);
			button1.setBounds(center.x +bsize.x - bsize.x/2, 3* bsize.y + 1.2f*bsize.y*counter, bsize.x, bsize.y);

			button1.addListener(new InputListener() {
		        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		               	
		                return true;
		        }
		        
		        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		        	if(x < button1.getWidth() && x >0 && y<button1.getHeight() && y > 0){
		        		maingame.setScreen(lev);
		        	}
		        	
		        }
			});
			
			table.addActor(button1);
			counter++;
		}
	}
	

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
