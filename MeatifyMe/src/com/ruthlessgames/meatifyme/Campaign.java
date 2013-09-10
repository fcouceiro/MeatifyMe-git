package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.UI;

public class Campaign extends UI{
	
	MeatifyMe maingame;
	String[] nomes_mapas;

	private int cur_level = 0;
	private SequenceAction storyboard_animation;
	
	//stage 1
	Drawable[] level1 = new Drawable[8];
	Drawable level2,level3;
	Drawable[] level4 = new Drawable[4];
	Drawable[] evo1 = new Drawable[5];
	
	//stage 2
	Drawable level6,level7;
	Drawable[] level8 = new Drawable[2];
	Drawable[] evo2 = new Drawable[9];
	
	//stage 3
	Drawable[] level10 = new Drawable[3];
	Drawable level9,level11;
	Drawable[] evo3 = new Drawable[3];
	
	//stage 4
	Drawable level12,level13,level14,level15,level16;

	public Campaign(MeatifyMe main){
		super(main.batch,main.font,false);
		maingame = main;
		nomes_mapas = main.nomes_levels;
		this.pop_drawables();
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
		
		storyboard_animation = new SequenceAction();
		storyboard_animation.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				table.setBackground(level1[0]);
			}
			
		}));
		return this.storyboard_animation;
	}
	
	private void pop_drawables(){
		//stage 1
		level1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/1.png"));
		level1[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/2.png"));
		level1[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/3.png"));
		level1[3] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/4.png"));
		level1[4] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/5.png"));
		level1[5] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/6.png"));
		level1[6] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/7.png"));
		level1[7] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level1/8.png"));
		
		level2 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level2/1.png"));
		
		level3 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level3/1.png"));
		
		level4[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level4/1.png"));
		level4[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level4/2.png"));
		level4[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level4/3.png"));
		level4[3] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/level4/4.png"));
		
		evo1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/1.png"));
		evo1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/2.png"));
		evo1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/3.png"));
		evo1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/4.png"));
		evo1[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/5.png"));
	}
	
	private Sprite loadSprite(String name)
	{
		Texture a = MeatifyMe.asm.get(name,Texture.class);
		a.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return new Sprite(a);
	}
}
