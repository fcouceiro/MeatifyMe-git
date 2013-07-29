package com.me.meatifyme;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.UI;

public class Level extends UI{
	
	int type;
	int style;
	int moves,time;
	Vector2 pl_ini_pos;
	int max_x,max_y;
	static Vector2 ratio;
	CharSequence name;
	boolean border;
	
	private ArrayList<ImageButton> blocos = new ArrayList<ImageButton>();
	int tipos[][];
	
	MeatifyMe maingame;
	
	public Level(MeatifyMe main,int style, int type,boolean border, CharSequence name)
	{
		super(main.batch,main.font,false);
		maingame=main;
		this.type = type;
		this.border = border;
		max_x = 20;
		max_y = 14;
		this.name = name;
		ratio = new Vector2(Gdx.graphics.getWidth() / max_x,Gdx.graphics.getHeight() / max_y);
		tipos = new int[max_x][max_y];
		
		this.style = style;
		this.setBg(style);
	}
	
	public void addBlock(int x,int y,int type)
	{
		ImageButton newblock = new ImageButton(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(type),32*(4-style),32,32)));
		newblock.setBounds(x*ratio.x, y*ratio.y, ratio.x, ratio.y);
		
		blocos.add(newblock);
		table.addActor(newblock);
		
		tipos[x][y] = type;
		
		if(MeatifyMe.debug) Gdx.app.log("Block", "added");
	}
	
	public int getBlockType(int x,int y)
	{
		int posx = x*32;
		int posy = y*32;
		
		for(ImageButton block:blocos)
		{
			if(block.getX() == posx && block.getY() == posy) return(tipos[x][y]);
		}
		
		if(MeatifyMe.debug) Gdx.app.log("type", "not found");
		return 0;
	}
	
	public void removeBlock(int x,int y)
	{
		x *= 32;
		y *= 32;
		
		for(ImageButton block:blocos)
		{
			if(block.getX() == x && block.getY() == y)
			{
				blocos.remove(block);
				block.clearActions();
				table.removeActor(block);
				if(MeatifyMe.debug) Gdx.app.log("Block", "removed");
			}
		}
	}
	
	public void setBorder(int tipo)
	{
			//create border
			//upper line
			for (int p = 0; p < max_x; p++) {
				addBlock(p, max_y -1, tipo);
			}
			//bottom line
			for (int p = 0; p < max_x; p++) {
				addBlock(p, 0, tipo);
			}
			//left line
			for (int p = 0; p < max_y; p++) {
				addBlock(0,p, tipo);
			}
			//upper line
			for (int p = 0; p < max_y; p++) {
				addBlock(max_x -1, p, tipo);
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

}
