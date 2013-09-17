package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.scenes.scene2d.ui.Table;


public interface Player{
	
	public void setOnTable(int blocos[][],Table table);
	public int update();
	public int getActionsNumber();
	public void setInitialPos(int xi,int yi);
	public void setEndPointPos(int xf,int yf);
	public void win();
	public void lose();
	public void updateMoves();
	public int getMoves();
}
