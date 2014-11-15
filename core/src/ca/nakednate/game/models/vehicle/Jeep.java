package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.weapon.MachineGun;
import ca.nakednate.game.models.weapon.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Jeep extends Vehicle {

    private static final int HP = 100;
    private static final int INITIAL_DEPLOYABLE_AMMO = 3;

    public Jeep() {
        setHitPoint(HP);
        setWeapon(new MachineGun());
        addDeployableWeapon(INITIAL_DEPLOYABLE_AMMO);
        setTexture(new Texture(Gdx.files.internal("skin/vehicle_jeep_red.png")));
        setBounds(getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
    }

    public void addDeployableWeapon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            addDeployableWeapon(new Turret());
        }
    }
}
