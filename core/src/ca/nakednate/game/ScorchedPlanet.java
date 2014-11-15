package ca.nakednate.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScorchedPlanet extends Game {


    private MainMenuScreen mMainMenuScreen;

    public ScorchedPlanet() {
        super();
        mMainMenuScreen = new MainMenuScreen(this);
    }

    private Skin mSkin;

    @Override
    public void create() {

        setSkin(new Skin(Gdx.files.internal("skin/uiskin.json")));


        setScreen(mMainMenuScreen);
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

    public ca.nakednate.game.p2p.listeners.PeerDiscoveryListener getPeerDiscoveryListener() {
        return mMainMenuScreen;
    }

}
