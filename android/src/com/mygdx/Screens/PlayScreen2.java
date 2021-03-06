package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
 * Created by Vikas on 5/24/2016.
 */
public class PlayScreen2 implements Screen, InputProcessor{
    BounceGame game;
    SpriteBatch batch;
    BitmapFont bitmapFont,bitmapFont2;
    Sprite frameSprite, sprite, foreback, firesprite, bordersprite1, bordersprite2;
    Rectangle mario, fireboundary;
    TextureAtlas textureAtlas1, textureAtlas2,textureAtlas3,textureAtlas4;
    float x=100,deviceAngle,elapsedTime,explosion_time,explosion_x, explosion_y;
    boolean touchDown=false,collide=false,start=false, translate=false,b=false;
    Animation animation1,animation2, animation3,animation4,animation5;
    ArrayList<Wheel1> w1=new ArrayList<>();
    ArrayList<Wheel2> w2=new ArrayList<>();
    ArrayList<Wheel3> w3=new ArrayList<>();
    ArrayList<Wheel4> w4=new ArrayList<>();
    Sound sound;
    Score score=new Score();
    int game_time=90,frames=0,no_of_fires=0, game_level,NO_OF_FIRES=45;
    ArrayList<Float> f=new ArrayList();


    public PlayScreen2 (BounceGame game,int game_level)
    {
        this.game=game;
        this.game_level=game_level;
        batch=new SpriteBatch();
        bitmapFont=new BitmapFont();
        bitmapFont2=new BitmapFont();

        frameSprite=new Sprite(new Texture(Gdx.files.internal("frame3.PNG")));
        frameSprite.setPosition(0, 0);
        frameSprite.setSize(2 * Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bordersprite1=new Sprite(new Texture(Gdx.files.internal("border.PNG")));
        bordersprite2=new Sprite(new Texture(Gdx.files.internal("border.PNG")));
        bordersprite1.setSize(20,Gdx.graphics.getHeight()-40);
        bordersprite2.setSize(20,Gdx.graphics.getHeight()-40);

        if (game_level==7)
        {
            w1.add(new Wheel1((float) 0.23 * frameSprite.getWidth(), (float) 0.486 * Gdx.graphics.getHeight()));
            w1.get(0).setFrame(1);
            w4.add(new Wheel4((float) 0.5 * frameSprite.getWidth(), (float) 0.486 * Gdx.graphics.getHeight()));
            w4.get(0).setFrame(2);
            w2.add(new Wheel2((float) 0.8 * frameSprite.getWidth(), (float) 0.486 * Gdx.graphics.getHeight()));
            w2.get(0).setFrame(3);
            w3.add(new Wheel3((float) 0.8 * frameSprite.getWidth(), (float) 0.486 * Gdx.graphics.getHeight()));
            w3.get(0).setFrame(3);
        }

        textureAtlas1=new TextureAtlas(Gdx.files.internal("mario.atlas"));
        textureAtlas2=new TextureAtlas(Gdx.files.internal("firee.atlas"));
        textureAtlas3=new TextureAtlas(Gdx.files.internal("explosion.atlas"));
        textureAtlas4=new TextureAtlas(Gdx.files.internal("buttons.atlas"));

        foreback=new Sprite(new Texture(Gdx.files.internal("blueback2.PNG")));
        foreback.setPosition(Gdx.graphics.getWidth() / 4 - 50, Gdx.graphics.getHeight() / 4 - 25);
        foreback.setSize(Gdx.graphics.getWidth() / 2 + 50, Gdx.graphics.getHeight() / 2 + 25);
        sound=Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));

        firesprite=new Sprite(textureAtlas2.findRegion("f1"));
        firesprite.setPosition(-50, -50);
        fireboundary=new Rectangle(-50,-50,18,50);

        sprite=new Sprite(textureAtlas1.findRegion("mb1"));
        sprite.setPosition(x, 25);
        sprite.setSize((float) 0.03734 * Gdx.graphics.getWidth(), (float) 0.107 * Gdx.graphics.getHeight());
        mario=new Rectangle(x + 8, 25, (float) 0.023 * Gdx.graphics.getWidth(), (float) 0.093 * Gdx.graphics.getHeight());

