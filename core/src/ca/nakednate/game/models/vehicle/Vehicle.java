package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.GameObject;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.models.weapon.Weapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
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

    protected void init() {
        super.init();
        //we want the pivot to be further back on vehicles
        setOrigin(getTexture().getWidth() / 2, getTexture().getHeight() / 4);
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

    public void moveTo(float x, float y) {

        if (getActions().size > 0) {
            Array<Action> actions = getActions();
            for (int i = 0; i < actions.size; i++) {
                getActions().removeIndex(i);
            }
        }
        y = Gdx.graphics.getHeight() - y;

        float dx = x - getX();
        float dy = y - getY();
        //using breshenhams approximator
        double distance = Math.sqrt((dx*dx)+(dy*dy));

        float angle = MathUtils.atan2(dy, dx);

        float finalAngle = 0;

        // i hate trig

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


        RotateToAction rotateAction = new RotateToAction();
        rotateAction.setRotation(MathUtils.radiansToDegrees * finalAngle);
        rotateAction.setDuration(0.25f);
        addAction(rotateAction);

        if (mSpeed == 0 || distance == 0) {
            return;
        }

        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(x, y);

        moveAction.setDuration((float) (distance / mSpeed));
        addAction(moveAction);
    }
}
