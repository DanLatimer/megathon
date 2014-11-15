package ca.nakednate.game.android;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import ca.nakednate.game.ScorchedPlanet;
import ca.nakednate.game.models.GameInfo;
import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.p2p.MessageHandler;
import ca.nakednate.p2p.P2PServer;
import ca.nakednate.p2p.PeerDiscoverer;
import ca.nakednate.p2p.listeners.MainScreenListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication  implements MainScreenListener {

	private static final String LOG_TAG = AndroidLauncher.class.getSimpleName();

    private P2PServer mP2PServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new ScorchedPlanet(), config);

        mP2PServer = new P2PServer(this);
		PeerDiscoverer peerDiscoverer = new PeerDiscoverer(this);
		MessageHandler.setMainScreenListener(this);

		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new ScorchedPlanet(), config);
	
	}

	@Override
	public void onGameInfoRecieved(GameInfo gameInfo) {
		Log.i(LOG_TAG, gameInfo.toJSON());
		Toast.makeText(this, gameInfo.toJSON(), Toast.LENGTH_LONG);
	}

	@Override
	public void onPlayerInfoRecieved(PlayerInfo playerInfo) {
		Log.i(LOG_TAG, playerInfo.toJSON());
		Toast.makeText(this, playerInfo.toJSON(), Toast.LENGTH_LONG);
	}

	@Override
	protected void onDestroy() {
		mP2PServer.tearDown();
		super.onDestroy();
	}
}
