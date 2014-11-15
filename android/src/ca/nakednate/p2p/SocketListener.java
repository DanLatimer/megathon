package ca.nakednate.p2p;

public interface SocketListener {
    void socketOpenFail();

    void socketOpenSuccess(int port);
}
