package ai.idealistic.vacan.api;

import ai.idealistic.vacan.Register;
import ai.idealistic.vacan.abstraction.Enums;
import ai.idealistic.vacan.abstraction.Enums.HackType;
import ai.idealistic.vacan.abstraction.Enums.Permission;
import ai.idealistic.vacan.abstraction.check.CheckDetection;
import ai.idealistic.vacan.abstraction.check.CheckRunner;
import ai.idealistic.vacan.abstraction.protocol.PlayerProtocol;
import ai.idealistic.vacan.functionality.connection.IDs;
import ai.idealistic.vacan.functionality.moderation.AwarenessNotifications;
import ai.idealistic.vacan.functionality.moderation.DetectionNotifications;
import ai.idealistic.vacan.functionality.moderation.Wave;
import ai.idealistic.vacan.functionality.server.Config;
import ai.idealistic.vacan.functionality.server.Permissions;
import ai.idealistic.vacan.functionality.server.PluginBase;
import ai.idealistic.vacan.utils.math.AlgebraUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class BackgroundAPI {

    static String licenseID() {
        return IDs.user();
    }

    static String getVersion() {
        return Register.plugin != null ? Register.plugin.getDescription().getVersion() : "Unknown";
    }

    static String getMessage(String path) {
        return Config.messages.getColorfulString(path);
    }

    static boolean getSetting(String path) {
        return Config.settings.getBoolean(path);
    }

    @Deprecated
    static String getCategory(Player p, HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'getCategory' has been removed.");
        return null;
    }

    static boolean hasVerboseEnabled(Player p) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);
        return DetectionNotifications.isVerboseEnabled(protocol);
    }

    static boolean hasNotificationsEnabled(Player p) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);
        return DetectionNotifications.isEnabled(protocol);
    }

    @Deprecated
    static int getViolationResetTime() {
        AwarenessNotifications.forcefullySend("The API method 'getViolationResetTime' has been removed.");
        return 0;
    }

    static void setVerbose(Player p, boolean value) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);

        if (value) {
            DetectionNotifications.addVerbose(protocol);
        } else {
            DetectionNotifications.removeVerbose(protocol);
        }
    }

    static void setNotifications(Player p, boolean value) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);

        if (value) {
            DetectionNotifications.set(protocol, DetectionNotifications.defaultFrequency);
        } else {
            DetectionNotifications.remove(protocol);
        }
    }

    @Deprecated
    static void setVerbose(Player p, boolean value, int frequency) {
        AwarenessNotifications.forcefullySend("The API method 'setVerbose' has been removed.");
    }

    static void setNotifications(Player p, int frequency) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            DetectionNotifications.set(protocol, Math.abs(frequency));
        }
    }

    static int getPing(Player p) {
        return PluginBase.getProtocol(p).getPing();
    }

    @Deprecated
    static double getTPS() {
        AwarenessNotifications.forcefullySend("The API method 'getTPS' has been removed.");
        return 0.0;
    }

    static boolean hasPermission(Player p, Permission Permission) {
        return Permissions.has(p, Permission);
    }

    static boolean isEnabled(HackType HackType) {
        return HackType.getCheck().isEnabled(null, null);
    }

    static boolean isSilent(HackType HackType) {
        return HackType.getCheck().isSilent(null, null);
    }

    static int getVL(Player p, HackType hackType) {
        return AlgebraUtils.integerRound(getDecimalVL(p, hackType));
    }

    static double getCertainty(Player p, HackType hackType) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);
        Collection<CheckDetection> detections = protocol.profile().getRunner(hackType).getDetections();
        double avg = 0.0, punishment = 0.0;

        for (CheckDetection detection : detections) {
            avg += detection.getLevel(protocol.getDataType());
            punishment += detection.getPunishmentLevel(protocol.getDataType());
        }
        return AlgebraUtils.integerRound(
                (avg / detections.size()) / (punishment / detections.size())
        );
    }

    static double getDecimalVL(Player p, HackType hackType) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);
        Collection<CheckDetection> detections = protocol.profile().getRunner(hackType).getDetections();
        double avg = 0.0;

        for (CheckDetection detection : detections) {
            avg += detection.getLevel(protocol.getDataType());
        }
        return avg / detections.size();
    }

    static int getVL(Player p) {
        return AlgebraUtils.integerRound(getDecimalVL(p));
    }

    static double getDecimalVL(Player p) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);
        double avg = 0.0,
                detectionsTotal = 0.0;

        for (CheckRunner runner : protocol.profile().getRunners()) {
            Collection<CheckDetection> detections = runner.getDetections();

            for (CheckDetection detection : detections) {
                avg += detection.getLevel(protocol.getDataType());
            }
            detectionsTotal += detections.size();
        }
        return avg / detectionsTotal;
    }

    @Deprecated
    static void setVL(Player p, HackType HackType, int amount) {
        AwarenessNotifications.forcefullySend("The API method 'setVL' has been removed.");
    }

    @Deprecated
    static int getCancelViolation(HackType hackType, String worldName) {
        return getCancelViolation(hackType);
    }

    @Deprecated
    static int getCancelViolation(HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'getCancelViolation' has been removed.");
        return 0;
    }

    @Deprecated
    static int getViolationDivisor(Player p, HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'getViolationDivisor' has been removed.");
        return 0;
    }

    static void reloadConfig() {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            Config.reload(null);
        }
    }

    @Deprecated
    static void reloadPermissions() {
        AwarenessNotifications.forcefullySend("The API method 'reloadPermissions' has been removed.");
    }

    @Deprecated
    static void reloadPermissions(Player p) {
        AwarenessNotifications.forcefullySend("The API method 'reloadPermissions' has been removed.");
    }

    static void enableCheck(HackType HackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            HackType.getCheck().setEnabled(null, true);
        }
    }

    static void disableCheck(HackType HackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            HackType.getCheck().setEnabled(null, false);
        }
    }

    static void cancelCheck(Player p, HackType hackType, int ticks) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            protocol.profile().getRunner(hackType).addDisableCause("Developer-API", null, ticks);
        }
    }

    static void cancelCheckPerVerbose(Player p, String string, int ticks) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) { // Keep the null pointer protection to prevent the method from acting differently
            PlayerProtocol protocol = PluginBase.getProtocol(p);

            for (HackType hackType : Enums.HackType.values()) {
                protocol.profile().getRunner(hackType).addDisableCause("Developer-API", string, ticks);
            }
        }
    }

    static void enableSilentChecking(HackType HackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            HackType.getCheck().setSilent(null, true);
        }
    }

    static void disableSilentChecking(HackType HackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            HackType.getCheck().setSilent(null, false);
        }
    }

    static void enableSilentChecking(Player p, HackType hackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            protocol.profile().getRunner(hackType).addSilentCause("Developer-API", null, 0);
        }
    }

    static void disableSilentChecking(Player p, HackType hackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            protocol.profile().getRunner(hackType).removeSilentCause();
        }
    }

    static void startCheck(Player p, HackType hackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            protocol.profile().getRunner(hackType).removeDisableCause();
        }
    }

    static void stopCheck(Player p, HackType hackType) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            PlayerProtocol protocol = PluginBase.getProtocol(p);
            protocol.profile().getRunner(hackType).addDisableCause("Developer-API", null, 0);
        }
    }

    static void resetVL() {
        Collection<PlayerProtocol> protocols = PluginBase.getProtocols();

        if (!protocols.isEmpty()) {
            for (PlayerProtocol protocol : protocols) {
                for (CheckRunner runner : protocol.profile().getRunners()) {
                    for (CheckDetection detection : runner.getDetections()) {
                        detection.clearData(protocol.getDataType());
                    }
                }
            }
        }
    }

    static void resetVL(Player p) {
        PlayerProtocol protocol = PluginBase.getProtocol(p);

        for (CheckRunner runner : protocol.profile().getRunners()) {
            for (CheckDetection detection : runner.getDetections()) {
                detection.clearData(protocol.getDataType());
            }
        }
    }

    static boolean isBypassing(Player p) {
        return Permissions.isBypassing(p, null);
    }

    static boolean isBypassing(Player p, HackType HackType) {
        return Permissions.isBypassing(p, HackType);
    }

    @Deprecated
    static void banPlayer(UUID uuid, String reason) {
        AwarenessNotifications.forcefullySend("The API method 'banPlayer' has been removed.");

    }

    @Deprecated
    static boolean isBanned(UUID uuid) {
        AwarenessNotifications.forcefullySend("The API method 'isBanned' has been removed.");
        return false;
    }

    @Deprecated
    static void unbanPlayer(UUID uuid) {
        AwarenessNotifications.forcefullySend("The API method 'unbanPlayer' has been removed.");
    }

    @Deprecated
    static String getBanReason(UUID uuid) {
        AwarenessNotifications.forcefullySend("The API method 'getBanReason' has been removed.");
        return null;
    }

    @Deprecated
    static String getBanPunisher(UUID uuid) {
        AwarenessNotifications.forcefullySend("The API method 'getBanPunisher' has been removed.");
        return null;
    }

    @Deprecated
    static boolean isHacker(Player p) {
        AwarenessNotifications.forcefullySend("The API method 'isHacker' has been removed.");
        return false;
    }

    @Deprecated
    static boolean isLegitimate(Player p) {
        AwarenessNotifications.forcefullySend("The API method 'isLegitimate' has been removed.");
        return false;
    }

    @Deprecated
    static boolean hasMiningNotificationsEnabled(Player p) {
        return hasNotificationsEnabled(p);
    }

    @Deprecated
    static void setMiningNotifications(Player p, boolean value) {
        setNotifications(p, value);
    }

    @Deprecated
    static int getCPS(Player p) {
        AwarenessNotifications.forcefullySend("The API method 'getCPS' has been removed.");
        return 0;
    }

    @Deprecated
    static UUID[] getBanList() {
        AwarenessNotifications.forcefullySend("The API method 'getBanList' has been removed.");
        return new UUID[]{};
    }

    static boolean addToWave(UUID uuid, String command) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            Wave.add(uuid, command);
            return true;
        }
        return false;
    }

    static void removeFromWave(UUID uuid) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            Wave.remove(uuid);
        }
    }

    static void clearWave() {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            Wave.clear();
        }
    }

    static void runWave() {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            Wave.start();
        }
    }

    static UUID[] getWaveList() {
        return Wave.getWaveList();
    }

    static int getWaveSize() {
        return Wave.getWaveList().length;
    }

    static boolean isAddedToTheWave(UUID uuid) {
        return Wave.getCommand(uuid) != null;
    }

    static void warnPlayer(Player p, String reason) {
        AwarenessNotifications.forcefullySend("The API method 'warnPlayer' has been removed.");
    }

    @Deprecated
    static void addPermission(Player p, Permission permission) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            AwarenessNotifications.forcefullySend("The API method 'addPermission' has been removed.");
        }
    }

    @Deprecated
    static void sendClientSidedBlock(Player p, Location loc, Material m, byte b) {
        AwarenessNotifications.forcefullySend("The API method 'sendClientSidedBlock' has been removed.");
    }

    @Deprecated
    static void destroyClientSidedBlock(Player p, Location loc) {
        AwarenessNotifications.forcefullySend("The API method 'destroyClientSidedBlock' has been removed.");
    }

    @Deprecated
    static void removeClientSidedBlocks(Player p) {
        AwarenessNotifications.forcefullySend("The API method 'removeClientSidedBlocks' has been removed.");
    }

    @Deprecated
    static boolean containsClientSidedBlock(Player p, Location loc) {
        AwarenessNotifications.forcefullySend("The API method 'containsClientSidedBlock' has been removed.");
        return false;
    }

    @Deprecated
    static Material getClientSidedBlockMaterial(Player p, Location loc) {
        AwarenessNotifications.forcefullySend("The API method 'getClientSidedBlockMaterial' has been removed.");
        return null;
    }

    @Deprecated
    static byte getClientSidedBlockData(Player p, Location loc) {
        AwarenessNotifications.forcefullySend("The API method 'getClientSidedBlockData' has been removed.");
        return (byte) 0;
    }

    static String getConfiguredCheckName(HackType hackType) {
        return hackType.getCheck().getName();
    }

    static void setConfiguredCheckName(HackType hackType, String name) {
        if (Config.settings.getBoolean("Important.enable_developer_api")) {
            hackType.getCheck().setName(name);
        }
    }

    @Deprecated
    static void disableVelocityProtection(Player p, int ticks) {
        AwarenessNotifications.forcefullySend("The API method 'disableVelocityProtection' has been removed.");
    }

    @Deprecated
    static void setOnGround(Player p, int ticks) {
        AwarenessNotifications.forcefullySend("The API method 'setOnGround' has been removed.");
    }

    @Deprecated
    static int getMaxPunishmentViolation(HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'getMaxPunishmentViolation' has been removed.");
        return 0;
    }

    @Deprecated
    static int getMinPunishmentViolation(HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'getMinPunishmentViolation' has been removed.");
        return 0;
    }

    @Deprecated
    static boolean mayPunishPlayer(Player p, HackType hackType) {
        AwarenessNotifications.forcefullySend("The API method 'mayPunishPlayer' has been removed.");
        return false;
    }
}
