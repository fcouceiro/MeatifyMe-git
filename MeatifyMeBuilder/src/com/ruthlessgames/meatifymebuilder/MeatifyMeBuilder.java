package com.ruthlessgames.meatifymebuilder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MeatifyMeBuilder implements ApplicationListener,InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Stage stage;
	private Table table;
	float w,h,bWidth,bHeight;
	
	public static String LEVELS_PATH = "MeatifyMe/Levels/";
	
	private Texture blocos;
	
	private AndroidBridge a_bridge;
	
	Drawable bgs[];
	//parte editor
	HashMap<String,Bloco> blocks_rep;
	int blocks[][];
	int style = 4;
	int type = 1;
	boolean border = false;
	int bordertype;
	String name = "Undefined";
	ArrayList<Bloco> border_rep;
	
	Helper helper;
	
	
	public MeatifyMeBuilder(AndroidBridge a_b)
	{
		this.a_bridge = a_b;
		bgs = new Drawable[4];
	}
	
	@Override
	public void create() {		
		w = Gdx.graphics.getWidth();
		h  = Gdx.graphics.getHeight();
		bWidth = w / 20;
		bHeight = h / 14;
		camera = new OrthographicCamera(w, h);
		Gdx.app.log("view", w + ";" + h);
		batch = new SpriteBatch();
		batch.getTransformMatrix().setToTranslation(-1*(w/2), -1*(h/2), 0);
		batch.setProjectionMatrix(camera.combined);
		
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		
		stage.addActor(table);
		
		this.loadImages();
		
		blocks_rep = new HashMap<String,Bloco>();
		border_rep = new ArrayList<Bloco>();
		blocks = new int[20][14];
		for(int i=0;i<20;i++)
			for(int j=0;j<14;j++)
				blocks[i][j] = 0;
		
		helper = new Helper();
		Gdx.input.setInputProcessor(this);
		this.setStyle(style);
	}

	private void loadImages()
	{
		blocos = new Texture(Gdx.files.internal("data/blocos.png"));
		blocos.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Texture bg1_t = new Texture(Gdx.files.internal("data/1.png"));
		bg1_t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgs[0] = new TextureRegionDrawable(new Sprite(bg1_t));
		
		Texture bg2_t = new Texture(Gdx.files.internal("data/2.png"));
		bg2_t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgs[1] = new TextureRegionDrawable(new Sprite(bg2_t));

		Texture bg3_t = new Texture(Gdx.files.internal("data/3.png"));
		bg3_t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgs[2] = new TextureRegionDrawable(new Sprite(bg3_t));
		
		Texture bg4_t = new Texture(Gdx.files.internal("data/4.png"));
		bg4_t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgs[3] = new TextureRegionDrawable(new Sprite(bg4_t));
		
	}
	
	@Override
	public void dispose() {
		batch.dispose();

	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}


	public FileToUpload save(){
		FileHandle saved_file = this.helper.save(blocks, style, type, name, this.border,this.bordertype);
		FileToUpload temp = new FileToUpload(name,saved_file.file());
		
		return temp;
	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
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

	String getImageId(int x, int y)
	{
		return x + ";" + y;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		
		int x = (int) (screenX / this.bWidth);
		int y = (int) ((this.h - screenY) / this.bHeight);
		
		if(x == 0 || x == 19 || y == 0 || y == 13){
			a_bridge.showToast("Can't build here!");
			return true;
		}
		
		if(this.blocks[x][y] != 0){
			//bloco ja existe -> remover
			blocks[x][y] = 0;
			
			table.removeActor(this.blocks_rep.get(this.getImageId(x, y)).image);
			this.blocks_rep.remove(this.getImageId(x, y));
			return true;
		}
		
		int type = a_bridge.getSelectedBlockType();
		this.blocks[x][y] = type;
		
		Gdx.app.log("NEW BLOCK", "At (" + this.getImageId(x, y) + ") type: " + type);
		
		//create image
		Image temp = new Image(new TextureRegionDrawable(new TextureRegion(blocos,32*(type),32*(4-style),32,32)));
		temp.setSize(bWidth, bHeight);
		temp.setPosition(x * this.bWidth, y * this.bHeight);
		
		Bloco bloco_temp = new Bloco(type,temp);
		blocks_rep.put(this.getImageId(x, y), bloco_temp);
		table.addActor(temp);
		
		return true;
	}

	
	public void setBorder(boolean bor,int type)
	{
		this.border = bor;
		this.bordertype = type;
		if(!bor){
			//delete border
			for(Bloco i:border_rep){
				table.removeActor(i.image);
			}
			
			border_rep.clear();
			return;
		}
		
		//create border
		//upper line
		for (int p = 0; p < 20; p++) {
			Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(blocos,32*(this.a_bridge.getSelectedBlockType()),32*(4-style),32,32)));
			newblock.setPosition(p * bWidth, 13 * bHeight);
			newblock.setSize(bWidth, bHeight);
			this.border_rep.add(new Bloco(a_bridge.getSelectedBlockType(),newblock));
			table.addActor(newblock);
		}
		//bottom line
		for (int p = 0; p < 20; p++) {
			
			Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(blocos,32*(this.a_bridge.getSelectedBlockType()),32*(4-style),32,32)));
			newblock.setPosition(p * bWidth, 0 * bHeight);
			newblock.setSize(bWidth, bHeight);
			this.border_rep.add(new Bloco(a_bridge.getSelectedBlockType(),newblock));
			table.addActor(newblock);
		}
		//left line
		for (int p = 0; p < 13; p++) {
			
			Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(blocos,32*(this.a_bridge.getSelectedBlockType()),32*(4-style),32,32)));
			newblock.setPosition(0 * bWidth, p * bHeight);
			newblock.setSize(bWidth, bHeight);
			this.border_rep.add(new Bloco(a_bridge.getSelectedBlockType(),newblock));
			table.addActor(newblock);
		}
		//right line
		for (int p = 0; p < 13; p++) {
			
			Image newblock = new Image(new TextureRegionDrawable(new TextureRegion(blocos,32*(this.a_bridge.getSelectedBlockType()),32*(4-style),32,32)));
			newblock.setPosition(19 * bWidth, p * bHeight);
			newblock.setSize(bWidth, bHeight);
			this.border_rep.add(new Bloco(a_bridge.getSelectedBlockType(),newblock));
			table.addActor(newblock);
		}
	}
	
	public void setStyle(int s){
		this.style = s;
		
		for(int i=0;i<20;i++){
			for(int j=0;j<14;j++){
				Bloco cur_b = blocks_rep.get(this.getImageId(i, j));
				
				if(cur_b != null)
				{
					cur_b.image.setDrawable(new TextureRegionDrawable(new TextureRegion(blocos,32*(cur_b.type),32*(4-style),32,32)));
				}
			}
		}
		
		
		for(Bloco b:border_rep){
			b.image.setDrawable(new TextureRegionDrawable(new TextureRegion(blocos,32*(b.type),32*(4-style),32,32)));
		}
		//update bg
		table.setBackground(bgs[style-1]);
	}
	
	public void setType(int t){
		this.type = t;
	}
	
	public void setName(String name){
		this.name = name;
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
}
