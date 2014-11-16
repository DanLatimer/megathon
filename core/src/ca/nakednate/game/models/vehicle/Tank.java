package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.weapon.Cannon;
import ca.nakednate.game.models.weapon.Mine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tank extends Vehicle {

    private static final int HP = 200;
    private static final int INITIAL_DEPLOYABLE_AMMO = 3;

    public Tank() {
        setTexture(new Texture(Gdx.files.internal("skin/vehicle_jeep_red.png")));
        super.init();
        setHitPoint(HP);
        setWeapon(new Cannon());
        addDeployableWeapon(INITIAL_DEPLOYABLE_AMMO);
    }

    public void addDeployableWeapon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            addDeployableWeapon(new Mine());
        }
    }
}
