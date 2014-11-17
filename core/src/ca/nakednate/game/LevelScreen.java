package ca.nakednate.game;

import ca.nakednate.game.models.GameState;
import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;
import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

        mVehicle.getGroup().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        getStage().addActor(mVehicle.getGroup());
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
                // TODO: clean up this hacky mess (last hour changes before demo)

                // To get to work we need to instantiate the Vehicle in this thread
                Vehicle newVehicle = null;
                if(opponentVehicle instanceof Jeep) {
                    newVehicle = new Jeep(false);
                } else if (opponentVehicle instanceof Tank) {
                    newVehicle = new Tank(false);
                }

                newVehicle.getGroup().setX(opponentVehicle.getGroup().getX());
                newVehicle.getGroup().setY(opponentVehicle.getGroup().getY());
                newVehicle.getGroup().setRotation(opponentVehicle.getGroup().getRotation());
                opponentVehicle = newVehicle;
                gameState.setOpponentVehicle(opponentVehicle);
                getStage().addActor(opponentVehicle.getGroup());
            }

//            opponentVehicle.setPosition(opponentVehicle.getX(), opponentVehicle.getY());
//            opponentVehicle.setRotation(opponentVehicle.getRotation());
        }

        OrthographicCamera cam = (OrthographicCamera) getStage().getCamera();
        cam.position.set(mVehicle.getGroup().getX() + (mVehicle.getGroup().getWidth() / 2), mVehicle.getGroup().getY() + (mVehicle.getGroup().getHeight() / 2), 0);
        cam.zoom = 2;
        cam.update();
        mMapRenderer.setView((OrthographicCamera) getStage().getCamera());
        mMapRenderer.render();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();

        mOpponent.sendMyPosition();
    }

    private void addButtons() {

        final float OFF_BOTTOM_PCT = 0.2f;
        final float OFF_CENTER = 100.0f;

        TextureAtlas buttonAtlas = new TextureAtlas("ui/button.atlas");
        Skin buttonSkin = new Skin(Gdx.files.internal("ui/button.json"), buttonAtlas);

        TextButton fireButton = new TextButton("Fire", buttonSkin);
        TextButton deployButton = new TextButton("Deploy", buttonSkin);

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
