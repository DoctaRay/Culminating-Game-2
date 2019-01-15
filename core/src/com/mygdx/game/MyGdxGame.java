package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.menuScreen;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	Texture img;

	public static final float G_WIDTH = 640;
	public static final float G_HEIGHT = 250;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short PLAYER1_BIT = 1;
	public static final short PLAYER2_BIT = 2;
	public static final short GROUND_BIT = 4;
	public static final short LOSE_BIT = 8;
	public static final short SPEED_BIT = 16;

	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new menuScreen(this));

		manager = new AssetManager();
		manager.load("background.mp3", Music.class);
		manager.finishLoading();
	}

	@Override
	public void render () {

		super.render();
		manager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
