package ca.nakednate.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getTexture(), this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                getTexture().getWidth(), getTexture().getHeight(), false, false);
    }

    protected void init() {
        setBounds(getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
        setOrigin(getTexture().getWidth() / 2, getTexture().getHeight() / 2);
    }

}
