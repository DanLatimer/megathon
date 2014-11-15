package ca.nakednate.game.models;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Base class for data models
 */
public class BaseModel {

    private static final String LOG_TAG = BaseModel.class.getSimpleName();

    private static final Gson mGson = new Gson();

    public String toJSON() {
        return mGson.toJson(this);
    }

    public static <T extends BaseModel> T fromJson(String serializedModel, Class<T> type) {
        T model = null;
        if (serializedModel != null && !serializedModel.isEmpty()) {
            try {
                model = mGson.fromJson(serializedModel, type);
            } catch (JsonSyntaxException e) {
                Log.e(LOG_TAG, "Could not de-serialize object");
            }
        }
        return model;
    }
}
