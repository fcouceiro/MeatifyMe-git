package com.ruthlessgames.meatifymebuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileToUpload {
	String nome;
	InputStream ficheiro;
	
	public FileToUpload(String n,File f){
		this.nome = n;
		try {
			this.ficheiro = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
