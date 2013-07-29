package com.me.meatifyme;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.StylesManager;

public class Textures {

	static List<Texture> disposable = new ArrayList<Texture>();
	static Sprite blocos;
	static Sprite rLogo;
	static Sprite mLogo;
	
	static Sprite main_bg;
	static Sprite camp;
	
	static Sprite bg1;
	static Sprite bg2;
	static Sprite bg3;
	static Sprite bg4;
	static Sprite buttons_region;
	
	//--------styles-----------//
	static TextButtonStyle btnSlick;
	static TextButtonStyle btnMeatify;
	
	Textures()
	{
		this.load();
		this.loadStyles();
	}
	
	public void load()
	{
		blocos = loadSprite("levels/blocos");
		rLogo = loadSprite("rlogo");
		rLogo.setSize(MeatifyMe.w, MeatifyMe.h);
		mLogo = loadSprite("mlogo");
		mLogo.setSize(MeatifyMe.w, MeatifyMe.h);
		main_bg = loadSprite("mainmenu/bg");
		main_bg.setSize(MeatifyMe.w, MeatifyMe.h);
		camp = loadSprite("mainmenu/camp_b");
		
		bg1 = loadSprite("levels/bgs/1");
		bg1.setSize(MeatifyMe.w, MeatifyMe.h);
		bg2 = loadSprite("levels/bgs/2");
		bg2.setSize(MeatifyMe.w, MeatifyMe.h);
		bg3 = loadSprite("levels/bgs/3");
		bg3.setSize(MeatifyMe.w, MeatifyMe.h);
		bg4 = loadSprite("levels/bgs/4");
		bg4.setSize(MeatifyMe.w, MeatifyMe.h);
		buttons_region = loadSprite("buttons");
		
		loadStyles();
	}
	
	private void loadStyles()
	{
		TextureRegion upRegion = new TextureRegion(buttons_region,0,0,219,29);
		TextureRegion downRegion = new TextureRegion(buttons_region,0,3*29 +1,219,29);
		BitmapFont buttonFont = StylesManager.skin.getFont("default-font");

		btnSlick = new TextButtonStyle();
		btnSlick.up = new TextureRegionDrawable(upRegion);
		btnSlick.down = new TextureRegionDrawable(downRegion);
		btnSlick.font = buttonFont;
		
		btnMeatify = new TextButtonStyle();
		btnMeatify.up = new TextureRegionDrawable(upRegion);
		btnMeatify.down = new TextureRegionDrawable(downRegion);
		btnMeatify.font = buttonFont;
	}
	
	private Sprite loadSprite(String name)
	{
	
		Texture a = new Texture(Gdx.files.internal(name + ".png"));
		a.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		disposable.add(a);
		return new Sprite(a);
	
	}
	
	public void dispose()
	{
		for(Texture t:disposable)
		{
			t.dispose();
		}
	}
}
