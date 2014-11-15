package ca.nakednate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BaseScreen implements Screen {

    final private ScorchedPlanet mGame;

    private Stage mStage;

    public BaseScreen(final ScorchedPlanet game) {
        mGame = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        getStage().getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        mStage = new Stage();
        Gdx.input.setInputProcessor(mStage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        getStage().dispose();
    }

    public ScorchedPlanet getGame() {
        return mGame;
    }

    public Skin getSkin() {
        return getGame().getSkin();
    }

    public Stage getStage() {
        return mStage;
    }
}
