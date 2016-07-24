package com.mygdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vikas on 5/20/2016.
 */
public class Wheel2 {
    public Sprite sprite;
    public Vector2 position,velocity,acceleration;
    public float elapsedTimeX=0,elapsedTimeY=0;
    Sound sound;
    public Circle boundary;
    int frame;

    public Wheel2(float posX, float posY)
    {
        sound=Gdx.audio.newSound(Gdx.files.internal("Swords_Collide-Sound_Explorer-2015600826.wav"));
        position=new Vector2(posX,posY);
        velocity=new Vector2((float)0.12*Gdx.graphics.getWidth()*(float)1.5,0);
        acceleration=new Vector2(0,(float)-0.32*Gdx.graphics.getHeight());
        sprite=new Sprite(new Texture(Gdx.files.internal("wheel2.PNG")));
        sprite.setPosition(posX,posY);
        boundary=new Circle(posX+48,posY+48,48);
    }

    public void setFrame(int frame)
    {
        this.frame=frame;
    }
    public int getFrame()
    {
        return frame;
    }

    public void spriteCollisionDetection(Sprite framesprite)
    {

        if(sprite.getX()<framesprite.getX()+33)
        {
            sound.play();
            position.x=framesprite.getX()+33;
            velocity.x = -velocity.x;
            elapsedTimeX=0;
        }
        if(sprite.getX()>framesprite.getX()+framesprite.getWidth()-sprite.getWidth()-33)
        {
            sound.play();
            position.x=framesprite.getX()+framesprite.getWidth()-sprite.getWidth()-33;
            velocity.x=-velocity.x;
            elapsedTimeX=0;
        }

        if(sprite.getY()<27)
        {
            sound.play();
            velocity.y=(float)0.4589*Gdx.graphics.getHeight()/(float)1.1;
            position.y=27;
            elapsedTimeY=0;
        }
        if (sprite.getY()+sprite.getHeight()>Gdx.graphics.getHeight()-27)
        {
            sound.play();
            velocity.y=-velocity.y;
            position.y=Gdx.graphics.getHeight()-sprite.getHeight()-27;
            elapsedTimeY=0;
        }

    }

    public void spriteCollisionDetection(Sprite border1, Sprite border2, int frame)
    {
        if(frame==1)
        {
            if(sprite.getX()<border1.getX()+33)
            {
                sound.play();
                position.x=border1.getX()+33;
                velocity.x = -velocity.x;
                elapsedTimeX=0;
            }
            if(sprite.getX()>border2.getX()-sprite.getWidth())
            {
                sound.play();
                position.x=border2.getX()-sprite.getWidth();
                velocity.x=-velocity.x;
                elapsedTimeX=0;
            }

        }

        if(frame==2)
        {
            if(sprite.getX()<border1.getX()+border1.getWidth())
            {
                sound.play();
                position.x=border1.getX()+border1.getWidth();
                velocity.x = -velocity.x;
                elapsedTimeX=0;
            }
            if(sprite.getX()>border2.getX()-sprite.getWidth())
            {
                sound.play();
                position.x=border2.getX()-sprite.getWidth();
                velocity.x=-velocity.x;
                elapsedTimeX=0;
            }

        }

        if(frame==3)
        {
            if(sprite.getX()<border1.getX()+border1.getWidth())
            {
                sound.play();
                position.x=border1.getX()+border1.getWidth();
                velocity.x = -velocity.x;
                elapsedTimeX=0;
            }
            if(sprite.getX()+sprite.getWidth()>border2.getX()+border2.getWidth()-33)
            {
                sound.play();
                position.x=border2.getX()+border2.getWidth()-sprite.getWidth()-33;
                velocity.x=-velocity.x;
                elapsedTimeX=0;
            }

        }

        if(sprite.getY()<27)
        {
            sound.play();
            velocity.y=(float)0.4589*Gdx.graphics.getHeight()/(float)1.1;
            position.y=27;
            elapsedTimeY=0;
        }
        if (sprite.getY()+sprite.getHeight()>Gdx.graphics.getHeight()-27)
        {
            sound.play();
            velocity.y=-Math.abs(velocity.y);
            position.y=Gdx.graphics.getHeight()-sprite.getHeight()-27;
            elapsedTimeY=0;
        }
    }

    public boolean manCollisionDetection(Rectangle rectangle)
    {
        return Intersector.overlaps(boundary, rectangle);
    }

}
