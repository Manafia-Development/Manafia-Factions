package com.github.manafia.factions.util.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;

public class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        return LocalTime.of(object.get("hours").getAsInt(), object.get("minutes").getAsInt(), object.get("seconds").getAsInt());
    }

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("hours", new JsonPrimitive(localTime.getHour()));
        object.add("minutes", new JsonPrimitive(localTime.getMinute()));
        object.add("seconds", new JsonPrimitive(localTime.getSecond()));
        return object;
    }
}
