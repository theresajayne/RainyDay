package com.github.theresajayne.rainyday.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.theresajayne.rainyday.EndScreen;

import java.util.Random;


public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();
    private static final float GAP_BETWEEN_PUDDLES = 200F;
    private static final float DRENCH_AMOUNT = 100;

    private Texture background;
    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Array<Puddle> puddles = new Array<Puddle>();
    private Toddler toddler = new Toddler(WORLD_HEIGHT);
    private Drenching drench = new Drenching();
    private Random random = new Random();
    private float drenched = 0;
    private int score = 0;
    private Music splash = Gdx.audio.newMusic(Gdx.files.internal("sfx_step_water_l.ogg"));
    private Sound rain = Gdx.audio.newSound(Gdx.files.internal("1.ogg"));
    private final Game game;

    public GameScreen(Game game)
    {
        this.game = game;
    }

    private void updateScore()
    {
        Puddle puddle = puddles.first();
        if(puddle.getX()<toddler.getX() && !puddle.isPointClaimed()) {
            puddle.markPointClaimed();
            score++;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        toddler.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        bitmapFont = new BitmapFont(Gdx.files.internal("Forte.fnt"));
        bitmapFont.setColor(Color.YELLOW);
        glyphLayout = new GlyphLayout();
        background = new Texture(Gdx.files.internal("bg.png"));
        rain.loop(0.3f);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background,0,0);
        drawPuddles();
        drawScore();
        drawPlayer();
        drawDrench();
        batch.end();
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        shapeRenderer.end();
        update(delta);
    }

    private void update(float delta) {
        toddler.update();
        updatePuddles(delta);
        updateScore();
        if(checkForCollision())
        {
            //add to drenching
            if(!splash.isPlaying())
            splash.play();
            addDrenching(delta*DRENCH_AMOUNT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) toddler.jump();
        else if(toddler.isJumping())toddler.setJumped(true);
        blockToddlerLeavingTheWorld();
        drench.setDrenching(drenched);
    }

    private void addDrenching(float amount) {
        System.out.println("Being Drenched "+amount);
        drenched += amount;
        if(drenched >= 100) endGame();
    }

    private void endGame()
    {
        System.out.println("Final Score ="+score);
        game.setScreen(new EndScreen(game,score));
        System.out.println("You got too wet, your mommy told you off and you were grounded");
    }

    private boolean checkForCollision()
    {
        for(Puddle puddle:puddles)
        {
            if(puddle.isToddlerColliding(toddler))
            {
                return true;
            }
        }
        return false;
    }

    private void updatePuddles(float delta)
    {
        for(Puddle puddle:puddles)
        {
            puddle.update(delta);
        }
        checkIfNewPuddleIsNeeded();
        removePuddlesIfPassed();
    }

    private void drawDebug()
    {
        toddler.drawDebug(shapeRenderer);
        for(Puddle puddle:puddles)
        {
            puddle.drawDebug(shapeRenderer);
        }
    }

    private void drawPuddles()
    {
        for(Puddle puddle:puddles)
        {
            puddle.draw(batch);
        }
    }

    private void drawPlayer()
    {
        toddler.draw(batch);
    }

    private void drawDrench()
    {
        drench.draw(batch);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void blockToddlerLeavingTheWorld() {
        toddler.setPosition(toddler.getX(), MathUtils.clamp(toddler.getY(), 0 + toddler.getRadius(), WORLD_HEIGHT / 3));
    }

    private void createNewPuddle()
    {
        float width = random.nextFloat()*200.0f;
        Puddle puddle = new Puddle(width);
        puddle.setPosition(WORLD_WIDTH+width);
        puddles.add(puddle);
    }

    private void checkIfNewPuddleIsNeeded()
    {
        if(puddles.size ==0)
        {
            createNewPuddle();
        } else
        {
            Puddle puddle = puddles.peek();
            if(puddle.getX() < WORLD_WIDTH - GAP_BETWEEN_PUDDLES)
            {
                createNewPuddle();
            }
        }
    }

    private void removePuddlesIfPassed()
    {
        if(puddles.size>0)
        {
            Puddle firstPuddle = puddles.first();
            if(firstPuddle.getX()< -firstPuddle.getWidth())
            {
                puddles.removeValue(firstPuddle,true);
            }
        }
    }

    private void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(batch, scoreAsString,(viewport.getWorldWidth()/2) - (glyphLayout.width / 2), (4 * viewport.getWorldHeight() / 5) - glyphLayout.height / 2);
    }
}
