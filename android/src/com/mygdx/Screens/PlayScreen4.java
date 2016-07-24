package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.Score;
import com.mygdx.Wheel1;
import com.mygdx.Wheel2;
import com.mygdx.Wheel3;
import com.mygdx.Wheel4;
import com.mygdx.game.BounceGame;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Vikas on 5/16/2016.
 */

public class PlayScreen4 implements Screen, InputProcessor {
    BounceGame game;
    Score score=new Score();
    SpriteBatch batch;
    Sound sound;
    Rectangle mario, fireboundary;
    TextureAtlas textureAtlas1,textureAtlas2,textureAtlas3, textureAtlas4;
    Animation animation1, animation2, animation3,animation4,animation5;
    BitmapFont bitmapFont,bitmapFont2;
    float x=100,deviceAngle,elapsedTime=0,explosion_time,explosion_x, explosion_y;
    boolean touchDown=false,collide=false,start=false,b=false;
    ArrayList<Wheel1> w1=new ArrayList<>();
    ArrayList<Wheel2> w2=new ArrayList<>();
    ArrayList<Wheel3> w3=new ArrayList<>();
    Sprite sprite, firesprite, frameSprite, foreback;
    int no_of_fires=0;
    ArrayList<Float> f=new ArrayList();
    int game_time=70,frames=0, game_level, NO_OF_FIRES=33;

