package ca.nakednate.game.p2p.listeners;

import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;
import ca.nakednate.game.models.events.VehiclePositionEvent;

public interface GameStateListener {

    public void onOpponentInitialChoicesEvent(OpponentInitialChoicesEvent opponentInitialChoicesEvent);
    public void onVehiclePositionEvent(VehiclePositionEvent vehiclePositionEvent);

}
