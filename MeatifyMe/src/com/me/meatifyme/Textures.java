package com.me.meatifyme;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.StylesManager;

public class Textures {

	static Sprite blocos;
	static Sprite rLogo;
	static Sprite mLogo;
	
	static Sprite main_bg;
	
	static Sprite bg1;
	static Sprite bg2;
	static Sprite bg3;
	static Sprite bg4;
	
	//player stage 4
	static Sprite pl4_main;
	static Sprite pl4_andar;
	static Sprite pl4_subir1_1;
	static Sprite pl4_subir1_2;
	static Sprite pl4_subir2_1;
	static Sprite pl4_subir2_2;
	static Sprite pl4_subir2_3;
	static Sprite pl4_subir2_4;
	static Sprite pl4_descer1_1;
	static Sprite pl4_descer1_2;
	static Sprite pl4_saltar1;
	static Sprite pl4_saltar2;
	static Sprite pl4_saltar3;
	
	static void load()
	{
		blocos = loadSprite("levels/blocos");
		rLogo = loadSprite("rlogo");
		rLogo.setSize(MeatifyMe.w, MeatifyMe.h);
		mLogo = loadSprite("mlogo");
		mLogo.setSize(MeatifyMe.w, MeatifyMe.h);
		main_bg = loadSprite("mainmenu/bg");
		main_bg.setSize(MeatifyMe.w, MeatifyMe.h);
		
		bg1 = loadSprite("levels/bgs/1");
		bg1.setSize(MeatifyMe.w, MeatifyMe.h);
		bg2 = loadSprite("levels/bgs/2");
		bg2.setSize(MeatifyMe.w, MeatifyMe.h);
		bg3 = loadSprite("levels/bgs/3");
		bg3.setSize(MeatifyMe.w, MeatifyMe.h);
		bg4 = loadSprite("levels/bgs/4");
		bg4.setSize(MeatifyMe.w, MeatifyMe.h);
		
		pl4_main = loadSprite("player/stage4/main");
		pl4_andar = loadSprite("player/stage4/anims/animacao1-1");
		pl4_subir1_1 = loadSprite("player/stage4/anims/animacao2-1");
		pl4_subir1_2 = loadSprite("player/stage4/anims/animacao2-2");
		pl4_subir2_1 = loadSprite("player/stage4/anims/animacao3-1");
		pl4_subir2_2 = loadSprite("player/stage4/anims/animacao3-2");
		pl4_subir2_3 = loadSprite("player/stage4/anims/animacao3-3");
		pl4_subir2_4 = loadSprite("player/stage4/anims/animacao3-4");
		pl4_descer1_1 = loadSprite("player/stage4/anims/animacao4-1");
		pl4_descer1_2 = loadSprite("player/stage4/anims/animacao4-2");
		pl4_saltar1 = loadSprite("player/stage4/anims/animacao6-1");
		pl4_saltar2 = loadSprite("player/stage4/anims/animacao6-2");
		pl4_saltar3 = loadSprite("player/stage4/anims/animacao6-3");
	}
	
	

	private static Sprite loadSprite(String name)
	{
		Texture a = MeatifyMe.asm.get(name + ".png",Texture.class);
		a.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return new Sprite(a);
	}
	
}
