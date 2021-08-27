package com.massivecraft.factions.zcore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.massivecraft.factions.FactionsPlugin;

import java.io.*;
import java.util.EnumSet;
import java.util.HashMap;

public class LangUtil {

    public static void initLang() {
        File langFile = new File("plugins/Factions/lang.json");
        File langDir = new File("plugins/Factions");
        if (!langDir.exists()) {
            FactionsPlugin.getInstance().log("Directory /plugins/Factions wasn't found. Generating new directory...");
            langDir.mkdir();
        }
        if (!langFile.exists()) {
            try {
                FactionsPlugin.getInstance().log("File /plugins/Factions/lang.json wasn't found. Generating new file...");
                langFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonObject obj = new JsonObject();
        EnumSet.allOf(TL.class).forEach(lang -> {
            obj.addProperty(lang.getPath(), lang.getDefault());
        });
        try (FileWriter writer = new FileWriter(langFile.getPath())) {
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String prettyString = gson.toJson(obj);
            writer.write(prettyString);
            FactionsPlugin.getInstance().log("File lang.json was successfully loaded.");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            FactionsPlugin.getInstance().log("There was an error loading the language module. Please report this stacktrace to thmihnea.");
        }
    }

    public static HashMap<String, String> getLangMap() {
        File langFile = new File("plugins/Factions/lang.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(langFile.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(reader, HashMap.class);
    }

}
