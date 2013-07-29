package com.me.meatifyme;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ruthlessgames.api.StylesManager;


public class MeatifyMe extends Game{
	
	public static OrthographicCamera camera;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	
	//screens
	public Editor editor;
	public MainMenu mainmenu;
	public LevelSelector levelSelector;
	//flash screens
	FlashScreen rLogo,mLogo;
	
	public static float w;
	public static float h;
	public static boolean debug = false;
	
	public ArrayList<Level> levels = new ArrayList<Level>(10);
	public ArrayList<Level> levels_custom = new ArrayList<Level>(10);
	public Helper loader;
	private Textures textureLoader;
	public StylesManager StylesManager;
	
	//Android bridge
	public ActionResolver actionResolver;
	public static boolean ready_for_game = false;
	public static int nextLevelIndex = 0;
	
	public MeatifyMe(ActionResolver actionR)
	{
		actionResolver = actionR;
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		//initializations
		w = Gdx.graphics.getWidth();
		h  = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		Gdx.app.log("view", w + ";" + h);
		batch = new SpriteBatch();
		batch.getTransformMatrix().setToTranslation(-1*(w/2), -1*(h/2), 0);
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		loader = new Helper(this);
		
		//load assets
		StylesManager = new StylesManager("2d");
		textureLoader = new Textures();
		Sounds.load();
		
		font = StylesManager.btnBlue.font;
		levelSelector = new LevelSelector(this);
		
		//load levels
		this.loadLevels();
		this.loadCustomLevels();
		
		editor = new Editor(this);
		mainmenu = new MainMenu(this);
		
		
		//flashscreens
		mLogo = new FlashScreen(this,Textures.mLogo,mainmenu,60);
		rLogo = new FlashScreen(this,Textures.rLogo,mLogo,60);
		
		//start maintheme song
		this.setScreen(rLogo);
	}
	
	private void loadLevels()
	{
		//list all levels here
		levels.add(loader.get("levels/teste2.xml",false));
		this.levelSelector.populateLevels();
	}
	
	public void loadCustomLevels()
	{
		levels_custom.clear();
		
		FileHandle list[] = loader.listLocalLevels();
		
		if (list != null) {
			for (FileHandle file : list) {
				levels_custom.add(loader.get("levels/" + file.name(), true));
			}
			this.levelSelector.populateCustomLevels();
		}
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		textureLoader.dispose();
		mainmenu.dispose();
		editor.dispose();
		levelSelector.dispose();
		rLogo.dispose();
		mLogo.dispose();
		Sounds.dispose();
	}
	
}
