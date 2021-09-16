/*
 * Copyright (C) 2019 Driftay
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.massivecraft.factions.zcore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massivecraft.factions.FactionsPlugin;
import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

/**
 * An enum for requesting strings from the language file. The contents of this enum file may be subject to frequent
 * changes.
 */
public enum TL {
    /**
     * Actions translations
     */
    ACTIONS_NOPERMISSION("&e&lFactions &8➤ &f &cThe {faction} doesn't allow you to {action}."),
    ACTIONS_NOPERMISSIONPAIN("&e&lFactions &8➤ &f &cYou can't {action} in the territory of {faction}."),
    ACTIONS_OWNEDTERRITORYDENY("&e&lFactions &8➤ &f &cYou can't do that in this territory, it's owned by {owners}."),
    ACTIONS_OWNEDTERRITORYPAINDENY("&e&lFactions &8➤ &f &cYou can't {action} in this territory, it's owned by {owners}."),
    ACTIONS_MUSTBE("&e&lFactions &8➤ &f &cYou must be {role} to {action}."),
    ACTIONS_NOSAMEROLE("&e&lFactions &8➤ &f &cThe {role} can't control each other."),
    ACTIONS_NOFACTION("&e&lFactions &8➤ &f &cYou aren't a member of any faction."),

    ACTION_DENIED_SAFEZONE("&e&lFactions &8➤ &f &cYou can't use %1$s in safezone!"),
    ACTION_DENIED_WARZONE("&e&lFactions &8➤ &f &cYou can't use %1$s in warzone!"),
    ACTION_DENIED_WILDERNESS("&e&lFactions &8➤ &f &cYou can't use %1$s in wilderness!"),
    ACTION_DENIED_OTHER("&e&lFactions &8➤ &f &cYou can't %2$s in the territory of %1$s."),

    /**
     * Command translations
     */
    COMMAND_USEAGE_TEMPLATE_COLOR("&c"),

    /**
     * Messsges for /f help
     */
    COMMAND_HELP_NEXTCREATE("Learn how to create a faction on the next page."),
    COMMAND_HELP_INVITATIONS("You might want to close it and use invitations:"),
    COMMAND_HELP_HOME("And don't forget to set your home:"),
    COMMAND_HELP_404("&e&lFactions &8➤ &f &c&l» &7This page does &cnot &7exist"),
    COMMAND_HELP_BANK_1("Your faction has a bank which is used to pay for certain"), //Move to last /f help page
    COMMAND_HELP_BANK_2("things, so it will need to have money deposited into it."), //Move to last /f help page
    COMMAND_HELP_BANK_3("To learn more, use the money command."), //Move to last /f help page
    COMMAND_HELP_PLAYERTITLES("Player titles are just for fun. No rules connected to them."), //Move to last /f help page
    COMMAND_HELP_OWNERSHIP_1("Claimed land with ownership set is further protected so"), //Move to last /f help page
    COMMAND_HELP_OWNERSHIP_2("that only the owner(s), faction admin, and possibly the"), //Move to last /f help page
    COMMAND_HELP_OWNERSHIP_3("faction moderators have full access."), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_1("Only faction members can build and destroy in their own"), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_2("territory. Usage of the following items is also restricted:"), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_3("Door, Chest, Furnace, Dispenser, Diode."), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_4(""),
    COMMAND_HELP_PERMISSIONS_5("Make sure to put pressure plates in front of doors for your"), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_6("guest visitors. Otherwise they can't get through. You can"), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_7("also use this to create member only areas."), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_8("As dispensers are protected, you can create traps without"), //Move to last /f help page
    COMMAND_HELP_PERMISSIONS_9("worrying about those arrows getting stolen."), //Move to last /f help page
    COMMAND_HELP_ADMIN_1("&a&l» &a/f claim safezone \n   &7claim land for the Safe Zone"),
    COMMAND_HELP_ADMIN_2("&a&l» &a/f claim warzone \n   &7claim land for the War Zone"),
    COMMAND_HELP_ADMIN_3("&a&l» &a/f autoclaim [safezone|warzone] \n   &7take a guess"),
    COMMAND_HELP_MOAR_1("Finally some commands for the server admins:"),
    COMMAND_HELP_MOAR_2("More commands for server admins:"),
    COMMAND_HELP_MOAR_3("Even more commands for server admins:"),
    COMMAND_HELP_DESCRIPTION("\n  &a&l» &7Display a &ahelp &7page"),

    COMMAND_NEAR_DESCRIPTION("Get nearby faction players in a radius."),
    COMMAND_NEAR_DISABLED_MSG("&e&lFactions &8➤ &f &cThis command is disabled!"),
    COMMAND_NEAR_FORMAT("{playername} &c({distance}m)"),
    COMMAND_NEAR_USE_MSG("Faction members nearby: ."),

    /**
     * Messsges for Faction Admins/Mods
     */

    COMMAND_CONTEXT_ADMINISTER_DIF_FACTION("&e&lFactions &8➤ &f &cThe user %1$s isn't in the same faction as you."),
    COMMAND_CONTEXT_ADMINISTER_ADMIN_REQUIRED("&e&lFactions &8➤ &f &cOnly the faction administrators can do that."),
    COMMAND_CONTEXT_ADMINISTER_SAME_RANK_CONTROL("&e&lFactions &8➤ &f &cModerators can't control each other."),
    COMMAND_CONTEXT_ADMINISTER_MOD_REQUIRED("&e&lFactions &8➤ &f &cYou must be a faction moderator to do that."),

    COMMAND_UPGRADES_DESCRIPTION("Open the upgrades menu."),
    COMMAND_UPGRADES_POINTS_TAKEN("Faction upgrade purchased for &e%1$s points! New points balance - %2$s."),
    COMMAND_UPGRADES_NOT_ENOUGH_POINTS("&e&lFactions &8➤ &f &cYour faction doesn't have enough points to purchase this upgrade."),
    COMMAND_UPGRADES_DISABLED("&e&lFactions &8➤ &f &cFaction upgrades are currently disabled."),

    COMMAND_CORNER_CANT_CLAIM("&e&lFactions &8➤ &f &cYou can't claim this corner."),
    COMMAND_CORNER_CLAIMED("&e&lFactions &8➤ &f &fYou have claimed the corner successfully, totalling in &6%1$d&f chunks."),
    COMMAND_CORNER_ATTEMPTING_CLAIM("&e&lFactions &8➤ &f &cYou're currently attempting to claim the corner."),
    COMMAND_CORNER_FAIL_WITH_FEEDBACK("&e&lFactions &8➤ &f &cOne or more claims in this corner couldn't be claimed. Total chunks claimed - {chunks_claimed}. "), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CORNER_NOT_CORNER("&e&lFactions &8➤ &f &cYou must be in a corner to use this command."),
    COMMAND_CORNER_DESCRIPTION("Claim a corner at the world border."),
    COMMAND_CORNERLIST_DESCRIPTION("List of all the corners."),

    COMMAND_ADMIN_NOTMEMBER("&e&lFactions &8➤ &f &cThe user %1$s isn't a member in your faction."),
    COMMAND_ADMIN_NOTADMIN("&e&lFactions &8➤ &f &cYou aren't the faction administrator."),
    COMMAND_ADMIN_TARGETSELF("&e&lFactions &8➤ &f &cThe target player can't be yourself."),
    COMMAND_ADMIN_DEMOTES("&e&lFactions &8➤ &f &cYou've demoted %1$s from the position of faction administrator."),
    COMMAND_ADMIN_DEMOTED("&e&lFactions &8➤ &f &cYou've been demoted by %1$s from your faction role."),
    COMMAND_ADMIN_PROMOTES("&e&lFactions &8➤ &f &f You've promoted %1$s to the position of faction administrator."),
    COMMAND_ADMIN_PROMOTED("&e&lFactions &8➤ &f &f The user &6%1$s&f gave &6%2$s&f the leadership of &6%3$s."),
    COMMAND_ADMIN_DESCRIPTION("Hand over your administrator rights."),
    COMMAND_ADMIN_NOMEMBERS("&e&lFactions &8➤ &f &cThere's no one else to promote."),

    COMMAND_AHOME_DESCRIPTION("Send a player to their faction home."),
    COMMAND_AHOME_NOHOME("&e&lFactions &8➤ &f &cThe faction %1$s doesn't have a home."),
    COMMAND_AHOME_SUCCESS("&e&lFactions &8➤ &f &fThe user &e%1%s&f was sent to their faction home."),
    COMMAND_AHOME_OFFLINE("&e&lFactions &8➤ &f &cThe user %1$s is offline."),
    COMMAND_AHOME_TARGET("&e&lFactions &8➤ &f &fYou were sent to your faction home."),

    COMMAND_ANNOUNCE_DESCRIPTION("Announce a message to all players in your faction."),
    COMMAND_ALTS_DESCRIPTION("Displays the faction alt commands."),

    COMMAND_ALTS_LIST_DESCRIPTION("List all the alts in your faction."),

    COMMAND_FREECAM_ENEMYINRADIUS("&e&lFactions &8➤ &f &cFreecam was disabled, an enemy is near."),
    COMMAND_FREECAM_OUTSIDEFLIGHT("&e&lFactions &8➤ &f &cPlease don't leave the flight radius."),
    COMMAND_FREECAM_ENABLED("&aFreecam is now enabled."),
    COMMAND_FREECAM_DISABLED("&e&lFactions &8➤ &f &cFreecam is now disabled."),
    COMMAND_FREECAM_DESCRIPTION("Go into spectator mode."),

    ANTI_SPAWNER_MINE_PLAYERS_NEAR("&e&lFactions &8➤ &fYou may not break spawners while enemies are near!"),

    COMMAND_AUTOCLAIM_ENABLED("&aNow auto-claiming land for %1$s."),
    COMMAND_AUTOCLAIM_DISABLED("&e&lFactions &8➤ &f &cAuto-claiming of land is now disabled."),
    COMMAND_AUTOCLAIM_REQUIREDRANK("&e&lFactions &8➤ &f &cYou must be %1$s to claim land."),
    COMMAND_AUTOCLAIM_OTHERFACTION("&e&lFactions &8➤ &f &cYou can't claim land for %1$s."),
    COMMAND_AUTOCLAIM_DESCRIPTION("Auto-claim land as you walk around."),

    //I AM HERE!

    COMMAND_ALTINVITE_DESCRIPTION("Invite alts to your faction."),
    COMMAND_ALTKICK_DESCRIPTION("Kick alts from your faction."),
    COMMAND_ALTKICK_NOTALT("&e&lFactions &8➤ &f &cThat user isn't an alt."),
    COMMAND_ALTKICK_NOTMEMBER("&e&lFactions &8➤ &f &cThis user isn't a member of your faction."),

