package com.ruthlessgames.meatifyme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Animations {
	
		Player gordo;
		
		public Animations(Player pl){
			this.gordo = pl;
		}
		public SequenceAction andar()
		{
			//must be pooled (study)
			
			final SequenceAction andar4  = new SequenceAction();
			andar4.addAction(Actions.delay(1));
			andar4.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_andar)));
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_andar)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_andar)));
						break;
					}
				}}));
			andar4.addAction(Actions.delay(0.5f));
			andar4.addAction(Actions.moveBy(MeatifyMe.bWidth, 0));
			andar4.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_main)));
						Sounds.pl2_andar.play();
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_main)));
						Sounds.pl3_andar.play();
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
						Sounds.pl4_andar.play();
						break;
					}		
				}}));
			
			return andar4;
		}
		
		public SequenceAction subir1(){
			SequenceAction subir1 = new SequenceAction();
			subir1.addAction(Actions.delay(1));
			subir1.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_subir1_1)));
						Sounds.pl2_subir.play();
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_subir1_1)));
						Sounds.pl3_subir.play();
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir1_1)));
						Sounds.pl4_subir1.play();
						break;
					}
					
				}}));
			subir1.addAction(Actions.delay(0.5f));
			subir1.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_subir1_2)));
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_subir1_2)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir1_2)));
						break;
					}
				}}));
			subir1.addAction(Actions.delay(0.5f));
			subir1.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth, MeatifyMe.bHeight),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_main)));
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_main)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
						break;
					}
				}})));
			
			return subir1;
		}
		
		public SequenceAction subir2(){
			SequenceAction subir2 = new SequenceAction();
			subir2.addAction(Actions.delay(1));
			subir2.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_1)));
					Sounds.pl4_subir1.play();
				}}));
			subir2.addAction(Actions.delay(0.5f));
			subir2.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_2)));
				}}));
			subir2.addAction(Actions.delay(0.5f));
			subir2.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_3)));
					
				}}));
			subir2.addAction(Actions.delay(0.5f));
			subir2.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_subir2_4)));
					Sounds.pl4_subir2.play();
				}}));
			subir2.addAction(Actions.delay(0.5f));
			subir2.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth, 2*MeatifyMe.bHeight),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
				}})));
			
			return subir2;
		}
		
		public SequenceAction descer1(){
			SequenceAction descer = new SequenceAction();
			descer.addAction(Actions.delay(1));
			descer.addAction(Actions.parallel(Actions.moveBy(-MeatifyMe.bWidth, -MeatifyMe.bHeight),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_descer1_1)));
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_descer1_1)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_1)));
						break;
					}
				}})));
			descer.addAction(Actions.delay(0.5f));
			descer.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_descer1_2)));
						Sounds.pl2_descer.play();
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_descer1_2)));
						Sounds.pl3_descer.play();
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_2)));
						Sounds.pl4_descer.play();
						break;
					}
				}}));
			descer.addAction(Actions.delay(0.5f));
			descer.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,0),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_2:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl2_main)));
						break;
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_main)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
						break;
					}			
				}})));
			
			return descer;
		}

		public SequenceAction descer2(){
			SequenceAction descer2 = new SequenceAction();
			descer2.addAction(Actions.delay(1));
			descer2.addAction(Actions.parallel(Actions.moveBy(-MeatifyMe.bWidth, -MeatifyMe.bHeight),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_1)));
				}})));
			descer2.addAction(Actions.delay(0.5f));
			descer2.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_descer1_2)));
					Sounds.pl4_descer.play();
				}}));
			descer2.addAction(Actions.delay(0.5f));
			descer2.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,-MeatifyMe.bHeight),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
					
				}})));
			
			return descer2;
		}
		
		public SequenceAction saltar(){
			SequenceAction saltar = new SequenceAction();
			saltar.addAction(Actions.delay(1));
			saltar.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_saltar1)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar1)));
						break;
					}
				}}));
			saltar.addAction(Actions.delay(0.5f));
			saltar.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_saltar2)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar2)));
						break;
					}
					Sounds.peido.play();
				}}));
			saltar.addAction(Actions.delay(0.5f));
			saltar.addAction(Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_saltar3)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_saltar3)));
						break;
					}
					
				}}));
			saltar.addAction(Actions.delay(0.5f));
			saltar.addAction(Actions.parallel(Actions.moveBy(MeatifyMe.bWidth * 2,0),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch(gordo.state){
					case GORDO_3:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl3_main)));
						break;
					case GORDO_4:
						gordo.setDrawable(new TextureRegionDrawable(new TextureRegion(Textures.pl4_main)));
						break;
					}
				}})));
			
			return saltar;
		}
}
