package com.me.meatifyme;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;



public class Player extends ImageButton{

	Vector2 Pos;
	Vector2 Size;
	int Img;
	int Evol;
	
	boolean moving;
	boolean animating;
	int timer,animtimer;
	int maxTimer,animmaxTimer;
	int animstate,frameindex;
	int framesAnim1,framesAnim2,framesAnim3,framesAnim4,framesAnim5,framesAnim6;
	Vector2 tempX;
	
	Player(Vector2 Pos, Vector2 size, int evol)
	{
		super(new TextureRegionDrawable(Textures.bg1));
		this.setPosition(Pos.x, Pos.y);
		this.setSize(size.x, size.y);
		
		switch(evol)
		{
		case 0:
			this.Img = 25;
			framesAnim1 = 2;
			framesAnim2 = 2;
			framesAnim3 = 3;
			framesAnim4 = 2;
			framesAnim5 = 3;
			framesAnim6 = 4;
			break;
		case 1:
			this.Img = 40;
			framesAnim1 = 2;
			framesAnim2 = 3;
			framesAnim3 = 4;
			framesAnim4 = 2;
			framesAnim5 = 2;
			framesAnim6 = 3;
			break;
		case 2:
			this.Img = 96;
			framesAnim1 = 2;
			framesAnim2 = 2;
			framesAnim3 = 4;
			framesAnim4 = 2;
			framesAnim5 = 2;
			framesAnim6 = 3;
			break;
		case 3:
			this.Img = 111;
			framesAnim1 = 2;
			framesAnim2 = 2;
			framesAnim3 = 4;
			framesAnim4 = 2;
			framesAnim5 = 2;
			framesAnim6 = 3;
			this.Img = 25;
			break;
		}
		
		this.moving = false;
		this.maxTimer = 60;
		this.timer = 0;
		this.Evol = evol;
		this.animstate = 1;
		this.animmaxTimer = 40;
		this.animating = false;
		frameindex = 0;
	}
	
	
	
}
