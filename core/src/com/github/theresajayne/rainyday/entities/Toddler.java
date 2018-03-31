package com.github.theresajayne.rainyday.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;

public class Toddler {
    private static final float COLLISION_RADIUS = 24f;
    private static final float DIVE_ACCEL = 0.10f;
    private static final float FLY_ACCEL = 2f;
    private final float WORLD_HEIGHT;
    private final Circle collisionCircle;
    private Array<Texture> frames = new Array<Texture>();
    private Animation<Texture> animation;

    private float x = 0;
    private float y = 0;
    private float ySpeed =0;
    private boolean jumped = false;
    private boolean jumping = false;
    private float elapsedTime = 0;

    public Toddler(float WORLD_HEIGHT)
    {
        this.WORLD_HEIGHT = WORLD_HEIGHT;
        collisionCircle = new Circle(getX(), getY(),COLLISION_RADIUS);
        for(int a = 1;a<=16;a++)
        {
            frames.add(new Texture(Gdx.files.internal("run_"+a+".png")));
            System.out.println("adding run_"+a+".png");
        }
        animation = new Animation<Texture>(1/16f,frames);
        System.out.println("We have "+animation.getAnimationDuration());
    }

    public void drawDebug(ShapeRenderer shapeRenderer)
    {
        //shapeRenderer.circle(collisionCircle.x,collisionCircle.y,collisionCircle.radius);
    }

    public void draw(SpriteBatch batch)
    {

        //batch.draw(girl,collisionCircle.x-24,collisionCircle.y-24);
        elapsedTime  += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime,true),collisionCircle.x-24,collisionCircle.y-24,48f,64f);

        //batch.draw(girl,collisionCircle.x,collisionCircle.y,0f,0f,48f,48f,0.5f,0.5f,0f);
    }


    public void setPosition(float x, float y) {
        this.setX(x);
        this.setY(y);
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(getX());
        collisionCircle.setY(getY());
    }

    public void update()
    {
        ySpeed -= DIVE_ACCEL;
        setPosition(getX(), getY() + ySpeed);
    }

    public void jump()
    {
        if(!jumped) {
            ySpeed = FLY_ACCEL;
            setPosition(getX(), getY() +ySpeed);
            setJumping(true);
            if(getY()>= WORLD_HEIGHT/3) jumped = true;
        }
        if(getY()< COLLISION_RADIUS+0.1)
        {
            jumped = false;
        }
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

    public float getRadius()
    {
        return COLLISION_RADIUS;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }
}
