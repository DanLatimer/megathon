package ca.nakednate.game.models.weapon;

import ca.nakednate.game.models.GameObject;

public class Projectile extends GameObject {

    float mOriginX;
    float mOriginY;
    float mAzimuth; // angle of fire from the point of origin
    float mSpeed;
    float mRange;

    public Projectile(float originX, float originY, float azimuth, float speed, float range) {
        mOriginX = originX;
        mOriginY = originY;
        mAzimuth = azimuth;
        mSpeed = speed;
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

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }

    public float getRange() {
        return mRange;
    }

    public void setRange(float range) {
        mRange = range;
    }
}
