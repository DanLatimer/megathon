package ca.nakednate.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private P2PServer mApp;
    private Socket mClient;

    public ClientHandler(Socket client, P2PServer app) {
        mApp = app;
        mClient = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));


            // TODO: Parse command objects that have been JSON-ified

//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                int keyCode;
//                try {
//                    keyCode = Integer.parseInt(line);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//
//                Instrumentation instrumentation = new Instrumentation();
//                instrumentation.sendKeyDownUpSync(keyCode);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
