package ca.nakednate.game.models;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class for objects that are viewable in the game
 */
public abstract class GameObject extends BaseModel {
    private Texture mTexture;

    public Texture getTexture() {
        return mTexture;
    }

    public void setTexture(Texture texture) {
        mTexture = texture;
    }
}
