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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayScreen;

public class menuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private TextureAtlas atlas;
    private Skin skin;
    private TextureRegion region;
    private OrthographicCamera camera;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    private Image shrek;

    private Game game;

    public menuScreen(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(MyGdxGame.G_WIDTH, MyGdxGame.G_HEIGHT, camera);
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("skin/arcade-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/arcade-ui.json"), atlas);
        font = new BitmapFont();

        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, ((MyGdxGame) game).batch);
    }
    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);

        //Set alignment of contents in the table.
        mainTable.center();

        //Create buttons
        TextButton playButton = new TextButton("New Game", textButtonStyle);
        TextButton instrButton = new TextButton("Instructions", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.ROYAL);
        Label title = new Label("Raiyan's **NEW** Culminating Game", font);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.manager.get("background.mp3", Music.class).play();
                ((MyGdxGame) game).setScreen(new PlayScreen((MyGdxGame) game, 300, 5, 5));
            }
        });
        instrButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.manager.get("background.mp3", Music.class).play();
                ((MyGdxGame) game).setScreen(new Instructions((MyGdxGame) game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        mainTable.add(title).expandX();
        mainTable.row();
        mainTable.add(playButton).expandX().padTop(10f);
        mainTable.row();
        mainTable.add(instrButton).expandX().padTop(10f);
        mainTable.row();
        mainTable.add(exitButton).expandX().padTop(10f);
        mainTable.row();

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
        skin.dispose();
        atlas.dispose();
    }
}
