package ca.nakednate.game.models.events;

import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;

public class OpponentInitialChoicesEvent extends BaseEvent {

    public enum VehicleEnum {
        JEEP,
        TANK
    }

    private VehicleEnum mVehicle;

    public OpponentInitialChoicesEvent(ClientHandler messageOriginator) {
        super(messageOriginator);
    }

    public Vehicle getVehicle() {
        switch(mVehicle) {
            case JEEP:
                return new Jeep(false);
            case TANK:
                return new Tank(false);
        }
        return null;
    }

    public void setVehicle(VehicleEnum vehicle) {
        mVehicle = vehicle;
    }
}
