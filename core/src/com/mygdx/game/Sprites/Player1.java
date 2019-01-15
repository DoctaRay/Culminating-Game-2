package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.gameBoard;

public class Player1 extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, WIN}
    public State currState;
    public State prevState;
    private boolean isDead;
    private boolean speedUp;

    private World world;
    public Body b2body;
    private MyGdxGame game;

    private float stateTimer;

    private TextureRegion heroStand;

    private boolean runningRight;

    public Player1(PlayScreen screen, MyGdxGame game) {
        super(screen.getAtlas().findRegion("spongebob"));
        this.world = screen.getWorld();
        this.game = game;


        currState = State.STANDING;
        prevState = State.STANDING;
        isDead = false;
        speedUp = false;

        runningRight = true;

        heroStand = new TextureRegion(getTexture(), 1080, 185, 28, 36);
        definePlayer1();
        setBounds(0, 0, 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);
        setRegion(heroStand);

    }

    public void hit() {
        isDead = true;
        Filter filter = new Filter();
        filter.maskBits = MyGdxGame.NOTHING_BIT;
        for(Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(0, 7f), b2body.getWorldCenter(), true);
        gameBoard.p1dies();
    }
    public void update(float dt) {


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public void speedUp() {
        speedUp = true;
    }

    public boolean isSpeedUp() {
        return speedUp;
    }

    public TextureRegion getFrame(float dt) {
        currState = getState();

        TextureRegion region;

        switch(currState) {
            case DEAD:
                region = heroStand;
                break;
            case JUMPING:
                region = heroStand;
            case STANDING:
            default:
                region = heroStand;
                break;

        }
        region = heroStand;


        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currState == prevState ? stateTimer + dt : 0;
        prevState = currState;
        return region;
    }

    public State getState() {
        if (isDead) {
            return State.DEAD;
        }
        else if(b2body.getLinearVelocity().y > 2) {
            return State.JUMPING;
        }
        else {
            return State.STANDING;
        }
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void definePlayer1(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / MyGdxGame.PPM, 128 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.PLAYER1_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT |
                MyGdxGame.PLAYER2_BIT |
                MyGdxGame.LOSE_BIT |
                MyGdxGame.SPEED_BIT;
        fdef.shape = shape;
        fdef.restitution = 1f;
        b2body.createFixture(fdef).setUserData(this);


    }
}
