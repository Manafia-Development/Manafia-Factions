package com.massivecraft.factions.zcore.util;

import com.massivecraft.factions.zcore.MPlugin;

import java.io.File;
import java.lang.reflect.Type;
import java.util.logging.Level;

// TODO: Give better name and place to differentiate from the entity-orm-ish system in "com.massivecraft.core.persist".

public class Persist {

    private final MPlugin p;

    public Persist(MPlugin p) {
        this.p = p;
    }

    // ------------------------------------------------------------ //
    // GET NAME - What should we call this type of object?
    // ------------------------------------------------------------ //

    public <T> T loadOrSaveDefault(T def, Class<T> clazz, String name) {
        return loadOrSaveDefault(def, clazz, getFile(name));
    }

    public <T> T loadOrSaveDefault(T def, Class<T> clazz, File file) {
        if (!file.exists()) {
            p.log("Creating default: " + file);
            this.save(def, file);
            return def;
        }

        T loaded = this.load(clazz, file);

        if (loaded == null) {
            p.log(Level.WARNING, "Couldn't load the persisten conf file.");
            return def;
        }

        return loaded;
    }

    // ------------------------------------------------------------ //
    // GET FILE - In which file would we like to store this object?
    // ------------------------------------------------------------ //

    public boolean save(Object instance) {
        return save(instance, getFile(instance));
    }

    public boolean save(Object instance, File file) {
        return DiscUtil.writeCatch(file, p.gson.toJson(instance), false);
    }


    // NICE WRAPPERS

    public <T> T load(Class<T> clazz, File file) {
        String content = DiscUtil.readCatch(file);
        if (content == null)
            return null;
        try {
            return p.gson.fromJson(content, clazz);
        } catch (Exception ex) {    // output the error message rather than full stack trace; error parsing the file, most likely
            p.log(Level.WARNING, ex.getMessage());
        }
        return null;
    }

    public File getFile(Object obj) {
        return getFile(getName(obj));
    }

    // SAVE

    public File getFile(String name) {
        return new File(p.getDataFolder(), name + ".json");
    }

    public static String getName(Object o) {
        return getName(o.getClass());
    }

    public static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    public boolean save(Object instance, String name) {
        return save(instance, getFile(name));
    }

    public boolean saveSync(Object instance) {
        return saveSync(instance, getFile(instance));
    }

    public boolean saveSync(Object instance, File file) {
        return DiscUtil.writeCatch(file, p.gson.toJson(instance), true);
    }

    // LOAD BY TYPE
    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOfT, String name) {
        return load(typeOfT, getFile(name));
    }

    @SuppressWarnings("unchecked")
    public <T> T load(Type typeOfT, File file) {
        String content = DiscUtil.readCatch(file);
        if (content == null)
            return null;
        try {
            return p.gson.fromJson(content, typeOfT);
        } catch (Exception ex) {    // output the error message rather than full stack trace; error parsing the file, most likely
            p.log(Level.WARNING, ex.getMessage());
        }
        return null;
    }
}
