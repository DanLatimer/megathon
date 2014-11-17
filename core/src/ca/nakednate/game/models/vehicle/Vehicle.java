package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.GameObject;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.models.weapon.Weapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.Array;

import java.util.Stack;

public abstract class Vehicle extends GameObject {

    private int mHitPoint;

    private float mSpeed;

    private Weapon mWeapon;

    protected Stack<DeployableWeapon> mDeployableWeapon;

    private static final double SQRT_2 = Math.sqrt(2);

    private Group mGroup = new Group();

    protected void init() {
        super.init();
        //we want the pivot to be further back on vehicles
        setOrigin(getTexture().getWidth() / 2, getTexture().getHeight() / 4);
        mGroup.addActor(this);
        this.setPosition(0, 0);
        getWeapon().setPosition(0, 0);
        getWeapon().setOrigin(getTexture().getWidth() / 2, getTexture().getHeight() / 4);
        getGroup().setOrigin(getTexture().getWidth() / 2, getTexture().getHeight() / 4);
        getGroup().addActor(getWeapon());
    }

    public DeployableWeapon deploy() {
        if (!getDeployableWeapon().empty()) {
            return getDeployableWeapon().pop();
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
        getDeployableWeapon().push(dw);
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

    private Stack<DeployableWeapon> getDeployableWeapon() {
        if (mDeployableWeapon == null) {
            mDeployableWeapon = new Stack<DeployableWeapon>();
        }
        return mDeployableWeapon;
    }

    public Group getGroup() {
        return mGroup;
    }

    public void moveTo(float x, float y, float dx, float dy) {

        getGroup().setPosition(x, y);

        float angle = MathUtils.atan2(dy, dx);

        float finalAngle = 0;

        //0 radian is east
        //0 degrees is north
        if (dx > 0) {
            finalAngle = MathUtils.PI2;
            if (dy > 0) {
                finalAngle -= (MathUtils.PI / 2) - angle;
            } else {
                finalAngle = ((3 * MathUtils.PI2) / 4) + angle;
            }
        } else {
            float halfPI = (MathUtils.PI / 2);
            if (dy > 0) {
                finalAngle = angle - halfPI;
            } else {
                finalAngle += MathUtils.PI + halfPI + angle;
            }
        }
        getGroup().setRotation(MathUtils.radiansToDegrees * finalAngle);
    }
}
