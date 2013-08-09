package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class LevelInputListenner implements InputProcessor,GestureListener{

	MeatifyMe maingame;
	
	public LevelInputListenner(MeatifyMe main)
	{
		maingame = main;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Keys.BACK) maingame.gotoMainMenu();
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
			// TODO Auto-generated method stub
			int x = (int) (screenX / MeatifyMe.bWidth);
			int y = (int) ((MeatifyMe.h - screenY) / MeatifyMe.bHeight);
			maingame.curLevel.addBlock(x, y, maingame.curLevel.block_type);
	
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
			//ascendente
			if(velocityY > 0)
			{
				maingame.curLevel.deincBlockType();
				if(MeatifyMe.debug) Gdx.app.log("INPUT", "block type --");
				Gdx.app.log("INPUT", "" + maingame.curLevel.block_type);
				
			}
			else{	//descendente
				
				maingame.curLevel.incBlockType();
				if(MeatifyMe.debug) Gdx.app.log("INPUT", "block type ++");
				Gdx.app.log("INPUT", "" + maingame.curLevel.block_type);
				
			}
			
			

		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
