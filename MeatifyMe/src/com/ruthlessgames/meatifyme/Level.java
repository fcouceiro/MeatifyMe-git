 package com.ruthlessgames.meatifyme;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.UI;

public class Level extends UI implements Cloneable{
	
	int type;
	int style;
	int moves,time;
	Vector2 pl_ini_pos;
	int max_x,max_y;
	CharSequence name;
	boolean border;
	
	private ArrayList<Image> blocos = new ArrayList<Image>();
	int tipos[][];
	
	//score
	boolean completed;
	
	MeatifyMe maingame;
	
	//ingame stuff
	int block_type =  1; //tijolo
	int collisionbox_folga = 10; //(pixeis)
	Player player;
	Vector2 player_initialposition;
	
	public Level(MeatifyMe main){
		super(main.batch,main.font,false);
	}
	
	public Level(MeatifyMe main,int style, int type,boolean border, CharSequence name)
	{
		super(main.batch,main.font,false);
		maingame=main;
		this.type = type;
		this.border = border;
		max_x = 20;
		max_y = 14;
		this.name = name;
		
		tipos = new int[max_x][max_y];
		for(int i=0;i<20;i++)
			for(int j=0;i<14;i++)
				tipos[i][j] = 0;
		
		this.style = style;
		this.setBg(style);
		
		completed = false;
		
		//gera player
		switch(style)
		{
		case 1:
			player = new Gordo1();
			break;
		case 2:
			player = new Gordo2();
			break;
		case 3:
			player = new Gordo3();
			break;
		case 4:
			player = new Gordo4();
			break;
		}
		
		

		
	}
	
	public void addBlock(final int xi,final int yi,int type)
	{
		if(tipos[xi][yi] != 0 || xi == 0 || yi == 0 || xi == 19 || yi == 13) return;
		
		final Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(type),32*(4-style),32,32)));
		newblock.setPosition(xi * MeatifyMe.bWidth, yi * MeatifyMe.bHeight);
		newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
		
		newblock.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
               	newblock.getColor().a = 0.6f;
                return true;
        }
        
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        	if(x < newblock.getWidth() && x >0 && y<newblock.getHeight() && y > 0){
        		blocos.remove(newblock);
        		newblock.clearActions();
				newblock.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.removeActor()));
				tipos[xi][yi] = 0;
        	}
        	else newblock.getColor().a = 1;
      
        }
		});
		
		if(type != 4 && type != 3)
		blocos.add(newblock);
		
		table.addActor(newblock);
		
		newblock.getColor().a = 0.0f;
		newblock.addAction(Actions.fadeIn(0.5f));
		
		if(type != 4 && type != 3)
		tipos[xi][yi] = type;
		
		if(type == 4) this.player.setInitialPos(xi,yi);
		else if(type == 3) this.player.setEndPointPos(xi, yi);
		if(MeatifyMe.debug) Gdx.app.log("Block", "added");
	}
	
	public int getBlockType(int x,int y)
	{
		return(tipos[x][y]);
	}
	
	
	public void setBorder(int tipo)
	{
			//create border
			//upper line
			for (int p = 0; p < max_x; p++) {
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition(p * MeatifyMe.bWidth, (max_y-1) * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				table.addActor(newblock);
			}
			//bottom line
			for (int p = 0; p < max_x; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition(p * MeatifyMe.bWidth, 0 * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				table.addActor(newblock);
			}
			//left line
			for (int p = 0; p < max_y; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition(0 * MeatifyMe.bWidth, p * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				table.addActor(newblock);
			}
			//upper line
			for (int p = 0; p < max_y; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition((max_x -1) * MeatifyMe.bWidth, p * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				table.addActor(newblock);
			}
		
	}
	
	public void setBg(int bg)
	{
		switch(bg)
		{
		case 1:
			table.setBackground(new TextureRegionDrawable(Textures.bg1));
			break;
		case 2:
			table.setBackground(new TextureRegionDrawable(Textures.bg2));
			break;
		case 3:
			table.setBackground(new TextureRegionDrawable(Textures.bg3));
			break;
		case 4:
			table.setBackground(new TextureRegionDrawable(Textures.bg4));
			break;
		default:
			if(MeatifyMe.debug) Gdx.app.log("Error","level bg index");
			break;
		}
	}

	@Override
	public void show(){
		super.show();
		
		ui_input.addProcessor(maingame.inputListenner);
		ui_input.addProcessor(maingame.inputGesture);
		
		if(maingame.sound){
		
		switch(style)
		{
		case 1:
			Sounds.sl1.setLooping(true);
			Sounds.sl1.play();
			break;
		case 2:
			Sounds.sl2.setLooping(true);
			Sounds.sl2.play();
			break;
		case 3:
			Sounds.sl3.setLooping(true);
			Sounds.sl3.play();
			break;
		case 4:
			Sounds.sl4.setLooping(true);
			Sounds.sl4.play();
			break;
		}
		
		}
		//setup player
		player.setOnTable(tipos,table);
		
		
		table.addAction(Actions.forever(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(player.getActionsNumber() == 0){
					int returned_by_gordo = player.update();
					if(returned_by_gordo == 1){
						player.win();
						maingame.gotoMainMenu();
					}
					else if(returned_by_gordo == -2){
						ui_input.removeProcessor(maingame.inputGesture);
						ui_input.removeProcessor(maingame.inputListenner);
					}
				}
			}})));
	}
	
	 protected Level clone()
	{
		try {
			return (Level) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
