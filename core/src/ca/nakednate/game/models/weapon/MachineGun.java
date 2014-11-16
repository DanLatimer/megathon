package ca.nakednate.game.models.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MachineGun extends Weapon {

    private static final float FIRE_RATE = 120.0f;
    private static final int AMMO = 14;
    private static final int PAYLOAD = 10;

    private static final String TEXTURE_PATH = "skin/sprites/vehicle_jeep_gun.png";

    public MachineGun() {
        super(FIRE_RATE, PAYLOAD, AMMO);
        setTexture(new Texture(Gdx.files.internal(TEXTURE_PATH)));
        super.init();
    }
}
