package ca.nakednate.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Base class for data models
 */
public abstract class BaseModel extends Actor {

    private static final String LOG_TAG = BaseModel.class.getSimpleName();

    private static final Gson mGson = new Gson();

    public String toJSON() {
        String json = mGson.toJson(this);
        json = removeNewlines(json);
        return json;
    }

    private String removeNewlines(String json) {
        return json.replaceAll("\\r?\\n", "");
    }

    public static <T extends BaseModel> T fromJson(String serializedModel, Class<T> type) {
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
}
