package com.wfuertes.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantAdapter implements JsonDeserializer<Instant>, JsonSerializer<Instant> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
        } catch (RuntimeException err) {
            return Instant.from(DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC).parse(json.getAsJsonPrimitive().getAsString()));
        }
    }

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC).format(instant));
    }
}
