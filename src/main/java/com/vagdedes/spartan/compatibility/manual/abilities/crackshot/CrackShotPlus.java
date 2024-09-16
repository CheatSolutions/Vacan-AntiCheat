package com.vagdedes.spartan.compatibility.manual.abilities.crackshot;

import com.vagdedes.spartan.abstraction.configuration.implementation.Compatibility;
import com.vagdedes.spartan.abstraction.data.Buffer;
import com.vagdedes.spartan.abstraction.player.SpartanPlayer;
import com.vagdedes.spartan.functionality.server.Config;
import com.vagdedes.spartan.functionality.server.SpartanBukkit;
import com.vagdedes.spartan.utils.java.OverflowMap;
import me.DeeCaaD.CrackShotPlus.Events.WeaponSecondScopeEvent;
import me.vagdedes.spartan.system.Enums;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.LinkedHashMap;

public class CrackShotPlus implements Listener {

    private static final Buffer buffers = new Buffer(
            new OverflowMap<>(new LinkedHashMap<>(), 512)
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    private void WeaponScope(WeaponSecondScopeEvent e) {
        if (Compatibility.CompatibilityType.CRACK_SHOT_PLUS.isFunctional()) {
            SpartanPlayer p = SpartanBukkit.getProtocol(e.getPlayer()).spartanPlayer;

            if (!e.isCancelled()) {
                Config.compatibility.evadeFalsePositives(
                        p,
                        Compatibility.CompatibilityType.CRACK_SHOT_PLUS,
                        new Enums.HackCategoryType[]{
                                Enums.HackCategoryType.MOVEMENT,
                                Enums.HackCategoryType.COMBAT
                        },
                        20
                );

                if (e.isZoomIn()) {
                    buffers.set(p.protocol.getUUID() + "=crackshotplus=compatibility=scope", 1);
                } else {
                    buffers.remove(p.protocol.getUUID() + "=crackshotplus=compatibility=scope");
                }
            } else {
                buffers.remove(p.protocol.getUUID() + "=crackshotplus=compatibility=scope");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void EntityDamage(EntityDamageEvent e) {
        if (Compatibility.CompatibilityType.CRACK_SHOT_PLUS.isFunctional()) {
            Entity entity = e.getEntity();

            if (entity instanceof Player) {
                SpartanPlayer p = SpartanBukkit.getProtocol((Player) entity).spartanPlayer;

                if (isUsingScope(p)) {
                    Config.compatibility.evadeFalsePositives(
                            p,
                            Compatibility.CompatibilityType.CRACK_SHOT_PLUS,
                            new Enums.HackCategoryType[]{
                                    Enums.HackCategoryType.MOVEMENT,
                                    Enums.HackCategoryType.COMBAT
                            },
                            60
                    );
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void EntityByEntityDamage(EntityDamageByEntityEvent e) {
        if (Compatibility.CompatibilityType.CRACK_SHOT_PLUS.isFunctional()) {
            Entity entity = e.getDamager();

            if (entity instanceof Player) {
                SpartanPlayer p = SpartanBukkit.getProtocol((Player) entity).spartanPlayer;

                if (isUsingScope(p)) {
                    Config.compatibility.evadeFalsePositives(
                            p,
                            Compatibility.CompatibilityType.CRACK_SHOT_PLUS,
                            new Enums.HackCategoryType[]{
                                    Enums.HackCategoryType.MOVEMENT,
                                    Enums.HackCategoryType.COMBAT
                            },
                            30
                    );
                }
            }
        }
    }

    static boolean isUsingScope(SpartanPlayer p) {
        return Compatibility.CompatibilityType.CRACK_SHOT_PLUS.isFunctional()
                && buffers.get(p.protocol.getUUID() + "=crackshotplus=compatibility=scope") != 0;
    }
}

