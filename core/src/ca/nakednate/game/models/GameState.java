package ca.nakednate.game.models;

import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;
import ca.nakednate.game.models.events.VehiclePositionEvent;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.MessageHandler;
import ca.nakednate.game.p2p.listeners.GameStateListener;

/**
 * Information about a game
 */
public class GameState extends BaseModel implements GameStateListener {

    private Vehicle mMyVehicle;
    private Vehicle mOpponentVehicle;

    private GameState() {
        mMyVehicle = null;
        mOpponentVehicle = null;

        MessageHandler.setGameStateListener(this);
    }

    public void resetGame() {
        SingletonHolder.INSTANCE = new GameState();
    }

    @Override
    public void onOpponentInitialChoicesEvent(OpponentInitialChoicesEvent opponentInitialChoicesEvent) {
        mOpponentVehicle = opponentInitialChoicesEvent.getVehicle();
    }

    @Override
    public void onVehiclePositionEvent(VehiclePositionEvent vehiclePositionEvent) {
        if(mOpponentVehicle == null) {
            mOpponentVehicle = new Tank(false);
        }

        mOpponentVehicle.getGroup().setX(vehiclePositionEvent.getX());
        mOpponentVehicle.getGroup().setY(vehiclePositionEvent.getY());
        mOpponentVehicle.getGroup().setRotation(vehiclePositionEvent.getHeading());

        ClientHandler clientHandler = vehiclePositionEvent.getMessageOriginator();
        clientHandler.sendMyPosition();
    }

    public Vehicle getOpponentVehicle() {
        return mOpponentVehicle;
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

    private static class SingletonHolder {
        public static GameState INSTANCE = new GameState();
    }

    public static GameState getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
