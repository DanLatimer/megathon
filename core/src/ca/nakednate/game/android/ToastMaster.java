package ca.nakednate.game.android;

import ca.nakednate.game.android.listeners.ToastMasterListener;
import com.badlogic.gdx.Gdx;

public class ToastMaster {

    // TODO: determine if in debug better
    private static final boolean DEBUG = true;

    private static final String LOG_TAG = ToastMaster.class.getSimpleName();

    private static ToastMasterListener mToastMasterListener;

    public static void toast(String message, boolean isLong) {
        if(mToastMasterListener == null) {
            Gdx.app.error(LOG_TAG, "ToastMaster listener not set, unable to toast message: " + message);
        }

        mToastMasterListener.onToast(message, isLong);
    }

    public static void debugToast(String message, boolean isLong) {

        if(DEBUG) {
            toast(message, isLong);
        } else {
            Gdx.app.debug(LOG_TAG, message);
        }
    }

    public static void setToastMasterListener(ToastMasterListener toastMasterListener) {
        mToastMasterListener = toastMasterListener;
    }
}
