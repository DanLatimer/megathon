package ca.nakednate.game.models.weapon;

import ca.nakednate.game.models.GameObject;

public abstract class Weapon extends GameObject {

    private float mFireRate;
    private int mPayLoad;
    private int mAmmo;



    public Weapon(float fireRate, int payLoad, int ammo) {
        setFireRate(fireRate);
        setPayLoad(payLoad);
        addAmmo(ammo);
    }

    public float getFireRate() {
        return mFireRate;
    }

    /**
     * @param fireRate projectiles per minute
     */
    private void setFireRate(float fireRate) {
        mFireRate = fireRate;
    }

    public int getAmmo() {
        return mAmmo;
    }

    private void setPayLoad(int payLoad) {
        mPayLoad = payLoad;
    }

    public int getPayLoad() {
        return mPayLoad;
    }

    public void addAmmo(int ammo) {
        mAmmo += ammo;
    }

    public boolean fire() {
        if (mAmmo > 0) {
            mAmmo--;
            return true;
        }
        return false;
    }

}
