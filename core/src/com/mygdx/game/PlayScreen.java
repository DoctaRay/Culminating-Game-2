package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Screens.gameOver;
import com.mygdx.game.Sprites.Item;
import com.mygdx.game.Sprites.ItemDef;
import com.mygdx.game.Sprites.Player1;
import com.mygdx.game.Sprites.Player2;
import com.mygdx.game.Sprites.Speed;
import com.mygdx.game.Tools.WContactListener;
import com.mygdx.game.Tools.WorldCreator;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera camera;
    private StretchViewport gamePort;
    private World world;
    private Box2DDebugRenderer b2dr;
    private gameBoard gameBoard;

    private TextureAtlas atlas;

    private WorldCreator creator;

    private Player1 player1;
    private Player2 player2;
    private boolean oneDead;
    private boolean twoDead;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private Array<Speed> speeds;
    private Speed testSpeed;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;



    public PlayScreen(MyGdxGame game, Integer wt, Integer P1L, Integer P2L) {
        this.game = game;

        camera = new OrthographicCamera();
        gamePort = new StretchViewport(MyGdxGame.G_WIDTH / MyGdxGame.PPM, MyGdxGame.G_HEIGHT / MyGdxGame.PPM, camera);

        gameBoard = new gameBoard(game.batch, wt, P1L, P2L);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("new_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        atlas = new TextureAtlas("Culgame.txt");

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player1 = new Player1(this, this.game);
        player2 = new Player2(this, this.game);
        oneDead = false;
        twoDead = false;

        world.setContactListener(new WContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        speeds = new Array<Speed>();
        testSpeed = new Speed(this, 420, 128);

        creator = new WorldCreator(this);
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Speed.class){
                items.add(new Speed(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }



    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.b2body.applyLinearImpulse(new Vector2(0, 0.3f), player1.b2body.getWorldCenter(), true);
        }

            if (Gdx.input.isKeyPressed(Input.Keys.D) && player1.b2body.getLinearVelocity().x <= 2) {
                if(player1.isSpeedUp())
                    player1.b2body.applyLinearImpulse(new Vector2(2f, 0), player1.b2body.getWorldCenter(), true);
                player1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player1.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player1.b2body.getLinearVelocity().x >= -2) {
                if(player1.isSpeedUp())
                    player1.b2body.applyLinearImpulse(new Vector2(-2f, 0), player1.b2body.getWorldCenter(), true);
                player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player1.b2body.getWorldCenter(), true);
            }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2.b2body.applyLinearImpulse(new Vector2(0, 0.3f), player2.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player2.b2body.getLinearVelocity().x <= 2) {
            if(player2.isSpeedUp())
                player2.b2body.applyLinearImpulse(new Vector2(2f, 0), player2.b2body.getWorldCenter(), true);
            player2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player2.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player2.b2body.getLinearVelocity().x >= -2) {
            if(player2.isSpeedUp())
                player2.b2body.applyLinearImpulse(new Vector2(-2f, 0), player2.b2body.getWorldCenter(), true);
            player2.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player2.b2body.getWorldCenter(), true);
        }
        }


    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();
//        if (player1.isSpeedUp()) {
//            player1.b2body.setLinearVelocity(new Vector2(2f, 0f));
//        }
//        if (player2.isSpeedUp()) {
//            player2.b2body.setLinearVelocity(new Vector2(2f, 0f));
//        }
        world.step(1/60f, 6, 2);

        player1.update(dt);
        player2.update(dt);
        testSpeed.update(dt);

        for(Item item: creator.getSpeeds()) {
            item.update(dt);
        }

        gameBoard.update(dt);

        camera.update();
        renderer.setView(camera);


    }
    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

//        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player1.draw(game.batch);
        player2.draw(game.batch);
        testSpeed.draw(game.batch);

        for(Item item: creator.getSpeeds()) {
            item.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(gameBoard.stage.getCamera().combined);
        gameBoard.stage.draw();

        if (isDead()) {
            game.setScreen(new PlayScreen(this.game, gameBoard.getWorldTimer(), gameBoard.getP1lives(), gameBoard.getP2lives()));
        }

        if(gameOver()){
            game.setScreen(new gameOver(this.game, whoWon()));
        }

    }
//    public void speedUp() {
//        if (player1.isSpeedUp()) {
//            player1.b2body.setLinearVelocity(new Vector2(2f, 0f));
//        }
//        if (player2.isSpeedUp()) {
//            player2.b2body.setLinearVelocity(new Vector2(2f, 0f));
//        }
//    }

    public boolean isDead() {
        if (player1.getState() == Player1.State.DEAD && player1.getStateTimer() > 2){
            return true;
        } else if (player2.getState() == Player2.State.DEAD && player2.getStateTimer() > 2) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean gameOver() {
        if (gameBoard.getP1lives() == 0 || gameBoard.getP2lives() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean whoWon() {
        if (gameBoard.getP1lives() == 0)
            return false;
        else
            return true;

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
