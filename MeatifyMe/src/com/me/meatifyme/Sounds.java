package com.me.meatifyme;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;

public class Sounds {
	
	// styled sounds
	static Music maintheme;
	static Music sl1,sl2,sl3,sl4;
	static void load()
	{
		// counter attack
		maintheme = getSound("MainTheme.ogg");
		sl1 = getSound("levels/sounds/sl1.ogg");
		sl2 = getSound("levels/sounds/sl2.ogg");
		sl3 = getSound("levels/sounds/sl3.ogg");
		sl4 = getSound("levels/sounds/sl4.ogg");
	}
	
	static Music getSound(String key)
	{
		Music temp = MeatifyMe.asm.get(key, Music.class);
		return temp;
	}
}
