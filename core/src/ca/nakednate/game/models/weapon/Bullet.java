package ca.nakednate.game.models.weapon;

import ca.nakednate.game.models.GameObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Bullet extends GameObject {

    private static final float DURATION = 3.0f;

    public Bullet(float x, float y, float range, float radians) {
        setPosition(x, y);

        float dx = MathUtils.cos(radians) * range;
        float dy = MathUtils.sin(radians) * range;

        MoveToAction moveToAction = new MoveToAction();

        moveToAction.setPosition(getX() + dx, getY() + dy);
        moveToAction.setDuration(DURATION);

        addAction(moveToAction);
    }
}
