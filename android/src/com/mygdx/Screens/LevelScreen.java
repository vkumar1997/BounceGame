package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.Score;
import com.mygdx.game.BounceGame;

/**
 * Created by Vikas on 5/29/2016.
 */
public class LevelScreen implements Screen, InputProcessor{
    BounceGame game;
    SpriteBatch batch;
    BitmapFont bitmapFont,bitmapFont2;
    Score score;
    Sprite level[],next,previous;
    int l=1;


    public LevelScreen(BounceGame game) {
        this.game = game;
        batch = new SpriteBatch();
        score=new Score();

        level=new Sprite[10];
        level[0]=new Sprite(new Texture(Gdx.files.internal("level1.PNG")));
        level[1]=new Sprite(new Texture(Gdx.files.internal("level2.PNG")));
        level[2]=new Sprite(new Texture(Gdx.files.internal("level3.PNG")));
        level[3]=new Sprite(new Texture(Gdx.files.internal("level4.PNG")));
        level[4]=new Sprite(new Texture(Gdx.files.internal("level5.PNG")));
        level[5]=new Sprite(new Texture(Gdx.files.internal("level6.PNG")));
        level[6]=new Sprite(new Texture(Gdx.files.internal("level7.PNG")));
        level[7]=new Sprite(new Texture(Gdx.files.internal("level8.PNG")));
        level[8]=new Sprite(new Texture(Gdx.files.internal("level9.PNG")));
        level[9]=new Sprite(new Texture(Gdx.files.internal("level10.PNG")));

        for(int i=0;i<10;i++) {
            level[i].setSize(Gdx.graphics.getWidth() / (float)1.8, Gdx.graphics.getHeight() /2);
            level[i].setPosition(Gdx.graphics.getWidth() / 2 - level[i].getWidth() / 2, 150);
        }
        next=new Sprite(new Texture(Gdx.files.internal("next.PNG")));
        next.setSize(250, 80);
        next.setPosition(Gdx.graphics.getWidth()-next.getWidth()-50,50);
        previous=new Sprite(new Texture(Gdx.files.internal("previous.PNG")));
        previous.setPosition(50,50);
        previous.setSize(250,80);
        bitmapFont=new BitmapFont();
        bitmapFont2=new BitmapFont();



        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("xoxoxa.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=(int)((float)0.039*(float)Gdx.graphics.getWidth());
        parameter.color= Color.LIGHT_GRAY;
        parameter.borderWidth=2;
        parameter.borderColor=Color.FOREST;
        bitmapFont=generator.generateFont(parameter);
        parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color=Color.MAROON;
        parameter.size=22;
        parameter.borderWidth=2;
        bitmapFont2=generator.generateFont(parameter);
        generator.dispose();

        Gdx.input.setInputProcessor(this);
    }

    public void render(float delta) {
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bitmapFont.draw(batch, "High Score = " + score.get_high_score(), 40, Gdx.graphics.getHeight() - 40);


        level[l-1].draw(batch);
        next.draw(batch);
        previous.draw(batch);
        batch.end();

    }
    public void resize ( int width, int height){
        }
    public void show() {
    }
    public void hide() {
    }
    public void pause() {
    }
    public void resume() {
    }
    public void dispose() {
        batch.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY=Gdx.graphics.getHeight()-screenY;
        if(screenX<(next.getX()+next.getWidth()) && screenX>next.getX() && screenY<(next.getY()+next.getHeight()) && screenY >next.getY() && l<=9)
            l=l+1;
        if(screenX<(previous.getX()+previous.getWidth()) && screenX>previous.getX() && screenY<(previous.getY()+previous.getHeight()) && screenY >previous.getY() && l>=2)
            l=l-1;
        if(screenX<(level[0].getX()+level[0].getWidth()) && screenX>level[0].getX() && screenY<(level[0].getY()+level[0].getHeight()) && screenY >level[0].getY() )
        {
            switch(l)
            {
                case 1: game.setScreen(new PlayScreen(game,1));
                        break;
                case 2: game.setScreen(new PlayScreen1(game,2));
                        break;
                case 3: game.setScreen(new PlayScreen(game,3));
                        break;
                case 4: game.setScreen(new PlayScreen(game,4));
                        break;
                case 5: game.setScreen(new PlayScreen1(game,5));
                        break;
                case 6: game.setScreen(new PlayScreen4(game,6));
                        break;
                case 7: game.setScreen(new PlayScreen2(game,7));
                        break;
                case 8: game.setScreen(new PlayScreen3(game,8));
                        break;
                case 9: game.setScreen(new PlayScreen5(game,9));
                        break;
                case 10:game.setScreen(new PlayScreen6(game,10));
                        break;
            }
        }
        return true;
    }
    public boolean keyDown(int keycode) {return false;}public boolean keyUp(int keycode) {return false;}public boolean keyTyped(char character) {return false;}public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}public boolean mouseMoved(int screenX, int screenY) {return false;}public boolean scrolled(int amount) {return false;}

}