package com.me.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class Editor extends UI{

	MeatifyMe maingame;
	Level curLevel;
	Vector2 mpos = new Vector2(0,0);
	int curType = 2;
	
	Table setName;
	Table menu;
	
	public Editor(MeatifyMe main)
	{
		super(main.batch,main.font,false);
		
		maingame = main;
		newLevel();
		
		this.pop_menu();

//		buttons.add(new Button(null,"Border", new Vector2(11.3f*Level.MeatifyMe.bWidth,2*Level.MeatifyMe.bHeight),new Vector2(3f*Level.MeatifyMe.bWidth,Level.MeatifyMe.bHeight),new Vector2(10,18),borderInput));
//		buttons.add(new Button(null,"Save", new Vector2(14.5f*Level.MeatifyMe.bWidth,2*Level.MeatifyMe.bHeight),new Vector2(2.5f*Level.MeatifyMe.bWidth,Level.MeatifyMe.bHeight),new Vector2(10,18),null));
//		
	}
	
	private void pop_menu()
	{
		menu = new Table();
		
		Image Type_img = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32,32,32,32)));
		Type_img.setBounds(10, 10, 32, 32);
	
		TextButton changeName = new TextButton("Change name",StylesManager.skin);
		changeName.setBounds(52, 10, 6f*MeatifyMe.bWidth,MeatifyMe.bHeight);
		
		TextButton changeStyle = new TextButton("Style",StylesManager.skin);
		changeStyle.setBounds(62 + 6f*MeatifyMe.bWidth, 10, 4f*MeatifyMe.bWidth,MeatifyMe.bHeight);
		
		CheckBox checkBorder = new CheckBox("Border",StylesManager.skin);
		checkBorder.setPosition(72 + 6f*MeatifyMe.bWidth, 10);
		
		menu.addActor(Type_img);
		menu.addActor(changeName);
		menu.addActor(changeStyle);
		menu.addActor(checkBorder);
		
		menu.getColor().a = 0;
		menu.setVisible(false);
		stage.addActor(menu);
	}
	public void newLevel()
	{
		curLevel = new Level(maingame,1,1,false,"not saved");
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0.8f, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		mpos.x = Gdx.input.getX();
		mpos.y = MeatifyMe.h - Gdx.input.getY();
		
		//check new input
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		input();
	}

	private void input()
	{
		if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
			maingame.gotoMainMenu();
		}
		
		if(Gdx.input.justTouched())
		{
			menu.setVisible(true);
			menu.addAction(Actions.fadeIn(0.5f));
			int a = (int) (mpos.x / MeatifyMe.bWidth);
			int b = (int) (mpos.y / MeatifyMe.bHeight);
			
			if (!menu.isVisible()) {
				//add/remove block from level
				if (curLevel.getBlockType(a, b) != curType)
					curLevel.addBlock(a, b, curType);
				else if (curLevel.getBlockType(a, b) == curType);
					//curLevel.removeBlock(a, b);
			}
		}
	}
	
	public void savelevel()
	{
		maingame.loader.save(curLevel);
	}

}