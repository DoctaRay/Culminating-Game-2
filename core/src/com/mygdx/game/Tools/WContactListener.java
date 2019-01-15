package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Item;
import com.mygdx.game.Sprites.Player1;
import com.mygdx.game.Sprites.Player2;

public class WContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixa = contact.getFixtureA();
        Fixture fixb = contact.getFixtureB();

        int cDef = fixa.getFilterData().categoryBits | fixb.getFilterData().categoryBits;

        switch (cDef) {
            case MyGdxGame.PLAYER1_BIT | MyGdxGame.PLAYER2_BIT:
                if (fixa.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    Gdx.app.log("Hi", "there");


                } else if (fixb.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    Gdx.app.log("Hi", "there");
                }
                break;
            case MyGdxGame.PLAYER2_BIT | MyGdxGame.LOSE_BIT:
                if (fixa.getFilterData().categoryBits == MyGdxGame.PLAYER2_BIT) {
                    ((Player2) fixa.getUserData()).hit();

                } else if (fixb.getFilterData().categoryBits == MyGdxGame.PLAYER2_BIT) {
                    ((Player2) fixb.getUserData()).hit();
                }
                break;
            case MyGdxGame.PLAYER1_BIT | MyGdxGame.LOSE_BIT:
                if (fixa.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    ((Player1) fixa.getUserData()).hit();

                } else if (fixb.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    ((Player1) fixb.getUserData()).hit();
                }
                break;
            case MyGdxGame.PLAYER1_BIT | MyGdxGame.SPEED_BIT:
                if (fixa.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    ((Player1) fixa.getUserData()).speedUp();
                    ((Item) fixb.getUserData()).use(null, null);

                } else if (fixb.getFilterData().categoryBits == MyGdxGame.PLAYER1_BIT) {
                    ((Player1) fixb.getUserData()).speedUp();
                    ((Item) fixa.getUserData()).use(null, null);
                }
                break;
            case MyGdxGame.PLAYER2_BIT | MyGdxGame.SPEED_BIT:
                if (fixa.getFilterData().categoryBits == MyGdxGame.PLAYER2_BIT) {
                    ((Player2) fixa.getUserData()).speedUp();
                    ((Item) fixb.getUserData()).use(null, null);

                } else if (fixb.getFilterData().categoryBits == MyGdxGame.PLAYER2_BIT) {
                    ((Player2) fixb.getUserData()).speedUp();
                    ((Item) fixa.getUserData()).use(null, null);
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
