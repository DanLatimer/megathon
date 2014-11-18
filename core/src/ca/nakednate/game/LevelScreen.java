package ca.nakednate.game;

import ca.nakednate.game.models.GameState;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelScreen extends BaseScreen {

    private static final String LOG_TAG = LevelScreen.class.getSimpleName();

    private static final int COLLISION_LAYER_ID = 1;

    private Stage mHudStage;
    private Vehicle mVehicle;
    private Vehicle mOpponentVehicle;
    private ClientHandler mOpponent;
    private OrthogonalTiledMapRenderer mMapRenderer;
    private MapObjects mCollisionObjects;

    private GameState mGameState = GameState.getInstance();
    private Touchpad mMovementTouchPad;

    public LevelScreen(final UnfriendlyFire game) {
        super(game);

    }

    @Override
    public void show() {
        super.show();
        TiledMap tiledMap = new TmxMapLoader().load("skin/level_provingGrounds.tmx");
        mMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        mOpponent = mGameState.getOpponent();

        mOpponentVehicle = mGameState.getOpponentVehicle();
        getStage().addActor(mOpponentVehicle.getGroup());

        mVehicle = mGameState.getMyVehicle();
        mVehicle.getGroup().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        getStage().addActor(mVehicle.getGroup());

        MapLayer collisionLayer = tiledMap.getLayers().get(COLLISION_LAYER_ID);
        mCollisionObjects = collisionLayer.getObjects();

        setupHud();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (mMovementTouchPad.isTouched()) {

            float dx = mMovementTouchPad.getKnobPercentX() * mVehicle.getSpeed();
            float dy = mMovementTouchPad.getKnobPercentY() * mVehicle.getSpeed();

            float newX = mVehicle.getGroup().getX() + dx;
            float newY = mVehicle.getGroup().getY() + dy;

            boolean collided = detectMapCollision(newX, newY);

            if (!collided) {
                mVehicle.moveTo(newX, newY, dx, dy);
            }
        }
        
        OrthographicCamera cam = (OrthographicCamera) getStage().getCamera();
        cam.position.set(mVehicle.getGroup().getX() + (mVehicle.getWidth() / 2),
                mVehicle.getGroup().getY() + (mVehicle.getHeight() / 2) - (mVehicle.getHeight() / 8), 0);
        cam.zoom = 2;
        cam.update();
        mMapRenderer.setView((OrthographicCamera) getStage().getCamera());
        mMapRenderer.render();
        getStage().act(Gdx.graphics.getDeltaTime());
        getStage().draw();
        mHudStage.act(Gdx.graphics.getDeltaTime());
        mHudStage.draw();

        mOpponent.sendMyPosition();
    }

    private boolean detectMapCollision(float newX, float newY) {
        Rectangle playerRectangle = new Rectangle(newX, newY, mVehicle.getWidth(), mVehicle.getHeight());

        boolean collided = false;
        for (RectangleMapObject collisionRectangleObject : mCollisionObjects.getByType(RectangleMapObject.class)) {

            Rectangle collisionRectangle = collisionRectangleObject.getRectangle();
            if (Intersector.overlaps(playerRectangle, collisionRectangle)) {
                // collision happened
                mVehicle.clearActions();
                collided = true;
                break;
            }
        }

        return collided;
    }

    private void setupHud() {
        mHudStage = new Stage();
        Gdx.input.setInputProcessor(mHudStage);

        addTouchPads();
        addButtons();
    }

    private void addTouchPads() {
        mMovementTouchPad = new Touchpad(10, getSkin());
        mMovementTouchPad.setBounds(100, 100, 200, 200);

        Touchpad aimingTouchPad = new Touchpad(10, getSkin());
        aimingTouchPad.setBounds(Gdx.graphics.getWidth() - 300, 100, 200, 200);

        mHudStage.addActor(mMovementTouchPad);
        mHudStage.addActor(aimingTouchPad);
    }

    private void addButtons() {

        final float OFF_BOTTOM_PCT = 0.02f;
        final float OFF_CENTER = 100.0f;

        TextureAtlas buttonAtlas = new TextureAtlas("ui/button/button.atlas");
        Skin buttonSkin = new Skin(Gdx.files.internal("ui/button/button.json"), buttonAtlas);

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

        mHudStage.addActor(fireButton);
        mHudStage.addActor(deployButton);
    }
}
