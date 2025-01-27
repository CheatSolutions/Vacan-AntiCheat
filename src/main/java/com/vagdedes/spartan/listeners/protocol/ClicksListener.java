package com.vagdedes.spartan.listeners.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.vagdedes.spartan.Register;
import com.vagdedes.spartan.abstraction.event.PlayerLeftClickEvent;
import com.vagdedes.spartan.abstraction.protocol.PlayerProtocol;
import com.vagdedes.spartan.functionality.server.MultiVersion;
import com.vagdedes.spartan.functionality.server.PluginBase;
import org.bukkit.entity.Player;

public class ClicksListener extends PacketAdapter {

    public ClicksListener() {
        super(Register.plugin, ListenerPriority.NORMAL,
                PacketType.Play.Client.ARM_ANIMATION,
                PacketType.Play.Client.BLOCK_DIG,
                (MultiVersion.isOrGreater(MultiVersion.MCVersion.V1_17)
                        ? PacketType.Play.Client.GROUND
                        : PacketType.Play.Client.FLYING)

        );
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        PlayerProtocol protocol = PluginBase.getProtocol(player);

        synchronized (protocol) {
            if (event.getPacket().getType().equals(PacketType.Play.Client.ARM_ANIMATION)) {
                long delay = System.currentTimeMillis() - protocol.oldClickTime;

                if (delay > 150) {
                    protocol.clickBlocker = false;
                }
                if (!protocol.clickBlocker) {
                    protocol.profile().executeRunners(
                            false,
                            new PlayerLeftClickEvent(
                                    player,
                                    delay
                            )
                    );
                }
                protocol.oldClickTime = System.currentTimeMillis();
            } else if (event.getPacket().getType().equals(PacketType.Play.Client.BLOCK_DIG)) {
                String s = event.getPacket().getStructures().getValues().toString();
                protocol.oldClickTime = System.currentTimeMillis();
                protocol.clickBlocker = !s.contains("ABORT");
            } else if (event.getPacket().getType().equals(PacketType.Play.Client.FLYING)
                    || event.getPacket().getType().equals(PacketType.Play.Client.GROUND)) {
                // stub
            }
        }
    }

}
