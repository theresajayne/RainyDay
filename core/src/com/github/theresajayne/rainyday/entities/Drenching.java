package com.github.theresajayne.rainyday.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Drenching {

    private final Rectangle outer;
    private final Rectangle inner;
    private float drenching;
    private Texture outerTexture;
    private Texture innerTexture;


    public Drenching()
    {
        this.outer = new Rectangle(10,Gdx.graphics.getHeight()-100,400,50);
        this.inner = new Rectangle(11, Gdx.graphics.getHeight()-99,1, 48);
        outerTexture = new Texture(Gdx.files.internal("outer.png"));
        innerTexture = new Texture(Gdx.files.internal("inner.png"));
    }

    public void setDrenching(float x) {
        this.drenching = x;
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(innerTexture,inner.getX(),inner.getY(),drenching*4,inner.height);
        batch.draw(outerTexture,outer.getX(),outer.getY(),outer.width,outer.height);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        //shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
    }

    public void update(float delta)
    {
        inner.width = drenching;
    }



}
