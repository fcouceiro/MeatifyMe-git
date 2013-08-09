package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Gordo4 extends Image implements Player{
	
	int blocos[][];
	
	int xi,yi,xf,yf;
	int moves = -1;
    boolean lose = false;
    
	public Gordo4(){
		this.setSize(MeatifyMe.bWidth*4 , MeatifyMe.bHeight * 4);
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
	}
	
	
	private boolean isWalkable(int a)
	{
		return(a != 0 && a != 6 && a != 8 && a != 11 && a != 12);
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
		//must be pooled (study)
		final SequenceAction andar  = new SequenceAction();
		andar.addAction(Actions.delay(1));
		andar.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_andar)));
				
			}}));
		andar.addAction(Actions.delay(0.5f));
		andar.addAction(Actions.moveBy(MeatifyMe.bWidth, 0));
		andar.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
				Sounds.pl4_andar.play();
				
			}}));
		
		this.addAction(andar);
		Gdx.app.log("PLAYER AI", "ANDAR");
	}
	
	private void subir1(){
		SequenceAction subir1 = new SequenceAction();
		subir1.addAction(Actions.delay(1));
		subir1.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir1_1)));
				Sounds.pl4_subir1.play();
			}}));
		subir1.addAction(Actions.delay(0.5f));
		subir1.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir1_2)));
			}}));
		subir1.addAction(Actions.delay(0.5f));
		subir1.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth, MeatifyMe.bHeight),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
			}})));
		
		this.addAction(subir1);
		Gdx.app.log("PLAYER AI", "SUBIR1");
	}

	private void subir2(){
		SequenceAction subir2 = new SequenceAction();
		subir2.addAction(Actions.delay(1));
		subir2.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_1)));
				Sounds.pl4_subir1.play();
			}}));
		subir2.addAction(Actions.delay(0.5f));
		subir2.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_2)));
			}}));
		subir2.addAction(Actions.delay(0.5f));
		subir2.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_3)));
				
			}}));
		subir2.addAction(Actions.delay(0.5f));
		subir2.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_4)));
				Sounds.pl4_subir2.play();
			}}));
		subir2.addAction(Actions.delay(0.5f));
		subir2.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth, 2*MeatifyMe.bHeight),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
			}})));
		
		this.addAction(subir2);
		Gdx.app.log("PLAYER AI", "SUBIR2");
	}
	
	private void descer1(){
		SequenceAction descer = new SequenceAction();
		descer.addAction(Actions.delay(1));
		descer.addAction(Actions.parallel(Actions.moveBy(-MeatifyMe.bWidth, -MeatifyMe.bHeight),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_1)));
			}})));
		descer.addAction(Actions.delay(0.5f));
		descer.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_2)));
				Sounds.pl4_descer.play();
			}}));
		descer.addAction(Actions.delay(0.5f));
		descer.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,0),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
				
			}})));
		
		this.addAction(descer);
		Gdx.app.log("PLAYER AI", "DESCER1");
	}
	
	private void descer2(){
		SequenceAction descer2 = new SequenceAction();
		descer2.addAction(Actions.delay(1));
		descer2.addAction(Actions.parallel(Actions.moveBy(-MeatifyMe.bWidth, -MeatifyMe.bHeight),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_1)));
			}})));
		descer2.addAction(Actions.delay(0.5f));
		descer2.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_2)));
				Sounds.pl4_descer.play();
			}}));
		descer2.addAction(Actions.delay(0.5f));
		descer2.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,-MeatifyMe.bHeight),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
				
			}})));
		
		this.addAction(descer2);
		Gdx.app.log("PLAYER AI", "DESCER2");
	}

	private void saltar(){
		SequenceAction saltar = new SequenceAction();
		saltar.addAction(Actions.delay(1));
		saltar.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar1)));
			}}));
		saltar.addAction(Actions.delay(0.5f));
		saltar.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar2)));
				Sounds.peido.play();
			}}));
		saltar.addAction(Actions.delay(0.5f));
		saltar.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar3)));
			}}));
		saltar.addAction(Actions.delay(0.5f));
		saltar.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,0),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
			}})));
		
		this.addAction(saltar);
		Gdx.app.log("PLAYER AI", "SALTAR");
	}

	@Override
	public void setOnTable(int[][] blocos, Table table) {
		// TODO Auto-generated method stub
		this.blocos = blocos;
		
		setPosition(this.xi * MeatifyMe.bWidth, this.yi * MeatifyMe.bHeight);
		table.addActor(this);
	}


	@Override
	public int getActionsNumber() {
		// TODO Auto-generated method stub
		return this.getActions().size;
	}


	@Override
	public void setInitialPos(int xi, int yi) {
		// TODO Auto-generated method stub
		this.xi = xi;
		this.yi = yi;
	}


	@Override
	public void win() {
		// TODO Auto-generated method stub
		Sounds.pl4_win.play();
		Gdx.app.log("PLAYER AI", "WIN");
	}


	@Override
	public void lose() {
		// TODO Auto-generated method stub
		Sounds.pl4_lose.play();
		Gdx.app.log("PLAYER AI", "LOST");
	}


	@Override
	public void updateMoves() {
		// TODO Auto-generated method stub
		if(moves == -1) return;
		
		this.moves--;
		
		if(moves == 0) this.lose();
	}


	@Override
	public void setEndPointPos(int xf, int yf) {
		// TODO Auto-generated method stub
		this.xf = xf;
		this.yf = yf;
	}
}

