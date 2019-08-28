package com.heshaowei.myproj.im.server.utils;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class GsonUtil {

    private static final Gson gson;

    static{
        gson = new GsonBuilder()
                .registerTypeAdapter(ObjectId.class, (JsonDeserializer<ObjectId>) (jsonElement, type, jsonDeserializationContext) -> {
                    if(null != jsonElement) {
                        return new ObjectId(jsonElement.getAsString());
                    }
                    return null;
                })
                .registerTypeAdapter(ObjectId.class, new JsonSerializer<ObjectId>() {
                    @Override
                    public JsonElement serialize(ObjectId objectId, Type type, JsonSerializationContext jsonSerializationContext) {
                        if(null != objectId) {
                            return new JsonParser().parse(objectId.toHexString());
                        }
                        return null;
                    }
                })
        .create();
    }


    public static Gson get(){
        return gson;
    }
}
