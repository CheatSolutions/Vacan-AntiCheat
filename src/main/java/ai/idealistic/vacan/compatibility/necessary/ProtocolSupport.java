package ai.idealistic.vacan.compatibility.necessary;

import ai.idealistic.vacan.compatibility.Compatibility;
import org.bukkit.entity.Player;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;

public class ProtocolSupport {

    static boolean isBedrockPlayer(Player p) {
        if (Compatibility.CompatibilityType.PROTOCOL_SUPPORT.isFunctional()) {
            try {
                Connection c = ProtocolSupportAPI.getConnection(p);
                return c != null && c.getVersion().toString().contains("_PE_");
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
