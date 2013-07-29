package com.me.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class MainMenu extends UI{

	MeatifyMe maingame;
	Vector2 mpos = new Vector2(0,0);
	boolean wait_for_game = false;
	
	public MainMenu(MeatifyMe main)
	{
		super(main.batch,main.font,false);
		maingame = main;
		table.setBackground(new TextureRegionDrawable(Textures.main_bg));
		
		TextButton btnCampaign = new TextButton("Campaign",StylesManager.skin);
		btnCampaign.setBounds(Level.ratio.x,Level.ratio.y, 4*Level.ratio.x,Level.ratio.y);
	
		TextButton btnSelLev = new TextButton("Levels",StylesManager.skin);
		btnSelLev.setBounds(6*Level.ratio.x,Level.ratio.y, 5*Level.ratio.x,Level.ratio.y);
	
		final TextButton btnLevEditor = new TextButton("Create more",StylesManager.skin);
		btnLevEditor.setBounds(12*Level.ratio.x,Level.ratio.y, 5*Level.ratio.x,Level.ratio.y);
	
		TextButton btnOptions = new TextButton("Options",StylesManager.skin);
		btnOptions.setBounds(Level.ratio.x,5*Level.ratio.y, 3*Level.ratio.x,3*Level.ratio.y);
	
		TextButton btnCloud = new TextButton("OntTheCloud",StylesManager.skin);
		btnCloud.setBounds(15*Level.ratio.x,13*Level.ratio.y, 5*Level.ratio.x,Level.ratio.y);
	
		
		btnSelLev.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnLevEditor.getWidth() && x >0 && y<btnLevEditor.getHeight() && y > 0)
				{
					maingame.setScreen(maingame.levelSelector);	
				}
					
			}
		});
		
		btnLevEditor.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnLevEditor.getWidth() && x >0 && y<btnLevEditor.getHeight() && y > 0)
				{
					maingame.setScreen(maingame.editor);	
				}
					
			}
		});
		
		table.addActor(btnCampaign);
		table.addActor(btnSelLev);
		table.addActor(btnLevEditor);
		table.addActor(btnOptions);
		table.addActor(btnCloud);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(!Sounds.maintheme.isPlaying()){
		Sounds.maintheme.setLooping(true);
		Sounds.maintheme.play();
		}
		
		Gdx.input.setInputProcessor(stage);
	}

}
