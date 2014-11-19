package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class BaseEvent {

    private static final String LOG_TAG = BaseEvent.class.getSimpleName();
    private static final Gson mGson = new Gson();
    private ClientHandler mMessageOriginator;

    public String toJSON() {
        String json = mGson.toJson(this);
        json = removeNewlines(json);
        return json;
    }

    private String removeNewlines(String json) {
        return json.replaceAll("\\r?\\n", "");
    }

    public static <T extends BaseEvent> T fromJson(String serializedModel, Class<T> type) {
        T model = null;
        if (serializedModel != null && !serializedModel.isEmpty()) {
            try {
                model = mGson.fromJson(serializedModel, type);
            } catch (JsonSyntaxException e) {
                Gdx.app.error(LOG_TAG, "Could not de-serialize object");
            }
        }
        return model;
    }

    public ClientHandler getMessageOriginator() {
        return mMessageOriginator;
    }

    public void setMessageOriginator(ClientHandler messageOriginator) {
        mMessageOriginator = messageOriginator;
    }
}
