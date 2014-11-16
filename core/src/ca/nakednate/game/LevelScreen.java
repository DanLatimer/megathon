package ca.nakednate.game;

import ca.nakednate.game.models.GameState;
import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;
import ca.nakednate.game.models.events.VehiclePositionEvent;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelScreen extends BaseScreen {

    private static final String LOG_TAG = LevelScreen.class.getSimpleName();

    private final Vehicle mVehicle;
    private ClientHandler mOpponent;
    private long mLastVehicleEvent = 0;
    private boolean mOpponentAddedToStage = false;
    private OrthogonalTiledMapRenderer mMapRenderer;

    private GameState gameState = GameState.getInstance();

    public LevelScreen(ClientHandler opponent, final UnfriendlyFire game, Vehicle vehicle) {
        super(game);
        mVehicle = vehicle;
        mOpponent = opponent;

        OpponentInitialChoicesEvent myInitialChoices = new OpponentInitialChoicesEvent(null, vehicle);
        opponent.sendJson(OpponentInitialChoicesEvent.class, myInitialChoices.toJSON());

        gameState.setMyVehicle(mVehicle);
    }

    @Override
    public void show() {
        super.show();
        TiledMap tiledMap = new TmxMapLoader().load("skin/level_provingGrounds.tmx");
        mMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        mVehicle.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        getStage().addActor(mVehicle);
        addButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            mVehicle.moveTo(Gdx.input.getX(), Gdx.input.getY());
            mVehicle.fire();
        }

        Vehicle opponentVehicle = gameState.getOpponentVehicle();
        if(opponentVehicle != null) {
            if(!mOpponentAddedToStage) {
                mOpponentAddedToStage = true;
                getStage().addActor(opponentVehicle);
            }

            opponentVehicle.setPosition(opponentVehicle.getX(), opponentVehicle.getY());
        }

        getStage().getCamera().position.set(mVehicle.getX() + (mVehicle.getWidth() / 2), mVehicle.getY() + (mVehicle.getHeight() / 2), 0);
        getStage().getCamera().update();
        mMapRenderer.setView((OrthographicCamera) getStage().getCamera());
        mMapRenderer.render();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();

        sendMyPosition();
    }

    private void sendMyPosition() {
        if(System.currentTimeMillis() - mLastVehicleEvent > 1000) {
            mLastVehicleEvent = System.currentTimeMillis();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VehiclePositionEvent myVehiclePosition = new VehiclePositionEvent(null, mVehicle.getX(), mVehicle.getY());
                    mOpponent.sendJson(VehiclePositionEvent.class, myVehiclePosition.toJSON());
                }
            }).start();
        }
    }

    private void addButtons() {

        final float OFF_BOTTOM_PCT = 0.2f;
        final float OFF_CENTER = 100.0f;
        final float BUTTON_WIDTH = 150.0f;
        final float BUTTON_HEIGHT = 75.0f;

        TextureAtlas buttonAtlas = new TextureAtlas("skin/sprites/button/button.atlas");

        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonAtlas);

        BitmapFont font = new BitmapFont(Gdx.files.internal("skin/default.fnt"), false);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = buttonSkin.getDrawable("ui_background_button_on");
        style.down = buttonSkin.getDrawable("ui_background_button_off");
        style.font = font;

        TextButton fireButton = new TextButton("Fire", style);
        TextButton deployButton = new TextButton("Deploy", style);

        fireButton.setWidth(BUTTON_WIDTH);
        fireButton.setHeight(BUTTON_HEIGHT);

        deployButton.setWidth(BUTTON_WIDTH);
        deployButton.setHeight(BUTTON_HEIGHT);

        fireButton.setPosition(
                (Gdx.graphics.getWidth() / 2) - (fireButton.getWidth() / 2) + OFF_CENTER,
                (Gdx.graphics.getHeight() * OFF_BOTTOM_PCT)
        );

        deployButton.setPosition(
                (Gdx.graphics.getWidth() / 2) - (deployButton.getWidth() / 2) - OFF_CENTER,
                (Gdx.graphics.getHeight() * OFF_BOTTOM_PCT)
        );

        fireButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.error(LOG_TAG, "pew pew pew");

                if (!mVehicle.fire()) {
                    // TODO: out of ammo, change state
                }
            }
        });

        deployButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.error(LOG_TAG, "deploy deploy");

                DeployableWeapon dp = mVehicle.deploy();

                if (dp == null) {
                    Gdx.app.error(LOG_TAG, "out of ammo");
                } else {
                    // TODO: use dp
                }
            }
        });

        getStage().addActor(fireButton);
        getStage().addActor(deployButton);
    }
}
