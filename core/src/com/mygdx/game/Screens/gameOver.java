package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayScreen;

public class gameOver implements Screen {

    private Viewport viewport;
    private Stage stage;

    private TextureAtlas v2;
    private TextureRegion region;
    private Image shrek;
    private String player;
    private String playerL;

    private Game game;

    public gameOver(Game game, boolean whoWon) {
        this.game = game;
        viewport = new FitViewport(MyGdxGame.G_WIDTH, MyGdxGame.G_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
        v2 = new TextureAtlas("v2.txt");
        region = new TextureRegion(v2.findRegion("shrek"), 87, 0, 1194, 597);
        shrek = new Image(region);

        if (whoWon) {
            player = "Player 1";
            playerL = "Player 2";
        } else {
            player = "Player 2";
            playerL = "Player 1";
        }

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label(player + " wins!!!", font);
        Label playAgainLabel = new Label("Click to take the b8", font);
        table.add(gameOverLabel).expandX();
        table.row();
//        table.add(scoreLabel).expandX().padTop(10f);
        table.row();
        table.add(new Label("PRO TIP for " + playerL + ": GIT GUD", font)).padTop(10f).expandX();
        table.row();
        table.add(shrek).padTop(10f);
        table.row();
        table.add(playAgainLabel).padTop(10f);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        MyGdxGame.manager.get("music/background.mp3", Music.class).stop();
//        MyGdxGame.manager.get("music/death.mp3", Music.class).play();
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
        stage.dispose();
    }
}
