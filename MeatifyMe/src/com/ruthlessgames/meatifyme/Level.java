 package com.ruthlessgames.meatifyme;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
	
	GestureDetector gestureDetector;
	CameraController controller;
	MeatifyMe maingame;
	
	//ingame stuff
	private Player player;
	boolean campaign;
	private boolean play = false;
	
	public Level(MeatifyMe main){
		super(main.batch,main.font,false);
	}
	
	public Level(MeatifyMe main,int style, int type,boolean border, CharSequence name,boolean campaign)
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
		
		//setup camera listener
		controller = new CameraController();
		gestureDetector = new GestureDetector(10, 0.5f, 0.5f, 0.15f, controller);
	}
	
	public void setPlayer(Player gordo){
		this.player = gordo;
		switch(style){
		case 1:
			this.player.setState(Player.EvolState.GORDO_1);
			break;
		case 2:
			this.player.setState(Player.EvolState.GORDO_2);
			break;
		case 3:
			this.player.setState(Player.EvolState.GORDO_3);
			break;
		case 4:
			this.player.setState(Player.EvolState.GORDO_4);
			break;
		}
		
		player.blocos = tipos;
		
		table.addActor(player);
	}
	
	public void setPlayerInitialPos(Vector2 pos){
		pl_ini_pos = pos;
		player.setPosition(pl_ini_pos.x * MeatifyMe.bWidth, pl_ini_pos.y * MeatifyMe.bHeight);
	}
	private void insertBlockInLevel(){
		Vector2 inCoord = this.getLastInputCoordinatesCart();
		int xf = (int) inCoord.x;
		int yf = (int) inCoord.y;
		
		Gdx.app.log("pos", xf + ";" + yf);
		
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
		
		
		if(type == 4) this.setPlayerInitialPos(new Vector2(xi,yi));
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
				newblock.getColor().a = 0.6f;
				table.addActor(newblock);
			}
			//bottom line
			for (int p = 0; p < max_x; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition(p * MeatifyMe.bWidth, 0 * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				newblock.getColor().a = 0.6f;
				table.addActor(newblock);
			}
			//left line
			for (int p = 0; p < max_y; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition(0 * MeatifyMe.bWidth, p * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				newblock.getColor().a = 0.6f;
				table.addActor(newblock);
			}
			//upper line
			for (int p = 0; p < max_y; p++) {
				
				Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(Textures.blocos,32*(tipo),32*(4-style),32,32)));
				newblock.setPosition((max_x -1) * MeatifyMe.bWidth, p * MeatifyMe.bHeight);
				newblock.setSize(MeatifyMe.bWidth, MeatifyMe.bHeight);
				newblock.getColor().a = 0.6f;
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

		ui_input.addProcessor(this.gestureDetector);
		
		maingame.actionResolver.startChronometer();
		if(maingame.sound){
			Sounds.stop();
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
		
		maingame.actionResolver.showIngame(true);
	}
	
	@Override
	public void render(float arg0){
		super.render(arg0);
		
		((OrthographicCamera)stage.getCamera()).update();
		controller.update();
		
		if(Gdx.input.isKeyPressed(Keys.BACK)){
				maingame.gotoMainMenu();

				maingame.actionResolver.stopChronometer();
				maingame.actionResolver.showIngame(false);
		}
		
		if(player.getActionsNumber() == 0 && play){
			
			int returned_by_gordo = player.update();
			
			if(returned_by_gordo == 1){
				player.playerWon();
				
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
				player.playerLost();
				
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
	 
	private Vector2 getTileCoordinates(Vector2 pos){
		float x = (pos.x / MeatifyMe.bWidth);
		float y = (pos.y / MeatifyMe.bHeight);
		return new Vector2(x,y);
	}
	
	private Vector2 getLastInputCoordinatesCart(){
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		 ((OrthographicCamera)stage.getCamera()).unproject(worldCoordinates);
		return getTileCoordinates(new Vector2(worldCoordinates.x,worldCoordinates.y));
	}
	
	class CameraController implements GestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		OrthographicCamera camera = (OrthographicCamera)stage.getCamera();
		
		public boolean touchDown (float x, float y, int pointer, int button) {
			flinging = false;
			initialScale = camera.zoom;
			
			return false;
		}

		@Override
		public boolean tap (float x, float y, int count, int button) {
			Level.this.insertBlockInLevel();
			return false;
		}

		@Override
		public boolean longPress (float x, float y) {
			return false;
		}

		@Override
		public boolean fling (float velocityX, float velocityY, int button) {
			flinging = true;
			velX = camera.zoom * velocityX * 0.5f;
			velY = camera.zoom * velocityY * 0.5f;
			return false;
		}

		@Override
		public boolean pan (float x, float y, float deltaX, float deltaY) {
			// Gdx.app.log("GestureDetectorTest", "pan at " + x + ", " + y);
			camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
			return false;
		}

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			float ratio = originalDistance / currentDistance;
			camera.zoom = initialScale * ratio;
			if(camera.zoom > 2.5) camera.zoom = 2.5f;
			else if(camera.zoom < 0.4f) camera.zoom = 0.4f;
			//System.out.println(camera.zoom);
			return false;
		}

		@Override
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}

		public void update () {
			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
				if (Math.abs(velX) < 0.01f) velX = 0;
				if (Math.abs(velY) < 0.01f) velY = 0;
			}
		}
	}
}
