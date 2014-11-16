package ca.nakednate.game.android;

import android.os.Bundle;
import android.os.Handler;
import ca.nakednate.game.MainMenuScreen;
import ca.nakednate.game.UnfriendlyFire;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.ClientManager;
import ca.nakednate.game.p2p.listeners.DiscoveryServiceListener;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;
import ca.nakednate.p2p.P2PServer;
import ca.nakednate.p2p.PeerDiscoverer;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements DiscoveryServiceListener {

    private static final String LOG_TAG = AndroidLauncher.class.getSimpleName();

    private P2PServer mP2PServer;
	private PeerDiscoverer mPeerDiscoverer;

	UnfriendlyFire mUnfriendlyFire;
	Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		ClientManager.setDiscoveryServiceListener(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;

        mP2PServer = new P2PServer(this);
		mUnfriendlyFire = new UnfriendlyFire();


		initialize(mUnfriendlyFire, config);

		MainMenuScreen.setDiscoveryServiceListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		mPeerDiscoverer = new PeerDiscoverer(this);
		PeerDiscoveryListener peerDiscoveryListener = mUnfriendlyFire.getPeerDiscoveryListener();
		mPeerDiscoverer.setPeerDiscoveryListener(peerDiscoveryListener);

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPeerDiscoverer.start();
			}
		}, 3000);
	}

	@Override
	protected void onStop() {
		mP2PServer.tearDown();
		mPeerDiscoverer.tearDown();

		for(ClientHandler clientHandler : ClientManager.getClientHandlers()) {
			clientHandler.teardown();
		}

		super.onStop();
	}

	@Override
	public void onDiscoveryRefresh() {
		mPeerDiscoverer.refresh();
	}
}