    public PlayScreen4(BounceGame game, int game_level)
    {
        this.game=game;
        this.game_level=game_level;
        frameSprite=new Sprite(new Texture(Gdx.files.internal("frame1.PNG")));
        frameSprite.setPosition(0, 0);
        frameSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch=new SpriteBatch();
        bitmapFont=new BitmapFont();
        bitmapFont2=new BitmapFont();

        if(game_level==6) {
            w1.add(new Wheel1((float) 0.23 * Gdx.graphics.getWidth(),(float) 0.486 * Gdx.graphics.getHeight()));
            w1.get(0).velocity.y=(float) 0.4589 * Gdx.graphics.getHeight()/(float)1.1;
            w1.get(0).acceleration.y=-w1.get(0).acceleration.y;
            w1.add(new Wheel1((float) 0.46 * Gdx.graphics.getWidth(),(float) 0.486 * Gdx.graphics.getHeight()));
            w1.add(new Wheel1((float) 0.23 * Gdx.graphics.getWidth(),(float) 0.486 * Gdx.graphics.getHeight()));
            w1.get(1).velocity.y=(float) 0.4589 * Gdx.graphics.getHeight()/(float)1.1;
            w1.get(1).acceleration.y=-w1.get(1).acceleration.y;
            w1.get(1).velocity.x=-w1.get(1).velocity.x;
        }


        textureAtlas1=new TextureAtlas(Gdx.files.internal("mario.atlas"));
        textureAtlas2=new TextureAtlas(Gdx.files.internal("firee.atlas"));
        textureAtlas3=new TextureAtlas(Gdx.files.internal("explosion.atlas"));
        textureAtlas4=new TextureAtlas(Gdx.files.internal("buttons.atlas"));

        foreback=new Sprite(new Texture(Gdx.files.internal("blueback2.PNG")));
        foreback.setPosition(Gdx.graphics.getWidth() / 4-50, Gdx.graphics.getHeight() / 4-25);
        foreback.setSize(Gdx.graphics.getWidth() / 2+50, Gdx.graphics.getHeight() / 2+25);
        sound=Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));

        sprite=new Sprite(textureAtlas1.findRegion("mb1"));
        sprite.setPosition(x, 25);
        sprite.setSize((float) 0.03734 * Gdx.graphics.getWidth(), (float) 0.107 * Gdx.graphics.getHeight());
        mario=new Rectangle(x + 8, 25, (float) 0.023 * Gdx.graphics.getWidth(), (float) 0.093 * Gdx.graphics.getHeight());
        firesprite=new Sprite(textureAtlas2.findRegion("f1"));
        firesprite.setPosition(-50, -50);
        fireboundary=new Rectangle(-50,-50,18,50);

        animation1=new Animation(1/7f,textureAtlas1.findRegion("ma1"),textureAtlas1.findRegion("ma2"),textureAtlas1.findRegion("ma3"),textureAtlas1.findRegion("ma4"),textureAtlas1.findRegion("ma5"));
        animation2=new Animation(1/7f,textureAtlas1.findRegion("ma6"),textureAtlas1.findRegion("ma7"),textureAtlas1.findRegion("ma8"),textureAtlas1.findRegion("ma9"),textureAtlas1.findRegion("mb"));
        animation3=new Animation(1/6f,textureAtlas1.findRegion("ma"),textureAtlas1.findRegion("ma0"));
        animation4=new Animation(1/7f,textureAtlas2.getRegions());
        animation5=new Animation(1/30f,textureAtlas3.getRegions());


        FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("xoxoxa.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=(int)((float)0.039*(float)Gdx.graphics.getWidth());
        parameter.color= Color.LIGHT_GRAY;
        parameter.borderWidth=2;
        parameter.borderColor=Color.FOREST;
        bitmapFont=generator.generateFont(parameter);
        parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color=Color.ROYAL;
        parameter.size=22;
        parameter.borderWidth=2;
        bitmapFont2=generator.generateFont(parameter);
        generator.dispose();

        Gdx.input.setInputProcessor(this);
        start=false;
        collide=false;
        touchDown=false;
    }

    public void render(float delta) {
        frames=frames+1;
        if (frames>=60 && !collide && start && (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() ))
        {
            frames=0;
            game_time=game_time-1;
        }
        if(game_time==0 && !collide)
        {
            collide=true;
            frames=0;
        }
        getWheelPosition();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deviceAngle = Gdx.input.getPitch();
        batch.begin();
        frameSprite.draw(batch);

        bitmapFont.draw(batch, "Score~ " + score.getScore(), 50, Gdx.graphics.getHeight() - 50);
        bitmapFont.draw(batch,"Time~ "+game_time,Gdx.graphics.getWidth()/2-(int)(0.1*Gdx.graphics.getWidth()),Gdx.graphics.getHeight()-50);
        bitmapFont.draw(batch,"FireBalls~ "+(NO_OF_FIRES-no_of_fires),Gdx.graphics.getWidth()-(int)(0.2*Gdx.graphics.getWidth())-50,Gdx.graphics.getHeight()-50);

        drawMarioPosition();
        drawWheelPosition();
        drawFirePosition();

        if(!collide && start)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
        }

        if (no_of_fires==NO_OF_FIRES && (!w1.isEmpty() || !w2.isEmpty() ||  !w3.isEmpty() ) && f.isEmpty() && !collide)
        {
            collide=true;
            frames=0;
        }

        if (collide)
        {
            if(no_of_fires==NO_OF_FIRES)
            {
                batch.draw(textureAtlas1.findRegion("mb3"), x, 25, 150, 150);
                drawWheelPosition();
                foreback.draw(batch);
                bitmapFont.draw(batch, "Out of fire!!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);

            }
            else if(game_time==0){
                batch.draw(textureAtlas1.findRegion("mb3"), x, 25, 150, 150);
                drawWheelPosition();
                foreback.draw(batch);
                bitmapFont.draw(batch, "Time Out!!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);
            }
            else
            {
                batch.draw(textureAtlas1.findRegion("mb4"), x, 25, 150, 95);
                drawWheelPosition();
                foreback.draw(batch);
                bitmapFont.draw(batch, "You Died!!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);
            }
            bitmapFont.draw(batch,"Score~ "+score.getScore(),foreback.getX()+40,(2*foreback.getY()+foreback.getHeight())/2+50);
            if (frames>0 && frames<20) {
                score.total_score = score.getTotalScore() + score.getScore();
                frames=20;
                b=score.check_total_score();
                if (b)
                    score.update_high_score();
            }
            bitmapFont.draw(batch,"Total Score~ "+score.getTotalScore(),foreback.getX()+40,(2*foreback.getY()+foreback.getHeight())/2-10);
            if(b)
                bitmapFont2.draw(batch,"New Highscore",(2*foreback.getX()+foreback.getWidth())/2+100,(2*foreback.getY()+foreback.getHeight())/2-30);
            batch.draw(textureAtlas4.findRegion("retry"), foreback.getX() + 50, foreback.getY() + 20, 150, 50);
            batch.draw(textureAtlas4.findRegion("exit"),(2*foreback.getX()+foreback.getWidth())/2+50,foreback.getY()+20,150,50);
        }

        if(!start)
        {
            foreback.draw(batch);
            if (game_level==6) {
                bitmapFont.draw(batch, "Level 6", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);
                bitmapFont.draw(batch, "Reverse Psychology!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth() - 50, foreback.getY() + foreback.getHeight() - 90);
                bitmapFont.draw(batch, "Touch to begin!!", foreback.getX() + 50, foreback.getY() + 130);
                bitmapFont2.draw(batch, "Some wheels show reverse motion under gravity\nYour controls are modified", foreback.getX() + 50, foreback.getY() + 50);
            }
        }


        if (w1.isEmpty() && w2.isEmpty() && w3.isEmpty())
        {
            batch.draw(textureAtlas1.findRegion("mb2"),x, 25,150,150);
            foreback.draw(batch);
            bitmapFont.draw(batch, "Level Complete!!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);
            bitmapFont.draw(batch,"Score~ "+score.getScore(),foreback.getX()+40,(2*foreback.getY()+foreback.getHeight())/2+50);
            if (frames>0 && frames<20) {
                Score.total_score = score.getTotalScore() + score.getScore();
                frames=20;
                b=score.check_total_score();
                if (b)
                    score.update_high_score();}
            bitmapFont.draw(batch,"Total Score~ "+score.getTotalScore(),foreback.getX()+40,(2*foreback.getY()+foreback.getHeight())/2-10);
            if(b)
                bitmapFont2.draw(batch,"New Highscore",(2*foreback.getX()+foreback.getWidth())/2+100,(2*foreback.getY()+foreback.getHeight())/2-30);
            batch.draw(textureAtlas4.findRegion("nextlevel"),foreback.getX()+50,foreback.getY() + 20, 150,50);
            batch.draw(textureAtlas4.findRegion("exit"),(2*foreback.getX()+foreback.getWidth())/2+50,foreback.getY()+20,150,50);

        }

        explosion_time=explosion_time+Gdx.graphics.getDeltaTime();
        batch.draw(animation5.getKeyFrame(explosion_time,false),explosion_x,explosion_y);

        batch.end();

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY=Gdx.graphics.getHeight()-screenY;
        if(no_of_fires!=NO_OF_FIRES && start && !collide &&  (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() )) {
            no_of_fires = no_of_fires + 1;
            elapsedTime = 0;
            f.add(x);
            touchDown = true;
            f.add((float) 61);
        }
        if(!start)
        {
            start=true;
        }

        if (collide)
        {
            if(screenX>=foreback.getX()+50&&screenY>=foreback.getY()+20 && screenX<=(foreback.getX()+200)&&screenY<=(foreback.getY()+70))
            {
                Score.total_score=0;
                game.setScreen(new PlayScreen4(game, game_level));
            }
            else if(screenX>=((2*foreback.getX()+foreback.getWidth())/2+50) && screenY>=(foreback.getY()+20)&& screenX<=((2*foreback.getX()+foreback.getWidth())/2+200)&&screenY<=(foreback.getY()+70))
            {
                Score.total_score=0;
                game.setScreen(new MainScreen(game));
            }
        }

        if (w1.isEmpty() && w2.isEmpty() && w3.isEmpty())
        {
            if(screenX>=foreback.getX()+50&&screenY>=foreback.getY()+20 && screenX<=(foreback.getX()+200)&&screenY<=(foreback.getY()+70))
            {
                game.setScreen(new PlayScreen3(game,game_level+1));
            }
            else if(screenX>=(2*foreback.getX()+foreback.getWidth())/2+50 && screenY>=(foreback.getY()+20)&& screenX<=(2*foreback.getX()+foreback.getWidth())/2+200&&screenY<=(foreback.getY()+70))
            {
                Score.total_score=0;
                game.setScreen(new MainScreen(game));
            }
        }
        return true;
    }
    public boolean keyDown(int keycode) {return false;}public boolean keyUp(int keycode) {return false;}public boolean keyTyped(char character) {return false;}public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}public boolean mouseMoved(int screenX, int screenY) {return false;}public boolean scrolled(int amount) {return false;}
    public void resize(int width, int height){}
    public void show() {}
    public void hide() {}
    public void pause() {}
    public void resume() {}

    public void dispose() {
        batch.dispose();
        textureAtlas1.dispose();
        textureAtlas2.dispose();
        textureAtlas3.dispose();
    }


    public void getWheelPosition()
    {
        if (!collide && start && !w1.isEmpty()) {
            for (Wheel1 wheel : w1) {
                wheel.spriteCollisionDetection(frameSprite);
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();

                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 103, wheel.sprite.getY() + 103);

            }
        }

        if (!collide && start && !w2.isEmpty()) {
            for (Wheel2 wheel : w2) {
                wheel.spriteCollisionDetection(frameSprite);
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();

                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 48, wheel.sprite.getY() + 48);
            }
        }

        if (!collide && start && !w3.isEmpty()) {
            for (Wheel3 wheel : w3) {
                wheel.spriteCollisionDetection(frameSprite);
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();

                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 25, wheel.sprite.getY() + 25);
            }
        }
    }

    public void drawMarioPosition()
    {
        if (touchDown && !collide && start && (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty())) {
            sprite.setRegion(animation3.getKeyFrame(elapsedTime, true));
            sprite.setPosition(x, 25);
            sprite.draw(batch);
            mario.setPosition(x+8,25);
            if(animation3.isAnimationFinished(elapsedTime))
                touchDown=false;
        }

        else if (!collide &&  (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() )) {
            if ((deviceAngle <= 10 && deviceAngle >= -10) || x >= Gdx.graphics.getWidth() -sprite.getWidth()-33 || x <= 33 || !start) {
                sprite.setRegion(textureAtlas1.findRegion("mb1"));
                sprite.setPosition(x,25);
                sprite.draw(batch);
                mario.setPosition(x+8,25);
            }

            if (deviceAngle <-10 && x>= 33 && start) {
                x = x + (int) (0.35 * (deviceAngle));
                sprite.setRegion(animation2.getKeyFrame(elapsedTime, true));
                sprite.setPosition(x, 25);
                sprite.draw(batch);
                mario.setPosition(x + 8, 25);
            }

            if (deviceAngle >10 && x  <= Gdx.graphics.getWidth()-sprite.getWidth()-33  && start) {
                x = x + (int) (0.35 * (deviceAngle));
                sprite.setRegion(animation1.getKeyFrame(elapsedTime,true));
                sprite.setPosition(x,25);
                sprite.draw(batch);
                mario.setPosition(x+8,25);
            }

        }
    }

    public void drawWheelPosition()
    {
        if(!w1.isEmpty()) {
            for (Wheel1 wheel : w1) {
                wheel.sprite.draw(batch);
                wheel.sprite.rotate(2.7f);
                if (!collide)
                {
                    collide = wheel.manCollisionDetection(mario);
                    if(collide)
                        frames=0;
                }
            }
        }
        if(!w2.isEmpty()) {
            for (Wheel2 wheel : w2) {
                wheel.sprite.draw(batch);
                wheel.sprite.rotate(2.7f);
                if (!collide)
                {
                    collide = wheel.manCollisionDetection(mario);
                    if(collide)
                        frames=0;
                }
            }
        }
        if(!w3.isEmpty()) {
            for (Wheel3 wheel : w3) {
                wheel.sprite.draw(batch);
                wheel.sprite.rotate(2.7f);
                if (!collide)
                {
                    collide = wheel.manCollisionDetection(mario);
                    if(collide)
                        frames=0;
                }
            }
        }
    }

    public void drawFirePosition()
    {
        if (f.isEmpty())
        {
            fireboundary=new Rectangle(-50,-50,18,50);
        }
        if(!f.isEmpty() && !collide && start && elapsedTime>=animation3.getFrameDuration()/(float)(1.5)) {

            for(ListIterator<Float> iterator=f.listIterator(); iterator.hasNext();) {
                float fx=iterator.next();
                float fy=iterator.next();
                firesprite.setRegion(animation4.getKeyFrame(elapsedTime, true));
                firesprite.setPosition(fx, fy);
                firesprite.draw(batch);
                iterator.previous();
                iterator.set(fy + 7f);
                iterator.previous();
                fireboundary=new Rectangle(fx+22,fy+60,18, 50);
                boolean collision=fireCollision();
                if (w1.isEmpty() && w2.isEmpty()&& w3.isEmpty() && collision)
                    frames=0;
                iterator.next();
                if (firesprite.getY() + firesprite.getHeight() > Gdx.graphics.getHeight() - 27) {
                    score.miss_fire();
                    collision=true;
                }
                if (collision) {
                    sound.play();
                    explosion_time=0;
                    explosion_x=fx+20;
                    explosion_y=fy+65;
                    iterator.remove();
                    iterator.next();
                    iterator.remove();
                }
                else
                    iterator.next();
            }
        }
    }

    public boolean fireCollision()
    {
        if(!w1.isEmpty()) {
            for (ListIterator<Wheel1> iterator=w1.listIterator(); iterator.hasNext();) {
                Wheel1 wheel=iterator.next();
                if(Intersector.overlaps(wheel.boundary,fireboundary))
                {
                    score.w1_hit(game_time);
                    w2.add(new Wheel2(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w2.get(w2.size()-1).velocity.x=-w2.get(w2.size()-1).velocity.x;
                    w2.get(w2.size()-1).velocity.y=-Math.abs(wheel.velocity.y);
                    w2.get(w2.size()-1).acceleration.y=-w2.get(w2.size()-1).acceleration.y;
                    w2.add(new Wheel2(wheel.sprite.getX()+wheel.sprite.getWidth()/2,wheel.sprite.getY()+wheel.sprite.getHeight()/2));
                    w2.get(w2.size()-1).velocity.y=Math.abs(wheel.velocity.y);

                    iterator.remove();
                    return true;
                }
            }
        }

        if(!w2.isEmpty()) {
            for (ListIterator<Wheel2> iterator=w2.listIterator(); iterator.hasNext();) {
                Wheel2 wheel=iterator.next();
                if(Intersector.overlaps(wheel.boundary,fireboundary))
                {
                    score.w2_hit(game_time);
                    w3.add(new Wheel3(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w3.get(w3.size()-1).velocity.x=-w3.get(w3.size()-1).velocity.x;
                    w3.get(w3.size()-1).velocity.y=-Math.abs(wheel.velocity.y);
                    w3.get(w3.size()-1).acceleration.y=-w3.get(w3.size()-1).acceleration.y;
                    w3.add(new Wheel3(wheel.sprite.getX()+wheel.sprite.getWidth()/2,wheel.sprite.getY()+wheel.sprite.getHeight()/2));
                    w3.get(w3.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    iterator.remove();
                    return true;
                }
            }
        }

        if(!w3.isEmpty()) {
            for (ListIterator<Wheel3> iterator=w3.listIterator(); iterator.hasNext();) {
                Wheel3 wheel=iterator.next();
                if(Intersector.overlaps(wheel.boundary,fireboundary))
                {
                    score.w3_hit(game_time);
                    iterator.remove();
                    return true;
                }
            }
        }

        return false;
    }

}
