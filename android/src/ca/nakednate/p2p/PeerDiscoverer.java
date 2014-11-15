package ca.nakednate.p2p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.ResolveListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.game.p2p.Peer;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PeerDiscoverer {

    public static final String SERVICE_NAME = "ScorchedPlanet";
    public static final String SERVICE_TYPE = "_http._tcp.";

    public static final String EXTRA_P2P_HOST = "EXTRA_P2P_HOST";
    public static final String EXTRA_P2P_PORT = "EXTRA_P2P_PORT";
    public static final String EXTRA_P2P_NAME = "EXTRA_P2P_NAME";


    private PeerDiscoveryListener mPeerDiscoveryListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager mNsdManager;
    private ResolveListener mResolveListener;
    private boolean mDiscoveringServices = false;

    private List<Peer> mDiscoveredServices = new ArrayList<Peer>();

    private Activity mActivity;

    public PeerDiscoverer(Activity activity) {
        mActivity = activity;
    }

    public void start() {
        initializeResolveListener();
        initializeDiscoveryListener();

        mNsdManager = (NsdManager) mActivity.getSystemService(Context.NSD_SERVICE);
        refresh();
    }

    /**
     * Run
     *
     * @param service
     */
    public void onServiceSelected(NsdServiceInfo service) {

        Intent intent = null; // TODO: find libgdx equivelant to intents
        intent.putExtra(EXTRA_P2P_HOST, service.getHost());
        intent.putExtra(EXTRA_P2P_NAME, service.getServiceName());
        intent.putExtra(EXTRA_P2P_PORT, service.getPort());

        // TODO: do the things, aka start the game
    }

    /**
     * Find new services out there
     */
    public void refresh() {
        discoverServices();
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }

    private void discoverServices() {
        if (mDiscoveringServices) {
            return;
        }

        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    private void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            public static final String TAG = "ResolveListener";

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);


                Peer peer = new Peer(serviceInfo.getHost(), serviceInfo.getPort());
                mDiscoveredServices.add(peer);

                mPeerDiscoveryListener.onPeersDiscovered(mDiscoveredServices);

                test_system_send_the_datas(serviceInfo);
            }
        };
    }

    /**
     * TEST PURPOSES ONLY, TO BE REMOVED
     * @param serviceInfo
     */
    private void test_system_send_the_datas(NsdServiceInfo serviceInfo) {
        try {
            Socket mServerSocket = new Socket(serviceInfo.getHost(),
                    serviceInfo.getPort());

            OutputStream out = mServerSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(out);

            PlayerInfo playerInfo = new PlayerInfo("DLAT");

            printWriter.print(playerInfo.toJSON());
            printWriter.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            public static final String TAG = "DiscoveryListener";

            //  Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");

                mDiscoveringServices = true;
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found!  Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().startsWith(SERVICE_NAME)) {
                    mNsdManager.resolveService(service, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(final NsdServiceInfo serviceInfo) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost" + serviceInfo);

                mDiscoveredServices.remove(serviceInfo);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
                mDiscoveringServices = false;
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
                mDiscoveringServices = false;
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
                mDiscoveringServices = false;
            }
        };
    }

    public void setPeerDiscoveryListener(PeerDiscoveryListener peerDiscoveryListener) {
        mPeerDiscoveryListener = peerDiscoveryListener;
    }
}
