package ca.nakednate.game.models.events;

import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;

public class VehicleChoiceEvent extends BaseEvent {

    public enum VehicleEnum {
        JEEP,
        TANK
    }

    private VehicleEnum mVehicle;

    public VehicleChoiceEvent(ClientHandler messageOriginator, Vehicle vehicle) {
        super(messageOriginator);
        setVehicle(vehicle);
    }

    public VehicleEnum getVehicle() {
        return mVehicle;
    }

    public static Vehicle getVehicle(VehicleEnum vehicle) {
        switch(vehicle) {
            case JEEP:
                return new Jeep(false);
            case TANK:
                return new Tank(false);
        }
        return null;
    }

    public void setVehicle(Vehicle vehicle) {
        if(vehicle instanceof Jeep) {
            mVehicle = VehicleEnum.JEEP;
            return;
        }

        if(vehicle instanceof Tank) {
            mVehicle = VehicleEnum.TANK;
            return;
        }
    }
}
