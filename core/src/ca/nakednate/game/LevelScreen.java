package ca.nakednate.game;

import ca.nakednate.game.models.vehicle.Vehicle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LevelScreen extends BaseScreen {

    private final Vehicle mVehicle;

    public LevelScreen(final UnfriendlyFire game, Vehicle vehicle) {
        super(game);
        mVehicle = vehicle;
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();

        Label welcomeLabel = new Label("You selected the " + mVehicle.getClass().getSimpleName() + "!", getSkin());

        table.add(welcomeLabel);
        table.setBounds((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() / 2, 200, 100);

        getStage().addActor(table);
    }
}
