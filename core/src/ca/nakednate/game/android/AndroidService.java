package ca.nakednate.game.android;

import ca.nakednate.game.android.listeners.AndroidServiceListener;

public class AndroidService {

    public static final String DISPLAY_NAME = "DISPLAY_NAME";

    public static AndroidServiceListener mAndroidServiceListener;


    public static String getPhoneName() {
        if(mAndroidServiceListener == null) {
            return null;
        }

        return mAndroidServiceListener.onRequestPhoneName();
    }

    public static void onSaveSharedPref(String key, String value) {
        if(mAndroidServiceListener == null) {
            return;
        }

        mAndroidServiceListener.onSaveSharedPref(key, value);
    }

    public static String onLoadSharedPref(String key, String defaultValue) {
        if(mAndroidServiceListener == null) {
            return null;
        }

        return mAndroidServiceListener.onLoadSharedPref(key, defaultValue);
    }

    public static void setAndroidServiceListener(AndroidServiceListener androidServiceListener) {
        mAndroidServiceListener = androidServiceListener;
    }
}
