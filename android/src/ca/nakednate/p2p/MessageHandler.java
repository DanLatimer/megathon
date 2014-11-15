package ca.nakednate.p2p;

import ca.nakednate.game.models.BaseModel;
import ca.nakednate.game.models.DummyModel;
import ca.nakednate.game.models.GameInfo;
import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.p2p.listeners.MainScreenListener;

import java.io.BufferedReader;

/**
 * Parses commands and executes them as required
 */
public class MessageHandler {

    private MainScreenListener mMainScreenListener = null;

    public void setMainScreenListener(MainScreenListener mainScreenListener) {
        mMainScreenListener = mainScreenListener;
    }

    /**
     * Parses a message and handles it
     *
     * @param bufferedReader
     * @return returns true when no more input
     */
    public void handleMessages(BufferedReader bufferedReader) {
        while (true) {
            Class clazz = getClass(bufferedReader);
            if (clazz == null) {
                return;
            }

            Object object = getObject(clazz, bufferedReader);
            if (object == null) {
                return;
            }

            handleObject(clazz, object);
        }
    }

    /**
     * We should be able to get around needing this method and go directly to the
     * class specific versions. Can't think of anything quick to do it though.
     *
     * @param clazz
     * @param object
     */
    private void handleObject(Class clazz, Object object) {
        if (clazz == GameInfo.class) {
            handleObject((GameInfo) object);
        } else if (clazz == PlayerInfo.class) {
            handleObject((PlayerInfo) object);
        }
    }

    /**
     * Handles GameInfo objects
     *
     * @param gameInfo
     */
    private void handleObject(GameInfo gameInfo) {
        if (mMainScreenListener != null) {
            mMainScreenListener.onGameInfoRecieved(gameInfo);
        }
    }

    /**
     * Handles PlayerInfo objects
     *
     * @param playerInfo
     */
    private void handleObject(PlayerInfo playerInfo) {
        if (mMainScreenListener != null) {
            mMainScreenListener.onPlayerInfoRecieved(playerInfo);
        }
    }

    /**
     * Continue parsing lines until you find one that contains a class name that
     * we can instantiate
     *
     * @param bufferedReader
     * @return class to deserialize or null on EOF
     */
    private Class getClass(BufferedReader bufferedReader) {
        while (true) {
            String line = getLine(bufferedReader);
            if (line == null) {
                return null;
            }

            try {
                Class clazz = Class.forName(line);
                return clazz;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parse an object from the next line in the buffered reader
     *
     * @param clazz
     * @param bufferedReader
     * @return return null if EOF, return DummyModel if unparsable
     */
    private Object getObject(Class clazz, BufferedReader bufferedReader) {
        String line = getLine(bufferedReader);
        if (line == null) {
            return null;
        }

        Object object = BaseModel.fromJson(line, clazz);
        if (object == null) {
            return new DummyModel();
        }

        return object;
    }

    /**
     * Gets a line, or null on EOF
     *
     * @param bufferedReader
     * @return line of data
     */
    private String getLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
