package ca.nakednate.game.p2p;

import ca.nakednate.game.android.ToastMaster;
import ca.nakednate.game.models.BaseModel;
import ca.nakednate.game.models.events.*;
import ca.nakednate.game.p2p.listeners.GameStateListener;
import ca.nakednate.game.p2p.listeners.MainScreenListener;
import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;

/**
 * Parses commands and executes them as required
 */
public class MessageHandler {

    private static final String LOG_TAG = MessageHandler.class.getSimpleName();
    private static MainScreenListener mMainScreenListener = null;
    private static GameStateListener mGameStateListener = null;

    public static void setMainScreenListener(MainScreenListener mainScreenListener) {
        mMainScreenListener = mainScreenListener;
    }

    public static void setGameStateListener(GameStateListener gameStateListener) {
        mGameStateListener = gameStateListener;
    }

    private ClientHandler mClientHandler;

    public MessageHandler(ClientHandler clientHandler) {
        mClientHandler = clientHandler;

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

            BaseEvent event = getEvent(clazz, bufferedReader);
            if (event == null) {
                return;
            }

            handleEvent(clazz, event);
        }
    }

    /**
     * We should be able to get around needing this method and go directly to the
     * class specific versions. Can't think of anything quick to do it though.
     *
     * @param clazz
     * @param event
     */
    private void handleEvent(Class clazz, BaseEvent event) {
        if (clazz == NewPlayerEvent.class) {
            handleEvent((NewPlayerEvent) event);
        } else if (clazz == GameRequestEvent.class) {
            handleEvent((GameRequestEvent) event);
        } else if (clazz == VehicleChoiceEvent.class) {
            handleEvent((VehicleChoiceEvent) event);
        } else if (clazz == VehiclePositionEvent.class) {
            handleEvent((VehiclePositionEvent) event);
        }
    }

    /**
     * Event when we get a display name
     *
     * @param newPlayerEvent
     */
    private void handleEvent(NewPlayerEvent newPlayerEvent) {
        if (mMainScreenListener != null) {
            ToastMaster.debugToast("New Player Event: " + newPlayerEvent.getPlayerInfo().getDisplayName(), false);
            mMainScreenListener.onNewPlayerRecieved(newPlayerEvent);
        }
    }

    /**
     * Event where opponent requests game
     *
     * @param gameRequestEvent
     */
    private void handleEvent(GameRequestEvent gameRequestEvent) {
        if (mMainScreenListener != null) {
            mMainScreenListener.onGameRequestEvent(gameRequestEvent);
        }
    }

    /**
     * Opponent telling you what vehicle they chose
     *
     * @param vehicleChoiceEvent
     */
    private void handleEvent(VehicleChoiceEvent vehicleChoiceEvent) {
        if (mGameStateListener != null) {
            ToastMaster.debugToast("New Vehicle Choice Event: " + vehicleChoiceEvent.getVehicle(), false);
            mGameStateListener.onVehicleChoiceEvent(vehicleChoiceEvent);
        }
    }

    private void handleEvent(VehiclePositionEvent vehiclePositionEvent) {
        if (mGameStateListener != null) {
            mGameStateListener.onVehiclePositionEvent(vehiclePositionEvent);
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
    private BaseEvent getEvent(Class clazz, BufferedReader bufferedReader) {
        String line = getLine(bufferedReader);
        if (line == null) {
            return null;
        }

        BaseEvent event = (BaseEvent) BaseModel.fromJson(line, clazz);
        if (event == null) {
            return new DummyEvent(mClientHandler);
        }

        event.setMessageOriginator(mClientHandler);

        return event;
    }

    /**
     * Gets a line, or null on EOF
     *
     * @param bufferedReader
     * @return line of data
     */
    private String getLine(BufferedReader bufferedReader) {
        try {
            String line = bufferedReader.readLine();
            Gdx.app.log(LOG_TAG, "LINE: " + line);
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            mClientHandler.attemptReconnect();
        }
        return null;
    }


}
