package com.github.theresajayne.rainyday;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.theresajayne.rainyday.entities.Text;

public class EndScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();

    private Stage stage;
    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture titleTexture;
    private int score;
    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private final Game game;

    public EndScreen(Game game,int score)
    {
        this.score = score;
        this.game = game;
    }

    public void show() {
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("end.jpg"));
        Image background = new Image(backgroundTexture);
        playTexture = new Texture(Gdx.files.internal("restart.png"));
        playPressTexture = new Texture(Gdx.files.internal("restart.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(WORLD_WIDTH/2,WORLD_HEIGHT/4,4);
        play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event,x,y,count,button);
                game.setScreen(new StartScreen(game));
            }
        });


        stage.addActor(background);
        stage.addActor(play);
        System.out.println("Score in endscreen = "+score);
        stage.addActor(new Text(score));
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }


}
