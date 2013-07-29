package com.me.meatifyme;


import java.io.IOException;
import java.io.StringWriter;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

public class Helper {
	
	public static XmlReader xml_reader;
	public static XmlWriter xml_writer;
	static Level curLevel;
	MeatifyMe maingame;
	
	public Helper(MeatifyMe main)
	{
		maingame = main;
	}
	
	public Level get(String filePath,boolean fromLocal)
	{
		xml_reader = new XmlReader();
	
		try {
			XmlReader.Element a;
			
			if(!fromLocal)
			a = xml_reader.parse(Gdx.files.internal(filePath));
			else a = xml_reader.parse(Gdx.files.local(filePath));
			
			int style = a.getInt("style");
			int type = a.getInt("type");
			boolean bord = a.getChildByName("border").getBoolean("b");
			CharSequence nome = a.get("name");
		
			
			//instantiate new level to populate
			curLevel = new Level(maingame,style,type,bord,nome);
			
			if (bord) {
				//create border
				int tipo = Integer.parseInt(a
						.getChildByName("border").getChildByName("tipo")
						.getText());
				//upper line
				curLevel.setBorder(tipo);
			}
			//get blocos
			int c =a.getChildCount();
			
			for(int i=0;i<c;i++){
				XmlReader.Element curE = a.getChild(i);
				
				//handle blocos
				if(curE.getName().contains("bloco"))
				{

					int posx,posy,tipo;
					tipo = Integer.parseInt(curE.getChildByName("tipo").getText());
					posx = Integer.parseInt(curE.getChildByName("posx").getText());
					posy = Integer.parseInt(curE.getChildByName("posy").getText());
					
					curLevel.addBlock(posx, posy, tipo);
					
					if(tipo == 10) curLevel.pl_ini_pos = new Vector2(posx,posy);
				}
			
				
				//handle linhas
				if(curE.getName().contains("linha"))
				{
					int posx,posy,tipo,size;
					
					tipo = Integer.parseInt(curE.getChildByName("tipo").getText());
					posx = Integer.parseInt(curE.getChildByName("posx").getText());
					posy = Integer.parseInt(curE.getChildByName("posy").getText());
					
					String size_aux = null;
					try{
						size_aux = curE.getChildByName("size").getText();
						size = Integer.parseInt(size_aux);
					}
					catch(Exception e)
					{
						size = 25; //default size
					}
					
					for(int p=posx;(p<size+posx) && (p<25);p++)
					{
						curLevel.addBlock(p, posy, tipo);
					}					
				}
			}
			
			Gdx.app.log("loaded", curLevel.name+"");
			return curLevel;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void save(Level lev)
	{
		StringWriter writer = new StringWriter();
		xml_writer = new XmlWriter(writer);
		
		try {
				//write level attributes
			 	xml_writer.element("level")
		        .attribute("style", 4-lev.style).attribute("type", lev.type).attribute("name", lev.name);
			 	xml_writer.element("border").attribute("b", lev.border);
			 	if(lev.border){
			 		xml_writer.element("tipo").text(lev.getBlockType(0, 0)).pop();
			 	}
			 	xml_writer.pop();
			 	
			 	for(int i=1;i<lev.max_x-1;i++){
					for(int j=1;j<lev.max_y-1;j++){
						if(lev.getBlockType(i, j) != 0){
						xml_writer.element("bloco");
						xml_writer.element("tipo").text(lev.getBlockType(i, j)).pop();
						xml_writer.element("posx").text(i).pop();
						xml_writer.element("posy").text(j).pop();
						xml_writer.pop();
						}
					}
				}
			 	
				xml_writer.pop();
				
				String filename = "";
				if(!lev.name.toString().contains(".xml"))
				{
					filename = lev.name + ".xml";
				}
				else filename = lev.name.toString();
				
			 	FileHandle file = Gdx.files.local("levels/"+filename);
			 
			 	file.writeString(writer.toString(), false);
			 	
			 	if(Gdx.app.getType() == ApplicationType.Android){
			 	maingame.actionResolver.showShortToast("Level saved as: " + filename);
			 	}
			 	
			 	//reload levels
			 	maingame.loadCustomLevels();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FileHandle[] listLocalLevels(){
		if(Gdx.app.getType() == ApplicationType.Android){
			FileHandle dir = new FileHandle(Gdx.files.getLocalStoragePath()+"/levels");
			Gdx.app.log("dir length", dir.list().length+"");
			return dir.list();
		}
		else if (Gdx.app.getType() == ApplicationType.Desktop)
		{
			java.io.File dir = new java.io.File(Gdx.files.getLocalStoragePath()+"levels");
			String filen[] = dir.list();
			FileHandle list[] = new FileHandle[filen.length];
			
			for(int i=0;i<filen.length;i++)
			{
				list[i] = new FileHandle(filen[i]);
			}
			return list;
		}
		
		return null;
	}
}
