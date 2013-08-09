package com.ruthlessgames.meatifymebuilder;


import java.io.IOException;
import java.io.StringWriter;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

public class Helper {
	
	public XmlReader xml_reader;
	public XmlWriter xml_writer;
	
	/*public Level get(String filePath,boolean fromLocal)
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
	}*/
	
	public FileHandle save(int blocks[][],int style,int type,String name, boolean border,int bordertype)
	{
		FileHandle file;
		StringWriter writer = new StringWriter();
		xml_writer = new XmlWriter(writer);
		
		try {
				//write level attributes
			 	xml_writer.element("level")
		        .attribute("style", style).attribute("type", type).attribute("name", name);
			 	xml_writer.element("border").attribute("b", border);
			 	if(border){
			 		xml_writer.element("tipo").text(bordertype).pop();
			 	}
			 	xml_writer.pop();
			 	
			 	for(int i=1;i<20;i++){
					for(int j=1;j<14;j++){
						if(blocks[i][j] != 0){
						xml_writer.element("bloco");
						xml_writer.element("tipo").text(blocks[i][j]).pop();
						xml_writer.element("posx").text(i).pop();
						xml_writer.element("posy").text(j).pop();
						xml_writer.pop();
						}
					}
				}
			 	
				xml_writer.pop();
				
				String filename = "";
				if(!name.toString().contains(".xml"))
				{
					filename = name + ".xml";
				}
				else filename = name.toString();
				
			
			 	file = Gdx.files.external(MeatifyMeBuilder.LEVELS_PATH +filename);
			 
			 	file.writeString(writer.toString(), false);
			 	
			 	return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public FileHandle[] listLocalLevels(){
		
			FileHandle dir = new FileHandle(Gdx.files.getExternalStoragePath()+MeatifyMeBuilder.LEVELS_PATH);
			Gdx.app.log("dir length", dir.list().length+"");
			return dir.list();
		
	}
}
