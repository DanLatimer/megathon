package ca.nakednate.game.models.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cannon extends Weapon {

    private static final float FIRE_RATE = 30.0f;
    private static final int AMMO = 4;
    private static final int PAYLOAD = 50;

    private static final String TEXTURE_PATH = "skin/sprites/vehicle_tank_gun.png";

    public Cannon() {
        super(FIRE_RATE, PAYLOAD, AMMO);
        setTexture(new Texture(Gdx.files.internal(TEXTURE_PATH)));
        super.init();
    }
}
