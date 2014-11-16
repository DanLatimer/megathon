package ca.nakednate.game.p2p.listeners;

import ca.nakednate.game.models.events.VehicleChoiceEvent;
import ca.nakednate.game.models.events.VehiclePositionEvent;

public interface GameStateListener {

    public void onVehicleChoiceEvent(VehicleChoiceEvent vehicleChoiceEvent);
    public void onVehiclePositionEvent(VehiclePositionEvent vehiclePositionEvent);

}
