package com.heshaowei.myproj.im.server.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {
    public static ObjectIdSerializer INSTANCE = new ObjectIdSerializer();

    @Override
    public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null != objectId) {
            jsonGenerator.writeString(objectId.toString());
        }
    }
}
