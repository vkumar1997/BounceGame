package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.Screens.MainScreen;

public class BounceGame extends Game {
    BounceGame game;


    @Override
    public void create()
    {
        game=this;
        setScreen(new MainScreen(game));
    }

    public void dispose()
    {}

    public void render()
    {
        super.render();
    }

    public void resize(int width, int height)
    {}

    public void pause()
    {}

    public void resume(){
    }
}
