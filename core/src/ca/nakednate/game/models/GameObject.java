package ca.nakednate.game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

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

    private Rectangle mBounds = new Rectangle();

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(getTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation(),
                0, 0, getTexture().getWidth(), getTexture().getHeight(), false, false);
        updateBounds();
    }

    protected void init() {
        TextureRegion tr = new TextureRegion(getTexture());
        setBounds(getX(), getY(), tr.getRegionWidth(), tr.getRegionHeight());
        setOrigin(tr.getRegionWidth() / 2, tr.getRegionHeight() / 2);
    }

    private void updateBounds() {
        mBounds.set(getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        return mBounds;
    }

}
