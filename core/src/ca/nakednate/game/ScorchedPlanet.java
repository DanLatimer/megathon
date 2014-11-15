package ca.nakednate.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScorchedPlanet extends Game {


    private Skin mSkin;

    @Override
    public void create() {

        setSkin(new Skin(Gdx.files.internal("data/uiskin.json")));

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        getSkin().dispose();
    }

    public Skin getSkin() {
        return mSkin;
    }

    public void setSkin(Skin skin) {
        mSkin = skin;
    }
}
