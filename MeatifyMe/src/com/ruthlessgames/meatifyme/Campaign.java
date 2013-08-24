package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ruthlessgames.api.UI;

public class Campaign extends UI{
	
	//ref to maingame
	MeatifyMe maingame;
	String[] nomes_mapas;

	private int cur_level = 0;
	private SequenceAction storyboard_animation;
	
	public Campaign(MeatifyMe main){
		super(main.batch,main.font,false);
		maingame = main;
		nomes_mapas = main.nomes_levels;
	}
	
	private void load(){
		Preferences pref = Gdx.app.getPreferences("campaign");
		this.cur_level = pref.getInteger("cur_level",0);
	}
	
	private void save(){
		Preferences pref = Gdx.app.getPreferences("campaign");
		pref.putInteger("cur_level", this.cur_level);
	}
	
	public void start(){
		this.load();
		table.addAction(this.startStoryBoardFrom(cur_level));
	}
	
	public void stop(){
		if(storyboard_animation != null){
			table.removeAction(storyboard_animation);
		}
		this.save();
	}
	
	private SequenceAction startStoryBoardFrom(int level_index){
		int stage = 1;
		if(level_index > 0 && level_index <=2) stage = 1;
		else if(level_index > 2 && level_index <= 7) stage = 2;
		else if(level_index > 7 && level_index <= 15) stage = 3;
		else if(level_index > 15 && level_index <= 20) stage = 4;
		
		switch(level_index){
		case 0:
			break;
		}
		return this.storyboard_animation;
	}
}
