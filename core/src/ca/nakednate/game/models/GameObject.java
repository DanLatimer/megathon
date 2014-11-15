package ca.nakednate.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for objects that are viewable in the game
 */
public abstract class GameObject extends BaseModel {

    private Vector2 mCoordinate;

    private Texture mTexture;

    public Vector2 getCoordinate() {
        return mCoordinate;
    }

    public void setCoordinate(Vector2 coordinate) {
        mCoordinate = coordinate;
    }

    public Texture getTexture() {
        return mTexture;
    }

    public void setTexture(Texture texture) {
        mTexture = texture;
    }
}
