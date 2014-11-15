package ca.nakednate.game;

import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * VehicleSelectionScreen
 */
public class VehicleSelectionScreen extends BaseScreen {

    public VehicleSelectionScreen(UnfriendlyFire game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Label titleLabel = new Label("Select your vehicle", getSkin());

        Image jeepImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("skin/vehicle_jeep_red.png"))));
        Button jeepButton = new Button(jeepImage, getSkin());
        jeepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                launchGame(new Jeep());
            }
        });

        Image tankImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("skin/vehicle_jeep_blue.png"))));
        Button tankButton = new Button(tankImage, getSkin());
        tankButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                launchGame(new Tank());
            }
        });

        Table table = new Table();
        table.add(titleLabel).colspan(2);
        table.row();
        table.add(jeepButton);
        table.add(tankButton);
        table.setBounds(0, ((Gdx.graphics.getHeight() / 2) - (table.getHeight() / 2)), Gdx.graphics.getWidth(),
                table.getHeight());

        getStage().addActor(table);
    }

    private void launchGame(Vehicle selectedVehicle) {
        getGame().setScreen(new LevelScreen(getGame(), selectedVehicle));
    }
}
