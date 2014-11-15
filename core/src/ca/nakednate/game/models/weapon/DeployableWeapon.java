package ca.nakednate.game.models.weapon;

public abstract class DeployableWeapon extends Weapon {

    private int mProximity;
    private int mHitPoint;
    private int mTimeOut;

    public DeployableWeapon(float fireRate, int payLoad, int ammo,
                            int proximity, int hitPoint, int timeout) {
        super(fireRate, payLoad, ammo);
        setProximity(proximity);
        setHitPoint(hitPoint);
        setTimeOut(timeout);
    }

    public int getProximity() {
        return mProximity;
    }

    private void setProximity(int proximity) {
        mProximity = proximity;
    }

    public int getHitPoint() {
        return mHitPoint;
    }

    private void setHitPoint(int hitPoint) {
        mHitPoint = hitPoint;
    }

    public int getTimeOut() {
        return mTimeOut;
    }

    private void setTimeOut(int timeOut) {
        mTimeOut = timeOut;
    }

}
