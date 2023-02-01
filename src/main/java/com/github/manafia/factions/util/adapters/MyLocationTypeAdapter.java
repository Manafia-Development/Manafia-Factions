package com.github.manafia.factions.util.adapters;

import com.google.gson.*;
import com.github.manafia.factions.util.LazyLocation;
import com.github.manafia.factions.util.Logger;

import java.lang.reflect.Type;


public class MyLocationTypeAdapter implements JsonDeserializer<LazyLocation>, JsonSerializer<LazyLocation> {

    private static final String WORLD = "world";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String YAW = "yaw";
    private static final String PITCH = "pitch";

    @Override
    public LazyLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject obj = json.getAsJsonObject();

            String worldName = obj.get(WORLD).getAsString();
            double x = obj.get(X).getAsDouble();
            double y = obj.get(Y).getAsDouble();
            double z = obj.get(Z).getAsDouble();
            float yaw = obj.get(YAW).getAsFloat();
            float pitch = obj.get(PITCH).getAsFloat();

            return new LazyLocation(worldName, x, y, z, yaw, pitch);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.print("Error encountered while deserializing a LazyLocation.", Logger.PrefixType.WARNING);
            return null;
        }
    }

    @Override
    public JsonElement serialize(LazyLocation src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        try {
            obj.addProperty(WORLD, src.getWorldName());
            obj.addProperty(X, src.getX());
            obj.addProperty(Y, src.getY());
            obj.addProperty(Z, src.getZ());
            obj.addProperty(YAW, src.getYaw());
            obj.addProperty(PITCH, src.getPitch());

            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.print("Error encountered while serializing a LazyLocation.", Logger.PrefixType.WARNING);
            return obj;
        }
    }
}