        animation1=new Animation(1/5f,textureAtlas1.findRegion("ma1"),textureAtlas1.findRegion("ma2"),textureAtlas1.findRegion("ma3"),textureAtlas1.findRegion("ma4"),textureAtlas1.findRegion("ma5"));
        animation2=new Animation(1/5f,textureAtlas1.findRegion("ma6"),textureAtlas1.findRegion("ma7"),textureAtlas1.findRegion("ma8"),textureAtlas1.findRegion("ma9"),textureAtlas1.findRegion("mb"));
        animation3=new Animation(1/5f,textureAtlas1.findRegion("ma"),textureAtlas1.findRegion("ma0"));
        animation4=new Animation(1/6f,textureAtlas2.getRegions());
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

    public void render (float delta)
    {
        frames=frames+1;
        if (frames>=60 && !collide && start && (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() || !w4.isEmpty()))
        {
            frames=0;
            game_time=game_time-1;
        }
        if(game_time==0 && !collide)
        {
            collide=true;
            frames=0;
        }
        batch.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deviceAngle = Gdx.input.getPitch();
        frameSprite.draw(batch);
        bitmapFont.draw(batch, "Score~ " + score.getScore(), 50, Gdx.graphics.getHeight() - 50);
        bitmapFont.draw(batch,"Time~ "+game_time,Gdx.graphics.getWidth()/2-(int)(0.1*Gdx.graphics.getWidth()),Gdx.graphics.getHeight()-50);
        bitmapFont.draw(batch, "FireBalls~ " + (NO_OF_FIRES - no_of_fires), Gdx.graphics.getWidth() - (int) (0.2 * Gdx.graphics.getWidth()) - 50, Gdx.graphics.getHeight()-50);

        getWheelPosition();
        drawMarioPosition();
        bordersprite1.setPosition(frameSprite.getX() + frameSprite.getWidth() / 3, frameSprite.getY() + 20);
        bordersprite2.setPosition(frameSprite.getX() + frameSprite.getWidth() / 3*2, frameSprite.getY() + 20);
        bordersprite1.draw(batch);
        bordersprite2.draw(batch);
        drawWheelPosition();
        drawFirePosition();


        if(!collide && start)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
        }

