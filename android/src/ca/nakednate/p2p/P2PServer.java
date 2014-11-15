package ca.nakednate.p2p;

import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.widget.Toast;

public class P2PServer {

    private static String mServiceName;

    private NsdManager mNsdManager;
    private NsdManager.RegistrationListener mRegistrationListener;
    private Activity mActivity;

    public P2PServer(Activity activity) {
        mActivity = activity;
        registerService();
    }

    public Activity getActivity() {
        return mActivity;
    }

    public static String getServiceName() {
        return mServiceName;
    }

    public void tearDown() {
        mNsdManager.unregisterService(mRegistrationListener);
    }

    public void registerService() {
        mRegistrationListener = initializeRegistrationListener();
        mNsdManager = (NsdManager) mActivity.getSystemService(Context.NSD_SERVICE);

        SocketHandler socketHandler = new SocketHandler(this);
        socketHandler.setSocketOpenListener(new SocketListener() {
            @Override
            public void socketOpenFail() {
                Toast.makeText(mActivity, "Error establishing server socket", Toast.LENGTH_LONG).show();
            }

            @Override
            public void socketOpenSuccess(int port) {
                // Create the NsdServiceInfo object, and populate it.
                NsdServiceInfo serviceInfo = new NsdServiceInfo();

                // The name is subject to change based on conflicts
                // with other services advertised on the same network.
                serviceInfo.setServiceName(PeerDiscoverer.SERVICE_NAME);

                serviceInfo.setServiceType(PeerDiscoverer.SERVICE_TYPE);
                serviceInfo.setPort(port);

                mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
            }
        });
        new Thread(socketHandler).start();
    }

    public NsdManager.RegistrationListener initializeRegistrationListener() {
        NsdManager.RegistrationListener registrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                /*
                 * Save the service name.  Android may have changed it in order to
                 * resolve a conflict, so update the name you initially requested
                 * with the name Android actually used.
                 */
                mServiceName = NsdServiceInfo.getServiceName();
                Toast.makeText(mActivity, "Discovery Service Registered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
                Toast.makeText(mActivity, "Discovery Service Registration Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. Only happens when you call NsdManager.unregisterService() and pass in this listener
                Toast.makeText(mActivity, "Discovery Service UnRegistered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed.  Put debugging code here to determine why.
                Toast.makeText(mActivity, "Discovery Service UnRegistration Failed", Toast.LENGTH_LONG).show();
            }
        };

        return registrationListener;
    }
}
