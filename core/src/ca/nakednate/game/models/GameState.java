package ca.nakednate.game.models;

import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.MessageHandler;
import ca.nakednate.game.p2p.listeners.GameStateListener;

/**
 * Information about a game
 */
public class GameState extends BaseModel implements GameStateListener {

    private Vehicle mMyVehicle;
    private Vehicle mYourVehicle;

    private GameState() {
        mMyVehicle = null;
        mYourVehicle = null;

        MessageHandler.setGameStateListener(this);
    }

    public void resetGame() {
        SingletonHolder.INSTANCE = new GameState();
    }

    @Override
    public void onOpponentInitialChoicesEvent(OpponentInitialChoicesEvent opponentInitialChoicesEvent) {
        mYourVehicle = opponentInitialChoicesEvent.getVehicle();
    }

    public Vehicle getYourVehicle() {
        return mYourVehicle;
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
