package ca.nakednate.p2p;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.widget.Toast;

public class P2PServer {
    private static final String SERVICE_NAME = "ScorchedPlanet";
    private static final String SERVICE_TYPE = "_http._tcp.";

    private NsdManager mNsdManager;
    private NsdManager.RegistrationListener mRegistrationListener;
    private Context mSoftKeyboard;

    public P2PServer(Context softKeyboard) {
        mSoftKeyboard = softKeyboard;
        registerService();
    }

    public Context getContext() {
        return mSoftKeyboard;
    }

    public void tearDown() {
        mNsdManager.unregisterService(mRegistrationListener);
    }

    public void registerService() {
        mRegistrationListener = initializeRegistrationListener();
        mNsdManager = (NsdManager) mSoftKeyboard.getSystemService(Context.NSD_SERVICE);

        SocketHandler socketHandler = new SocketHandler(this);
        socketHandler.setSocketOpenListener(new SocketListener() {
            @Override
            public void socketOpenFail() {
                Toast.makeText(mSoftKeyboard, "Error establishing server socket", Toast.LENGTH_LONG).show();
            }

            @Override
            public void socketOpenSuccess(int port) {
                // Create the NsdServiceInfo object, and populate it.
                NsdServiceInfo serviceInfo = new NsdServiceInfo();

                // The name is subject to change based on conflicts
                // with other services advertised on the same network.
                serviceInfo.setServiceName(SERVICE_NAME);

                serviceInfo.setServiceType(SERVICE_TYPE);
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
//                mServiceName = NsdServiceInfo.getServiceName();
                Toast.makeText(mSoftKeyboard, "Discovery Service Registered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
                Toast.makeText(mSoftKeyboard, "Discovery Service Registration Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. Only happens when you call NsdManager.unregisterService() and pass in this listener
                Toast.makeText(mSoftKeyboard, "Discovery Service UnRegistered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed.  Put debugging code here to determine why.
                Toast.makeText(mSoftKeyboard, "Discovery Service UnRegistration Failed", Toast.LENGTH_LONG).show();
            }
        };

        return registrationListener;
    }
}
