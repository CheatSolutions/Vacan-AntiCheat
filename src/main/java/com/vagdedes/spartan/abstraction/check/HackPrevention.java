package com.vagdedes.spartan.abstraction.check;

import com.vagdedes.spartan.abstraction.player.SpartanPlayer;
import com.vagdedes.spartan.abstraction.world.SpartanLocation;
import com.vagdedes.spartan.functionality.server.TPS;
import me.vagdedes.spartan.system.Enums;

public class HackPrevention {

    boolean canPrevent;
    private final SpartanLocation location;
    private final boolean groundTeleport;
    private final double damage;
    private final long expiration;

    // Separator

    HackPrevention() {
        this.canPrevent = false;
        this.location = null;
        this.groundTeleport = false;
        this.damage = 0.0;
        this.expiration = 0L;
    }

    HackPrevention(SpartanPlayer player, Enums.HackType hackType,
                   String information, double violations,
                   SpartanLocation location, int cancelTicks, boolean groundTeleport,
                   double damage) {
        long time = System.currentTimeMillis();
        this.canPrevent = false;
        this.location = location;
        this.groundTeleport = groundTeleport;
        this.damage = damage;
        this.expiration = TPS.getTick(player) + cancelTicks;
        player.getViolations(hackType).run(this, information, violations, time);
    }

    boolean hasExpired(long tick) {
        return tick > this.expiration;
    }

    void handle(SpartanPlayer player) {
        if (this.location != null) {
            player.teleport(this.location);
        }
        if (this.groundTeleport) {
            player.groundTeleport();
        }
        if (this.damage > 0.0) {
            player.damage(this.damage);
        }
    }

}
