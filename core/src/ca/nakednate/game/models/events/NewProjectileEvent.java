package ca.nakednate.game.models.events;

import ca.nakednate.game.models.weapon.Projectile;

public class NewProjectileEvent extends BaseEvent {

    float mOriginX;
    float mOriginY;
    float mAzimuth; // angle of fire from the point of origin
    float mSpeed;
    float mRange;

    public NewProjectileEvent(Projectile projectile) {
        mOriginX = projectile.getOriginX();
        mOriginY = projectile.getOriginY();
        mAzimuth = projectile.getAzimuth();
        mSpeed = projectile.getSpeed();
        mRange = projectile.getRange();
    }

    public Projectile getProjectile() {
        return new Projectile(mOriginX, mOriginY, mAzimuth, mSpeed, mRange);
    }

    public float getOriginX() {
        return mOriginX;
    }

    public void setOriginX(float originX) {
        mOriginX = originX;
    }

    public float getOriginY() {
        return mOriginY;
    }

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
