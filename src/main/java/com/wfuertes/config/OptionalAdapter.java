package com.wfuertes.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalAdapter implements JsonDeserializer<Optional<?>>, JsonSerializer<Optional<?>> {

    @Override
    public Optional<?> deserialize(JsonElement json,
                                   Type type,
                                   JsonDeserializationContext context) throws JsonParseException {
        return Optional.of(context.deserialize(json, type));
    }

    @Override
    public JsonElement serialize(Optional<?> optional, Type type, JsonSerializationContext context) {
        return optional.map(context::serialize).orElse(null);
    }
}
