package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.GameObject;
import ca.nakednate.game.models.weapon.DeployableWeapon;
import ca.nakednate.game.models.weapon.Weapon;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;

import java.util.Stack;

public abstract class Vehicle extends GameObject {

    private int mHitPoint;

    private float mSpeed;

    private Weapon mWeapon;

    protected Stack<DeployableWeapon> mDeployableWeapon;

    double sqrt2 = Math.sqrt(2);

    protected void init() {
        setBounds(getX(),getY(),getTexture().getWidth(),getTexture().getHeight());
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

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(getTexture(),this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                getTexture().getWidth(),getTexture().getHeight(),false,false);
    }

    protected void moveTo(float x, float y, int speed) {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(x, y);
        float dx = x - getX();
        float dy = y - getY();
        //using breshenhams approximator
        double distance = ((sqrt2 - 1)* dx ) + dy;

        float angleInRad = MathUtils.atan2(dy, dx);

        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(MathUtils.radiansToDegrees * angleInRad);
        rotateAction.setDuration(0.25f);
        addAction(rotateAction);

        moveAction.setDuration((float) (distance / speed));
        addAction(moveAction);
    }
}
