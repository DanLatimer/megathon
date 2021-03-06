package ca.nakednate.game;

import ca.nakednate.game.models.GameState;
import ca.nakednate.game.models.events.VehicleChoiceEvent;
import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.concurrent.TimeUnit;


/**
 * VehicleSelectionScreen
 */
public class VehicleSelectionScreen extends BaseScreen {

    private ClientHandler mOpponent;
    private long mStartTimeMillis = System.currentTimeMillis();

    private static long TIME_LIMIT_MILLIS = TimeUnit.SECONDS.toMillis(30);
    // Wait 5 seconds after timeup to ensure we get the vehicle selection from the opponent
    private static long TIME_NEXT_ACTIVITY_MILLIS = TimeUnit.SECONDS.toMillis(35);

    private boolean mChoiceSent = false;
    private Label mTimeRemainingLabel;

    public VehicleSelectionScreen(UnfriendlyFire game) {
        super(game);
        mOpponent = GameState.getInstance().getOpponent();
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

        mTimeRemainingLabel = new Label(getTimeRemainingForUser() + "", getSkin());

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        Table table = new Table(getSkin());
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        table.align(Align.top);

        float tableWidth = mTimeRemainingLabel.getWidth() + 30;
        float tableHeight = mTimeRemainingLabel.getHeight() + 10;
        table.setBounds(Gdx.graphics.getWidth() * 0.5f - (tableWidth / 2), 10, tableWidth, tableHeight);
        table.add(mTimeRemainingLabel).center();

        Stage stage = getStage();
        stage.addActor(background);
        stage.addActor(jeepImage);
        stage.addActor(tankImage);
        stage.addActor(table);
    }

    private long getTimeRemainingForUser() {
        long remainingTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(getRemainingToChooseMillis());
        remainingTimeSeconds = (remainingTimeSeconds < 0) ? 0 : remainingTimeSeconds;

        return remainingTimeSeconds;
    }

    private long getTimeTillNextActivityMillis() {
        long elapsedTimeMillis = System.currentTimeMillis() - mStartTimeMillis;
        return TIME_NEXT_ACTIVITY_MILLIS - elapsedTimeMillis;
    }

    private long getRemainingToChooseMillis() {
        long elapsedTimeMillis = System.currentTimeMillis() - mStartTimeMillis;
        return TIME_LIMIT_MILLIS - elapsedTimeMillis;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        mTimeRemainingLabel.setText(getTimeRemainingForUser() + "");

        if(getTimeTillNextActivityMillis() <= 0 || isBothVehiclesChosen()) {
            launchGame();
        }
        if(getRemainingToChooseMillis() <= 0 && !mChoiceSent) {
            chooseVehicleForPlayer();
        }
    }

    public boolean isBothVehiclesChosen() {
        GameState gameState = GameState.getInstance();
        return (gameState.isOpponentVehicleSet()) && (gameState.getMyVehicle() != null);
    }

    public void chooseVehicleForPlayer() {
        Vehicle vehicle = null;
        int vehicleChoice = (int) Math.floor(Math.random() * 2);
        switch(vehicleChoice) {
            case 0:
                vehicle = new Jeep(true);
                break;
            case 1:
                vehicle = new Tank(true);
                break;
        }

        chooseVehicle(vehicle);
    }

    private ClickListener getLaunchGameClickListener(final Vehicle vehicle) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                chooseVehicle(vehicle);

                if(GameState.getInstance().isOpponentVehicleSet()) {
                    launchGame();
                }
            }
        };
    }

    /**
     * Sets choice and sends to opponent
     *
     * @param vehicle
     */
    public void chooseVehicle(Vehicle vehicle) {
        mChoiceSent = true;
        VehicleChoiceEvent myInitialChoices = new VehicleChoiceEvent(vehicle);
        mOpponent.sendJson(VehicleChoiceEvent.class, myInitialChoices.toJSON());

        GameState gameState = GameState.getInstance();
        gameState.setMyVehicle(vehicle);
    }

    private void launchGame() {
        getGame().setScreen(new LevelScreen(getGame()));
    }
}
