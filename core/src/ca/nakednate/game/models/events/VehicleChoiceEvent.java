package ca.nakednate.game.models.events;

import ca.nakednate.game.models.vehicle.Jeep;
import ca.nakednate.game.models.vehicle.Tank;
import ca.nakednate.game.models.vehicle.Vehicle;

public class VehicleChoiceEvent extends BaseEvent {

    public enum VehicleEnum {
        JEEP,
        TANK
    }

    private VehicleEnum mVehicleEnum;

    public VehicleChoiceEvent(Vehicle vehicle) {
        setVehicleEnum(vehicle);
    }

    public VehicleEnum getVehicleEnum() {
        return mVehicleEnum;
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

    public void setVehicleEnum(Vehicle vehicle) {
        if(vehicle instanceof Jeep) {
            mVehicleEnum = VehicleEnum.JEEP;
            return;
        }

        if(vehicle instanceof Tank) {
            mVehicleEnum = VehicleEnum.TANK;
            return;
        }
    }
}
