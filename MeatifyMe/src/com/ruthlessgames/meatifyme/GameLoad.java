package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;
import com.ruthlessgames.api.LoadingScreen;

public class GameLoad extends UI implements LoadingScreen{

	AssetManager asm;
	MeatifyMe maingame;
	
	
	boolean loaded = false;
	
	public GameLoad(MeatifyMe main,Texture load){
		super(main.batch,main.font,false);
		asm = MeatifyMe.asm;
		maingame = main;
		
		table.setBackground(new TextureRegionDrawable(new TextureRegion(load)));
		this.pop_UI();
		this.setLoad();
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(asm.update() && !loaded)
		{
			this.didLoad();
			loaded = true;
		}
		
		float progress = asm.getProgress();
		maingame.actionResolver.updateProgressLoading(progress);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	private void didLoad(){
		this.createGame();
	}
	
	@Override
	public void createGame() {
		// TODO Auto-generated method stub
		
		maingame.font = StylesManager.btnBlue.font;
		Textures.load();
		Sounds.load();
		maingame.loadLevels();
		maingame.loadCustomLevels();
		maingame.campanha = new Campaign(maingame);
		maingame.inputListenner = new LevelInputListenner(maingame);
		maingame.inputGesture = new GestureDetector(maingame.inputListenner);
		maingame.actionResolver.setNiveisPopupMenu();
		maingame.waitscreen = new WaitScreen(maingame);
	}

	@Override
	public void setAssetManager(AssetManager asm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pop_UI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoad() {
		// TODO Auto-generated method stub
		//load sounds
		asm.load("MainTheme.ogg", Music.class);
		asm.load("levels/sounds/sl1.ogg", Music.class);
		asm.load("levels/sounds/sl2.ogg", Music.class);
		asm.load("levels/sounds/sl3.ogg", Music.class);
		asm.load("levels/sounds/sl4.ogg", Music.class);
		
		//load textures
		asm.load("levels/blocos.png",Texture.class);
		asm.load("rlogo.png",Texture.class);
		asm.load("mlogo.png",Texture.class);
		asm.load("mainmenu/bg.png",Texture.class);
		
		asm.load("levels/bgs/1.png",Texture.class);
		asm.load("levels/bgs/2.png",Texture.class);
		asm.load("levels/bgs/3.png",Texture.class);
		asm.load("levels/bgs/4.png",Texture.class);
		
		//player stage 4
		asm.load("player/stage4/main.png",Texture.class);
		asm.load("player/stage4/anims/animacao1-1.png",Texture.class);
		asm.load("player/stage4/anims/animacao2-1.png",Texture.class);
		asm.load("player/stage4/anims/animacao2-2.png",Texture.class);
		asm.load("player/stage4/anims/animacao3-1.png",Texture.class);
		asm.load("player/stage4/anims/animacao3-2.png",Texture.class);
		asm.load("player/stage4/anims/animacao3-3.png",Texture.class);
		asm.load("player/stage4/anims/animacao3-4.png",Texture.class);
		asm.load("player/stage4/anims/animacao4-1.png",Texture.class);
		asm.load("player/stage4/anims/animacao4-2.png",Texture.class);
		asm.load("player/stage4/anims/animacao6-1.png",Texture.class);
		asm.load("player/stage4/anims/animacao6-2.png",Texture.class);
		asm.load("player/stage4/anims/animacao6-3.png",Texture.class);
		
		asm.load("player/stage4/sfx/Andar.ogg", Music.class);
		asm.load("player/stage4/sfx/Descer.ogg", Music.class);
		asm.load("player/stage4/sfx/Level Up.ogg", Music.class);
		asm.load("player/stage4/sfx/Morrer.ogg", Music.class);
		asm.load("player/stage4/sfx/Subir 1.ogg", Music.class);
		asm.load("player/stage4/sfx/Subir 2.ogg", Music.class);
		
		//player stage 3
		asm.load("player/stage3/main.png",Texture.class);
		asm.load("player/stage3/anims/animacao1-1.png",Texture.class);
		asm.load("player/stage3/anims/animacao2-1.png",Texture.class);
		asm.load("player/stage3/anims/animacao2-2.png",Texture.class);
		asm.load("player/stage3/anims/animacao3-1.png",Texture.class);
		asm.load("player/stage3/anims/animacao3-2.png",Texture.class);
		asm.load("player/stage3/anims/animacao3-3.png",Texture.class);
		asm.load("player/stage3/anims/animacao3-4.png",Texture.class);
		asm.load("player/stage3/anims/animacao4-1.png",Texture.class);
		asm.load("player/stage3/anims/animacao4-2.png",Texture.class);
		asm.load("player/stage3/anims/animacao6-1.png",Texture.class);
		asm.load("player/stage3/anims/animacao6-2.png",Texture.class);
		asm.load("player/stage3/anims/animacao6-3.png",Texture.class);
		
		asm.load("player/stage3/sfx/andar.ogg", Music.class);
		asm.load("player/stage3/sfx/Descer.ogg", Music.class);
		asm.load("player/stage3/sfx/Level Up.ogg", Music.class);
		asm.load("player/stage3/sfx/Morrer.ogg", Music.class);
		asm.load("player/stage3/sfx/Subir.ogg", Music.class);
		asm.load("player/stage3/sfx/Saltar.ogg", Music.class);
		
		//player stage 2
		asm.load("player/stage2/main.png",Texture.class);
		asm.load("player/stage2/anims/animacao1-1.png",Texture.class);
		asm.load("player/stage2/anims/animacao2-0.png",Texture.class);
		asm.load("player/stage2/anims/animacao2-1.png",Texture.class);
		asm.load("player/stage2/anims/animacao2-2.png",Texture.class);
		asm.load("player/stage2/anims/animacao4-1.png",Texture.class);
		asm.load("player/stage2/anims/animacao4-2.png",Texture.class);
		
		asm.load("player/stage2/sfx/andar.ogg", Music.class);
		asm.load("player/stage2/sfx/Descer.ogg", Music.class);
		asm.load("player/stage2/sfx/Level Up.ogg", Music.class);
		asm.load("player/stage2/sfx/Morrer.ogg", Music.class);
		asm.load("player/stage2/sfx/Subir.ogg", Music.class);
		
		//player stage 1
		asm.load("player/stage1/main.png",Texture.class);
		
		asm.load("player/stage1/sfx/Andarbeep.ogg", Music.class);
		asm.load("player/stage1/sfx/Level Up.ogg", Music.class);
		asm.load("player/stage1/sfx/Morrer.ogg", Music.class);
		
		
		//campaign stage 1
		//level 1
		asm.load("levels/campaign/stage_1/level1/1.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/2.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/3.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/4.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/5.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/6.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/7.png",Texture.class);
		asm.load("levels/campaign/stage_1/level1/8.png",Texture.class);
		
		//level2
		asm.load("levels/campaign/stage_1/level2/1.png",Texture.class);
		
		//level3
		asm.load("levels/campaign/stage_1/level3/1.png",Texture.class);
		
		//level4
		asm.load("levels/campaign/stage_1/level4/1.png",Texture.class);
		asm.load("levels/campaign/stage_1/level4/2.png",Texture.class);
		asm.load("levels/campaign/stage_1/level4/3.png",Texture.class);
		asm.load("levels/campaign/stage_1/level4/4.png",Texture.class);
		
		//evolution
		asm.load("levels/campaign/stage_1/evolution/1.png",Texture.class);
		asm.load("levels/campaign/stage_1/evolution/2.png",Texture.class);
		asm.load("levels/campaign/stage_1/evolution/3.png",Texture.class);
		asm.load("levels/campaign/stage_1/evolution/4.png",Texture.class);
		asm.load("levels/campaign/stage_1/evolution/5.png",Texture.class);
		
		//campaign stage2
		//level6
		asm.load("levels/campaign/stage_2/level6/1.png",Texture.class);
		
		//level7
		asm.load("levels/campaign/stage_2/level7/1.png",Texture.class);
		
		//level8
		asm.load("levels/campaign/stage_2/level8/1.png",Texture.class);
		asm.load("levels/campaign/stage_2/level8/2.png",Texture.class);
		
		//evolution
		asm.load("levels/campaign/stage_2/evolution/1.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/2.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/3.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/4.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/5.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/6.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/7.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/8.png",Texture.class);
		asm.load("levels/campaign/stage_2/evolution/9.png",Texture.class);
		
		//campaign stage3
		//level9
		asm.load("levels/campaign/stage_3/level9/1.png",Texture.class);
		
		//level10
		asm.load("levels/campaign/stage_3/level10/1.png",Texture.class);
		asm.load("levels/campaign/stage_3/level10/2.png",Texture.class);
		asm.load("levels/campaign/stage_3/level10/3.png",Texture.class);
		
		//level11
		asm.load("levels/campaign/stage_3/level11/1.png",Texture.class);
		
		//evolution
		asm.load("levels/campaign/stage_3/evolution/1.png",Texture.class);
		asm.load("levels/campaign/stage_3/evolution/2.png",Texture.class);
		asm.load("levels/campaign/stage_3/evolution/3.png",Texture.class);
		
		//campaign stage4
		//level12
		asm.load("levels/campaign/stage_4/level12/1.png",Texture.class);
		
		//level13
		asm.load("levels/campaign/stage_4/level13/1.png",Texture.class);
		
		//level14
		asm.load("levels/campaign/stage_4/level14/1.png",Texture.class);
		
		//level15
		asm.load("levels/campaign/stage_4/level15/1.png",Texture.class);
		
		//level16
		asm.load("levels/campaign/stage_4/level16/1.png",Texture.class);
	}
	
	@Override
	public void show(){
		super.show();
		maingame.actionResolver.showLoading();
	}
}
