package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Player extends Image{
	static enum EvolState {GORDO_1,GORDO_2,GORDO_3,GORDO_4};
	Animations anims;
	
	int blocos[][];
	
	int xi,yi,xf,yf;
	int moves = -1;
    boolean lose = false;
    
    EvolState state;
    
	public Player(){
		this.setSize(MeatifyMe.bWidth*4 , MeatifyMe.bHeight * 4);
		anims = new Animations(this);
	}
	
	public void setState(EvolState estado){
		this.state = estado;
		
		switch(state){
			case GORDO_1:
			this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl1_main)));
			break;
			case GORDO_2:
				this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_main)));
				break;
			case GORDO_3:
				this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_main)));
				break;
			case GORDO_4:
				this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
				break;
		}
	}
	
	private boolean isWalkable(int a)
	{
		return(a != 0 && a != 11 && a != 12);
	}
	
	public int update(){ //inteligencia artificial
		//busca posicao na matriz
		if(lose) return -2;
		
		int MyX,MyY;
		MyX = (int) (this.getX() / MeatifyMe.bWidth);
		MyY = (int) ((this.getY() + 5)/ MeatifyMe.bHeight);
		
		
		//verifica vitoria
		if(MyX == xf && (MyY == yf || MyY +1 == yf))
		{
			return 1;
		}
		
		//caso base: ANDAR
		if(isWalkable(blocos[MyX +1][MyY -1]))
		{
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(blocos[MyX+1][MyY] == 0  && blocos[MyX+1][MyY + 1] == 0)
			{
				this.andar();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed ANDAR");
		}
		
		if(state == EvolState.GORDO_1) return -1;
		//subir 1
		if(isWalkable(blocos[MyX+1][MyY])){
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(MyY+2 < 13 && blocos[MyX][MyY+2] == 0 && blocos[MyX+1][MyY+1] == 0 && blocos[MyX+1][MyY+2] == 0)
			{
				this.subir1();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed SUBIR1");
		}
		
		//descer1
		if(MyY -2 >= 0 && isWalkable(blocos[MyX+1][MyY-2])){
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(blocos[MyX+1][MyY-1] == 0)
			{
				this.descer1();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed DESCER1");
		}
		
		if(state == EvolState.GORDO_2) return -1;
		
		//saltar
		if(MyY -1 >= 0 && MyX + 2 < 20 && isWalkable(blocos[MyX+2][MyY-1]))
		{
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(blocos[MyX+1][MyY-1] == 0 && blocos[MyX+2][MyY] == 0 && blocos[MyX+2][MyY+1] == 0 && blocos[MyX+1][MyY] == 0 && blocos[MyX+1][MyY+1] == 0 && blocos[MyX+1][MyY+2] == 0 && blocos[MyX+1][MyY +3] == 0){
				this.saltar();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed SALTAR");
		}
		
		if(state == EvolState.GORDO_3) return -1;
		
		//subir2
		if(isWalkable(blocos[MyX+1][MyY+1])){
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(MyY+3 < 13 && blocos[MyX][MyY+2] == 0 && blocos[MyX+1][MyY+2] == 0 && blocos[MyX+1][MyY+3] == 0 && blocos[MyX][MyY+3] == 0)
			{
				this.subir2();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed SUBIR2");
		}
		
		
		//descer 2
		if(MyY -3 >= 0 && isWalkable(blocos[MyX+1][MyY-3])){
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(blocos[MyX+1][MyY-2] == 0)
			{
				this.descer2();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed DESCER2");
		}
		
		return -1;
		
	}
	
	private void andar()
	{
		this.addAction(anims.andar());
		Gdx.app.log("PLAYER AI", "ANDAR");
	}
	
	private void subir1(){
		this.addAction(anims.subir1());
		Gdx.app.log("PLAYER AI", "SUBIR1");
	}

	private void subir2(){
		this.addAction(anims.subir2());
		Gdx.app.log("PLAYER AI", "SUBIR2");
	}
	
	private void descer1(){
		this.addAction(anims.descer1());
		Gdx.app.log("PLAYER AI", "DESCER1");
	}
	
	private void descer2(){
		this.addAction(anims.descer2());
		Gdx.app.log("PLAYER AI", "DESCER2");
	}

	private void saltar(){
		this.addAction(anims.saltar());
		Gdx.app.log("PLAYER AI", "SALTAR");
	}

	public int getActionsNumber() {
		// TODO Auto-generated method stub
		return this.getActions().size;
	}


	public void setInitialPos(int xi, int yi) {
		// TODO Auto-generated method stub
		this.xi = xi;
		this.yi = yi;
	}


	public void updateMoves() {
		// TODO Auto-generated method stub
		if(moves == -1) return;
		
		this.moves--;
	}


	public void setEndPointPos(int xf, int yf) {
		// TODO Auto-generated method stub
		this.xf = xf;
		this.yf = yf;
	}


	public int getMoves() {
		// TODO Auto-generated method stub
		return moves;
	}
	
	public void playerWon() {
		// TODO Auto-generated method stub
		Sounds.pl1_win.play();
	}

	public void playerLost() {
		// TODO Auto-generated method stub
		Sounds.pl1_lose.play();
	}
}

