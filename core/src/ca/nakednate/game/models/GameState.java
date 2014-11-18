package ca.nakednate.game.models;

import ca.nakednate.game.android.ToastMaster;
import ca.nakednate.game.models.events.VehicleChoiceEvent;
import ca.nakednate.game.models.events.VehiclePositionEvent;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.MessageHandler;
import ca.nakednate.game.p2p.listeners.GameStateListener;

/**
 * Information about a game
 */
public class GameState extends BaseModel implements GameStateListener {

    private static final String LOG_TAG = GameState.class.getSimpleName();

    private ClientHandler mOpponent;
    private Vehicle mMyVehicle;
    private VehicleChoiceEvent.VehicleEnum mOpponentVehicleEnum;
    private Vehicle mOpponentVehicle;

    private GameState() {
        mMyVehicle = null;
        mOpponentVehicle = null;
        mOpponentVehicleEnum = null;

        MessageHandler.setGameStateListener(this);
    }

    public void resetGame() {
        SingletonHolder.INSTANCE = new GameState();
    }

    @Override
    public void onVehicleChoiceEvent(VehicleChoiceEvent vehicleChoiceEvent) {
        mOpponentVehicleEnum = vehicleChoiceEvent.getVehicle();
    }

    @Override
    public void onVehiclePositionEvent(VehiclePositionEvent vehiclePositionEvent) {
        if(mOpponentVehicle == null) {
            return;
        }

        mOpponentVehicle.getGroup().setX(vehiclePositionEvent.getX());
        mOpponentVehicle.getGroup().setY(vehiclePositionEvent.getY());
        mOpponentVehicle.getGroup().setRotation(vehiclePositionEvent.getHeading());
        mOpponentVehicle.getWeapon().setRotation(vehiclePositionEvent.getTurretHeading());

        ClientHandler clientHandler = vehiclePositionEvent.getMessageOriginator();
        clientHandler.sendMyPosition();
    }

    public Vehicle getOpponentVehicle() {

        if(mOpponentVehicle == null) {
            if(mOpponentVehicleEnum == null) {
                mOpponentVehicleEnum = VehicleChoiceEvent.VehicleEnum.TANK;
                ToastMaster.debugToast("Attempted to get the opponent vehicle without receiving it. Faking it.", true);
            }

            mOpponentVehicle = VehicleChoiceEvent.getVehicle(mOpponentVehicleEnum);
        }

        return mOpponentVehicle;
    }

    public boolean isOpponentVehicleSet() {
        return (mOpponentVehicleEnum != null);
    }

    public void setOpponentVehicle(Vehicle opponentVehicle) {
        mOpponentVehicle = opponentVehicle;
    }

    public Vehicle getMyVehicle() {
        return mMyVehicle;
    }

    public void setMyVehicle(Vehicle myVehicle) {
        mMyVehicle = myVehicle;
    }

    public ClientHandler getOpponent() {
        return mOpponent;
    }

    public void setOpponent(ClientHandler opponent) {
        mOpponent = opponent;
    }

    // Singleton stuff below
    private static class SingletonHolder {
        public static GameState INSTANCE = new GameState();
    }

    public static GameState getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
