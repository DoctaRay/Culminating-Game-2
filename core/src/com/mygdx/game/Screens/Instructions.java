package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

public class Instructions implements Screen {
    private Game game;
    private FitViewport viewport;
    private Stage stage;

    private Label P1instr;
    private Label P2instr;

    public Instructions(MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(MyGdxGame.G_WIDTH, MyGdxGame.G_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label P1instr = new Label("Player 1: W - Up, A - Left, D - Right", font);
        Label P2instr = new Label("Player 2: UpArr - Up, LeftArr - Left, RightArr - Right", font);
        table.add(P1instr).expandX();
        table.row();
        table.add(P2instr).expandX();
        table.row();
//        table.add(scoreLabel).expandX().padTop(10f);
        table.add(new Label("You can jump as much as you want! But don't touch the borders of the screen!", font)).padTop(10f).expandX();
        table.row();
        table.add(new Label("It's a sumo fight to the death! Push each other off by any means necessary!", font)).padTop(10f).expandX();
        table.row();
        table.add(new Label("The items in the middle speed you up! You can steal the other player's item if you're fast enough.", font)).padTop(10f).expandX();
        table.row();
        table.add(new Label("Touch anywhere to return to the menu screen.", font)).padTop(10f).expandX();
        table.row();

        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
//            MyGdxGame.manager.get("sound_eff/start.mp3", Sound.class).play();
            game.setScreen(new menuScreen((MyGdxGame) game));
            MyGdxGame.manager.get("background.mp3", Music.class).stop();
//            MyGdxGame.manager.get("music/death.mp3", Music.class).stop();
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
