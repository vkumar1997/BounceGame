package com.mygdx.Screens;

/**
 * Created by Vikas on 5/21/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.Score;
import com.mygdx.game.AndroidLauncher;
import com.mygdx.game.BounceGame;


public class MainScreen implements Screen, InputProcessor {
    BounceGame game;
    SpriteBatch batch;
    Sprite background,playbutton,levelbutton,mario;
    TextureAtlas textureAtlas;
    Animation animation;
    float elapsedTime;



    public MainScreen(BounceGame game) {
        this.game = game;
        batch=new SpriteBatch();
        background=new Sprite(new Texture(Gdx.files.internal("blueback2.PNG")));
        background.setPosition(0,0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playbutton=new Sprite(new Texture(Gdx.files.internal("play_button.PNG")));
        playbutton.setPosition(Gdx.graphics.getWidth() / 2 - playbutton.getWidth() - 100, 100);
        levelbutton=new Sprite(new Texture(Gdx.files.internal("levels.PNG")));
        levelbutton.setPosition(Gdx.graphics.getWidth()/2+100,100);


        mario= new Sprite(new Texture(Gdx.files.internal("main_menu.PNG")));
        mario.setPosition(playbutton.getX()+150,Gdx.graphics.getHeight()-mario.getHeight()-150);

        textureAtlas=new TextureAtlas(Gdx.files.internal("exp.atlas"));
        animation=new Animation(1/5f,textureAtlas.getRegions());

        Gdx.input.setInputProcessor(this);
    }

    public void render(float delta) {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        Score.total_score=0;
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background.draw(batch);
        playbutton.draw(batch);
        levelbutton.draw(batch);
        mario.draw(batch);
        batch.draw(animation.getKeyFrame(elapsedTime,true),mario.getX()+mario.getWidth()-20,(2*mario.getY()+mario.getHeight())/2-50,150,150);
        batch.end();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY=Gdx.graphics.getHeight()-screenY;
        if (screenX<(playbutton.getX()+playbutton.getWidth()) && screenX>playbutton.getX() && screenY<(playbutton.getY()+playbutton.getHeight()) && screenY >playbutton.getY())
        {

            game.setScreen(new PlayScreen(game, 1));
        }
        if (screenX<(levelbutton.getX()+levelbutton.getWidth()) && screenX>levelbutton.getX() && screenY<(levelbutton.getY()+levelbutton.getHeight()) && screenY >levelbutton.getY())
        {

            game.setScreen(new LevelScreen(game));
        }
        return true;
    }
    public boolean keyDown(int keycode) {return false;}public boolean keyUp(int keycode) {return false;}public boolean keyTyped(char character) {return false;}public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}public boolean mouseMoved(int screenX, int screenY) {return false;}public boolean scrolled(int amount) {return false;}


    public void resize(int width,int height){}
    public void show() {}
    public void hide() {}
    public void pause() {}
    public void resume() {}

    public void dispose() {
        batch.dispose();
    }
}
