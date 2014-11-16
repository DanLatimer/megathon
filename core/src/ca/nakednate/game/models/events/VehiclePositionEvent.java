package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class VehiclePositionEvent extends BaseEvent {

    float mX;
    float mY;

    public VehiclePositionEvent(ClientHandler messageOriginator, float x, float y) {
        super(messageOriginator);
        mX = x;
        mY = y;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }
}
