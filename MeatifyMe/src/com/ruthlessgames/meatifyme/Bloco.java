package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bloco {
	Image image;
	int type;
	
	public Bloco(int type, Image i)
	{
		this.type = type;
		image = i;
	}
}
