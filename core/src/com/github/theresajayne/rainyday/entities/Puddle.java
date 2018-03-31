package com.github.theresajayne.rainyday.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Puddle {
    private static final float COLLISION_RECTANGLE_HEIGHT = 3f;
    private static final float MAX_SPEED_PER_SECOND = 100f;
    private final Rectangle collisionRectangle;
    private final Texture puddleTexture = new Texture(Gdx.files.internal("tex_Water.jpg"));
    private TextureRegion region;
    private float x = 0;
    private float y = 1;
    private float width = 50;
    private boolean pointClaimed = false;


    public Puddle(float width)
    {

        this.collisionRectangle = new Rectangle(getX(), getY(), width, COLLISION_RECTANGLE_HEIGHT);

    }

    public boolean isPointClaimed() {
        return pointClaimed;
    }

    public void markPointClaimed() {
        pointClaimed = true;
    }

    public void setPosition(float x) {
        this.setX(x);
        updateCollisionRectangle();
    }


    private void updateCollisionRectangle() {
        collisionRectangle.setX(getX());
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(puddleTexture,collisionRectangle.getX(),collisionRectangle.getY(),collisionRectangle.width,COLLISION_RECTANGLE_HEIGHT);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        //shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
    }

    public void update(float delta)
    {
        setPosition(getX() -(MAX_SPEED_PER_SECOND*delta));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isToddlerColliding(Toddler toddler)
    {
        Circle toddlerCollisionCircle = toddler.getCollisionCircle();
        return Intersector.overlaps(toddlerCollisionCircle,collisionRectangle);
    }

}
