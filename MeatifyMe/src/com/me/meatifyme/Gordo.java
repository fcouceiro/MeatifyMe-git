package com.me.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class Gordo extends Image{
	
	int blocos[][];
	
    
	public Gordo(int evolucao){
		this.setSize(MeatifyMe.bWidth*4 , MeatifyMe.bHeight * 4);
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
	}
	
	public void setOnTable(int blocos[][])
	{
		this.blocos = blocos;
	}
	
	
	private boolean isWalkable(int a)
	{
		return(a != 0 && a != 6 && a != 8 && a != 11 && a != 12);
	}
	
	private boolean isSafe(int x,int y)
	{
		return ((x>=0 && x < 20) && (y>=0 && y<14));
	}
	
	public int update(){ //inteligencia artificial
		//busca posicao na matriz
		int MyX,MyY;
		MyX = (int) (this.getX() / MeatifyMe.bWidth);
		MyY = (int) ((this.getY() + 5)/ MeatifyMe.bHeight);
		
		
		//verifica vitoria
		if(blocos[MyX][MyY] == 3)
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
			if(MyY+3 < 13 && blocos[MyX][MyY+2] == 0 && blocos[MyX+1][MyY+2] == 0 && blocos[MyX+1][MyY+3] == 0)
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
		
		//saltar
		if(MyY -1 >= 0 && MyX + 2 < 20 && isWalkable(blocos[MyX+2][MyY-1]))
		{
			Gdx.app.log("PLAYER POS", MyX + ";" + MyY);
			if(blocos[MyX+1][MyY-1] == 0 && blocos[MyX+2][MyY] == 0 && blocos[MyX+2][MyY+1] == 0){
				this.saltar();
				return 0;
			}
			Gdx.app.log("PLAYER AI","Failed SALTAR");
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
				andar.reset();
				
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
}

