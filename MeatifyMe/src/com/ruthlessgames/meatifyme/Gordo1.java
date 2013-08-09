package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Gordo1 extends Image implements Player{
	int xi,yi,xf,yf;
	int blocos[][];
	int moves = -1;
	
	public Gordo1(){
		this.setSize(MeatifyMe.bWidth*4 , MeatifyMe.bHeight * 4);
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl1_main)));
	}
	
	@Override
	public void setOnTable(int[][] blocos, Table table) {
		// TODO Auto-generated method stub
		this.blocos = blocos;
		setPosition(this.xi * MeatifyMe.bWidth, this.yi * MeatifyMe.bHeight);
		table.addActor(this);
	}
	
	private boolean isWalkable(int a)
	{
		return(a != 0 && a != 6 && a != 8 && a != 11 && a != 12);
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		//busca posicao na matriz
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
				
				return -1;
	}
	
	private void andar()
	{
		//must be pooled (study)
		final SequenceAction andar  = new SequenceAction();
		andar.addAction(Actions.delay(1));
		andar.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth, 0),Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Sounds.pl1_andar.play();
			}})));

		this.addAction(andar);
		Gdx.app.log("PLAYER AI", "ANDAR");
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
		Sounds.pl1_win.play();
	}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		Sounds.pl1_lose.play();
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
