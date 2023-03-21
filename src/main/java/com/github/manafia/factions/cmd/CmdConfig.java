package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

public class CmdConfig extends FCommand {

    private static HashMap<String, String> properFieldNames = new HashMap<>();

    public CmdConfig() {
        super();
        this.aliases.addAll(Aliases.config);

        this.requiredArgs.add("setting");
        this.requiredArgs.add("value");

        this.requirements = new CommandRequirements.Builder(Permission.CONFIG)
                .noErrorOnManyArgs()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        // store a lookup map of lowercase field names paired with proper capitalization field names
        // that way, if the person using this command messes up the capitalization, we can fix that
        if (properFieldNames.isEmpty()) {
            Field[] fields = Conf.class.getDeclaredFields();
            for (Field field : fields) {
                properFieldNames.put(field.getName().toLowerCase(), field.getName());
            }
        }

        String field = context.argAsString(0).toLowerCase();
        if (field.startsWith("\"") && field.endsWith("\"")) {
            field = field.substring(1, field.length() - 1);
        }
        String fieldName = properFieldNames.get(field);

        if (fieldName == null || fieldName.isEmpty()) {
            context.msg(TL.COMMAND_CONFIG_NOEXIST, field);
            return;
        }

        String success;

        StringBuilder value = new StringBuilder(context.args.get(1));
        for (int i = 2; i < context.args.size(); i++) {
            value.append(' ').append(context.args.get(i));
        }

        try {
            Field target = Conf.class.getField(fieldName);

            // boolean
            if (target.getType() == boolean.class) {
                boolean targetValue = context.strAsBool(value.toString());
                target.setBoolean(null, targetValue);

                if (targetValue) {
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_SET_TRUE;
                } else {
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_SET_FALSE;
                }
            }

            // int
            else if (target.getType() == int.class) {
                try {
                    int intVal = Integer.parseInt(value.toString());
                    target.setInt(null, intVal);
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_OPTIONSET + intVal + ".";
                } catch (NumberFormatException ex) {
                    context.sendMessage(TL.COMMAND_CONFIG_INTREQUIRED.format(fieldName));
                    return;
                }
            }

            // long
            else if (target.getType() == long.class) {
                try {
                    long longVal = Long.parseLong(value.toString());
                    target.setLong(null, longVal);
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_OPTIONSET + longVal + ".";
                } catch (NumberFormatException ex) {
                    context.sendMessage(TL.COMMAND_CONFIG_LONGREQUIRED.format(fieldName));
                    return;
                }
            }

            // double
            else if (target.getType() == double.class) {
                try {
                    double doubleVal = Double.parseDouble(value.toString());
                    target.setDouble(null, doubleVal);
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_OPTIONSET + doubleVal + ".";
                } catch (NumberFormatException ex) {
                    context.sendMessage(TL.COMMAND_CONFIG_DOUBLEREQUIRED.format(fieldName));
                    return;
                }
            }

            // float
            else if (target.getType() == float.class) {
                try {
                    float floatVal = Float.parseFloat(value.toString());
                    target.setFloat(null, floatVal);
                    success = "\"" + fieldName + TL.COMMAND_CONFIG_OPTIONSET + floatVal + ".";
                } catch (NumberFormatException ex) {
                    context.sendMessage(TL.COMMAND_CONFIG_FLOATREQUIRED.format(fieldName));
                    return;
                }
            }

            // String
            else if (target.getType() == String.class) {
                target.set(null, value.toString());
                success = "\"" + fieldName + TL.COMMAND_CONFIG_OPTIONSET + value + "\".";
            }

            // ChatColor
            else if (target.getType() == ChatColor.class) {
                ChatColor newColor = null;
                try {
                    newColor = ChatColor.valueOf(value.toString().toUpperCase());
                } catch (IllegalArgumentException ex) {

                }
                if (newColor == null) {
                    context.sendMessage(TL.COMMAND_CONFIG_INVALID_COLOUR.format(fieldName, value.toString().toUpperCase()));
                    return;
                }
                target.set(null, newColor);
                success = "\"" + fieldName + TL.COMMAND_CONFIG_COLOURSET + value.toString().toUpperCase() + "\".";
            }

            // Set<?> or other parameterized collection
            else if (target.getGenericType() instanceof ParameterizedType) {
                ParameterizedType targSet = (ParameterizedType) target.getGenericType();
                Type innerType = targSet.getActualTypeArguments()[0];

                // not a Set, somehow, and that should be the only collection we're using in Conf.java
                if (targSet.getRawType() != Set.class) {
                    context.sendMessage(TL.COMMAND_CONFIG_INVALID_COLLECTION.format(fieldName));
                    return;
                }

                // Set<Material>
                else if (innerType == Material.class) {
                    Material newMat = null;
                    try {
                        newMat = Material.valueOf(value.toString().toUpperCase());
                    } catch (IllegalArgumentException ex) {

                    }
                    if (newMat == null) {
                        context.sendMessage(TL.COMMAND_CONFIG_INVALID_MATERIAL.format(fieldName, value.toString().toUpperCase()));
                        return;
                    }

                    @SuppressWarnings("unchecked") Set<Material> matSet = (Set<Material>) target.get(null);

                    // Material already present, so remove it
                    if (matSet.contains(newMat)) {
                        matSet.remove(newMat);
                        target.set(null, matSet);
                        success = TL.COMMAND_CONFIG_MATERIAL_REMOVED.format(fieldName, value.toString().toUpperCase());
                    }
                    // Material not present yet, add it
                    else {
                        matSet.add(newMat);
                        target.set(null, matSet);
                        success = TL.COMMAND_CONFIG_MATERIAL_ADDED.format(fieldName, value.toString().toUpperCase());
                    }
                }

                // Set<String>
                else if (innerType == String.class) {
                    @SuppressWarnings("unchecked") Set<String> stringSet = (Set<String>) target.get(null);

                    // String already present, so remove it
                    if (stringSet.contains(value.toString())) {
                        stringSet.remove(value.toString());
                        target.set(null, stringSet);
                        success = TL.COMMAND_CONFIG_SET_REMOVED.format(fieldName, value.toString());
                    }
                    // String not present yet, add it
                    else {
                        stringSet.add(value.toString());
                        target.set(null, stringSet);
                        success = TL.COMMAND_CONFIG_SET_ADDED.format(fieldName, value.toString());
                    }
                }

                // Set of unknown type
                else {
                    context.sendMessage(TL.COMMAND_CONFIG_INVALID_TYPESET.format(fieldName));
                    return;
                }
            }

            // unknown type
            else {
                context.sendMessage(TL.COMMAND_CONFIG_ERROR_TYPE.format(fieldName, target.getClass().getName()));
                return;
            }
        } catch (NoSuchFieldException ex) {
            context.sendMessage(TL.COMMAND_CONFIG_ERROR_MATCHING.format(fieldName));
            return;
        } catch (IllegalAccessException ex) {
            context.sendMessage(TL.COMMAND_CONFIG_ERROR_SETTING.format(fieldName, value.toString()));
            return;
        }

        if (!success.isEmpty()) {
            if (context.sender instanceof Player) {
                context.sendMessage(success);
                Logger.print(success + TL.COMMAND_CONFIG_LOG.format(context.sender), Logger.PrefixType.DEFAULT);
            } else  // using FactionsPlugin.getInstance().log() instead of sendMessage if run from server console so that "[Factions v#.#.#]" is prepended in server log
            {
                Logger.print(success, Logger.PrefixType.DEFAULT);
            }
        }
        // save change to disk
        Conf.save();
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CONFIG_DESCRIPTION;
    }

}
