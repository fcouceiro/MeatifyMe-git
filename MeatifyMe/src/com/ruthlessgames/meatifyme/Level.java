 package com.ruthlessgames.meatifyme;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	int time;
	Vector2 pl_ini_pos;
	int max_x,max_y;
	CharSequence name;
	boolean border;
	
	private HashMap<String,Bloco> blocos = new HashMap<String,Bloco>();
	private int tipos[][];
	
	MeatifyMe maingame;
	
	//ingame stuff
	private Player player;
	private Vector2 player_initialposition;
	boolean campaign;
	private boolean play = false;
	
	public Level(MeatifyMe main){
		super(main.batch,main.font,false);
	}
	
	public Level(MeatifyMe main,final int style, int type,boolean border, CharSequence name,boolean campaign)
	{
		super(main.batch,main.font,false);
		maingame=main;
		this.type = type;
		this.border = border;
		max_x = 20;
		max_y = 14;
		this.name = name;
		this.campaign = campaign;
		
		tipos = new int[max_x][max_y];
		for(int i=0;i<20;i++)
			for(int j=0;i<14;i++)
				tipos[i][j] = 0;
		
		this.style = style;
		this.setBg(style);
		
		
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
		
		stage.addListener(new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	               	
	                return true;
	        }
	        
	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	
	        	int xf = (int) (x / MeatifyMe.bWidth);
	    		int yf = (int) (y / MeatifyMe.bHeight);
	    		
	    		if(xf == 0 || xf == 19 || yf == 0 || yf == 13){
	    			maingame.actionResolver.showShortToast("Can't build here!");
	    			return;
	    		}
	    		
	    		int type = maingame.actionResolver.getBlockType();
	    		
	    		if(type == 2){ //se stone tira dois moves
    				player.updateMoves();
	    		}
	    		
	    		if(type != 9)
	    		player.updateMoves();
	    		
	    		if(tipos[xf][yf] != 0){
	    			//bloco ja existe -> remover
	    			
	    			if(tipos[xf][yf] == 10){
	    				maingame.actionResolver.showShortToast("Rocks are permanent!");
		    			return;
	    			}
	    			
	    			tipos[xf][yf]  = 0;
	    			
	    			table.removeActor(blocos.get(getImageId(xf, yf)).image);
	    			blocos.remove(getImageId(xf, yf));
	    			
	    			
	    			return;
	    		}
	    		
	    		switch(type){
	    		case 5:
	    			//caso seja areia o bloco por baixo nao pode ser vazio
	    			if(tipos[xf][yf -1] == 0){
	    				maingame.actionResolver.showShortToast("Needs to be over other block!");
	    				return;
	    			}
	    			break;
	    		case 7:
	    			//caso seja rocket o bloco por baixo tem de ser vazio
	    			if(tipos[xf][yf -1] != 0){
	    				maingame.actionResolver.showShortToast("Needs to levitate!");
	    				return;
	    			}
	    			break;
	    		case 8:
	    			//forever alone
	    			if(tipos[xf][yf -1] != 0 || tipos[xf][yf +1] != 0 || tipos[xf-1][yf ] != 0 || tipos[xf+1][yf] != 0  || tipos[xf+1][yf+1] != 0  || tipos[xf+1][yf -1] != 0 || tipos[xf-1][yf-1] != 0 || tipos[xf-1][yf +1] != 0 ){
	    				maingame.actionResolver.showShortToast("One block away from others!");
	    				return;
	    			}
	    			break;
	    		}
	    		
	    		if(tipos[xf][yf -1] == 6) //building over glass
	    		{
	    			maingame.actionResolver.showShortToast("Can't build over glass!");
	    			return;
	    		}
	    		else{
	    			tipos[xf][yf] = type;
	    		}
	    		
	    		if(!MeatifyMe.debug) Gdx.app.log("NEW BLOCK", "At (" + getImageId(xf, yf) + ") type: " + type);
	    		
	    	
	    		//create image
	    		Image temp = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(type),32*(4-style),32,32)));
	    		temp.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
	    		temp.setPosition(xf * MeatifyMe.bWidth, yf * MeatifyMe.bHeight);
	    		temp.getColor().a = 0.0f;
	    		temp.addAction(Actions.fadeIn(0.8f));
	    		
	    		Bloco bloco_temp = new Bloco(type,temp);
	    		blocos.put(getImageId(xf, yf), bloco_temp);
	    		table.addActor(temp);
	    		
	        }
	        
		});

		
	}
	private String getImageId(int x, int y)
	{
		return x + ";" + y;
	}
	
	public void addBlock(final int xi,final int yi,int type)
	{
		if(xi == 0 || yi == 0 || xi == 19 || yi == 13){
			this.maingame.actionResolver.showShortToast("Can't build here!");
			return;
		}
		
		final Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(type),32*(4-style),32,32)));
		newblock.setPosition(xi * MeatifyMe.bWidth, yi * MeatifyMe.bHeight);
		newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);

		
		if(type != 4 && type != 3){
			Bloco bloco_temp = new Bloco(type,newblock);
    		blocos.put(getImageId(xi, yi), bloco_temp);
    		tipos[xi][yi] = type;
		}
		
		table.addActor(newblock);
		
		newblock.getColor().a = 0.0f;
		newblock.addAction(Actions.fadeIn(0.5f));
		
		
		if(type == 4) this.player.setInitialPos(xi,yi);
		else if(type == 3) this.player.setEndPointPos(xi, yi);
		if(MeatifyMe.debug) Gdx.app.log("Block", "added " + type);
		
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

		//ui_input.addProcessor(maingame.inputListenner);
		//ui_input.addProcessor(maingame.inputGesture);
		maingame.actionResolver.startChronometer();
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
				if(Gdx.input.isKeyPressed(Keys.BACK)){
					if(!campaign)
						maingame.gotoMainMenu();
						else{
							maingame.setScreen(maingame.campanha);
							maingame.campanha.start();
						}
						
						maingame.actionResolver.stopChronometer();
						maingame.actionResolver.showIngame(false);
				}
				
				if(player.getActionsNumber() == 0 && play){
					
					int returned_by_gordo = player.update();
					if(returned_by_gordo == 1){
						player.win();
						
						if(!campaign)
						maingame.gotoMainMenu();
						else{
							maingame.campanha.incLevel();
						}
						
						maingame.actionResolver.stopChronometer();
						maingame.actionResolver.resetPlayButton();
						maingame.actionResolver.showIngame(false);
					}
					else if(returned_by_gordo == -2){
						player.lose();
						
						if(!campaign)
							maingame.gotoMainMenu();
							else{
								maingame.setScreen(maingame.campanha);
								maingame.campanha.start();
							}
							
						maingame.actionResolver.stopChronometer();
							maingame.actionResolver.showIngame(false);
					}
				}
			}})));
		
		maingame.actionResolver.showIngame(true);
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
	 
	public void play(){
		this.play = true;
	}
	
	public void pause(){
		this.play = false;
	}
	 

}
