package ca.nakednate.game;

import ca.nakednate.game.models.vehicle.Vehicle;
import com.badlogic.gdx.Gdx;

public class LevelScreen extends BaseScreen {

    private final Vehicle mVehicle;

    public LevelScreen(final UnfriendlyFire game, Vehicle vehicle) {
        super(game);
        mVehicle = vehicle;
    }

    @Override
    public void show() {
        super.show();
        mVehicle.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        getStage().addActor(mVehicle);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isTouched()) {
            mVehicle.moveTo(Gdx.input.getX(), Gdx.input.getY());
            mVehicle.fire();
        }
        super.render(delta);
    }
}
