package ca.nakednate.game.android;

import android.os.Bundle;
import ca.nakednate.game.ScorchedPlanet;
import ca.nakednate.p2p.P2PServer;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    private P2PServer mP2PServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new ScorchedPlanet(), config);

        mP2PServer = new P2PServer(this);
        config.useAccelerometer = false;
        config.useCompass = false;
        initialize(new ScorchedPlanet(), config);
    }
}
