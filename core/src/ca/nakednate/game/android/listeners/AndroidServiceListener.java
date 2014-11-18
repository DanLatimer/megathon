package ca.nakednate.game.android.listeners;

public interface AndroidServiceListener {

    public String onRequestPhoneName();

    public void onSaveSharedPref(String key, String value);
    public String onLoadSharedPref(String key, String defaultValue);
}
