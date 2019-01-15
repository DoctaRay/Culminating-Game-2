package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayScreen;


public class Speed extends Item {
    public Speed(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(new TextureAtlas("v2.txt").findRegion("vape2"), 0, 0, 844, 596);
        velocity = new Vector2(0f, 0);
    }

    @Override

    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.SPEED_BIT;
        fdef.filter.maskBits = MyGdxGame.PLAYER1_BIT |
                MyGdxGame.PLAYER2_BIT |
                MyGdxGame.GROUND_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Player1 player1, Player2 player2) {
        player1 = (player1 != null) ? player1 : null;
        player2 = (player2 != null) ? player2 : null;
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }

    @Override
    public void destroy() {
        super.destroy();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);
    }
}
