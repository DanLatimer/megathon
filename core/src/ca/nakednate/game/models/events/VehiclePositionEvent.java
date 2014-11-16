package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class VehiclePositionEvent extends BaseEvent {

    float mX;
    float mY;
    float mHeading;

    public VehiclePositionEvent(ClientHandler messageOriginator, float x, float y, float heading) {
        super(messageOriginator);
        mX = x;
        mY = y;
        mHeading = heading;
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
