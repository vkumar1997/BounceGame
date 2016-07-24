package com.mygdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Vikas on 5/22/2016.
 */
public class Score {
    int score=0;
    public static int total_score;



    public void miss_fire()
    {
        if (score>=50) {
            score = score - 50;
        }
    }

    public void w1_hit(int game_time)
    {
        score=score+10*game_time;
    }

    public void w2_hit(int game_time)
    {
        score=score+8*game_time;
    }

    public void w3_hit(int game_time)
    {
        score=score+6*game_time;
    }

    public void w4_hit(int game_time)
    {
        score=score+10*game_time;
    }


    public boolean check_total_score()
    {
        FileHandle file=Gdx.files.local(("assets/highscore.txt"));
        String text=file.readString();
        if(total_score>Integer.parseInt(text))
            return true;
        else
            return false;
    }

    public void update_high_score()
    {
        FileHandle file=Gdx.files.local(("assets/highscore.txt"));
        file.delete();
        FileHandle file1=Gdx.files.local(("assets/highscore.txt"));
        file1.writeString(String.valueOf(total_score),false);
    }

    public int get_high_score()
    {
        FileHandle file=Gdx.files.local(("assets/highscore.txt"));
        return Integer.parseInt(file.readString());
    }

    public int getScore()
    {
        return score;
    }



    public int getTotalScore()
    {
        return total_score;
    }
}
