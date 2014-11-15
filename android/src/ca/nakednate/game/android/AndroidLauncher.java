package ca.nakednate.game.android;

import android.os.Bundle;
import android.os.Handler;
import ca.nakednate.game.UnfriendlyFire;
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



		UnfriendlyFire unfriendlyFire = new UnfriendlyFire();

		PeerDiscoveryListener peerDiscoveryListener = unfriendlyFire.getPeerDiscoveryListener();
		peerDiscoverer.setPeerDiscoveryListener(peerDiscoveryListener);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				peerDiscoverer.start();
			}
		}, 3000);

		initialize(unfriendlyFire, config);
	}

	@Override
	protected void onDestroy() {
		mP2PServer.tearDown();
		super.onDestroy();
	}
}
