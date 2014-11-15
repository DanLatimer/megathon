package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.GameObject;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.models.weapon.Weapon;

import java.util.Stack;

public abstract class Vehicle extends GameObject {

    private int mHitPoint;

    private float mSpeed;

    private Weapon mWeapon;

    protected Stack<DeployableWeapon> mDeployableWeapon;

    public DeployableWeapon deploy() {
        if (!mDeployableWeapon.empty()) {
            return mDeployableWeapon.pop();
        }
        return null;
    }

    public boolean fire() {
        return mWeapon.fire();
    }

    public void addAmmo(int ammo) {
        mWeapon.addAmmo(ammo);
    }

    protected void addDeployableWeapon(DeployableWeapon dw) {
        mDeployableWeapon.push(dw);
    }

    public abstract void addDeployableWeapon(int quantity);

    /**
     * Setters/Getters
     */

    public void setHitPoint(int hitPoint) {
        mHitPoint = hitPoint;
    }

    public int getHitPoint() {
        return mHitPoint;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public Weapon getWeapon() {
        return mWeapon;
    }

    public void setWeapon(Weapon primary) {
        mWeapon = primary;
    }
}
