package com.github.theresajayne.rainyday.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {
    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();
    BitmapFont bitmapFont;
    GlyphLayout glyphLayout;
    int myScore;      //I assumed you have some object
    //that you use to access score.
    //Remember to pass this in!
    public Text(int myScore){
        super();
        this.myScore = myScore;
        bitmapFont = new BitmapFont(Gdx.files.internal("Forte.fnt"));
        bitmapFont.setColor(Color.BLUE);
        glyphLayout = new GlyphLayout();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        glyphLayout.setText(bitmapFont,"Final Score: " +myScore );
        bitmapFont.draw(batch, "Final Score: " +myScore , (WORLD_WIDTH/2) - (glyphLayout.width/2), (4 * WORLD_HEIGHT / 5) - glyphLayout.height / 2);
        //Also remember that an actor uses local coordinates for drawing within
        //itself!
    }
}
