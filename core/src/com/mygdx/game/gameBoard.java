package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class gameBoard implements Disposable {
    private static Integer worldTimer;
    private float timeCount;
    private Integer score;

    public static Integer p1lives;
    public static Integer p2lives;

    private FitViewport viewport;
    public Stage stage;

    Label p1livesL;
    Label p2livesL;
    Label p1;
    Label p2;
    Label time;
    Label timeLabel;


    public gameBoard(SpriteBatch sb, Integer wt, Integer P1L, Integer P2L) {
        wt = (wt != null) ? wt : 300;
        worldTimer = wt;
        timeCount = 0;
        score = 0;

        p1lives = (P1L != null) ? P1L : 300;
        p2lives = (P2L != null) ? P2L : 300;

//        if (p1lives != 3) {
//            p1lives = one;
//        } else if (p2lives != 3) {
//            p2lives = two;
//        }


        viewport = new FitViewport(MyGdxGame.G_WIDTH, MyGdxGame.G_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        time = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p1 = new Label("P1 Lives", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2 = new Label("P2 Lives", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2livesL = new Label(String.format("%01d", p2lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p1livesL = new Label(String.format("%01d", p1lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(p1).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(p2).expandX().padTop(10);
        table.row();
        table.add(p1livesL).expandX().padTop(10);
        table.add(time).expandX().padTop(10);
        table.add(p2livesL).expandX().padTop(10);
        table.row();

        stage.addActor(table);


    }

    public static void p1dies() {
        p1lives = p1lives - 1;
    }

    public static void p2dies() {
        p2lives = p2lives - 1;
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount > 1){
            worldTimer--;
            time.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        p1livesL.setText(String.format("%03d", p1lives));

        p2livesL.setText(String.format("%03d", p2lives));
    }

    public static Integer getWorldTimer() {
        return worldTimer;
    }

    public Integer getP1lives() {
        return p1lives;
    }

    public Integer getP2lives() {
        return p2lives;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