        if (no_of_fires==NO_OF_FIRES && (!w1.isEmpty() || !w2.isEmpty() ||  !w3.isEmpty() || !w4.isEmpty()) && f.isEmpty() && !collide)
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
            if(game_level==7) {
                foreback.draw(batch);
                bitmapFont.draw(batch, "Level 7", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth(), foreback.getY() + foreback.getHeight() - 40);
                bitmapFont.draw(batch, "Crazy fire!", (2 * foreback.getX() + foreback.getWidth()) / 2 - (float) 0.1 * Gdx.graphics.getWidth() - 50, foreback.getY() + foreback.getHeight() - 90);
                bitmapFont.draw(batch, "Touch to begin!!", foreback.getX() + 50, foreback.getY() + 130);
                bitmapFont2.draw(batch, "The fire changes its moving pattern\naccording to your position", foreback.getX() + 50, foreback.getY() + 50);
            }
        }


        if (w1.isEmpty() && w2.isEmpty() && w3.isEmpty() && w4.isEmpty())
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


        explosion_time = explosion_time + Gdx.graphics.getDeltaTime();
        batch.draw(animation5.getKeyFrame(explosion_time, false), explosion_x, explosion_y);
        batch.end();

    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY=Gdx.graphics.getHeight()-screenY;
        if(no_of_fires!=NO_OF_FIRES && start && !collide &&  (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() || !w4.isEmpty())) {
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
                game.setScreen(new PlayScreen2(game,game_level));
            }
            else if(screenX>=((2*foreback.getX()+foreback.getWidth())/2+50) && screenY>=(foreback.getY()+20)&& screenX<=((2*foreback.getX()+foreback.getWidth())/2+200)&&screenY<=(foreback.getY()+70))
            {
                Score.total_score=0;
                game.setScreen(new MainScreen(game));
            }
        }

        if (w1.isEmpty() && w2.isEmpty() && w3.isEmpty() && w4.isEmpty())
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
    }

    public void getWheelPosition()
    {
        if (!collide && start && !w1.isEmpty()) {
            for (Wheel1 wheel : w1) {
                if(wheel.getFrame()==1)
                    wheel.spriteCollisionDetection(frameSprite,bordersprite1,1);
                if(wheel.getFrame()==2)
                    wheel.spriteCollisionDetection(bordersprite1,bordersprite2,2);
                if(wheel.getFrame()==3)
                    wheel.spriteCollisionDetection(bordersprite2,frameSprite,3);
                if(translate) {
                    wheel.position.x = wheel.sprite.getX() + (int) (0.35 * deviceAngle);
                    wheel.elapsedTimeX = 0;
                }
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();
                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 103, wheel.sprite.getY() + 103);
            }
        }

        if (!collide && start && !w2.isEmpty()) {
            for (Wheel2 wheel : w2) {
                if(wheel.getFrame()==1)
                    wheel.spriteCollisionDetection(frameSprite,bordersprite1,1);
                if(wheel.getFrame()==2)
                    wheel.spriteCollisionDetection(bordersprite1,bordersprite2,2);
                if(wheel.getFrame()==3)
                    wheel.spriteCollisionDetection(bordersprite2,frameSprite,3);
                if(translate) {
                    wheel.position.x = wheel.sprite.getX() + (int) (0.35 * deviceAngle);
                    wheel.elapsedTimeX = 0;
                }
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();
                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 48, wheel.sprite.getY() + 48);
            }
        }

        if (!collide && start && !w3.isEmpty()) {
            for (Wheel3 wheel : w3) {
                if(wheel.getFrame()==1)
                    wheel.spriteCollisionDetection(frameSprite,bordersprite1,1);
                if(wheel.getFrame()==2)
                    wheel.spriteCollisionDetection(bordersprite1,bordersprite2,2);
                if(wheel.getFrame()==3)
                    wheel.spriteCollisionDetection(bordersprite2,frameSprite,3);
                if(translate) {
                    wheel.position.x = wheel.sprite.getX() + (int) (0.35 * deviceAngle);
                    wheel.elapsedTimeX = 0;
                }
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();
                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 25, wheel.sprite.getY() + 25);
            }
        }

        if (!collide && start && !w4.isEmpty()) {
            for (Wheel4 wheel : w4) {
                if(wheel.getFrame()==1)
                    wheel.spriteCollisionDetection(frameSprite,bordersprite1,1);
                if(wheel.getFrame()==2)
                    wheel.spriteCollisionDetection(bordersprite1,bordersprite2,2);
                if(wheel.getFrame()==3)
                    wheel.spriteCollisionDetection(bordersprite2,frameSprite,3);
                if(translate) {
                    wheel.position.x = wheel.sprite.getX() + (int) (0.35 * deviceAngle);
                    wheel.elapsedTimeX = 0;
                }
                wheel.elapsedTimeX += Gdx.graphics.getDeltaTime();
                wheel.elapsedTimeY += Gdx.graphics.getDeltaTime();
                wheel.sprite.setPosition(wheel.position.x + wheel.velocity.x * wheel.elapsedTimeX, wheel.position.y + wheel.velocity.y * wheel.elapsedTimeY + (float) 1 / 2 * wheel.acceleration.y * wheel.elapsedTimeY * wheel.elapsedTimeY);
                wheel.boundary.setPosition(wheel.sprite.getX() + 196, wheel.sprite.getY() + 196);
            }
        }
    }



    public void drawMarioPosition()
    {
        if (touchDown && !collide && start && (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() || !w4.isEmpty())) {
            sprite.setRegion(animation3.getKeyFrame(elapsedTime, true));
            sprite.setPosition(x, 25);
            sprite.draw(batch);
            mario.setPosition(x+8,25);
            if(animation3.isAnimationFinished(elapsedTime))
                touchDown=false;
        }
        else if (!collide &&  (!w1.isEmpty() || !w2.isEmpty() || !w3.isEmpty() || !w4.isEmpty())) {
            if (deviceAngle < -10 && x <= Gdx.graphics.getWidth() - sprite.getWidth() - 70  &&start) {
                x = x - (int) (0.35 * (deviceAngle));
                sprite.setRegion(animation1.getKeyFrame(elapsedTime, true));
                sprite.setPosition(x, 25);
                sprite.draw(batch);
                mario.setPosition(x + 8, 25);
                translate=false;
            } else if (deviceAngle > 10 && x >= 60  && start) {
                x = x - (int) (0.35 * (deviceAngle));
                sprite.setRegion(animation2.getKeyFrame(elapsedTime, true));
                sprite.setPosition(x, 25);
                sprite.draw(batch);
                mario.setPosition(x + 8, 25);
                translate=false;
            } else if (deviceAngle < -10 && x > Gdx.graphics.getWidth() - sprite.getWidth() - 70 && frameSprite.getX() >= -frameSprite.getWidth() / 2 &&start) {
                sprite.setRegion(animation1.getKeyFrame(elapsedTime, true));
                frameSprite.translateX((int) (0.35 * deviceAngle));
                sprite.draw(batch);
                translate=true;
            } else if (deviceAngle > 10 && x < 60 && frameSprite.getX() <= 0  && start) {
                sprite.setRegion(animation2.getKeyFrame(elapsedTime, true));
                frameSprite.translateX((int) (0.35 * deviceAngle));
                sprite.draw(batch);
                translate=true;
            } else  {
                sprite.setRegion(textureAtlas1.findRegion("mb1"));
                sprite.setPosition(x, 25);
                sprite.draw(batch);
                mario.setPosition(x + 8, 25);
                translate=false;
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
                if(translate)
                    wheel.sprite.translateX((int)0.35*deviceAngle);
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
        if(!w4.isEmpty()) {
            for (Wheel4 wheel : w4) {
                if(translate)
                    wheel.sprite.translateX((int)0.35*deviceAngle);
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
        boolean collision;
        if (f.isEmpty())
        {
            fireboundary=new Rectangle(-50,-50,18,50);
        }
        if(!f.isEmpty() && !collide && start && elapsedTime>=animation3.getFrameDuration()/(float)(1.5)) {

            for(ListIterator<Float> iterator=f.listIterator(); iterator.hasNext();) {
                collision=false;
                float fx=iterator.next();
                if(translate)
                    iterator.set(fx+(int)(0.35*deviceAngle));
                if(fx+firesprite.getWidth()<=bordersprite1.getX())
                    iterator.set(fx+4f);
                if(fx+firesprite.getWidth()>bordersprite1.getX() && fx<bordersprite1.getX()+bordersprite1.getWidth())
                    collision=true;
                if(fx>=bordersprite2.getX())
                    iterator.set(fx-4f);
                if(fx<bordersprite2.getX()+bordersprite2.getWidth() && fx + firesprite.getWidth()>bordersprite2.getX())
                    collision=true;
                float fy=iterator.next();
                firesprite.setRegion(animation4.getKeyFrame(elapsedTime, true));
                firesprite.setPosition(fx, fy);
                firesprite.draw(batch);
                iterator.previous();
                iterator.set(fy + 7f);
                iterator.previous();
                fireboundary=new Rectangle(fx+22,fy+60,18,50);
                if (!collision)
                    collision=fireCollision();
                if (w1.isEmpty() && w2.isEmpty()&& w3.isEmpty() && w4.isEmpty() && collision)
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
                if(Intersector.overlaps(wheel.boundary, fireboundary))
                {
                    score.w1_hit(game_time);
                    w2.add(new Wheel2(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w2.get(w2.size()-1).velocity.x=-w2.get(w2.size()-1).velocity.x;
                    w2.get(w2.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    w2.get(w2.size()-1).setFrame(wheel.getFrame());
                    w2.add(new Wheel2(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w2.get(w2.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    w2.get(w2.size()-1).setFrame(wheel.getFrame());
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
                    w3.get(w3.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    w3.get(w3.size()-1).setFrame(wheel.getFrame());
                    w3.add(new Wheel3(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w3.get(w3.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    w3.get(w3.size()-1).setFrame(wheel.getFrame());
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

        if(!w4.isEmpty()) {
            for (ListIterator<Wheel4> iterator=w4.listIterator(); iterator.hasNext();) {
                Wheel4 wheel=iterator.next();
                if(Intersector.overlaps(wheel.boundary,fireboundary)) {
                    score.w4_hit(game_time);
                    w1.add(new Wheel1(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w1.get(w1.size()-1).velocity.x=-w1.get(w1.size()-1).velocity.x;
                    w1.get(w1.size() - 1).velocity.y=Math.abs(wheel.velocity.y);
                    w1.get(w1.size()-1).setFrame(wheel.getFrame());
                    w1.add(new Wheel1(wheel.sprite.getX() + wheel.sprite.getWidth() / 2, wheel.sprite.getY() + wheel.sprite.getHeight() / 2));
                    w1.get(w1.size()-1).velocity.y=Math.abs(wheel.velocity.y);
                    w1.get(w1.size()-1).setFrame(wheel.getFrame());
                    iterator.remove();
                    return true;
                }
            }
        }


        return false;
    }

}





