package ca.nakednate.game;

import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * VehicleSelectionScreen
 */
public class VehicleSelectionScreen extends BaseScreen {

    ClientHandler mOpponent;

    public VehicleSelectionScreen(ClientHandler opponent, UnfriendlyFire game) {
        super(game);
        mOpponent = opponent;
    }

    @Override
    public void show() {
        super.show();

        Image jeepImage = new Image(
                new TextureRegion(new Texture(Gdx.files.internal("skin/sprites/ui_vehicle_select_weasel.png")))
        );
        jeepImage.addListener(getLaunchGameClickListener(new Jeep(true)));
        jeepImage.setBounds((Gdx.graphics.getWidth() * 0.5f) - jeepImage.getWidth() - 10, 20, jeepImage.getWidth(),
                jeepImage.getHeight());

        Image tankImage = new Image(
                new TextureRegion(new Texture(Gdx.files.internal("skin/sprites/ui_vehicle_select_warthog.png")))
        );
        tankImage.addListener(getLaunchGameClickListener(new Tank(true)));
        tankImage.setBounds((Gdx.graphics.getWidth() * 0.5f) + 10, 20, tankImage.getWidth(), tankImage.getHeight());

        Image background = new Image(new TextureRegion(new Texture(Gdx.files.internal("skin/vehicle_select.png"))));
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        getStage().addActor(background);
        getStage().addActor(jeepImage);
        getStage().addActor(tankImage);
    }

    private ClickListener getLaunchGameClickListener(final Vehicle vehicle) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                launchGame(vehicle);
            }
        };
    }

    private void launchGame(Vehicle selectedVehicle) {
        getGame().setScreen(new LevelScreen(mOpponent, getGame(), selectedVehicle));
    }
}
