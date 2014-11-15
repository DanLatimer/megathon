package ca.nakednate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseScreen implements Screen {

    final private ScorchedPlanet mGame;

    private Stage mStage;

    public BaseScreen(final ScorchedPlanet game) {
        mGame = game;
    }

    @Override
    public void render(float delta) {
        mStage.act(Gdx.graphics.getDeltaTime());
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        mStage.dispose();
    }

    public ScorchedPlanet getGame() {
        return mGame;
    }

    public Stage getStage() {
        return mStage;
    }
}