    COMMAND_ALTS_LIST_NOALTS("T&e&lFactions &8➤ &f &fhere aren't any alts in &6%s."),
    COMMAND_AUTOHELP_HELPFOR("Help for the command ."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_HOME_OTHER_NOTSET("&e&lFactions &8➤ &f &cCurrently, there's no home set for %s."),
    COMMAND_HOME_TELEPORT_OTHER("&aYou've teleported to %s's faction home."),
    COMMAND_SHOP_DESCRIPTION("Opens factions shop GUI."),

    COMMAND_BAN_DESCRIPTION("Ban players from joining your faction."),
    COMMAND_BAN_TARGET("&e&lFactions &8➤ &f &cYou were banned from %1$s."), // banned player perspective
    COMMAND_BAN_BANNED("&e&lFactions &8➤ &f &fThe user&6 %1$s &ebanned&6 %2$s &efrom the faction."),
    COMMAND_BAN_SELF("&e&lFactions &8➤ &f &cYou can't ban yourself from the faction."),
    COMMAND_BAN_INSUFFICIENTRANK("&e&lFactions &8➤ &f &fYour rank is too low to ban&6 %1$s &ffrom the faction."),
    COMMAND_BAN_ALREADYBANNED("This user is already banned from the faction."),

    COMMAND_BANLIST_DESCRIPTION("View a faction's ban list."),
    COMMAND_BANLIST_HEADER("There are %d bans for %s."),
    COMMAND_BANLIST_ENTRY("&7%d. &c%s &r&7// &c%s &r&7// &c%s"), //WE NEED TO FIND OUT WHAT EVERY PLACEHOLDER IS
    COMMAND_BANLIST_NOFACTION("&e&lFactions &8➤ &f &cYou're currently factionless."),
    COMMAND_BANLIST_INVALID("&e&lFactions &8➤ &f &cThe faction %s doesn't exist."),

    COMMAND_BOOM_PEACEFULONLY("This command is only usable by factions which are specifically designated as peaceful."),
    COMMAND_BOOM_TOTOGGLE("to toggle explosions"), //FROM HERE DOWNWARDS, WE NEED TO FIND OUT WHAT "TO" AND "FOR" DO
    COMMAND_BOOM_FORTOGGLE("for toggling explosions"),
    COMMAND_BOOM_ENABLED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas&6 %2$s &fexplosions in your faction's territory."),
    COMMAND_BOOM_DESCRIPTION("Toggle explosions, this can only be used if you're a peaceful faction."),


    COMMAND_BYPASS_ENABLE("&aYou've enabled administrator mode."),
    COMMAND_BYPASS_ENABLELOG(" has enabled administrator mode."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_BYPASS_DISABLE("&e&lFactions &8➤ &f &cYou've disabled administrator mode."),
    COMMAND_BYPASS_DISABLELOG(" has disabled administrator mode."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_BYPASS_DESCRIPTION("Enable administrator mode."),

    COMMAND_BANNER_DESCRIPTION("Turn a held banner into a war banner."),
    BANNER_CANNOT_BREAK("&e&lFactions &8➤ &f &cYou can't break a faction banner."),
    COMMAND_BANNER_NOBANNER("&e&lFactions &8➤ &f &fPlease set a banner using the command '&e/f setbanner'&f."),
    COMMAND_BANNER_NOTENOUGHMONEY("&e&lFactions &8➤ &f &cYou don't have enough money."),
    COMMAND_BANNER_MONEYTAKE("&e&lFactions &8➤ &f &cThe amount {amount} has been taken from your account."),
    COMMAND_BANNER_SUCCESS("&aYou've created a war banner."),
    COMMAND_BANNER_DISABLED("&e&lFactions &8➤ &f &cBuying war banners is currently disabled."),

    COMMAND_TPBANNER_NOTSET("&e&lFactions &8➤ &f &cYour faction doesn't have a war banner placed."),
    COMMAND_TPBANNER_SUCCESS("&aTeleporting to your factions's war banner."),
    COMMAND_TPBANNER_DESCRIPTION("Teleport to your faction's war banner."),


    COMMAND_CHAT_DISABLED("&e&lFactions &8➤ &f &cThe built-in chat channels are disabled on this server."),
    COMMAND_CHAT_INVALIDMODE("&e&lFactions &8➤ &f &cThat's an invalid chat-mode."),
    COMMAND_CHAT_DESCRIPTION("Change your current chat mode."),

    COMMAND_CHAT_MODE_PUBLIC("Your chat-mode is now - &6public."),
    COMMAND_CHAT_MODE_ALLIANCE("Your chat-mode is now - &6alliance."),
    COMMAND_CHAT_MODE_TRUCE("Your chat-mode is now - &6truce."),
    COMMAND_CHAT_MODE_FACTION("Your chat-mode is now - &6faction."),
    COMMAND_CHAT_MODE_MOD("Your chat-mode is now - &6moderator."),
    COMMAND_CHAT_MOD_ONLY("&e&lFactions &8➤ &f &cOnly moderators can talk through this chat mode."),

    COMMAND_CHATSPY_ENABLE("&aYou've enabled the chat spying mode."),
    COMMAND_CHATSPY_ENABLELOG(" &fhas enabled the chat spying mode."), //NEED A PLACEHOLDER
    COMMAND_CHATSPY_DISABLE("&e&lFactions &8➤ &f &cYou've disabled the chat spying mode."),
    COMMAND_CHATSPY_DISABLELOG(" &fhas disabled the chat spying mode."),
    COMMAND_CHATSPY_DESCRIPTION("Enable the administrator chat spying mode."),

    COMMAND_CLAIM_INVALIDRADIUS("&e&lFactions &8➤ &f &cIf you specify a radius, it must be at least 1."),
    COMMAND_CLAIM_DENIED("&e&lFactions &8➤ &f &cYou don't have permission to claim in a radius."),
    COMMAND_CLAIM_DESCRIPTION("Claim land from where you are standing."),

    COMMAND_CLAIMFILL_DESCRIPTION("Claim land while filling gaps between claims."),
    COMMAND_CLAIMFILL_ABOVEMAX("&e&lFactions &8➤ &f &fThe maximum limit for claim fill is&6 %s&f."),
    COMMAND_CLAIMFILL_ALREADYCLAIMED("&e&lFactions &8➤ &f &cYou can't claim fill using already claimed land."),
    COMMAND_CLAIMFILL_TOOFAR("&e&lFactions &8➤ &f &fThis fill would exceed the maximum distance of &6{maximum_distance}&f."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CLAIMFILL_PASTLIMIT("This claim would exceed the limit."),
    COMMAND_CLAIMFILL_NOTENOUGHLANDLEFT("&e&lFactions &8➤ &f &fThe faction &6%s&f doesn't have enough land left to make&6 %d &fclaims."),
    COMMAND_CLAIMFILL_TOOMUCHFAIL("&e&lFactions &8➤ &f &cAborting claim fill after %d failures."),

    COMMAND_CLAIMLINE_INVALIDRADIUS("&e&lFactions &8➤ &f &cIf you specify a distance, it must be at least 1."),
    COMMAND_CLAIMLINE_DENIED("&e&lFactions &8➤ &f &cYou don't have permission to claim in a line."),
    COMMAND_CLAIMLINE_DESCRIPTION("Claim land in a straight line."),
    COMMAND_CLAIMLINE_ABOVEMAX("&e&lFactions &8➤ &f &cThe maximum limit for claim line is %s."),
    COMMAND_CLAIMLINE_NOTVALID("&e&lFactions &8➤ &f &cThe value %s isn't a cardinal direction. Please use - north, east, south or west."),

    CHEST_ITEM_DENIED_TRANSFER("&e&lFactions &8➤ &f &cYou can't transfer %1$s into your faction's chest."),

    //I AM HERE!!
    COMMAND_CONFIG_NOEXIST("&e&lFactions &8➤ &f &cNo configuration setting '%1$s' exists."),
    COMMAND_CONFIG_SET_TRUE(" option was set to &atrue&f."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CONFIG_SET_FALSE(" option was set to &cfalse&f."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CONFIG_OPTIONSET(" option was set to {status}."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CONFIG_COLOURSET("'s color option set to {color}."), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CONFIG_INTREQUIRED("&e&lFactions &8➤ &f &cCan't set %1$s - An integer (whole number) value is required."),
    COMMAND_CONFIG_LONGREQUIRED("&e&lFactions &8➤ &f &cCan't set %1$s - A long integer (whole number) value is required."),
    COMMAND_CONFIG_DOUBLEREQUIRED("&e&lFactions &8➤ &f &cCan't set %1$s - A double (numeric) value is required."),
    COMMAND_CONFIG_FLOATREQUIRED("&e&lFactions &8➤ &f &cCan't set %1$s - A float (numeric) value is required."),
    COMMAND_CONFIG_INVALID_COLOUR("&e&lFactions &8➤ &f &cCan't set %1$s - %2$s isn't a valid color."),
    COMMAND_CONFIG_INVALID_COLLECTION("&e&lFactions &8➤ &f &cThe following %1$s isn't a data collection type which can be modified with this command."),
    COMMAND_CONFIG_INVALID_MATERIAL("&e&lFactions &8➤ &f &cCan't change %1$s. The following %2$s isn't a valid material."),
    COMMAND_CONFIG_INVALID_TYPESET("&e&lFactions &8➤ &f &cThe following %1$s isn't a data type set which can be modified with this command."),
    COMMAND_CONFIG_MATERIAL_ADDED("&aThe following %1$s was set. The material %2$s was added."),
    COMMAND_CONFIG_MATERIAL_REMOVED("&e&lFactions &8➤ &f &cThe following %1$s was set. The material %2$s was removed."),
    COMMAND_CONFIG_SET_ADDED("&aThe following %1$s was set. The following %2$s was added."),
    COMMAND_CONFIG_SET_REMOVED("The following %1$s was set. The follwoing %2$s was removed."),
    COMMAND_CONFIG_LOG("The command was executed by %1$s."),
    COMMAND_CONFIG_ERROR_SETTING("&e&lFactions &8➤ &f &cError setting the configuration setting %1$s to %2$s."),
    COMMAND_CONFIG_ERROR_MATCHING("&e&lFactions &8➤ &f &cThe configuration setting %1$s couldn't be matched, though it should be."),
    COMMAND_CONFIG_ERROR_TYPE("&e&lFactions &8➤ &f &cThe following %1$s is of type %2$s, which can't be modified with this command."),
    COMMAND_CONFIG_DESCRIPTION("Change a conf.json setting."),

    COMMAND_CONVERT_BACKEND_RUNNING("This is already running that backend."),
    COMMAND_CONVERT_BACKEND_INVALID("That backend is invalid."),
    COMMAND_CONVERT_DESCRIPTION("Convert the plugin backend."),

    COMMAND_COORDS_MESSAGE("&fThe user&6 {player}'s &fcoordinates are &6{x}, {y}, {z}&f in &6{world}&f."),
    COMMAND_COORDS_DESCRIPTION("Broadcast your coordinatess to your faction."),

    COMMAND_CHECKPOINT_DISABLED("&e&lFactions &8➤ &f &cYou can't use checkpoints while it's disabled."),
    COMMAND_CHECKPOINT_SET("&aYou've set the faction checkpoint at your current location."),
    COMMAND_CHECKPOINT_GO("&aTeleporting to faction checkpoint."),
    COMMAND_CHECKPOINT_INVALIDLOCATION("&e&lFactions &8➤ &f &cThat location is invalid. You can set checkpoints in your claims or wilderness."),
    COMMAND_CHECKPOINT_NOT_SET("&e&lFactions &8➤ &f &cYou need to set the faction checkpoint first."),
    COMMAND_CHECKPOINT_CLAIMED("&e&lFactions &8➤ &f &cYour current faction checkpoint is claimed, set a new one."),
    COMMAND_CHECKPOINT_DESCRIPTION("Set or go to your faction checkpoints."),

    COMMAND_CREATE_ALREADY_RESERVED("&e&lFactions &8➤ &f &cThis faction tag has already been reserved."),
    COMMAND_CREATE_MUSTLEAVE("&e&lFactions &8➤ &f &cYou must leave your current faction first."),
    COMMAND_CREATE_INUSE("&e&lFactions &8➤ &f &cThat tag is already in use."),
    COMMAND_CREATE_TOCREATE("to create a new faction"),
    COMMAND_CREATE_FORCREATE("for creating a new faction"),
    COMMAND_CREATE_ERROR("&e&lFactions &8➤ &f &cThere was an internal error while trying to create your faction."),
    COMMAND_CREATE_CREATED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas created a new faction named&6 %2$s&f"),
    COMMAND_CREATE_YOUSHOULD("You should now - %1$s."),
    COMMAND_CREATE_CREATEDLOG(" has created a new faction named "), //ADD CONFIGURABLE PLACEHOLDER FOR THIS MESSAGE
    COMMAND_CREATE_DESCRIPTION("Create a new faction."),


    COMMAND_DELHOME_SUCCESS("&e&lFactions &8➤ &f &cThe user %1$s has deleted your faction home."),
    COMMAND_DELHOME_DESCRIPTION("Delete your faction's home."),


    COMMAND_CHECK_DESCRIPTION("Manage your faction's check system."),
    CHECK_BUFFERS_CHECK("Faction walls » Check your buffers."),
    CHECK_WALLS_CHECK("Faction walls » Check your walls."),
    CHECK_ALREADY_CHECKED("Faction settings » Walls have already been checked."),
    CHECK_NO_CHECKS("Faction walls » Nothing to check."),
    CHECK_WALLS_MARKED_CHECKED("Faction walls » Marked walls as checked."),
    CHECK_BUFFERS_MARKED_CHECKED("Faction walls » Marked buffers as checked."),
    CHECK_HISTORY_GUI_TITLE("Check the history"),
    CHECK_SETTINGS_GUI_TITLE("Manage the checking settings"),
    CHECK_WALL_CHECK_GUI_ICON("Wall checking settings"),
    CHECK_BUFFER_CHECK_GUI_ICON("Buffer checking settings"),
    CHECK_CHECK_LORE_LINE("Check - %1$s."),
    CHECK_WALLS_CHECKED_GUI_ICON("Walls checked"),
    CHECK_BUFFERS_CHECKED_GUI_ICON("Buffers checked"),
    CHECK_WALLS_UNCHECKED_GUI_ICON("Walls unchecked"),
    CHECK_BUFFERS_UNCHECKED_GUI_ICON("Buffers unchecked"),
    CHECK_TIME_LORE_LINE("Time - %1$s."),
    CHECK_PLAYER_LORE_LINE("Player - %1$s."),
    CHECK_HISTORY_GUI_ICON("Check history"),
    CHECK_MUST_BE_ATLEAST_COLEADER("&e&lFactions &8➤ &f &cYou must be co-leader to access the checking settings."),
    WEE_WOO_MESSAGE("&4&lFaction weewoo » We are being raided."),
    COMMAND_WEEWOO_STARTED("&4&lFaction weewoo » Weewoo was started by %1$s."),
    COMMAND_WEEWOO_STOPPED("&4&lFaction WeeWoo » Weewoo was stopped by %1$s."),
    COMMAND_WEEWOO_ALREADY_STARTED("Weewoo already started."),
    COMMAND_WEEWOO_ALREADY_STOPPED("Weewoo already stopped."),
    COMMAND_WEEWOO_DESCRIPTION("Notifies all your faction members you're being raided."),
    CHECK_LEADERBOARD_HEADER("---- Check Leaderboard ----"),
    CHECK_LEADERBOARD_LINE("&f%1$s. &d%2$s: &f%3$s (%4$s Buffer, %5$s Walls)"), //FIND OUT WHAT EACH PLACEHOLDER IS
    CHECK_LEADERBOARD_NO_DATA("No data."),
    COMMAND_DISCORD_DESCRIPTION("Link your discord account."),
    COMMAND_DEBUG_DESCRIPTION("Print debugging information to the console."),
    COMMAND_DEBUG_PRINTED("Debug information has been printed to the console."),

    SHIELD_EXPIRED_MESSAGE("&e&lFactions &8➤ &f &cYour faction shield has expired."),
    SHIELD_ALREADY_RUNNING("&e&lFactions &8➤ &f &cThere's a shield already running. There's {time} left."),
    SHIELD_PLANNED("&e&lFactions &8➤ &f &fShield planned from &6{from}&f to &6{to}&f."),
    SHIELD_STARTED("&e&lFactions &8➤ &f &fShield has started and ends at &6{to}&f."),
    SHIELD_SCHEDULED("&e&lFactions &8➤ &f &fScheduled shield from &6{from}&f to &6{to}&f."),
    SHIELD_ENDED("&e&lFactions &8➤ &f &fYour faction shield has ended."),
    SHIELD_SET("&aThe user %1$s has set your faction shield to %2$s - %3$s."),
    SHIELD_SET_GLOBAL("&e&lFactions &8➤ &f &fThe faction %s has set their faction shield from %2$s - %3$s."),
    SHIELD_INFO("&e&lFactions &8➤ &f &fYour faction shield is set from %1$s - %2$s"),
    SHIELD_NOT_SET("&e&lFactions &8➤ &f Your shield has not been set!"),
    SHIELD_NOT_ENABLED("&e&lFactions &8➤ &fShields are not enabled!"),


    FORCESHIELD_CANCELLED_BY_ADMIN("&e&lFactions &8➤ &f &cYour shield has been cancelled by {player}."),

    COMMAND_DEINVITE_CANDEINVITE("&e&lFactions &8➤ &f &fPlayers you can de-invite - &6{deinvite_players}&f. "),
    COMMAND_DEINVITE_CLICKTODEINVITE("&e&lFactions &8➤ &f &fClick to revoke the invite for&6 %1$s&f."),
    COMMAND_DEINVITE_ALREADYMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fis already a member of&6 %2$s&f."),
    COMMAND_DEINVITE_MIGHTWANT("You might want to %1$s."),
    COMMAND_DEINVITE_REVOKED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas revoked your invitation to&6 %2$s&f."),
    COMMAND_DEINVITE_REVOKES("&e&lFactions &8➤ &f &cThe user %1$s has revoked %2$s's invitation."),
    COMMAND_DEINVITE_DESCRIPTION("Remove a pending invitation."),

    COMMAND_DELFWARP_DELETED("&e&lFactions &8➤ &f &cYou have deleted the warp %1$s."),
    COMMAND_DELFWARP_INVALID("&e&lFactions &8➤ &f &cThe warp %1$s doesn't exist."),
    COMMAND_DELFWARP_TODELETE("to delete warp"),
    COMMAND_DELFWARP_FORDELETE("for deleting warp"),
    COMMAND_DELFWARP_DESCRIPTION("Delete a faction warp."),
    COMMAND_DESCRIPTION_CHANGES("&e&lFactions &8➤ &f &fThe faction&6 %1$s &fchanged their description to "), //NEED TO ADD THE PLACEHOLDER
    COMMAND_DESCRIPTION_CHANGED("&e&lFactions &8➤ &f &fYou've changed the description for&6 %1$s &fto&6"), //NEED TO ADD THE PLACEHOLDER
    COMMAND_DESCRIPTION_TOCHANGE("to change faction description"),
    COMMAND_DESCRIPTION_FORCHANGE("for changing faction description"),
    COMMAND_DESCRIPTION_DESCRIPTION("Change the faction's description."),

    COMMAND_DISBAND_IMMUTABLE("&e&lFactions &8➤ &f &cYou can't disband wilderness, safezone or warzone."),
    COMMAND_DISBAND_TOO_YOUNG("&e&lFactions &8➤ &f &fYour faction is too young to withdraw money like this."),
    COMMAND_DISBAND_MARKEDPERMANENT("&e&lFactions &8➤ &f &fThis faction is designated as permanent, you can't disband it."),
    COMMAND_DISBAND_BROADCAST_YOURS("&e&lFactions &8➤ &f &fYou have disbanded your faction."),
    COMMAND_DISBAND_BROADCAST_GENERIC("&e&lFactions &8➤ &f &fThe faction&6 %1$s &fwas disbanded!"),
    COMMAND_DISBAND_BROADCAST_NOTYOURS("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas disbanded the faction&6 %2$s&f."),
    COMMAND_DISBAND_HOLDINGS("&e&lFactions &8➤ &f &fYou've been given the disbanded faction's bank, totalling&6 %1$s&f."),
    COMMAND_DISBAND_PLAYER("&e&lFactions &8➤ &f &cYou've disbanded your faction."),
    COMMAND_DISBAND_CONFIRM("&e&lFactions &8➤ &f &fYour faction has&6 {tnt} &ftnt in the bank, it'll be lost if the faction is disbanded. Type '&c/f disband&f' again within 10 seconds to disband."),
    COMMAND_DISBAND_DESCRIPTION("Disband your faction."),

    COMMAND_FLY_DISABLED("&e&lFactions &8➤ &f &cFaction flight is disabled on this server."),
    COMMAND_FLY_DESCRIPTION("Enable or disable the faction flight mode."),
    COMMAND_FLY_CHANGE("&e&lFactions &8➤ &f &fFaction flight has been&6 %1$s&f"),
    COMMAND_FLY_COOLDOWN("&e&lFactions &8➤ &f &fYou'll not take fall damage for&6 {amount} &fseconds."),
    COMMAND_FLY_DAMAGE("&e&lFactions &8➤ &f &cFaction flight was disabled due to entering combat."),
    COMMAND_FLY_NO_ACCESS("&e&lFactions &8➤ &f &fYou can't fly in the territory of&6 %1$s&f."),
    COMMAND_FLY_ENEMY_NEAR("&e&lFactions &8➤ &f &cThe flight mode has been disabled because an enemy is near."),
    COMMAND_FLY_CHECK_ENEMY("&e&lFactions &8➤ &f &cYou can't fly here, an enemy is near."),
    COMMAND_FLY_NO_EPEARL("&e&lFactions &8➤ &f &cYou can't throw enderpearls while flying."),
    COMMAND_FLY_AUTO("&e&lFactions &8➤ &f &fThe faction auto flight&6 %1$s&f."),


    // IAM HERE!!
    COMMAND_FOCUS_SAMEFACTION("&e&lFactions &8➤ &f &fYou can't focus players in your faction."),
    COMMAND_FOCUS_FOCUSING("&e&lFactions &8➤ &f &fYour faction is now focusing on&6 %s&f."),
    COMMAND_FOCUS_NO_LONGER("&e&lFactions &8➤ &f &fYour faction is no longer focusing on&6 %s&f."),
    COMMAND_FOCUS_DESCRIPTION("Focus on a specific user."),

    COMMAND_FRIENDLY_FIRE_DESCRIPTION("Toggle friendly fire for yourself."),
    COMMAND_FRIENDLY_FIRE_TOGGLE_OFF("&e&lFactions &8➤ &f &cYou've toggled friendly fire off."),
    COMMAND_FRIENDLY_FIRE_TOGGLE_ON("&e&lFactions &8➤ &f &cYou've toggled friendly fire on."),
    FRIENDLY_FIRE_OFF_ATTACKER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas friendly fire toggled off."),
    FRIENDLY_FIRE_YOU_MUST("You must have friendly fire active to attack faction members."),

    COMMAND_FWARP_CLICKTOWARP("Click to warp."),
    COMMAND_FWARP_COMMANDFORMAT("/f warp <warp name> [password]."),
    COMMAND_FWARP_WARPED("&e&lFactions &8➤ &f &fYou've warped to&6 %1$s&f."),
    COMMAND_FWARP_INVALID_WARP("&e&lFactions &8➤ &f &cCouldn't find warp %1$s."),
    COMMAND_FWARP_TOWARP("to warp"),
    COMMAND_FWARP_FORWARPING("for warping"),
    COMMAND_FWARP_WARPS("Warps:"), //NEED TO ADD PLACEHOLDER HERE
    COMMAND_FWARP_DESCRIPTION("Teleport to a faction warp."),
    COMMAND_FWARP_INVALID_PASSWORD("Invalid warp password."),
    COMMAND_FWARP_PASSWORD_REQUIRED("This warp's password - {warp_password}."),
    COMMAND_FWARP_PASSWORD_TIMEOUT("The warp's password was canceled."),

    COMMAND_GRACE_TIME_REMAINING("&e&lGrace period &8» &fTime remaining -&6 %1$s&f."),
    COMMAND_GRACE_DISABLED_NO_FORMAT("Grace is disabled, explosions are allowed."),
    COMMAND_GRACE_ENABLED_FORMAT("&e&lGrace period &8» &fGrace period has now started. Time remaining -&6 %1$s&f."),
    COMMAND_GRACE_DISABLED_FORMAT("&e&lGrace period &8» Grace period has now ended, explosions are now enabled."),
    COMMAND_GRACE_DESCRIPTION("Toggles the grace period on or off."),
    COMMAND_GRACE_ENABLED_PLACEMENT("&e&lFactions &8➤ &f &cYou can't place %s while the grace period is active."),

    COMMAND_HINT_PERMISSION("You can manage your faction's permissions using '/f perms'."),

    COMMAND_SPAWNERCHUNK_CLAIM_SUCCESSFUL("&e&lFactions &8➤ &f &f7You have successfully claimed a &espawner chunk &ffor your faction."),
    COMMAND_SPAWNERCHUNK_ALREADY_CHUNK("&e&lFactions &8➤ &f &f7This chunk is already a spawnerchunk!"),
    COMMAND_SPAWNERCHUNK_PAST_LIMIT("&e&lFactions &8➤ &fYou have exceeded your max spawnerchunk limit! &7Limit: &f%1$s"),
    SPAWNER_CHUNK_UNCLAIMED("&e&lFactions &8➤ &fou have unclaimed a spawnerchunk!"),
    COMMAND_SPAWNERCHUNK_DESCRIPTION("Claim a spawnerchunk"),

    COMMAND_HOME_DISABLED("&e&lFactions &8➤ &f &fFaction homes are currently disabled on this server."),
    COMMAND_HOME_TELEPORTDISABLED("&e&lFactions &8➤ &f &fThe ability to teleport to faction homes is currently disabled on this server."),
    COMMAND_HOME_NOHOME("&e&lFactions &8➤ &f &fYour faction doesn't have a home set. "),
    COMMAND_HOME_UNSET("&e&lFactions &8➤ &f &fYour faction home has been un-set since it's no longer in your territory."),
    COMMAND_HOME_INENEMY("&e&lFactions &8➤ &f &fYou can't teleport to your faction's home while in the territory of an enemy faction."),
    COMMAND_HOME_WRONGWORLD("&e&lFactions &8➤ &f &fYou can't teleport to your faction's home while in a different world."),
    COMMAND_HOME_ENEMYNEAR("&e&lFactions &8➤ &f &fYou can't teleport to your faction's home while an enemy is within&6 %s &fblocks of you."),
    COMMAND_HOME_TOTELEPORT("to teleport to your faction home"),
    COMMAND_HOME_FORTELEPORT("for teleporting to your faction home"),
    COMMAND_HOME_DESCRIPTION("Teleport to your faction's home."),
    COMMAND_HOME_BLOCKED("&e&lFactions &8➤ &f &fYou can't teleport to a home that is claimed by %1$s."),

    COMMAND_INVENTORYSEE_DESCRIPTION("View a faction member's inventory."),

    COMMAND_INSPECT_DISABLED_MSG("&e&lFactions &8➤ &f &cInspect mode is now disabled."),
    COMMAND_INSPECT_DISABLED_NOFAC("&e&lFactions &8➤ &f &cInspect mode is now disabled because you don't have a faction."),
    COMMAND_INSPECT_ENABLED("&aInspect mode is now enabled."),
    COMMAND_INSPECT_HEADER("---Inspect Data---&c//&7x:{x},y:{y},z:{z}"), //COME BACK TO THIS MESSAGE - SEE HOW IT IS IN-GAME
    COMMAND_INSPECT_ROW("&e&lFactions &8➤ &f &c{time} &7// &c{action} &7// &c{player} &7// &c{block-type}"), //COME BACK TO THIS MESSAGE - SEE HOW IT IS IN-GAME
    COMMAND_INSPECT_NODATA("&e&lFactions &8➤ &f &cThere wasn't any data found."),
    COMMAND_INSPECT_NOTINCLAIM("&e&lFactions &8➤ &f &cYou can only inspect in your own claims."),
    COMMAND_INSPECT_BYPASS("&aInspecting is in bypass mode."),
    COMMAND_INSPECT_DESCRIPTION("&e&lFactions &8➤ &f &cToggle the inspecting blocks mode."),

    COMMAND_INVITE_TOINVITE("to invite someone"),
    COMMAND_INVITE_FORINVITE("for inviting someone"),
    COMMAND_INVITE_CLICKTOJOIN("&aClick to join."),
    COMMAND_INVITE_INVITEDYOU("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas invited you to join&6 %2$s&f."),
    COMMAND_INVITE_INVITED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas invited&6 %2$s &fto join your faction."),
    COMMAND_ALTINVITE_INVITED_ALT("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas invited&6 %2$s &fto your faction as an alt."),

    COMMAND_INVITE_ALREADYMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fis already a member of&6 %2$s&f."),
    COMMAND_INVITE_ALREADYINVITED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas already been invited."),
    COMMAND_INVITE_DESCRIPTION("Invite a user to your faction."),
    COMMAND_INVITE_BANNED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fis banned from your joining faction."),

    BANKNOTE_WITHDRAW_NOT_ENOUGH("&e&lFactions &8➤ &f &cYou don't have enough money."),
    BANKNOTE_WITHDRAW_NO_ARGS("Try the command '/withdraw <amount>'."),
    XPBOTTLE_NOT_ENOUGH("&e&lFactions &8➤ &f &cYou don't have enough experience."),
    XPBOTTLE_WITHDRAW_NO_ARGS("Try the command '/bottle <amount>'."),

    COMMAND_JOIN_CANNOTFORCE("&e&lFactions &8➤ &f &fYou don't have permission to move other users into a faction."),
    COMMAND_JOIN_SYSTEMFACTION("&e&lFactions &8➤ &f &fYou can only join normal factions, this is a system faction."),
    COMMAND_JOIN_ALREADYMEMBER("&e&lFactions &8➤ &f &f&e&lFactions &8➤ &f &f&6 %1$s %2$s &falready a member of&6 %3$s&f."), //FIND OUT WHAT EACH PLACEHOLDER IS
    COMMAND_JOIN_ATLIMIT("&e&lFactions &8➤ &f &fThe faction&6 %1$s &fhas reached the limit of&6 %2$d members, %3$s&7 &fcan't join the faction."),
    COMMAND_JOIN_ATLIMIT_ALTS("&e&lFactions &8➤ &f &fThe faction&6 %1$s &freached the limit of&6 %2$d alts, %3$s&7 &fcan't join the faction."),
    COMMAND_JOIN_INOTHERFACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fmust leave&6 %2$s &ffirst to join your faction."),
    COMMAND_JOIN_NEGATIVEPOWER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fcan't join a faction with a negative power level."),
    COMMAND_JOIN_REQUIRESINVITATION("&e&lFactions &8➤ &f &cThis faction requires an invitation."),
    COMMAND_JOIN_ATTEMPTEDJOIN("&e&lFactions &8➤ &f &fThe user&6 %1$s &ftried to join your faction."),
    COMMAND_JOIN_TOJOIN("to join a faction"),
    COMMAND_JOIN_FORJOIN("for joining a faction"),
    COMMAND_JOIN_SUCCESS("&e&lFactions &8➤ &f &fYou have joined the faction%2$s"),
    COMMAND_JOIN_MOVED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas moved you into the&6 %2$s &ffaction."),
    COMMAND_JOIN_JOINED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas joined your faction."),
    COMMAND_JOIN_JOINEDLOG("The user %1$s has joined the %2$s faction."),
    COMMAND_JOIN_MOVEDLOG("The user %1$s has moved %2$s into the %3$s faction."),
    COMMAND_JOIN_DESCRIPTION("Join an existing faction."),
    COMMAND_JOIN_BANNED("&e&lFactions &8➤ &f &fYou're currently banned from joining&6 %1$s&f."),

    COMMAND_KICK_CANDIDATES("&e&lFactions &8➤ &f &fHere are the users that you can kick:&6"), //ADD THE PLACEHOLDER HERE
    COMMAND_KICK_CLICKTOKICK("Click to kick."),
    COMMAND_KICK_SELF("&e&lFactions &8➤ &f &fYou can't kick yourself."),
    COMMAND_KICK_NONE("&e&lFactions &8➤ &f &fThat user isn't in a faction."),
    COMMAND_KICK_NOTMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't a member of&6 %2$s&f."),
    COMMAND_KICK_INSUFFICIENTRANK("&e&lFactions &8➤ &f &fYour rank is too low to kick this player."),
    COMMAND_KICK_NEGATIVEPOWER("&e&lFactions &8➤ &f &fYou can't kick that member until their power is positive."),
    COMMAND_KICK_TOKICK("to kick someone from the faction"),
    COMMAND_KICK_FORKICK("for kicking someone from the faction"),
    COMMAND_KICK_FACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas kicked&6 %2$s &ffrom the faction."), //message given to faction members
    COMMAND_KICK_KICKS("&e&lFactions &8➤ &f &fYou've kicked&6 %1$s &ffrom the faction&6 %2$s&f."), //kicker perspective
    COMMAND_KICK_KICKED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas kicked you from&6 %2$s&f."), //kicked player perspective
    COMMAND_KICK_DESCRIPTION("Kick a player from your faction."),

    COMMAND_LIST_FACTIONLIST("Factions list "), //FIND OUT WHAT MESSAGE IS THIS IN-GAME
    COMMAND_LIST_TOLIST("to list the factions"),
    COMMAND_LIST_FORLIST("for listing the factions"),
    COMMAND_LIST_ONLINEFACTIONLESS("Online factionless:"), //ADD A PLACEHOLDER HERE
    COMMAND_LIST_DESCRIPTION("See a list of factions in the server."),


    COMMAND_SPAM_ENABLED("&aFactions anti-spam is now enabled."),
    COMMAND_SPAM_DISABLED("&e&lFactions &8➤ &f &cFactions anti-spam in now disabled."),
    COMMAND_SPAM_DESCRIPTION("Enable the factions anti-spam system."),

    COMMAND_LOCK_LOCKED("&e&lFactions &8➤ &f &cFactions are now locked."),
    COMMAND_LOCK_UNLOCKED("&aFactions are now unlocked."),
    COMMAND_LOCK_DESCRIPTION("Lock all write stuff. Apparently."), //FIND OUT WHAT THE COMMAND DOES

    COMMAND_LOGINS_TOGGLE("&e&lFactions &8➤ &f &fSet login/logout notifications for faction members to -&6 %s&f."),
    COMMAND_LOGINS_DESCRIPTION("Toggle login/logout notifications for faction members."),

    COMMAND_LOWPOWER_HEADER("&e&lFactions &8➤ &f &fPlayers with power under {maxpower}:&6"),
    COMMAND_LOWPOWER_FORMAT("&e&lFactions &8➤ &f &fThe user&6 {player} &fhas - &6{player_power}/{maxpower} &fpower."),
    COMMAND_LOWPOWER_DESCRIPTION("Shows a list of players in your faction with lower power levels."),

    COMMAND_LOOKUP_INVALID("&e&lFactions &8➤ &f &fThere wasn't any faction with that name found."),
    COMMAND_LOOKUP_FACTION_HOME("&e&lFactions &8➤ &f &fFaction's home location -&6 %1$dx %2$sy %3$sz&f."),
    COMMAND_LOOKUP_CLAIM_COUNT("&e&lFactions &8➤ &f &fFound&6 %1$s &fclaimed chunk(s) for&6 %2$s&f."),
    COMMAND_LOOKUP_CLAIM_LIST("&f%1$s &7(%2$sx, %2$sz)"), //FIND OUT WHERE THIS MESSAGE GOES/DOES
    COMMAND_LOOKUP_ONLY_NORMAL("&e&lFactions &8➤ &f &fYou can only enter normal factions."),
    COMMAND_LOOKUP_DESCRIPTION("Shows claim and home statistics for a faction."),


    // I AM HERE
    COMMAND_MAP_TOSHOW("to show the map"),
    COMMAND_MAP_FORSHOW("for showing the map"),
    COMMAND_MAP_UPDATE_ENABLED("&aThe map auto update is now enabled."),
    COMMAND_MAP_UPDATE_DISABLED("&e&lFactions &8➤ &f &cThe map auto update is now disabled."),
    COMMAND_MAP_DESCRIPTION("Show the territory map."),

    COMMAND_MAPHEIGHT_DESCRIPTION("Update the lines that '/f map' sends."),
    COMMAND_MAPHEIGHT_SET("&e&lFactions &8➤ &f &fYou've set the '&6/f map&f' lines to&6 %1$d&f."),
    COMMAND_MAPHEIGHT_CURRENT("Current map height - %1$d."),

    COMMAND_MOD_CANDIDATES("&e&lFactions &8➤ &f &fUsers that you can promote:&6"), //ADD A PLACEHOLDER
    COMMAND_MOD_CLICKTOPROMOTE("&aClick to promote."),
    COMMAND_MOD_NOTMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't a member in your faction."),
    COMMAND_MOD_NOTADMIN("&e&lFactions &8➤ &f &fYou aren't the faction administrator."),
    COMMAND_MOD_SELF("&e&lFactions &8➤ &f &fThe target user can't be yourself."),
    COMMAND_MOD_TARGETISADMIN("&e&lFactions &8➤ &f &fThe target user is a faction administrator."),
    COMMAND_MOD_REVOKES("&e&lFactions &8➤ &f &cYou've removed the moderator status from %1$s ."),
    COMMAND_MOD_REVOKED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't moderator in your faction anymore."),
    COMMAND_MOD_PROMOTES("&e&lFactions &8➤ &f &fThe user&6 %1$s &fwas promoted to moderator in your faction."),
    COMMAND_MOD_PROMOTED("&e&lFactions &8➤ &f &fYou've promoted&6 %1$s &fto moderator."),
    COMMAND_MOD_DESCRIPTION("Give or revoke the moderator rights."),

    COMMAND_COLEADER_CANDIDATES("&e&lFactions &8➤ &f &fUsers that you can promote - &6{users_can_promote}&f."), //ADD PLACEHOLDER
    COMMAND_COLEADER_CLICKTOPROMOTE("&aClick to promote."),
    COMMAND_COLEADER_NOTMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't a member in your faction."),
    COMMAND_COLEADER_NOTADMIN("&e&lFactions &8➤ &f &fYou're not the faction administrator."),
    COMMAND_COLEADER_SELF("&e&lFactions &8➤ &f &fThe target user can't be yourself."),
    COMMAND_COLEADER_TARGETISADMIN("&e&lFactions &8➤ &f &fThe target user is a faction administrator."),
    COMMAND_COLEADER_REVOKES("&e&lFactions &8➤ &f &cYou've removed co-leader status from %1$s."),
    COMMAND_COLEADER_REVOKED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't co-leader in your faction anymore."),
    COMMAND_COLEADER_PROMOTES("&e&lFactions &8➤ &f &fThe user&6 %1$s &fwas promoted to co-leader in your faction."),
    COMMAND_COLEADER_PROMOTED("&e&lFactions &8➤ &f &fYou've promoted&6 %1$s &fto co-leader in your faction."),
    COMMAND_COLEADER_DESCRIPTION("Give or revoke co-leader rights."),

    COMMAND_CONVERTCONFIG_DESCRIPTION("Convert your Manafia Factions configuration."),
    COMMAND_CONVERTCONFIG_SUCCESS("Configuration successfully converted."),
    COMMAND_CONVERTCONFIG_FAIL("Configuration conversion failed."),
    COMMAND_CONVERTCONFIG_FAILCONFIGMISSING("Please confirm that you've placed ManafiaFactions files in a folder called 'ManafiaFactions'."),

    COMMAND_MODIFYPOWER_ADDED("&e&lFactions &8➤ &f &fYou've added&6 %1$f &fpower to&6 %2$s&f. The new total rounded power -&6 %3$d&f."),
    COMMAND_MODIFYPOWER_DESCRIPTION("Modify the power of a faction or user."),

    COMMAND_MONEY_LONG("The faction money commands."), //FIND OUT WHERE THIS MESSAGE IS/WHAT COMMAND
    COMMAND_MONEY_DESCRIPTION("Display the faction money commands."),

    COMMAND_MONEY_CANTAFFORD("&e&lFactions &8➤ &f &fThe user&6 %1$s &fcan't afford&6 %2$s %3$s&f."), //FIND OUT WHAT THE PLACEHOLDERS ARE
    COMMAND_MONEY_GAINED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas gained&6 %2$s %2%6&f."), //FIND OUT WHAT THE PLACEHOLDERS ARE

    COMMAND_MONEYBALANCE_SHORT("Show your faction's balance."),
    COMMAND_MONEYBALANCE_DESCRIPTION("Show your faction's balance."),

    COMMAND_MONEYDEPOSIT_DESCRIPTION("Deposit money into your faction."),
    COMMAND_MONEYDEPOSIT_DEPOSITED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas deposited&6 %2$s &finto the faction bank. New balance -&6 %3$s&f."),

    COMMAND_MONEYTRANSFERFF_DESCRIPTION("Transfer money from faction to faction."),
    COMMAND_MONEYTRANSFERFF_TRANSFER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas transferred&6 %2$s &ffrom the faction '&6 %3$s &f' to the faction '&6 %4$s&f'."),
    COMMAND_MONEYTRANSFERFF_TRANSFERCANTAFFORD("The faction %1$s can't afford to transfer %2$s to '%3$s'."),


    COMMAND_MONEYTRANSFERFP_DESCRIPTION("Transfer f -> plugin"), //FIND OUT WHAT THIS DOES???
    COMMAND_MONEYTRANSFERFP_TRANSFER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas transferred&6 %2$s &ffrom the faction '&6 %3$s &f' to the user '&6 %4$s&f'."),

    COMMAND_MONEYTRANSFERPF_DESCRIPTION("Transfer plugin -> f"), //FIND OUT WHAT THIS DOES???
    COMMAND_MONEYTRANSFERPF_TRANSFER("&e&lFactions &8➤ &f &f&6 %1$s&7 &ftransferred&6 &c%2$s&7 &ffrom the player &6\"%3$s\" &fto the faction&6 \"%4$s\""),

    COMMAND_MONEYWITHDRAW_DESCRIPTION("Withdraw money from your faction's bank."),
    COMMAND_MONEYWITHDRAW_WITHDRAW("&e&lFactions &8➤ &f &fThe user&6 %1$s &fwithdrew&6 %2$s &ffrom the faction bank. New balance -&6 %3$s&f."),


    COMMAND_COOLDOWN("&e&lFactions &8➤ &f &cYou're currently on cooldown for this command."),
    COMMAND_OPEN_TOOPEN("to open or close the faction"), //FIND OUT WHAT THIS IS
    COMMAND_OPEN_FOROPEN("for opening or closing the faction"), //FIND OUT WHAT THIS IS
    COMMAND_OPEN_OPEN("open"), //FIND OUT WHAT THIS IS
    COMMAND_OPEN_CLOSED("closed"), //FIND OUT WHAT THIS IS
    COMMAND_OPEN_CHANGES("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas changed the faction to&6 %2$s &fstatus."),
    COMMAND_OPEN_CHANGED("&e&lFactions &8➤ &f &fThe faction&6 %1$s &fis now&6 %2$s &fstatus."),
    COMMAND_OPEN_DESCRIPTION("Switch if invitation is required to join."),

    //I AM HERE!!

    COMMAND_OWNER_DISABLED("&e&lFactions &8➤ &f &fOwned areas are currently disabled on this server."),
    COMMAND_OWNER_LIMIT("&e&lFactions &8➤ &f &cYou've reached the server's limit of %1$d owned areas per faction."),
    COMMAND_OWNER_WRONGFACTION("&e&lFactions &8➤ &f &fThis land isn't claimed by your faction, you can't set the ownership of it."),
    COMMAND_OWNER_NOTCLAIMED("&e&lFactions &8➤ &f &fThis land isn't claimed by a faction. Ownership isn't possible."),
    COMMAND_OWNER_NOTMEMBER("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't a member of this faction."),
    COMMAND_OWNER_CLEARED("&e&lFactions &8➤ &f &fYou've cleared the ownership for this claimed area."),
    COMMAND_OWNER_REMOVED("&e&lFactions &8➤ &f &fYou've removed the ownership of this claimed land from '%1$s'."),
    COMMAND_OWNER_TOSET("to set ownership of claimed land"),
    COMMAND_OWNER_FORSET("for setting ownership of claimed land"),
    COMMAND_OWNER_ADDED("&e&lFactions &8➤ &f &fYou've added&6 %1$s &fto the owner list for this claimed land."),
    COMMAND_OWNER_DESCRIPTION("Set ownership of a specific claimed land."),

    COMMAND_KILLHOLOGRAMS_DESCRIPTION("Kill all the holograms in a radius."),

    COMMAND_OWNERLIST_DISABLED("&e&lFactions &8➤ &f &fOwned areas are currently disabled on this server."),//dup->
    COMMAND_OWNERLIST_WRONGFACTION("&e&lFactions &8➤ &f &fThis land isn't claimed by your faction."),//eq
    COMMAND_OWNERLIST_NOTCLAIMED("&e&lFactions &8➤ &f &fThis land isn't claimed by any faction."),//eq
    COMMAND_OWNERLIST_NONE("&e&lFactions &8➤ &f &fNo owners are set here, everyone in the faction has access."),
    COMMAND_OWNERLIST_OWNERS("&e&lFactions &8➤ &f &fCurrent owner(s) of this land -&6 %1$s&f."),
    COMMAND_OWNERLIST_DESCRIPTION("List owner(s) of that claimed land."),


    PAYPALSEE_PLAYER_PAYPAL("&e&lFactions &8➤ &f &fYour faction's PayPal is - '&6 %1$s&f'."),
    COMMAND_PAYPAL_NOTSET("&e&lFactions &8➤ &f &fYour faction doesn't have their PayPal set."),
    COMMAND_PAYPALSET_ADMIN_SUCCESSFUL("&e&lFactions &8➤ &f &fYou've set&6 %1$s's &fPayPal to '&6 %2$s&f'."),
    COMMAND_PAYPALSET_ADMIN_FAILED("&e&lFactions &8➤ &f &cThe argument '%1$s' isn't an e-mail."),
    COMMAND_PAYPALSET_NOTEMAIL("&e&lFactions &8➤ &f &cThe argument '%1$s' isn't an e-mail."),
    COMMAND_PAYPALSET_DESCRIPTION("Set the PayPal e-mail of your faction to claim the rewards."),
    COMMAND_PAYPALSEE_DESCRIPTION("View a specific faction's PayPal e-mail with '/f <seepaypal/getpaypal> <faction>'."),
    COMMAND_PAYPALSET_CREATED("Make sure to type '/f <paypal/setpaypal> <email>'."),
    COMMAND_PAYPALSET_SUCCESSFUL("&aYou've successfully set your faction's PayPal e-mail to '%1$s'."),
    COMMAND_PAYPALSEE_FACTION_PAYPAL("&e&lFactions &8➤ &f &fThe user&6 %1$s's &ffaction has their PayPal set to '&6 %2$s&f'."),
    COMMAND_PAYPALSEE_FACTION_NOTSET("&e&lFactions &8➤ &f &fThe PayPal e-mail of&6 %1$s &fhasn't been set."),
    COMMAND_PAYPALSEE_FACTION_NOFACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fdoesn't have a faction."),

    COMMAND_PEACEFUL_DESCRIPTION("Set a faction to peaceful."),
    COMMAND_PEACEFUL_YOURS("&e&lFactions &8➤ &f &f&6 %1$s &fhas&6 %2$s &fyour faction"), //FIND OUT IF THESE AND BELOW ARE FACTIONS OR PLAYER PLACEHOLDERS
    COMMAND_PEACEFUL_OTHER("&e&lFactions &8➤ &f &6 %s &fhas&6 %s the faction '&6 %s&f'."),
    COMMAND_PEACEFUL_GRANT("&e&lFactions &8➤ &f &f granted peaceful status to&6"),
    COMMAND_PEACEFUL_REVOKE("&e&lFactions &8➤ &f &fRemoved peaceful status from&6 ."), //ADD PLACEHOLDER

    COMMAND_PERM_DESCRIPTION("Edit or list your faction's permissions."),
    COMMAND_PERM_INVALID_RELATION("&e&lFactions &8➤ &f &cInvalid relation defined."),
    COMMAND_PERM_INVALID_ACCESS("&e&lFactions &8➤ &f &cInvalid access defined."),
    COMMAND_PERM_INVALID_ACTION("&e&lFactions &8➤ &f &cInvalid action defined."),
    COMMAND_PERM_SET("Set permission %1$s to %2$s for relation %3$s."),
    COMMAND_PERM_TOP("RCT MEM OFF ALLY TRUCE NEUT ENEMY"), //FIND OUT WHAT THIS IS
    COMMAND_PERM_LOCKED("&e&lFactions &8➤ &f &cThis permission has been locked by the server."),


    COMMAND_POINTS_SHOW_DESCRIPTION("See the point(s) balance of factions."),
    COMMAND_POINTS_SHOW_WILDERNESS("&e&lFactions &8➤ &f &fYou can't check the point(s) balance of wilderness."),
    COMMAND_POINTS_SHOW_OWN("&e&lFactions &8➤ &f &fYour faction has&6 %1$s &fpoint(s)."),
    COMMAND_POINTS_SHOW_OTHER("&e&lFactions &8➤ &f &fThe faction '&6 {faction} &f' has a point(s) balance of&6 {points}&f."),
    COMMAND_POINTS_FAILURE("&e&lFactions &8➤ &f &cThe faction '{faction}' doesn't exist."),
    COMMAND_POINTS_SUCCESSFUL("&e&lFactions &8➤ &f &fYou've added&6 %1$s &fpoint(s) to '&6 %2$s &f'. The new point(s) balance of&6 %2$s &fis&6 %3$s&f."),
    COMMAND_POINTS_INSUFFICIENT("&e&lFactions &8➤ &f &cYou can't add/set/remove a negative number of point(s) to a faction."),
    COMMAND_POINTS_DESCRIPTION("General command for faction points."),

    COMMAND_ADDPOINTS_DESCRIPTION("Add point(s) to a faction."),


    COMMAND_REMOVEPOINTS_SUCCESSFUL("&e&lFactions &8➤ &f &fYou've taken&6 %1$s &fpoint(s) from '&6 %2$s &f'. The new point(s) balance of&6 %2$s &fis&6 %3$s&f."),
    COMMAND_REMOVEPOINTS_DESCRIPTION("Remove point(s) from a faction."),

    COMMAND_SETPOINTS_SUCCESSFUL("&e&lFactions &8➤ &f &fYou've set&6 %1$s &fpoint(s) to&6 %2$s &f. The new point(s) balance of&6 %2$s &fis&6 %3$s&f."),
    COMMAND_SETPOINTS_DESCRIPTION("Set point(s) of a faction."),

    COMMAND_PERMANENT_DESCRIPTION("Toggles a permanent faction option."),
    COMMAND_PERMANENT_GRANT("&e&lFactions &8➤ &f &f added permanent status to&6"), //FIND OUT WHAT THIS IS AND ADD PLACEHOLDERS
    COMMAND_PERMANENT_REVOKE("&e&lFactions &8➤ &f &f removed permanent status from&6"), //FIND OUT WHAT THIS IS AND ADD PLACEHOLDERS
    COMMAND_PERMANENT_YOURS("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas&6 %2$s &fyour faction."),
    COMMAND_PERMANENT_OTHER("&e&lFactions &8➤ &f &6 %s &fhas &6 %s &fthe faction '&6 &c%s&f'."),
    COMMAND_PROMOTE_TARGET("&e&lFactions &8➤ &f &f You've been&6 %1$s &fto&6 %2$s&f."),
    COMMAND_PROMOTE_SUCCESS("&e&lFactions &8➤ &f &fYou've successfully&6 %1$s %2$s &fto&6 %3$s&f."),
    COMMAND_PROMOTE_PROMOTED("promoted"),
    COMMAND_PROMOTE_DEMOTED("demoted"),
    COMMAND_PROMOTE_LOWEST_RANK("&e&lFactions &8➤ &f &fThe user&6 %1$s &falready has the lowest rank in the faction."),
    COMMAND_PROMOTE_HIGHEST_RANK("&e&lFactions &8➤ &f &fThe user&6 %1$s &falready has the highest rank in the faction."),
    COMMAND_PROMOTE_HIGHER_RANK("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas a higher rank than yours, you can't modify his rank."),
    COMMAND_PROMOTE_COLEADER_ADMIN("&e&lFactions &8➤ &f &cCo-leaders can't promote users to administrators."),

    COMMAND_PERMANENTPOWER_DESCRIPTION("Toggle the permanent faction power option."),
    COMMAND_PERMANENTPOWER_GRANT("added permanentpower status to"), //FIND OUT WHAT THIS IS AND ADD PLACEHOLDERS
    COMMAND_PERMANENTPOWER_REVOKE("removed permanentpower status from"), //FIND OUT WHAT THIS IS AND ADD PLACEHOLDERS
    COMMAND_PERMANENTPOWER_SUCCESS("&e&lFactions &8➤ &f &f You&6 %s %s&f."),
    COMMAND_PERMANENTPOWER_FACTION("&e&lFactions &8➤ &f &6 %s %s &fyour faction"),

    COMMAND_PROMOTE_DESCRIPTION("/f promote <name>"), //FIND OUT WHAT THIS IS TO EDIT
    COMMAND_PROMOTE_WRONGFACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't part of your faction."),
    COMMAND_NOACCESS("&e&lFactions &8➤ &f &cYou don't have access to that."),
    COMMAND_PROMOTE_NOT_ALLOWED("&e&lFactions &8➤ &f &cYou can't promote users to the same rank as yourself."),
    COMMAND_PROMOTE_NOTSELF("&e&lFactions &8➤ &f &cYou can't manage your own rank."),
    COMMAND_PROMOTE_NOT_SAME("&e&lFactions &8➤ &f &cYou can't promote users to the same rank as yourself."),


    COMMAND_POWER_TOSHOW("to show player power info"), //FIND OUT WHAT THIS IS
    COMMAND_POWER_FORSHOW("for showing player power info"), //FIND OUT WHAT THIS IS
    COMMAND_POWER_POWER("%1$s » Power/maximum power » %2$d/%3$d (%4$s)"),
    COMMAND_POWER_BONUS("+"),
    COMMAND_POWER_PENALTY("-"),
    COMMAND_POWER_DESCRIPTION("Show a user's power information."),

    COMMAND_POWERBOOST_HELP_1("You need to specify 'plugin' or 'player' to target a user. Use 'faction' to target a faction."), //MAKE THESE CONFIGURABLE TO ADD LINES IF NEEDED
    COMMAND_POWERBOOST_HELP_2("&e&lFactions &8➤ &f &f ex. /f powerboost plugin SomePlayer 0.5  -or-  /f powerboost f SomeFaction -5"),
    COMMAND_POWERBOOST_INVALIDNUM("You need to specify a valid numeric value for the power bonus/penalty amount."),
    COMMAND_POWERBOOST_PLAYER("%1$s"), //FIND OUT WHAT THIS IS
    COMMAND_POWERBOOST_FACTION("%1$s"), //FIND OUT WHAT THIS IS
    COMMAND_POWERBOOST_BOOST("&e&lFactions &8➤ &f &fThe user or faction '&6 %1$s &f' now has a power bonus/penalty of&6 %2$d &fto minimum and maximum power levels."), //FIND OUT WHAT THIS IS
    COMMAND_POWERBOOST_BOOSTLOG("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas set the power bonus/penalty for '&6 %2$s &f' to&6 %3$d&f."),
    COMMAND_POWERBOOST_DESCRIPTION("Apply permanent power bonus/penalty to a specific user or faction."),

    COMMAND_RELATIONS_ALLTHENOPE("&e&lFactions &8➤ &f &cYou can't do that."),
    COMMAND_RELATIONS_MORENOPE("&e&lFactions &8➤ &f &cYou can't declare a relation to yourself."),
    COMMAND_RELATIONS_ALREADYINRELATIONSHIP("&e&lFactions &8➤ &f &fYou already have that relation set with&6 %1$s&f."),
    COMMAND_RELATIONS_TOMARRY("to change a relation wish"),
    COMMAND_RELATIONS_FORMARRY("for changing a relation wish"),
    COMMAND_RELATIONS_MUTUAL("&e&lFactions &8➤ &f &fYour faction is now&6 %1$s &fto&6 %2$s&f."),
    COMMAND_RELATIONS_PEACEFUL("&e&lFactions &8➤ &f &fThis will have no effect while your faction is peaceful."),
    COMMAND_RELATIONS_PEACEFULOTHER("&e&lFactions &8➤ &f &fThis will have no effect while their faction is peaceful."),
    COMMAND_RELATIONS_DESCRIPTION("Set relation wish to another faction."),
    COMMAND_RELATIONS_EXCEEDS_ME("&e&lFactions &8➤ &f &cFailed to set relation wish. You can only have %1$s %2$s."),
    COMMAND_RELATIONS_EXCEEDS_THEY("&e&lFactions &8➤ &f &cFailed to set relation wish. They can only have %1$s %2$s."),

    COMMAND_RELATIONS_PROPOSAL_1("&e&lFactions &8➤ &f &fThe faction&6 %1$s &fwishes to be your&6 %2$s&f."),
    COMMAND_RELATIONS_PROPOSAL_2("Type '/%1$s %2$s %3$s' to accept."),
    COMMAND_RELATIONS_PROPOSAL_SENT("&e&lFactions &8➤ &f &fThe faction '&6 %1$s &f' were informed that you wish to be&6 %2$s&f."),

    COMMAND_RELOAD_TIME("&e&lFactions &8➤ &f &fReloaded all configuration files from disk, this process took %1$dms."),
    COMMAND_RELOAD_DESCRIPTION("&e&lFactions &8➤ &f &fReload data file(s) from the disk."),

    COMMAND_RESERVE_DESCRIPTION("Reserve any faction name for any user."),
    COMMAND_RESERVE_SUCCESS("&e&lFactions &8➤ &f &fYou've reserved the faction '&6 %1$s &f' for&6 a%2$s&f."),
    COMMAND_RESERVE_ALREADYRESERVED("&e&lFactions &8➤ &f &cThe faction %1$s has already been reserved."),


    COMMAND_SAFEUNCLAIMALL_DESCRIPTION("Un-claim all the safezone land."),
    COMMAND_SAFEUNCLAIMALL_UNCLAIMED("&e&lFactions &8➤ &f &fYou un-claimed all the safezone land."),
    COMMAND_SAFEUNCLAIMALL_UNCLAIMEDLOG("&e&lFactions &8➤ &f &fThe user&6 %1$s &funclaimed all the safezones."),

    COMMAND_SAVEALL_SUCCESS("&e&lFactions &8➤ &f &fAll the factions were saved to the disk."),
    COMMAND_SAVEALL_DESCRIPTION("Save all data file(s) to the disk."),

    COMMAND_SCOREBOARD_DESCRIPTION("The generic command for factions scoreboard."),

    COMMAND_SETBANNER_SUCCESS("&e&lFactions &8➤ &f &fThe banner pattern was set."),
    COMMAND_SETBANNER_NOTBANNER("&e&lFactions &8➤ &f &cThe item isn't a banner."),
    COMMAND_SETBANNER_DESCRIPTION("Set the banner pattern for your faction."),


    COMMAND_SETDEFAULTROLE_DESCRIPTION("/f defaultrole <role> - set your Faction's default role."), //FIND OUT WHAT THIS IS
    COMMAND_SETDEFAULTROLE_NOTTHATROLE("&e&lFactions &8➤ &f &cYou can't set the default role to administrator."),
    COMMAND_SETDEFAULTROLE_SUCCESS("&e&lFactions &8➤ &f &fYou've set default role of your faction to&6 %1$s&f."),
    COMMAND_SETDEFAULTROLE_INVALIDROLE("&e&lFactions &8➤ &f &cCouldn't find matching role for %1$s."),

    COMMAND_SETFWARP_NOTCLAIMED("&e&lFactions &8➤ &f &fYou can only set warps in your faction territory."),
    COMMAND_SETFWARP_LIMIT("&e&lFactions &8➤ &f &cYour faction already has the maximum amount of warps set."),
    COMMAND_SETFWARP_SET("&e&lFactions &8➤ &f &fYou've set the warp &6 %1$s &f with a password of &6 %2$s &f in your current location."),
    COMMAND_SETFWARP_TOSET("to set warp"),
    COMMAND_SETFWARP_FORSET("for setting warp"),
    COMMAND_SETFWARP_DESCRIPTION("Set a faction warp in your location."),

    COMMAND_SETHOME_DISABLED("&e&lFactions &8➤ &f &cFaction homes are disabled on this server."),
    COMMAND_SETHOME_NOTCLAIMED("&e&lFactions &8➤ &f &cYour faction home can only be set inside your own claimed territory."),
    COMMAND_SETHOME_TOSET("to set the faction home"),
    COMMAND_SETHOME_FORSET("for setting the faction home"),
    COMMAND_SETHOME_SET("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas set the home for your faction."),
    COMMAND_SETHOME_SETOTHER("&e&lFactions &8➤ &f &fYou've set the home for the '&6 %1$s &f' faction."),
    COMMAND_SETHOME_DESCRIPTION("Set the faction home."),

    COMMAND_SETTNT_SUCCESS("&aSet tnt for faction &e%s &ato &b%d"),
    COMMAND_SETTNT_DESCRIPTION("set the amount of tnt for a faction"),

    COMMAND_SETPOWER_SUCCESS("&aYou've set the power for %s to %d."),
    COMMAND_SETPOWER_DESCRIPTION("Set current playing power for a user."),

    COMMAND_SETMAXVAULTS_DESCRIPTION("Set maximum vaults for a faction."),
    COMMAND_SETMAXVAULTS_SUCCESS("&aYou've set the maximum vaults for %s to %d."),
    COMMAND_ONCOOOLDOWN("^cYou can't use this command for another %1$s seconds."),

    COMMAND_SHIELD_DESCRIPTION("Toggle the faction's shield."),
    COMMAND_SHIELD_SUCCESS("&aYou've enabled the faction's shield."),
    COMMAND_SHIELD_DISABLED("&e&lFactions &8➤ &f &cFaction shields are currently disabled."),

    COMMAND_FORCESHIELD_DESCRIPTION("Forcefully ends a faction's shield."),

    COMMAND_SPAWNER_LOCK_TOGGLED("&e&lFactions &8➤ &f &fYou've set the placement of spawners to&6 %1$s&f."),
    COMMAND_SPAWNER_LOCK_DESCRIPTION("Enable/disable placement of spawners."),
    COMMAND_SPAWNER_LOCK_CANNOT_PLACE("&e&lFactions &8➤ &f &cPlacement of spawners has been temporarily disabled."),

    COMMAND_STRIKES_CHANGED("&e&lFactions &8➤ &f &fYou've set the strikes of '&6 %1$s &f' to&6 %2$s&f."),
    COMMAND_STRIKES_INFO("&e&lFactions &8➤ &f &fThe faction '&6 %1$s &f' has&6 %2$s &fstrikes."),
    COMMAND_STRIKES_TARGET_INVALID("&e&lFactions &8➤ &f &cThe faction '%1$s' is invalid."),
    COMMAND_STRIKES_STRUCK("&e&lFactions &8➤ &f &fYour faction strikes have changed by&6 %1$s &fstrike(s). Your faction now has&6 %2$s/%3$s&f."),
    COMMAND_STRIKES_DESCRIPTION("Give strikes to factions to warn them."),
    COMMAND_STRIKESGIVE_DESCRIPTION("Give a faction 1 strike."),
    COMMAND_STRIKETAKE_DESCRIPTION("Remove a strike from a faction."),
    COMMAND_STRIKESET_DESCRIPTION("Set a faction's strikes explicitly."),
    COMMAND_STRIKESINFO_DESCRIPTION("Show a faction's strikes information."),

    //I AM HERE!!

    SHOP_NOT_ENOUGH_POINTS("&e&lFactions &8➤ &f &cYour faction doesn't have enough points to purchase this."),
    SHOP_ERROR_DURING_PURCHASE("&e&lFactions &8➤ &f &cThere was an error while trying to give items, please check your inventory."),
    SHOP_BOUGHT_BROADCAST_FACTION("Factions shop » The user {player} bought {item} for {cost} points."),


    COMMAND_VIEWCHEST_DESCRIPTION("View a faction's chest/vault."),

    COMMAND_VAULT_DESCRIPTION("Open your placed faction's vault."),
    COMMAND_VAULT_INVALID("Your vault was either claimed, broken or hasn't been placed yet."),
    COMMAND_VAULT_OPENING("Opening your faction's vault."),
    COMMAND_VAULT_NO_HOPPER("&e&lFactions &8➤ &f &cYou can't place a hopper near a vault."),

    COMMAND_GETVAULT_ALREADYSET("&e&lFactions &8➤ &f &fThe vault has already been set."),
    COMMAND_GETVAULT_ALREADYHAVE("&e&lFactions &8➤ &f &fYou already have a vault in your inventory."),
    COMMAND_GETVAULT_CHESTNEAR("&e&lFactions &8➤ &f &fThere's a chest or hopper near."),
    COMMAND_GETVAULT_SUCCESS("&aYou've sucessfully set the vault."),
    COMMAND_GETVAULT_INVALIDLOCATION("&e&lFactions &8➤ &f &cVaults can only be placed in faction land."),
    COMMAND_GETVAULT_DESCRIPTION("Get the faction vault item."),
    COMMAND_GETVAULT_RECEIVE("&e&lFactions &8➤ &f &fYou've recieved a faction vault."),
    COMMAND_GETVAULT_NOMONEY("&e&lFactions &8➤ &f &cYou don't have enough money for this."),
    COMMAND_GETVAULT_MONEYTAKE("&e&lFactions &8➤ &f &cThe amount {amount} has been taken from your account."),

    COMMAND_LOGOUT_KICK_MESSAGE("&e&lFactions &8➤ &f &fYou've safely logged out."),
    COMMAND_LOGOUT_ACTIVE("&e&lFactions &8➤ &f &fYou're already logging out."),
    COMMAND_LOGOUT_LOGGING("&e&lFactions &8➤ &f &fYou're logging out, please wait&6 %1$s &fseconds."),
    COMMAND_LOGOUT_DESCRIPTION("Logout safely from the server."),
    COMMAND_LOGOUT_MOVED("&e&lFactions &8➤ &f &cYour logout was cancelled because you moved."),
    COMMAND_LOGOUT_DAMAGE_TAKEN("&e&lFactions &8➤ &f &cYour logout was cancelled because you were damaged."),
    COMMAND_LOGOUT_TELEPORTED("&e&lFactions &8➤ &f &cYour logout was cancelled because you teleported."),

    COMMAND_NOTIFICATIONS_TOGGLED_ON("&e&lFactions &8➤ &f &fYou'll now see claimed land notifications."),
    COMMAND_NOTIFICATIONS_TOGGLED_OFF("&e&lFactions &8➤ &f &fYou'll no longer see claimed land notifications."),
    COMMAND_NOTIFICATIONS_DESCRIPTION("Toggle notifications for land claiming."),

    COMMAND_SHOW_NOFACTION_SELF("You're not in a faction."),
    COMMAND_SHOW_NOFACTION_OTHER("That's not a faction."),
    COMMAND_SHOW_TOSHOW("to show faction information"),
    COMMAND_SHOW_FOUNDED("&6Founded - &e{create-date}"),
    COMMAND_SHOW_FORSHOW("for showing faction information"),
    COMMAND_SHOW_DESCRIPTION("&6Description - &e{description}"),
    COMMAND_SHOW_PEACEFUL("This faction is peaceful."),
    COMMAND_SHOW_PERMANENT("This faction is permanent."),
    COMMAND_SHOW_JOINING("Joining - %1$s."),
    COMMAND_SHOW_INVITATION("Invitation is required."),
    COMMAND_SHOW_UNINVITED("Invitation isn't needed."),
    COMMAND_SHOW_NOHOME("n/a"),
    COMMAND_SHOW_ALTS("&6Alts - &e{alts}"),
    COMMAND_SHOW_HEADER("&8&m--------------&7 &8<&e{faction}&8> &8&m--------------"),
    COMMAND_SHOW_STRIKES("&6Strikes - &e{strikes}"),
    COMMAND_SHOW_POINTS("&6Faction Points - &e{faction-points}"),
    COMMAND_SHOW_OWNER("&6Owner - &e{leader}"),
    COMMAND_SHOW_POWER("&6Land/power/maximum power - &e{chunks}/{power}/{maxPower}"),
    COMMAND_SHOW_SHIELD("&6Shield Status - &e{shield-status"),
    COMMAND_SHOW_BONUS(" (bonus: "), //FIND OUT WHAT THIS IS
    COMMAND_SHOW_PENALTY(" (penalty: "), //FIND OUT WHAT THIS IS
    COMMAND_SHOW_DEPRECIATED("(%1$s depreciated)"), //This is spelled correctly.
    COMMAND_SHOW_LANDVALUE("&6Total land value - &e%1$s %2$s."),
    COMMAND_SHOW_BALANCE("&6Faction Balance - &e{faction-balance}"),
    COMMAND_SHOW_BANKCONTAINS("&6The bank contains - &e %1$s"),
    COMMAND_SHOW_ALLIES("&6Allies: &e{allies-list}"), //ADD PLACEHOLDER HERE
    COMMAND_SHOW_ENEMIES("&6Enemies - &e{enemies-list}"), //ADD PLACEHOLDER HERE
    COMMAND_SHOW_MEMBERSONLINE("&6Members Online: &e({online}/{members}) - {online-list}  "), //ADD PLACEHOLDER HERE
    COMMAND_SHOW_MEMBERSOFFLINE("&6Members Offline: &e({offline}/{members}) - {offline-list}"), //ADD PLACEHOLDER HERE
    COMMAND_SHOW_COMMANDDESCRIPTION("Show faction information"),
    COMMAND_SHOW_DEATHS_TIL_RAIDABLE("DTR - %1$d"),
    COMMAND_SHOW_EXEMPT("This faction is exempt and can't be seen."),
    COMMAND_SHOW_BANS("&6Bans - &e{faction-bancount}"),
    COMMAND_SHOW_NEEDFACTION("You need to join a faction to view your own."),

    COMMAND_SHOWCLAIMS_HEADER("{faction}'s Claims"), //COME BACK TO THIS HEADER!
    COMMAND_SHOWCLAIMS_FORMAT("&8[{world}]:"),
    COMMAND_SHOWCLAIMS_CHUNKSFORMAT("&8(&c{x}&8,&c{z}&8)"),
    COMMAND_SHOWCLAIMS_DESCRIPTION("Show your faction's claims."),

    COMMAND_SHOWINVITES_PENDING("Users with pending invites"), //ADD PLACEHOLDER HERE
    COMMAND_SHOWINVITES_CLICKTOREVOKE("Click to revoke the invitation for %1$s."),
    COMMAND_SHOWINVITES_DESCRIPTION("Show pending faction invites."),

    COMMAND_ALTS_LIST_FORMAT("The power of %1$s - %2$s. He was last seen - %3$s."),
    COMMAND_ALTS_DEINVITE_DESCRIPTION("Base command for revoking alt invitations."),

    COMMAND_SEECHUNK_DESCRIPTION("Show chunk boundaries."),
    COMMAND_SEECHUNK_TOGGLE("Seechunk has been %1$s."),

    COMMAND_STATUS_FORMAT("The power of %1$s - %2$s. He was last seen - %3$s."),
    COMMAND_STATUS_ONLINE("Online"), //NEED TO KNOW WHERE THIS IS
    COMMAND_STATUS_AGOSUFFIX(" ago."), //NEED TO KNOW WHERE THIS IS
    COMMAND_STATUS_DESCRIPTION("Show the status of a user."),

    COMMAND_STEALTH_DESCRIPTION("&e&lFactions &8➤ &f &fEnable and disable the stealth mode."),
    COMMAND_STEALTH_ENABLE("&aStealth » You'll no longer disable near users in flying mode."),
    COMMAND_STEALTH_DISABLE("&e&lFactions &8➤ &f &cStealth » You'll now disable other near users in flying mode."),
    COMMAND_STEALTH_MUSTBEMEMBER("Stealth » You must be in a faction to use this command."),

    COMMAND_STUCK_TIMEFORMAT("m 'minutes', s 'seconds.'"), //FIND OUT WHAT THIS IS!
    COMMAND_STUCK_CANCELLED("&e&lFactions &8➤ &f &cThe teleport was cancelled because you were damaged."),
    COMMAND_STUCK_OUTSIDE("&e&lFactions &8➤ &f &cThe teleport was cancelled because you left the %1$d block radius."),
    COMMAND_STUCK_EXISTS("&e&lFactions &8➤ &f &cYou're already teleporting, you must wait %1$s."),
    COMMAND_STUCK_START("&e&lFactions &8➤ &f &fTeleport will commence in&6 %s &f. Don't take or deal damage."),
    COMMAND_STUCK_TELEPORT("You teleported safely to %1$d, %2$d, %3$d."),
    COMMAND_STUCK_TOSTUCK("to safely teleport %1$s out"),
    COMMAND_STUCK_FORSTUCK("for %1$s initiating a safe teleport out"),
    COMMAND_STUCK_DESCRIPTION("Safely teleports you out of enemy faction."),

    COMMAND_SEECHUNK_ENABLED("&aSeechunk is now enabled."),
    COMMAND_SEECHUNK_DISABLED("&e&lFactions &8➤ &f &cSeechunk is now disabled."),


    COMMAND_TAG_TAKEN("&e&lFactions &8➤ &f &cThat tag is already taken."),
    COMMAND_TAG_TOCHANGE("to change the faction tag"),
    COMMAND_TAG_FORCHANGE("for changing the faction tag"),
    COMMAND_TAG_FACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas changed your faction's tag to '&6 %2$s&f'."),
    COMMAND_TAG_CHANGED("&e&lFactions &8➤ &f &fThe faction '&6 %1$s &f' changed their name to '&6 %2$s&f'."),
    COMMAND_TAG_DESCRIPTION("Change your faction's tag."),

    COMMAND_TITLE_TOCHANGE("to change a players title"),
    COMMAND_TITLE_FORCHANGE("for changing a players title"),
    COMMAND_TITLE_CHANGED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fchanged the title to '&6 %2$s&f'."),
    COMMAND_TITLE_DESCRIPTION("Set or remove a user's title."),

    COMMAND_TITLETOGGLE_TOGGLED("&e&lFactions &8➤ &f &fYou've changed your title setting to '&6 %1$s&f'."),
    COMMAND_TITLETOGGLE_DESCRIPTION("Toggle titles to be served to you."),


    COMMAND_TOGGLEALLIANCECHAT_DESCRIPTION("Toggles the alliance chat."),
    COMMAND_TOGGLEALLIANCECHAT_IGNORE("&e&lFactions &8➤ &f &cThe alliance chat is now ignored."),
    COMMAND_TOGGLEALLIANCECHAT_UNIGNORE("&aThe alliance chat is no longer ignored."),

    COMMAND_TOGGLESB_DISABLED("&e&lFactions &8➤ &f &cYou can't toggle scoreboards while they are disabled."),


    COMMAND_TOP_DESCRIPTION("Sort factions to see the top of some criteria."),
    COMMAND_TOP_TOP("Top Factions by %s. Page %d/%d"), //FIND OUT WHAT THIS IS
    COMMAND_TOP_LINE("%d. &6%s - &c%s."), // Rank. Faction: Value
    COMMAND_TOP_INVALID("Couldn't sort by %s. Try balance, online members, total members, power or land."),

    COMMAND_TNT_DISABLED_MSG("&e&lFactions &8➤ &f &cThis command is currently disabled."),
    COMMAND_TNT_INVALID_NUM("&e&lFactions &8➤ &f &cThe amount needs to be a number."),
    COMMAND_TNT_WIDTHDRAW_NOTENOUGH_TNT("&e&lFactions &8➤ &f &cYou don't have enough TNT in the bank."),
    COMMAND_TNTFILL_NODISPENSERS("&e&lFactions &8➤ &f &cNo dispensers were found in a radius of {radius} blocks."),
    COMMAND_TNT_DEPOSIT_SUCCESS("&e&lFactions &8➤ &f &fYou've deposited tnt into the bank."),
    COMMAND_TNT_EXCEEDLIMIT("&e&lFactions &8➤ &f &cThis exceeds the bank's limit."),
    COMMAND_TNT_WIDTHDRAW_SUCCESS("&e&lFactions &8➤ &f &fYou've withdrew tnt from the bank."),
    COMMAND_TNT_WIDTHDRAW_NOTENOUGH("&e&lFactions &8➤ &f &cYou don't have enough tnt in the bank."),
    COMMAND_TNT_DEPOSIT_NOTENOUGH("&e&lFactions &8➤ &f &cYou don't have enough tnt in the inventory."),
    COMMAND_TNT_AMOUNT("&e&lFactions &8➤ &f &fYour faction has&6 {amount}/{maxAmount} &ftnt in the bank."),
    COMMAND_TNT_POSITIVE("&e&lFactions &8➤ &f &fPlease use positive numbers."),
    COMMAND_TNT_DESCRIPTION("Add/widthraw tnt from the faction's bank."),
    COMMAND_TNT_WIDTHDRAW_NOTENOUGH_SPACE("&e&lFactions &8➤ &f &cYou don't have enough space in your inventory."),
    COMMAND_TNT_ADD_DESCRIPTION("&b/f tnt add&3 <amount>"), //FIND OUT WHERE THIS IS
    COMMAND_TNT_TAKE_DESCRIPTION("&b/f tnt take&3 <amount>"), //FIND OUT WHERE THIS IS

    COMMAND_TNTFILL_HEADER("Filling tnt in the dispensers."),
    COMMAND_TNTFILL_SUCCESS("&aFilled {amount} tnt in {dispensers} dispensers."),
    COMMAND_TNTFILL_NOTENOUGH("&e&lFactions &8➤ &f &cYou don't have enough tnt in the inventory."),
    COMMAND_TNTFILL_RADIUSMAX("The maximum radius is {max}."),
    COMMAND_TNTFILL_AMOUNTMAX("The maximum amount is {max}."),
    COMMAND_TNTFILL_MOD("&e&lFactions &8➤ &f &fThe tnt will be used from the faction's bank because you don't have the specified amount in your inventory and you're a {role}."),
    COMMAND_TNTFILL_DESCRIPTION("Fill the dispensers around you with tnt."),

    COMMAND_UNBAN_DESCRIPTION("Unban someone from your faction."),
    COMMAND_UNBAN_NOTBANNED("&e&lFactions &8➤ &f &fThe user&6 %s &fisn't banned from your faction."),
    COMMAND_UNBAN_TARGET_IN_OTHER_FACTION("&e&lFactions &8➤ &f &fThe user&6 %1$s &fisn't in your faction."),
    COMMAND_UNBAN_UNBANNED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas unbanned&6 %2$s&f."),
    COMMAND_UNBAN_TARGETUNBANNED("&e&lFactions &8➤ &f &fYou were unbanned from&6 %s&f."),

    COMMAND_UNCLAIM_SAFEZONE_SUCCESS("&aThe safezone was unclaimed."),
    COMMAND_UNCLAIM_SAFEZONE_NOPERM("&e&lFactions &8➤ &f &cThis is a safezone, you lack permissions to unclaim."),
    COMMAND_UNCLAIM_WARZONE_SUCCESS("&aThe warzone was unclaimed."),
    COMMAND_UNCLAIM_WARZONE_NOPERM("&e&lFactions &8➤ &f &cThis is a warzone, you lack permissions to unclaim."),
    COMMAND_UNCLAIM_UNCLAIMED("&e&lFactions &8➤ &f &fThe user&6 %1$s &funclaimed some of your land."),
    COMMAND_UNCLAIM_UNCLAIMS("&e&lFactions &8➤ &f &fYou've unclaimed this land."),
    COMMAND_UNCLAIM_LOG("The user %1$s unclaimed land at %2$s from the faction '%3$s'."),
    COMMAND_UNCLAIM_WRONGFACTION("&e&lFactions &8➤ &f &cYou don't own this land."),
    COMMAND_UNCLAIM_TOUNCLAIM("to unclaim this land"),
    COMMAND_UNCLAIM_FORUNCLAIM("for unclaiming this land"),
    COMMAND_UNCLAIM_FACTIONUNCLAIMED("&e&lFactions &8➤ &f &fThe user&6 %1$s &funclaimed some land."),
    COMMAND_UNCLAIM_DESCRIPTION("Unclaim the land where you are standing."),
    COMMAND_UNCLAIM_SPAWNERCHUNK_SPAWNERS("&e&lFactions &8➤ &f &fYou may not unclaim a spawnerchunk whilst there are still spawners in it! &eSpawner Count: %1$s"),

    COMMAND_UNCLAIMALL_TOUNCLAIM("to unclaim all faction land"),
    COMMAND_UNCLAIMALL_FORUNCLAIM("for unclaiming all faction land"),
    COMMAND_UNCLAIMALL_UNCLAIMED("&e&lFactions &8➤ &f &fThe user&6 %1$s &funclaimed all of your faction's land."),
    COMMAND_UNCLAIMALL_LOG("The user %1$s unclaimed all the land of the faction '%2$s'."),
    COMMAND_UNCLAIMALL_DESCRIPTION("Unclaim all of your faction's land."),
    COMMAND_UNCLAIM_CLICKTOUNCLAIM("Click to unclaim - %1$d, %2$d."),

    COMMAND_VERSION_NAME("&8➤ &eManafia Factions"),
    COMMAND_VERSION_VERSION("&e&lFactions &8➤ &f &fVersion » %1$s."),
    COMMAND_VERSION_DESCRIPTION("Show the plugin's information."),

    COMMAND_WARUNCLAIMALL_DESCRIPTION("Unclaim all the warzone land."),
    COMMAND_WARUNCLAIMALL_SUCCESS("&aYou've unclaimed all the warzone land."),
    COMMAND_WARUNCLAIMALL_LOG("The user %1$s has unclaimed all warzone land."),


    COMMAND_DRAIN_DESCRIPTION("The ability to obtain all the money in faction member's balances."),
    COMMAND_DRAIN_NO_PLAYERS("&e&lFactions &8➤ &f &cYou can't drain a faction with no other members."),
    COMMAND_DRAIN_RECIEVED_AMOUNT("&e&lFactions &8➤ &f &fYou've drained all of your faction member's balance and received&6 %1$s&f."),
    COMMAND_DRAIN_INVALID_AMOUNT("&e&lFactions &8➤ &f &cYou can't drain a faction with no worth."),


    COMMAND_WILD_DESCRIPTION("Teleport to a random location."),
    COMMAND_WILD_WAIT("Teleporting in %1$s."),
    COMMAND_WILD_SUCCESS("&aTeleporting you to a random location."),
    COMMAND_WILD_INTERUPTED("&e&lFactions &8➤ &f &cThe teleport has been cancelled."),
    COMMAND_WILD_FAILED("&e&lFactions &8➤ &f &cCouldn't find a location to teleport you to."),
    COMMAND_WILD_INPROGRESS("&e&lFactions &8➤ &f &cYou're already teleporting somewhere."),

    COMMAND_RULES_DISABLED_MSG("&e&lFactions &8➤ &f &cThis command is currently disabled."),
    COMMAND_RULES_DESCRIPTION("Set, remove or add rules!"),
    COMMAND_RULES_ADD_INVALIDARGS("Please include a rule."),
    COMMAND_RULES_SET_INVALIDARGS("Please include a line number & rule."),
    COMMAND_RULES_REMOVE_INVALIDARGS("Please include a line number."),
    COMMAND_RULES_ADD_SUCCESS("&aYou've added that rule successfully."),
    COMMAND_RULES_REMOVE_SUCCESS("&e&lFactions &8➤ &f &cYou've removed that rule successfully."),
    COMMAND_RULES_SET_SUCCESS("&aYou've set that rule successfully."),
    COMMAND_RULES_CLEAR_SUCCESS("&aYou've cleared that rule successfully."),

    // F Global \\
    COMMAND_F_GLOBAL_TOGGLE("You've %1$s the global chat."),
    COMMAND_F_GLOBAL_DESCRIPTION("Toggle the global chat feature."),

    /**
     * Leaving - This is accessed through a command, and so it MAY need a COMMAND_* slug :s
     */
    LEAVE_PASSADMIN("&e&lFactions &8➤ &f &fYou must give the administrator role to someone else first."),
    LEAVE_NEGATIVEPOWER("&e&lFactions &8➤ &f &cYou can't leave until your power is positive."),
    LEAVE_TOLEAVE("to leave your faction."),
    LEAVE_FORLEAVE("for leaving your faction."),
    LEAVE_LEFT("&e&lFactions &8➤ &f &f&6%s&f has left the faction"),
    LEAVE_DISBANDED("&e&lFactions &8➤ &f &fThe faction &6 %1$s &f was disbanded."),
    LEAVE_DISBANDEDLOG("&fThe faction &6%s &f(%s) was disbanded due to the last player &6%s &f leaving."),
    LEAVE_DESCRIPTION("Leave your current faction."),
    AUTOLEAVE_ADMIN_PROMOTED("&e&lFactions &8➤ &f &fThe faction administrator&6 %s &fhas been removed. The user&6 %s &fhas been promoted as the new faction administrator."),

    /**
     * Claiming - Same as above basically. No COMMAND_* because it's not in a command class, but...
     */
    CLAIM_PROTECTED("&e&lFactions &8➤ &f &fThis land is currently protected."),
    CLAIM_DISABLED("&e&lFactions &8➤ &f &cThis world has land claiming currently disabled."),
    CLAIM_CANTCLAIM("&e&lFactions &8➤ &f &cYou can't claim land for %s."),
    CLAIM_ALREADYOWN("&e&lFactions &8➤ &f&6 %s &f already owns this land."),
    CLAIM_MUSTBE("&e&lFactions &8➤ &f &fYou must be&6 %s &fto claim land."),
    CLAIM_MEMBERS("&e&lFactions &8➤ &f &fFactions must have at least&6 %s &fmembers to be able to claim land."),
    CLAIM_SAFEZONE("&e&lFactions &8➤ &f &cYou can't claim a safezone."),
    CLAIM_WARZONE("&e&lFactions &8➤ &f &cYou can't claim a warzone."),
    CLAIM_POWER("&e&lFactions &8➤ &f &cYou can't claim more land, you need more power."),
    CLAIM_LIMIT("&e&lFactions &8➤ &f &cYou can't claim more land as you've reached the limit."),
    CLAIM_ALLY("&e&lFactions &8➤ &f &cYou can't claim the land of your allies."),
    CLAIM_CONTIGIOUS("&e&lFactions &8➤ &f &fYou can only claim additional land which is connected to your first claim or controlled by another faction."),
    CLAIM_FACTIONCONTIGUOUS("&e&lFactions &8➤ &f &fYou can only claim additional land which is connected to your first claim."),
    CLAIM_PEACEFUL("&e&lFactions &8➤ &f &fThe faction '&6 %s &f' owns this land. Your faction is peaceful, you can't claim land from other factions."),
    CLAIM_PEACEFULTARGET("&e&lFactions &8➤ &f &fThe faction '&6 %s &f' owns this land and is a peaceful faction. You can't claim land from them."),
    CLAIM_THISISSPARTA("&e&lFactions &8➤ &f &fThe faction '&6 %s &f' owns this land and is strong enough to keep it."),
    CLAIM_BORDER("&e&lFactions &8➤ &f &fYou must start claiming land at the border of the territory."),
    CLAIM_TOCLAIM("to claim this land"),
    CLAIM_FORCLAIM("for claiming this land"),
    CLAIM_TOOVERCLAIM("to overclaim this land"),
    CLAIM_FOROVERCLAIM("for over claiming this land"),
    CLAIM_RADIUS_CLAIM("%1$s &eclaimed %2$s chunks &astarting from &e(X: %3$s, Z: %4$s)"), //FIND OUT IF THE PLACEHOLDER IS PLAYER OR FACTION
    CLAIM_CLAIMED("&e&lFactions &8➤ &f &fThe user&6 %s &fhas claimed land for '&6 %s &f' from '&6 %s&f'."),
    CLAIM_CLAIMEDLOG("The user %s has claimed land at %s for the faction '%s'."),
    CLAIM_OVERCLAIM_DISABLED("&e&lFactions &8➤ &f &cOver-claiming is disabled on this server."),
    CLAIM_TOOCLOSETOOTHERFACTION("&e&lFactions &8➤ &f &fYour claim is too close to another faction. The buffer required is&6 %d&f."),
    CLAIM_OUTSIDEWORLDBORDER("&e&lFactions &8➤ &f &fYour claim is outside the border."),
    CLAIM_OUTSIDEBORDERBUFFER("&e&lFactions &8➤ &f &fYour claim is outside the border."),
    CLAIM_CLICK_TO_CLAIM("&aClick to try claiming %1$d, %2$d."),
    CLAIM_MAP_OUTSIDEBORDER("&e&lFactions &8➤ &f &cThis claim is outside the world border."),
    CLAIM_YOUAREHERE("You're here."),
    CLAIM_NO_TERRITORY_PERM("&e&lFactions &8➤ &f &cYou don't have permission from your faction leader to do this."),

    /**
     * More generic, or less easily categorisable translations, which may apply to more than one class
     */
    GENERIC_YOU("you"),
    GENERIC_YOURFACTION("your faction"),
    GENERIC_NOPERMISSION("&e&lFactions &8➤ &f &cYou don't have permission to %1$s."),
    GENERIC_ACTION_NOPERMISSION("&e&lFactions &8➤ &f &cYou don't have permission to use %1$s"),
    GENERIC_FPERM_NOPERMISSION("&e&lFactions &8➤ &f &cThe faction leader doesn't allow you to %1$s."),
    GENERIC_DOTHAT("do that"),  //Ugh nuke this from high orbit
    GENERIC_NOPLAYERMATCH("&e&lFactions &8➤ &f &cNo player match found for '<plugin>%1$s'."),
    GENERIC_NOPLAYERFOUND("&e&lFactions &8➤ &f &cNo player '<plugin>%1$s' could be found."),
    GENERIC_ARGS_TOOFEW("Too few arguments. Use like this:"), //NEED A PLACEHOLDER
    GENERIC_ARGS_TOOMANY("&e&lFactions &8➤ &f &cStrange argument '<plugin>%1$s&c'. Use the command like this:"), //NEED A PLACEHOLDER
    GENERIC_DEFAULTDESCRIPTION("This is the default faction description."),
    GENERIC_OWNERS("Owner(s) - %1$s."),
    GENERIC_PUBLICLAND("Public faction land."),
    GENERIC_FACTIONLESS("factionless"),
    GENERIC_SERVERADMIN("A server admin"), //NEED TO FIND OUT WHERE THIS MESSAGE IS USED
    GENERIC_SERVER("ManafiaFactions Server"), //NEEED TO FIND OUT WHERE THIS MESSAGE IS USED
    GENERIC_DISABLED("&e&lFactions &8➤ &f &cThe feature %1$s is currently disabled."),
    GENERIC_ENABLED("&aenabled"),
    GENERIC_INFINITY("âˆž"),
    GENERIC_NULLPLAYER("null player"),
    GENERIC_CONSOLEONLY("&e&lFactions &8➤ &f &cThis command can't be run as a user."),
    GENERIC_PLAYERONLY("This command can only be used by in-game users."),
    GENERIC_ASKYOURLEADER("You need to ask your leader to ."), //NEED A PLACEHOLDER HERE
    GENERIC_YOUSHOULD("You should ."), //NEED A PLACEHOLDER HERE
    GENERIC_YOUMAYWANT("You may want to ."), //NEED A PLACEHOLDER HERE
    GENERIC_TRANSLATION_VERSION("Translation - %1$s(%2$s,%3$s), state - %4$s."),
    GENERIC_TRANSLATION_CONTRIBUTORS("Translation contributors - %1$s."),
    GENERIC_TRANSLATION_RESPONSIBLE("Responsible for translation - %1$s."),
    GENERIC_FACTIONTAG_BLACKLIST("&e&lFactions &8➤ &f &cThat faction tag is blacklisted."),
    GENERIC_FACTIONTAG_TOOSHORT("&e&lFactions &8➤ &f &cThe faction tag can't be shorter than %1$s characters."),
    GENERIC_FACTIONTAG_TOOLONG("&e&lFactions &8➤ &f &cThe faction tag can't be longer than %s characters."),
    GENERIC_FACTIONTAG_ALPHANUMERIC("&e&lFactions &8➤ &f &cThe faction tag must be alphanumeric. The argument '%s' isn't allowed."),
    GENERIC_PLACEHOLDER("<This is a placeholder for a message you should not see>"), //FIND OUT WHAT THE HECK THIS IS
    GENERIC_NOTENOUGHMONEY("&e&lFactions &8➤ &f &cYou don't have enough money."),
    GENERIC_MONEYTAKE("&e&lFactions &8➤ &f &cThe amount {amount} has been taken from your account."),
    GENERIC_FPERM_OWNER_NOPERMISSION("&e&lFactions &8➤ &f &cThis land is owner claimed, you need to be an owner to %1$s it."),
    GENERIC_NOFACTION_FOUND("&e&lFactions &8➤ &f &cCouldn't find a faction with that name."),
    GENERIC_YOUMUSTBE("&e&lFactions &8➤ &f &cYou must be at least %1$s to do this."),
    GENERIC_MEMBERONLY("&e&lFactions &8➤ &f &cYou must be in a faction to do this."),
    GENERIC_WORLDGUARD("&e&lFactions &8➤ &f &cThis area is world-guard protected."),
    GRACE_DISABLED_PLACEHOLDER("Disabled"), //FIND OUT WHERE THIS IS USED TO SEE IF IT NEEDS CAPITAL OR NOT
    MACRO_DETECTED("&e&lFactions &8➤ &f &cNo sir!"),
    //I AM HERE!

    // MISSION_CREATED_COOLDOWN("&e&lFactions &8➤ &f &c&l[!] &7Due to your immediate faction creation, you may not start missions for &b%1$s minutes&7!"),
    MISSION_MISSION_STARTED("&e&lFactions &8➤ &f &fThe user&6 %1$s &fhas started the&6 %2$s &fmission."),
    MISSION_ALREAD_COMPLETED("&e&lFactions &8➤ &f &fYou can't restart a mission that you've already completed."),
    MISSION_MISSION_ACTIVE("&e&lFactions &8➤ &f &fThis mission is currently active."),
    MISSION_MISSION_MAX_ALLOWED("&e&lFactions &8➤ &f &fYou can't have more than&6 %1$s &fmissions active at once."),
    MISSION_MISSION_ALL_COMPLETED("&e&lFactions &8➤ &f &fYour faction has completed all the available missions."),
    MISSION_MISSION_FINISHED("&e&lFactions &8➤ &f &fYour faction has successfully completed the&6 %1$s &fmission."),
    COMMAND_MISSION_DESCRIPTION("Opens the missions menu."),
    MISSION_MISSION_CANCELLED("&e&lFactions &8➤ &f &fYou've cancelled your faction's current mission."),
    // F Global \\


    PLAYER_NOT_FOUND("&e&lFactions &8➤ &f &cThe user %1$s is either offline or not in your faction."),
    PLACEHOLDER_ROLE_NAME("None"), //FIND OUT WHAT THIS IS
    PLACEHOLDER_CUSTOM_FACTION("{faction}"), //FIND OUT WHAT THIS IS

    SPAWNER_CHUNK_PLACE_DENIED_WILDERNESS("&e&lFactions &8➤ &fYou may not place spawners in wilderness!"),
    SPAWNER_CHUNK_PLACE_DENIED_NOT_SPAWNERCHUNK("&e&lFactions &8➤ &fYou may not place spawners in this chunk. Only in spawner chunks!"),


    WARBANNER_NOFACTION("&e&lFactions &8➤ &f &cYou need a faction to use a warbanner."),
    WARBANNER_COOLDOWN("&e&lFactions &8➤ &f &cThe warbanner is on cooldown for your faction."),
    WARBANNER_INVALIDLOC("&e&lFactions &8➤ &f &cYou can only use warbanners in enemy land or the warzone."),

    /**
     * ASCII compass (for chat map)
     */
    COMPASS_SHORT_NORTH("N"),
    COMPASS_SHORT_EAST("E"),
    COMPASS_SHORT_SOUTH("S"),
    COMPASS_SHORT_WEST("W"),

    /**
     * Chat modes
     */
    CHAT_MOD("moderator chat"),
    CHAT_FACTION("faction chat"),
    CHAT_ALLIANCE("alliance chat"),
    CHAT_TRUCE("truce chat"),
    CHAT_PUBLIC("public chat"),

    /**
     * Economy stuff
     */

    ECON_OFF("no %s"), // no balance, no value, no refund, etc
    ECON_FORMAT("###,###.###"),
    ECON_MONEYTRASFERREDFROM("The amount %1$s was transferred from %2$s to %3$s."),
    ECON_PERSONGAVEMONEYTO("The user %1$s gave %2$s to %3$s."),
    ECON_PERSONTOOKMONEYFROM("The user %1$s took %2$s from %3$s."),
    ECON_DISABLED("&e&lFactions &8➤ &f &cFactions economy is currently disabled."),
    ECON_OVER_BAL_CAP("The amount %s is over essential's balance limit."),
    ECON_MONEYLOST("&e&lFactions &8➤ &f &cThe faction %s&c lost %s&c %s."), //FIND OUT WHY ALL PLACEHOLDERS ARE THE SAME
    ECON_CANTAFFORD("&e&lFactions &8➤ &f &cThe faction %s can't afford %s %s."),
    ECON_UNABLETOTRANSFER("Unable to transfer %s to %s from %s."),
    ECON_PLAYERBALANCE("&e&lFactions &8➤ &f &fThe balance of&6 %s &fis&6 %s&f."),
    ECON_DEPOSITFAILED("&e&lFactions &8➤ &f &cThe user %s would've gained %s %s, but the deposit failed."),
    ECON_CANTCONTROLMONEY("&e&lFactions &8➤ &f &cThe user %s lacks the permission to control %s's money."),
    ECON_MONEYTRASFERREDFROMPERSONTOPERSON("The user %1$s has transferred %2$s from %3$s to %4$s."),


    /**
     * Relations
     */
    RELATION_MEMBER_SINGULAR("member"),
    RELATION_MEMBER_PLURAL("members"),
    RELATION_ALLY_SINGULAR("ally"),
    RELATION_ALLY_PLURAL("allies"),
    RELATION_TRUCE_SINGULAR("truce"),
    RELATION_TRUCE_PLURAL("truces"),
    RELATION_NEUTRAL_SINGULAR("neutral"),
    RELATION_NEUTRAL_PLURAL("neutrals"),
    RELATION_ENEMY_SINGULAR("enemy"),
    RELATION_ENEMY_PLURAL("enemies"),

    /**
     * Roles
     */
    ROLE_LEADER("leader"),
    ROLE_COLEADER("co-leader"),
    ROLE_MODERATOR("moderator"),
    ROLE_NORMAL("normal member"),
    ROLE_RECRUIT("recruit"),

    /**
     * Region types.
     */
    REGION_SAFEZONE("safezone"),
    REGION_WARZONE("warzone"),
    REGION_WILDERNESS("wilderness"),

    REGION_PEACEFUL("peaceful territory"),
    /**
     * In the player and entity listeners
     */
    PLAYER_CANTHURT("&e&lFactions &8➤ &f &cYou can't harm other players in %s."),
    PLAYER_SAFEAUTO("&e&lFactions &8➤ &f &fThis land is now a safezone."),
    PLAYER_WARAUTO("&e&lFactions &8➤ &f &fThis land is now a warzone."),
    PLAYER_OUCH("That is starting to hurt, you should give it a rest."),
    PLAYER_USE_WILDERNESS("&e&lFactions &8➤ &f &cYou can't use %s in the wilderness."),
    PLAYER_USE_SAFEZONE("&e&lFactions &8➤ &f &cYou can't use %s in a safezone."),
    PLAYER_USE_WARZONE("&e&lFactions &8➤ &f &cYou can't use %s in a warzone."),
    PLAYER_USE_TERRITORY("&e&lFactions &8➤ &f &cYou can't %s in the territory of %s."),
    PLAYER_USE_OWNED("&e&lFactions &8➤ &f &cYou can't use %s in this territory, it's owned by '%s'."),
    PLAYER_COMMAND_WARZONE("&e&lFactions &8➤ &f &cYou can't use the command '%s' in warzone."),
    PLAYER_COMMAND_NEUTRAL("&e&lFactions &8➤ &f &cYou can't use the command '%s' in neutral territory."),
    PLAYER_COMMAND_ENEMY("&e&lFactions &8➤ &f &cYou can't use the command '%s' in enemy territory."),
    PLAYER_COMMAND_PERMANENT("&e&lFactions &8➤ &f &cYou can't use the command '%s' because you're in a permanent faction."),
    PLAYER_COMMAND_ALLY("&e&lFactions &8➤ &f &cYou can't use the command '%s' in ally territory."),
    PLAYER_COMMAND_WILDERNESS("&e&lFactions &8➤ &f &cYou can't use the command '%s' in the wilderness."),

    PLAYER_POWER_NOLOSS_PEACEFUL("&e&lFactions &8➤ &f &fYou didn't lose any power since you're in a peaceful faction."),
    PLAYER_POWER_NOLOSS_WORLD("&e&lFactions &8➤ &f &fYou didn't lose any power due to the world you died in."),
    PLAYER_POWER_NOLOSS_WILDERNESS("&e&lFactions &8➤ &f &fYou didn't lose any power since you were in the wilderness."),
    PLAYER_POWER_NOLOSS_WARZONE("&e&lFactions &8➤ &f &fYou didn't lose any power since you were in a warzone."),
    PLAYER_POWER_LOSS_WARZONE("&e&lFactions &8➤ &f &cThe world you're in has power loss normally disabled, but you still lost power since you were in a warzone."),
    PLAYER_POWER_NOW("Your power is now %d/%d."),

    PLAYER_PVP_LOGIN("&e&lFactions &8➤ &f &cYou can't hurt other users for '%d' seconds after logging in."),
    PLAYER_PVP_REQUIREFACTION("&e&lFactions &8➤ &f &fYou can't hurt other users until you join a faction."),
    PLAYER_PVP_FACTIONLESS("&e&lFactions &8➤ &f &fYou can't hurt users who aren't currently in a faction."),
    PLAYER_PVP_PEACEFUL("&e&lFactions &8➤ &f &fPeaceful players can't participate in combat."),
    PLAYER_PVP_NEUTRAL("&e&lFactions &8➤ &f &cYou can't hurt neutral factions."),
    PLAYER_PVP_CANTHURT("&e&lFactions &8➤ &f &cYou can't hurt %s."),

    PLAYER_PVP_NEUTRALFAIL("&e&lFactions &8➤ &f &cYou can't hurt '%s' in their own territory unless you declare them as an enemy."),
    PLAYER_PVP_TRIED("&e&lFactions &8➤ &f &cThe user %s tried to hurt you."),

    /**
     * Strings lying around in other bits of the plugins
     */
    NOPAGES("There's no pages available."),
    INVALIDPAGE("Invalid page - must be between 1 and %1$d."),

    /**
     * The ones here before I started messing around with this
     */
    TITLE("title", "&bFactions &0|&r"), //FIGURE OUT EVERYTHING BELOW THIS POINT
    WILDERNESS("wilderness", "&2Wilderness"),
    WILDERNESS_DESCRIPTION("wilderness-description", ""),
    WARZONE("warzone", "&4Warzone"),
    WARZONE_DESCRIPTION("warzone-description", "Not the safest place to be."),
    SAFEZONE("safezone", "&6Safezone"),
    SAFEZONE_DESCRIPTION("safezone-description", "Free from pvp and monsters."),
    TOGGLE_SB("toggle-sb", "You now have scoreboards set to {value}"),
    FACTION_LEAVE("faction-leave", "Leaving %1$s&r, Entering %2$s&r"),
    FACTIONS_ANNOUNCEMENT_TOP("faction-announcement-top", "&d--Unread Faction Announcements--"),
    FACTIONS_ANNOUNCEMENT_BOTTOM("faction-announcement-bottom", "&d--Unread Faction Announcements--"),
    DEFAULT_PREFIX("default-prefix", "{relationcolor}[{faction}]"),
    FACTION_LOGIN("faction-login", "&e%1$s &9logged in."),
    FACTION_LOGOUT("faction-logout", "&e%1$s &9logged out.."),
    NOFACTION_PREFIX("nofactions-prefix", "&6&awilderness&6&r"),
    DATE_FORMAT("date-format", "MM/d/yy h:ma"), // 3/31/15 07:49AM

    /**
     * Raidable is used in multiple places. Allow more than just true/false.
     */
    RAIDABLE_TRUE("raidable-true", "true"),
    RAIDABLE_FALSE("raidable-false", "false"),
    /**
     * Warmups
     */
    WARMUPS_NOTIFY_FLIGHT("Flight will enable in %2$d seconds."),
    WARMUPS_NOTIFY_TELEPORT("You'll teleport to %1$s in %2$d seconds."),
    WARMUPS_ALREADY("You're already warming up."),
    WARMUPS_CANCELLED("You've cancelled your warmup."),

    COMMAND_BOOSTERS_ADD_DESCRIPTION("Give a faction a booster."),
    COMMAND_BOOSTERS_ADD_INVALID_FAC("&e&lFactions &8➤&cThe faction %s doesn't exist."),
    COMMAND_BOOSTERS_ADD_INVALID_TYPE("&e&lFactions &8➤&cThe booster type %s doesn't exist."),
    COMMAND_BOOSTERS_ADD_INVALID_MULTIPLIER("&e&lFactions &8➤&cThe multiplier %s is not valid."),
    COMMAND_BOOSTERS_ADD_INVALID_TIME("&e&lFactions &8➤&cThe time value %s is not valid."),
    COMMAND_BOOSTERS_ADD_SUCCESSFUL("&e&lFactions &8➤&aSuccessfully added booster type %s to faction %s with a multiplier of %s for %s."),
    COMMAND_BOOSTERS_ADD_ACTIVE("&e&lFactions &8➤&cThe booster type %s is already active for this faction."),
    COMMAND_BOOSTERS_REMOVE_DESCRIPTION("Remove a faction a booster."),
    COMMAND_BOOSTERS_REMOVE_INVALID_FAC("&e&lFactions &8➤&cThe faction %s doesn't exist."),
    COMMAND_BOOSTERS_REMOVE_INVALID_TYPE("&e&lFactions &8➤&cThe booster type %s doesn't exist."),
    COMMAND_BOOSTERS_REMOVE_INVALID_NOT_ACTIVE("&e&lFactions &8➤&cThe booster type %s is not active for this faction."),
    COMMAND_BOOSTERS_REMOVE_SUCCESSFUL("&aSuccessfully removed booster type %s from faction %s."),
    COMMAND_BOOSTERS_ALREADY_ACTIVE("&e&lFactions &8➤&cYou already have an active %s booster."),
    COMMAND_BOOSTERS_NOT_ENOUGH("&e&lFactions &8➤&cYou do not have enough money to complete this transaction."),
    COMMAND_BOOSTERS_PURCHASED("&e&lFactions &8➤&aYou have successfully purchased a %s booster for your faction."),
    COMMAND_BOOSTERS_DESCRIPTION("Open the booster shop gui."),
    COMMAND_BOOSTERS_STARTED_ANNOUNCE("&e&lFactions &8➤&aThe faction %s has just activated a %s booster for %s."),


    //RAIDS

    COMMAND_RAID_DESCRIPTION("Start a faction raid."),
    COMMAND_RAID_START(" "),
    COMMAND_RAID_STOP(" "),
    COMMAND_RAID_ACTIVE(" "),
    COMMAND_RAID_STARTED_ANNOUNCE(" "),


    PLACEHOLDERAPI_NULL("");

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static SimpleDateFormat sdf;
    private final String path;
    private final String def;

    TL(String path, String start) {
        this.path = path;
        this.def = start;
    }

    TL(String start) {
        this.path = this.name().toLowerCase();
        this.def = start;
    }

    public static Stream<TL> stream() {
        return Stream.of(TL.values());
    }

    public String format(Object... args) {
        return String.format(toString(), args);
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', FactionsPlugin.langMap.get(this.path));
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }
}
