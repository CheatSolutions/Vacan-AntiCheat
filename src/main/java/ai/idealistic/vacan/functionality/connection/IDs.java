package ai.idealistic.vacan.functionality.connection;

import ai.idealistic.vacan.api.API;
import ai.idealistic.vacan.utils.math.AlgebraUtils;
import lombok.Setter;

import java.util.Objects;

public class IDs {

    static String token = null;
    public static final String resource = "%%__RESOURCE__%%";
    public static final boolean enabled = AlgebraUtils.validInteger(resource);
    private static String
            user = "%%__USER__%%",
            file = "%%__NONCE__%%";

    public static final boolean
            hasUserIDByDefault = !user.startsWith("%%__");

    @Setter
    private static int platform = 0;

    // Setters

    static void set(int user, int nonce) {
        IDs.user = Integer.toString(user);
        IDs.file = Integer.toString(nonce);
    }

    // IDs

    public static String user() {
        return user;
    }

    public static String file() {
        if (IDs.enabled) {
            if (!file.startsWith("%%__") && !AlgebraUtils.validInteger(file)) {
                file = String.valueOf(Objects.hash(file));
            }
            return file;
        } else {
            return hasToken() ? Integer.toString(token.hashCode()) : user;
        }
    }

    static String platform() {
        return IDs.isBuiltByBit() ? "BuiltByBit" : IDs.isPolymart() ? "Polymart" : "SpigotMC";
    }

    // Platforms

    public static boolean canAdvertise() {
        return !IDs.enabled || IDs.isBuiltByBit() || IDs.isPolymart();
    }

    public static boolean isBuiltByBit() {
        return platform == 2 || "%%__FILEHASH__%%".length() != 16;
    }

    public static boolean isPolymart() {
        return platform == 3 || "%%__POLYMART__%%".length() == 1;
    }

    public static String hide(String id) {
        try {
            double version = Double.parseDouble(API.getVersion().substring(6)),
                    number = AlgebraUtils.cut(Integer.parseInt(id) / version, 6);
            return String.valueOf(number).replace("-", "*").replace(".", "-");
        } catch (Exception ex) {
            return "0";
        }
    }

    // Token

    public static boolean hasToken() {
        return token != null && !IDs.hasUserIDByDefault;
    }

    public static String getToken() {
        return IDs.hasUserIDByDefault ? null : token;
    }

}
