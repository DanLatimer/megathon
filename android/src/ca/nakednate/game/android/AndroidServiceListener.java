package ca.nakednate.game.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AndroidServiceListener implements ca.nakednate.game.android.listeners.AndroidServiceListener {

    private static final String PREFS_FILENAME = "ca.nakednate.game.android.SHARED_PREFS";

    Activity mActivity;

    public AndroidServiceListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public String onRequestPhoneName() {
        return android.os.Build.MANUFACTURER + android.os.Build.MODEL;
    }

    @Override
    public void onSaveSharedPref(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String onLoadSharedPref(String key, String defaultValue) {
        return getPrefs().getString(key, defaultValue);
    }

    private SharedPreferences getPrefs() {
        return mActivity.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences sharedPreferences = getPrefs();
        return sharedPreferences.edit();
    }
}
