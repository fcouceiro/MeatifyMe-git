package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.audio.Music;

public class Sounds {
	
	// styled sounds
	static Music maintheme;
	static Music sl1,sl2,sl3,sl4;
	
	//player stage 1
	static Music pl1_andar;
	static Music pl1_win;
	static Music pl1_lose;
	
	//player stage 2
	static Music pl2_andar;
	static Music pl2_win;
	static Music pl2_lose;
	static Music pl2_subir;
	static Music pl2_descer;
	
	//player stage 3
	static Music pl3_andar;
	static Music pl3_win;
	static Music pl3_lose;
	static Music pl3_subir;
	static Music pl3_descer;
	static Music peido;
	
	
	//player stage 4
	static Music pl4_andar;
	static Music pl4_win;
	static Music pl4_lose;
	static Music pl4_subir1;
	static Music pl4_subir2;
	static Music pl4_descer;
	
	static void load()
	{
		// counter attack
		maintheme = getSound("MainTheme.ogg");
		sl1 = getSound("levels/sounds/sl1.ogg");
		sl2 = getSound("levels/sounds/sl2.ogg");
		sl3 = getSound("levels/sounds/sl3.ogg");
		sl4 = getSound("levels/sounds/sl4.ogg");
		
		pl1_andar = getSound("player/stage1/sfx/Andarbeep.ogg");
		pl1_win = getSound("player/stage1/sfx/Level Up.ogg");
		pl1_lose = getSound("player/stage1/sfx/Morrer.ogg");
		
		pl2_andar = getSound("player/stage2/sfx/andar.ogg");
		pl2_win = getSound("player/stage2/sfx/Level Up.ogg");
		pl2_lose = getSound("player/stage2/sfx/Morrer.ogg");
		pl2_subir = getSound("player/stage2/sfx/Subir.ogg");
		pl2_descer = getSound("player/stage2/sfx/Descer.ogg");
		
		pl3_andar = getSound("player/stage3/sfx/andar.ogg");
		pl3_win = getSound("player/stage3/sfx/Level Up.ogg");
		pl3_lose = getSound("player/stage3/sfx/Morrer.ogg");
		pl3_subir = getSound("player/stage3/sfx/Subir.ogg");
		pl3_descer = getSound("player/stage3/sfx/Descer.ogg");
		peido = getSound("player/stage3/sfx/Saltar.ogg");
		
		pl4_andar = getSound("player/stage4/sfx/Andar.ogg");
		pl4_win = getSound("player/stage4/sfx/Level Up.ogg");
		pl4_lose = getSound("player/stage4/sfx/Morrer.ogg");
		pl4_subir1 = getSound("player/stage4/sfx/Subir 1.ogg");
		pl4_subir2 = getSound("player/stage4/sfx/Subir 2.ogg");
		pl4_descer = getSound("player/stage4/sfx/Descer.ogg");
	}
	
	static Music getSound(String key)
	{
		Music temp = MeatifyMe.asm.get(key, Music.class);
		return temp;
	}
	
	static void stop(){
		maintheme.stop();
		sl1.stop();
		sl2.stop();
		sl3.stop();
		sl4.stop();
		
		pl1_andar.stop();
		
		pl2_andar.stop();
		pl2_subir.stop();
		pl2_descer.stop();
		
		pl3_andar.stop();
		pl3_subir.stop();
		pl3_descer.stop();
		peido.stop();
		
		pl4_andar.stop();
		pl4_subir1.stop();
		pl4_subir2.stop();
		pl4_descer.stop();
	}
}
