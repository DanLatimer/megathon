package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class NewProjectileEvent extends BaseEvent {

    float mOriginX;
    float mOriginY;
    float mAzimuth; // angle of fire from the point of origin
    float mRange;

    public NewProjectileEvent(ClientHandler messageOriginator, float originX, float originY, float azimuth, float range) {
        super(messageOriginator);
        mOriginX = originX;
        mOriginY = originY;
        mAzimuth = azimuth;
        mRange = range;
    }

    @Override
    public float getOriginX() {
        return mOriginX;
    }

    @Override
    public void setOriginX(float originX) {
        mOriginX = originX;
    }

    @Override
    public float getOriginY() {
        return mOriginY;
    }

    @Override
    public void setOriginY(float originY) {
        mOriginY = originY;
    }

    public float getAzimuth() {
        return mAzimuth;
    }

    public void setAzimuth(float azimuth) {
        mAzimuth = azimuth;
    }

    public float getRange() {
        return mRange;
    }

    public void setRange(float range) {
        mRange = range;
    }
}
