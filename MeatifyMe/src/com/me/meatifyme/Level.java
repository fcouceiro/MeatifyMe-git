package com.me.meatifyme;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	Image block_selecionado;
	Gordo player;
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
		
		//adicionar bloco que msotra o bloco selecionado (2,2)
		block_selecionado = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(block_type),32*(4-style),32,32)));
		block_selecionado.setPosition(2 * MeatifyMe.bWidth, 2 * MeatifyMe.bHeight);
		block_selecionado.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
		stage.addActor(block_selecionado);
		
		//gera player
		player = new Gordo(style);
		

		
	}
	
	public void incBlockType(){
		int b = block_type;
		b++;
		
		if(b == 3) b = 5;
		else if(b == 13) b = 12;
		
		block_type = b;
		
		
		block_selecionado.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(block_type),32*(4-style),32,32)));
		
	
	}
	
	public void deincBlockType(){
		int b = block_type;
		b--;
		
		if(b == 4) b = 2;
		else if(b == 0) b = 1;
		
		block_type = b;
		
		block_selecionado.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(block_type),32*(4-style),32,32)));
	}
	
	public void addBlock(final int xi,final int yi,int type)
	{
		if(tipos[xi][yi] != 0 || xi == 0 || yi == 0 || xi == 19 || yi == 13) return;
		
		final Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(type),32*(4-style),32,32)));
		newblock.setPosition(xi * MeatifyMe.bWidth, yi * MeatifyMe.bHeight);
		newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
		
		newblock.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
               	
                return true;
        }
        
        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        	if(x < newblock.getWidth() && x >0 && y<newblock.getHeight() && y > 0){
        		blocos.remove(newblock);
        		newblock.clearActions();
				table.removeActor(newblock);
				tipos[xi][yi] = 0;
        	}
      
        }
		});
		
		blocos.add(newblock);
		table.addActor(newblock);
		
		tipos[xi][yi] = type;
		
		if(type == 4) this.player_initialposition = new Vector2(xi,yi);
		if(MeatifyMe.debug) Gdx.app.log("Block", "added");
	}
	
	public int getBlockType(int x,int y)
	{
		float posx = x*MeatifyMe.bWidth;
		float posy = y*MeatifyMe.bHeight;
		
		for(Image block:blocos)
		{
			if(block.getX() == posx  && block.getY() == posy) return(tipos[x][y]);
		}
		
		if(MeatifyMe.debug) Gdx.app.log("type", "not found");
		return 0;
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
		
		if(!MeatifyMe.sound) return;
		
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
		
		//setup player
		player.setOnTable(tipos);
		player.setPosition(this.player_initialposition.x * MeatifyMe.bWidth, this.player_initialposition.y * MeatifyMe.bHeight);
		table.addActor(player);
		
		table.addAction(Actions.forever(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(player.getActions().size == 0){
					if(player.update() == 1){
						
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
