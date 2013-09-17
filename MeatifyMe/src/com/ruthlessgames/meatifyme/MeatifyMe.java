package com.ruthlessgames.meatifyme;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.ruthlessgames.api.StylesManager;


public class MeatifyMe extends Game{
	
	public static OrthographicCamera camera;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	
	public static float w;
	public static float h;
	public static boolean debug = false;
	
	public boolean sound = true;
	//listas de niveis
	
	public String nomes_levels[];
	public String nomes_levels_custom[];
	
	public Helper loader;
	public StylesManager StylesManager;
	
	//Android bridge
	public ActionResolver actionResolver;
	public static boolean ready_for_game = false;
	public static int nextLevelIndex = 0;
	WaitScreen waitscreen;
	
	//AssetManager stuff
	GameLoad loading_screen;
	static AssetManager asm;
	
	
	//cena de niveis
	Campaign campanha;
	Level curLevel;
	static float bWidth,bHeight;
	public static String LEVELS_PATH = "/MeatifyMe/Levels/";
	
	public MeatifyMe(ActionResolver actionR)
	{
		actionResolver = actionR;
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(true);
		//initializations
		w = Gdx.graphics.getWidth();
		h  = Gdx.graphics.getHeight();
		bWidth = w / 20;
		bHeight = h / 14;
		camera = new OrthographicCamera(w, h);
		Gdx.app.log("view", w + ";" + h);
		batch = new SpriteBatch();
		batch.getTransformMatrix().setToTranslation(-1*(w/2), -1*(h/2), 0);
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		
		StylesManager = new StylesManager("2d");
		
		loader = new Helper(this);
		
		
		
		//start loading
		asm = new AssetManager();
		Texture load_img = new Texture(Gdx.files.internal("mlogo.png"));
		loading_screen = new GameLoad(this,load_img);
		
		
		
		this.setScreen(loading_screen);
	}
	
	public void gotoMainMenu(){
		//
		if(Sounds.sl1.isPlaying()) Sounds.sl1.stop();
		if(Sounds.sl2.isPlaying()) Sounds.sl2.stop();
		if(Sounds.sl3.isPlaying()) Sounds.sl3.stop();
		if(Sounds.sl4.isPlaying()) Sounds.sl4.stop();
		
		actionResolver.showMainMenu();
		if(sound){
		Sounds.maintheme.setLooping(true);
		Sounds.maintheme.play();
		}
		
		
		
		setScreen(this.waitscreen);
	}
	
	public void loadLevels()
	{
		FileHandle list[] = this.loader.listLevels();
		//gera nomes
		this.nomes_levels = new String[list.length];
		
		int i = 0;
		for(FileHandle f:list){
			if(f.name().contains(".xml")){
			Gdx.app.log("file name", f.name());
			this.nomes_levels[i] = f.name();
			i++;
			}
		}
	}
	
	public void loadCustomLevels()
	{
		FileHandle list[] = this.loader.listCustomLevels();
		//gera nomes
		this.nomes_levels_custom = new String[list.length];
		
		int i = 0;
		for(FileHandle f:list){
			if(f.name().contains(".xml")){
			this.nomes_levels_custom[i] = f.name();
			i++;
			}
		}
	}
	
	public void setSound(boolean a){
		sound = a;
		
		if(a)
		{
			if(Sounds.sl1.isPlaying()) Sounds.sl1.stop();
			if(Sounds.sl2.isPlaying()) Sounds.sl2.stop();
			if(Sounds.sl3.isPlaying()) Sounds.sl3.stop();
			if(Sounds.sl4.isPlaying()) Sounds.sl4.stop();
			
	
			Sounds.maintheme.setLooping(true);
			Sounds.maintheme.play();
			
		}
		else
		{
			Sounds.sl1.stop();
			Sounds.sl2.stop();
			Sounds.sl3.stop();
			Sounds.sl4.stop();
			Sounds.maintheme.stop();
		}
	}
	
	public void startLevel(int i)
	{
		Sounds.maintheme.stop();
		this.curLevel = loader.get(this.nomes_levels[i], false,true);
		setScreen(curLevel);
	}
	
	public void startCustomLevel(int i)
	{
		Sounds.maintheme.stop();
		this.curLevel = loader.get(this.nomes_levels_custom[i], true,false);
		setScreen(curLevel);
	}
	
	public void reloadLevels()
	{
		loadLevels();
		loadCustomLevels();
	}
	
	public void new_custom_level(String path){
		FileHandle dir = new FileHandle(Gdx.files.getExternalStoragePath() + path.replace("/sdcard/", ""));
		if(dir.exists()){
			dir.copyTo(new FileHandle(Gdx.files.getExternalStoragePath() + LEVELS_PATH));
			dir.delete();
			this.loadCustomLevels();
			Gdx.app.log("FILE PICKER", "ADDING TO CUSTOM LEVELS");
		}
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}
	
}
