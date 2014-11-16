package ca.nakednate.game.models.events;

import ca.nakednate.game.models.vehicle.Vehicle;
import ca.nakednate.game.p2p.ClientHandler;

public class VehiclePositionEvent extends BaseEvent {

    float mX;
    float mY;
    float mHeading;

    public VehiclePositionEvent(ClientHandler messageOriginator, Vehicle vehicle) {
        super(messageOriginator);
        mX = vehicle.getGroup().getX();
        mY = vehicle.getGroup().getY();
        mHeading = vehicle.getGroup().getRotation();
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    public float getHeading() {
        return mHeading;
    }
}
