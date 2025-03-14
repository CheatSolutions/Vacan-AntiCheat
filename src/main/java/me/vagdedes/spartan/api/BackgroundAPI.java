package me.vagdedes.spartan.api;

import me.vagdedes.spartan.api.system.Enums.HackType;
import me.vagdedes.spartan.api.system.Enums.Permission;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BackgroundAPI {

    static String licenseID() {
        return ai.idealistic.vacan.api.BackgroundAPI.licenseID();
    }

    static String getVersion() {
        return ai.idealistic.vacan.api.BackgroundAPI.getVersion();
    }

    static String getMessage(String path) {
        return ai.idealistic.vacan.api.BackgroundAPI.getMessage(path);
    }

    static boolean getSetting(String path) {
        return ai.idealistic.vacan.api.BackgroundAPI.getSetting(path);
    }

    @Deprecated
    static String getCategory(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getCategory(p, hackType.getHackType());
    }

    static boolean hasVerboseEnabled(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.hasVerboseEnabled(p);
    }

    static boolean hasNotificationsEnabled(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.hasNotificationsEnabled(p);
    }

    @Deprecated
    static int getViolationResetTime() {
        return ai.idealistic.vacan.api.BackgroundAPI.getViolationResetTime();
    }

    static void setVerbose(Player p, boolean value) {
        ai.idealistic.vacan.api.BackgroundAPI.setVerbose(p, value);
    }

    static void setNotifications(Player p, boolean value) {
        ai.idealistic.vacan.api.BackgroundAPI.setNotifications(p, value);
    }

    @Deprecated
    static void setVerbose(Player p, boolean value, int frequency) {
        ai.idealistic.vacan.api.BackgroundAPI.setVerbose(p, value, frequency);
    }

    static void setNotifications(Player p, int frequency) {
        ai.idealistic.vacan.api.BackgroundAPI.setNotifications(p, frequency);
    }

    static int getPing(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.getPing(p);
    }

    @Deprecated
    static double getTPS() {
        return ai.idealistic.vacan.api.BackgroundAPI.getTPS();
    }

    static boolean hasPermission(Player p, Permission Permission) {
        return ai.idealistic.vacan.api.BackgroundAPI.hasPermission(p, Permission.getPermission());
    }

    static boolean isEnabled(HackType HackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.isEnabled(HackType.getHackType());
    }

    static boolean isSilent(HackType HackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.isSilent(HackType.getHackType());
    }

    static int getVL(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getVL(p, hackType.getHackType());
    }

    static double getCertainty(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getCertainty(p, hackType.getHackType());
    }

    static double getDecimalVL(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getDecimalVL(p, hackType.getHackType());
    }

    static int getVL(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.getVL(p);
    }

    static double getDecimalVL(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.getDecimalVL(p);
    }

    @Deprecated
    static void setVL(Player p, HackType HackType, int amount) {
        ai.idealistic.vacan.api.BackgroundAPI.setVL(p, HackType.getHackType(), amount);
    }

    @Deprecated
    static int getCancelViolation(HackType hackType, String worldName) {
        return ai.idealistic.vacan.api.BackgroundAPI.getCancelViolation(hackType.getHackType(), worldName);
    }

    @Deprecated
    static int getCancelViolation(HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getCancelViolation(hackType.getHackType());
    }

    @Deprecated
    static int getViolationDivisor(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getViolationDivisor(p, hackType.getHackType());
    }

    static void reloadConfig() {
        ai.idealistic.vacan.api.BackgroundAPI.reloadConfig();
    }

    @Deprecated
    static void reloadPermissions() {
        ai.idealistic.vacan.api.BackgroundAPI.reloadPermissions();
    }

    @Deprecated
    static void reloadPermissions(Player p) {
        ai.idealistic.vacan.api.BackgroundAPI.reloadPermissions(p);
    }

    static void enableCheck(HackType HackType) {
        ai.idealistic.vacan.api.BackgroundAPI.enableCheck(HackType.getHackType());
    }

    static void disableCheck(HackType HackType) {
        ai.idealistic.vacan.api.BackgroundAPI.disableCheck(HackType.getHackType());
    }

    static void cancelCheck(Player p, HackType hackType, int ticks) {
        ai.idealistic.vacan.api.BackgroundAPI.cancelCheck(p, hackType.getHackType(), ticks);
    }

    static void cancelCheckPerVerbose(Player p, String string, int ticks) {
        ai.idealistic.vacan.api.BackgroundAPI.cancelCheckPerVerbose(p, string, ticks);
    }

    static void enableSilentChecking(HackType HackType) {
        ai.idealistic.vacan.api.BackgroundAPI.enableSilentChecking(HackType.getHackType());
    }

    static void disableSilentChecking(HackType HackType) {
        ai.idealistic.vacan.api.BackgroundAPI.disableSilentChecking(HackType.getHackType());
    }

    static void enableSilentChecking(Player p, HackType hackType) {
        ai.idealistic.vacan.api.BackgroundAPI.enableSilentChecking(p, hackType.getHackType());
    }

    static void disableSilentChecking(Player p, HackType hackType) {
        ai.idealistic.vacan.api.BackgroundAPI.disableSilentChecking(p, hackType.getHackType());
    }

    static void startCheck(Player p, HackType hackType) {
        ai.idealistic.vacan.api.BackgroundAPI.startCheck(p, hackType.getHackType());
    }

    static void stopCheck(Player p, HackType hackType) {
        ai.idealistic.vacan.api.BackgroundAPI.stopCheck(p, hackType.getHackType());
    }

    static void resetVL() {
        ai.idealistic.vacan.api.BackgroundAPI.resetVL();
    }

    static void resetVL(Player p) {
        ai.idealistic.vacan.api.BackgroundAPI.resetVL(p);
    }

    static boolean isBypassing(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.isBypassing(p);
    }

    static boolean isBypassing(Player p, HackType HackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.isBypassing(p, HackType.getHackType());
    }

    @Deprecated
    static void banPlayer(UUID uuid, String reason) {
        ai.idealistic.vacan.api.BackgroundAPI.banPlayer(uuid, reason);
    }

    @Deprecated
    static boolean isBanned(UUID uuid) {
        return ai.idealistic.vacan.api.BackgroundAPI.isBanned(uuid);
    }

    @Deprecated
    static void unbanPlayer(UUID uuid) {
        ai.idealistic.vacan.api.BackgroundAPI.unbanPlayer(uuid);
    }

    @Deprecated
    static String getBanReason(UUID uuid) {
        return ai.idealistic.vacan.api.BackgroundAPI.getBanReason(uuid);
    }

    @Deprecated
    static String getBanPunisher(UUID uuid) {
        return ai.idealistic.vacan.api.BackgroundAPI.getBanPunisher(uuid);
    }

    @Deprecated
    static boolean isHacker(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.isHacker(p);
    }

    @Deprecated
    static boolean isLegitimate(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.isLegitimate(p);
    }

    @Deprecated
    static boolean hasMiningNotificationsEnabled(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.hasMiningNotificationsEnabled(p);
    }

    @Deprecated
    static void setMiningNotifications(Player p, boolean value) {
        ai.idealistic.vacan.api.BackgroundAPI.setMiningNotifications(p, value);
    }

    @Deprecated
    static int getCPS(Player p) {
        return ai.idealistic.vacan.api.BackgroundAPI.getCPS(p);
    }

    @Deprecated
    static UUID[] getBanList() {
        return ai.idealistic.vacan.api.BackgroundAPI.getBanList();
    }

    static boolean addToWave(UUID uuid, String command) {
        return ai.idealistic.vacan.api.BackgroundAPI.addToWave(uuid, command);
    }

    static void removeFromWave(UUID uuid) {
        ai.idealistic.vacan.api.BackgroundAPI.removeFromWave(uuid);
    }

    static void clearWave() {
        ai.idealistic.vacan.api.BackgroundAPI.clearWave();
    }

    static void runWave() {
        ai.idealistic.vacan.api.BackgroundAPI.runWave();
    }

    static UUID[] getWaveList() {
        return ai.idealistic.vacan.api.BackgroundAPI.getWaveList();
    }

    static int getWaveSize() {
        return ai.idealistic.vacan.api.BackgroundAPI.getWaveSize();
    }

    static boolean isAddedToTheWave(UUID uuid) {
        return ai.idealistic.vacan.api.BackgroundAPI.isAddedToTheWave(uuid);
    }

    static void warnPlayer(Player p, String reason) {
        ai.idealistic.vacan.api.BackgroundAPI.warnPlayer(p, reason);
    }

    @Deprecated
    static void addPermission(Player p, Permission permission) {
        ai.idealistic.vacan.api.BackgroundAPI.addPermission(p, permission.getPermission());
    }

    @Deprecated
    static void sendClientSidedBlock(Player p, Location loc, Material m, byte b) {
        ai.idealistic.vacan.api.BackgroundAPI.sendClientSidedBlock(p, loc, m, b);
    }

    @Deprecated
    static void destroyClientSidedBlock(Player p, Location loc) {
        ai.idealistic.vacan.api.BackgroundAPI.destroyClientSidedBlock(p, loc);
    }

    @Deprecated
    static void removeClientSidedBlocks(Player p) {
        ai.idealistic.vacan.api.BackgroundAPI.removeClientSidedBlocks(p);
    }

    @Deprecated
    static boolean containsClientSidedBlock(Player p, Location loc) {
        return ai.idealistic.vacan.api.BackgroundAPI.containsClientSidedBlock(p, loc);
    }

    @Deprecated
    static Material getClientSidedBlockMaterial(Player p, Location loc) {
        return ai.idealistic.vacan.api.BackgroundAPI.getClientSidedBlockMaterial(p, loc);
    }

    @Deprecated
    static byte getClientSidedBlockData(Player p, Location loc) {
        return ai.idealistic.vacan.api.BackgroundAPI.getClientSidedBlockData(p, loc);
    }

    static String getConfiguredCheckName(HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getConfiguredCheckName(hackType.getHackType());
    }

    static void setConfiguredCheckName(HackType hackType, String name) {
        ai.idealistic.vacan.api.BackgroundAPI.setConfiguredCheckName(hackType.getHackType(), name);
    }

    @Deprecated
    static void disableVelocityProtection(Player p, int ticks) {
        ai.idealistic.vacan.api.BackgroundAPI.disableVelocityProtection(p, ticks);
    }

    @Deprecated
    static void setOnGround(Player p, int ticks) {
        ai.idealistic.vacan.api.BackgroundAPI.setOnGround(p, ticks);
    }

    @Deprecated
    static int getMaxPunishmentViolation(HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getMaxPunishmentViolation(hackType.getHackType());
    }

    @Deprecated
    static int getMinPunishmentViolation(HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.getMinPunishmentViolation(hackType.getHackType());
    }

    @Deprecated
    static boolean mayPunishPlayer(Player p, HackType hackType) {
        return ai.idealistic.vacan.api.BackgroundAPI.mayPunishPlayer(p, hackType.getHackType());
    }

}
