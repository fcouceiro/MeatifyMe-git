package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
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
		storyboard_animation = new SequenceAction();
		this.pop_drawables();
	}
	
	private void load(){
		Preferences pref = Gdx.app.getPreferences("campaign");
		this.cur_level = pref.getInteger("cur_level",0);
	}
	
	private void save(){
		Preferences pref = Gdx.app.getPreferences("campaign");
		pref.putInteger("cur_level", this.cur_level);
		pref.flush();
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
		this.storyboard_animation.getActions().clear();
		this.storyboard_animation.reset();
		
		switch(level_index){
		case 0:
			return level1();
		case 1:
			return level2();
		case 2:
			return level3();
		case 3 :
			return level4();
		case 4:
			return level6();
		case 5:
			return level7();
		case 6:
			return level8();
		case 7:
			return level8to9();
		case 8:
			return evolution2();
		case 9:
			return level9();
		case 10:
			return level10();
		case 11:
			return level11();
		case 12:
			return evolution3();
		case 13:
			return level12();
		case 14:
			return level13();
		case 15:
			return level14();
		case 16:
			return level15();
		case 17:
			return level16();
		}
		
		Gdx.app.log("returned null", "Cur level is" + this.cur_level);
		return null;
	}
	
	private void setPanel(final Drawable panel){
		storyboard_animation.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				table.setBackground(panel);
			}
			
		}));
	}
	
	private void setLevel(){
		storyboard_animation.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				maingame.actionResolver.showReady(true);
			}
			
		}));
	}
	
	private SequenceAction level1(){
		setPanel(level1[0]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[1]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(level1[0]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[2]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[3]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(level1[2]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[4]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[5]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(level1[6]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level1[7]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction level2(){
		setPanel(level2);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction level3(){
		setPanel(level3);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction level4(){
		setPanel(level4[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(level4[1]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level4[2]);
		storyboard_animation.addAction(Actions.delay(1));
		setPanel(level4[3]);
		storyboard_animation.addAction(Actions.delay(1));
		return this.evolution1();
	}
	
	private SequenceAction evolution1(){
		setPanel(evo1[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo1[1]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo1[2]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo1[3]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo1[4]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction level6(){
		setPanel(level6);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction level7(){
		setPanel(level7);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}

	private SequenceAction level8(){
		setPanel(level8[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level8to9(){
		setPanel(level8[1]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}
	
	private SequenceAction evolution2(){
		setPanel(evo2[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[1]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[2]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[3]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[4]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[5]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[6]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[7]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo2[8]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level9(){
		setPanel(level9);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level10(){
		setPanel(level10[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(level10[1]);
		storyboard_animation.addAction(Actions.delay(4));
		setPanel(level10[2]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level11(){
		setPanel(level11);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction evolution3(){
		setPanel(evo3[0]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo3[1]);
		storyboard_animation.addAction(Actions.delay(2));
		setPanel(evo3[2]);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level12(){
		setPanel(level12);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level13(){
		setPanel(level13);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level14(){
		setPanel(level14);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level15(){
		setPanel(level15);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
		return this.storyboard_animation;
	}	
	
	private SequenceAction level16(){
		setPanel(level16);
		storyboard_animation.addAction(Actions.delay(2));
		setLevel();
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
		evo1[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/2.png"));
		evo1[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/3.png"));
		evo1[3] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/4.png"));
		evo1[4] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_1/evolution/5.png"));
		
		//stage 2
		level6 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/level6/1.png"));
		
		level7 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/level7/1.png"));
		
		level8[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/level8/1.png"));
		level8[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/level8/2.png"));
		
		evo2[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/1.png"));
		evo2[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/2.png"));
		evo2[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/3.png"));
		evo2[3] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/4.png"));
		evo2[4] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/5.png"));
		evo2[5] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/6.png"));
		evo2[6] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/7.png"));
		evo2[7] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/8.png"));
		evo2[8] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_2/evolution/9.png"));
		
		//stage 3
		level9 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/level9/1.png"));
		
		level10[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/level10/1.png"));
		level10[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/level10/2.png"));
		level10[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/level10/3.png"));
		
		level11 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/level11/1.png"));
		
		evo3[0] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/evolution/1.png"));
		evo3[1] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/evolution/2.png"));
		evo3[2] = new TextureRegionDrawable(loadSprite("levels/campaign/stage_3/evolution/3.png"));
		
		//stage 4
		level12 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_4/level12/1.png"));
		level13 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_4/level13/1.png"));
		level14 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_4/level14/1.png"));
		level15 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_4/level15/1.png"));
		level16 = new TextureRegionDrawable(loadSprite("levels/campaign/stage_4/level16/1.png"));
	}
	
	private Sprite loadSprite(String name)
	{
		Texture a = MeatifyMe.asm.get(name,Texture.class);
		a.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return new Sprite(a);
	}
	
	public void incLevel(){
		this.cur_level++;
		maingame.setScreen(this);
		table.addAction(this.startStoryBoardFrom(cur_level));
		this.save();
	}
	
	public int getCurLevel()
	{
		return this.cur_level;
	}
	
	@Override
	public void render(float arg0){
		super.render(arg0);
		
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			maingame.gotoMainMenu();
		}
	}
	
	@Override
	public void show(){
		super.show();
		
		if(maingame.sound){
			Sounds.stop();
		}
	}
}
