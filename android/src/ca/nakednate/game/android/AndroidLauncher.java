package ca.nakednate.game.android;

import android.os.Bundle;
import android.os.Handler;
import ca.nakednate.game.ScorchedPlanet;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;
import ca.nakednate.p2p.P2PServer;
import ca.nakednate.p2p.PeerDiscoverer;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	private static final String LOG_TAG = AndroidLauncher.class.getSimpleName();

    private P2PServer mP2PServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;

        mP2PServer = new P2PServer(this);
		final PeerDiscoverer peerDiscoverer = new PeerDiscoverer(this);



		ScorchedPlanet scorchedPlanet = new ScorchedPlanet();

		PeerDiscoveryListener peerDiscoveryListener = scorchedPlanet.getPeerDiscoveryListener();
		peerDiscoverer.setPeerDiscoveryListener(peerDiscoveryListener);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				peerDiscoverer.start();
			}
		}, 500);

		initialize(scorchedPlanet, config);
	}

//	@Override
//	public void onGameInfoRecieved(GameInfo gameInfo) {
//		Log.i(LOG_TAG, gameInfo.toJSON());
//		Toast.makeText(this, gameInfo.toJSON(), Toast.LENGTH_LONG);
//	}
//
//	@Override
//	public void onPlayerInfoRecieved(PlayerInfo playerInfo) {
//		Log.i(LOG_TAG, playerInfo.toJSON());
//		Toast.makeText(this, playerInfo.toJSON(), Toast.LENGTH_LONG);
//	}

	@Override
	protected void onDestroy() {
		mP2PServer.tearDown();
		super.onDestroy();
	}
}
