package ca.nakednate.game.models.weapon;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Bullet extends Projectile {

    private static final float DURATION = 3.0f;

    public Bullet(float originX, float originY, float azimuth, float range) {
        super(originX, originY, azimuth, DURATION, range);

        setPosition(originX, originY);

        float endPointX = MathUtils.cos(azimuth) * range;
        float endPointY = MathUtils.sin(azimuth) * range;

        MoveToAction moveToAction = new MoveToAction();

        moveToAction.setPosition(getX() + endPointX, getY() + endPointY);
        moveToAction.setDuration(mSpeed);

        addAction(moveToAction);
    }
}
