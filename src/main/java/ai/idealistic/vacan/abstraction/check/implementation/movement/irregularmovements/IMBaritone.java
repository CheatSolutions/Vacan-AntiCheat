package ai.idealistic.vacan.abstraction.check.implementation.movement.irregularmovements;

import ai.idealistic.vacan.abstraction.check.Check;
import ai.idealistic.vacan.abstraction.check.CheckDetection;
import ai.idealistic.vacan.abstraction.check.CheckRunner;
import ai.idealistic.vacan.utils.math.RayUtils;
import ai.idealistic.vacan.utils.minecraft.vector.CVector2F;
import org.bukkit.Location;

public class IMBaritone extends CheckDetection {

    IMBaritone(CheckRunner executor) {
        super(executor, Check.DataType.JAVA, null, "baritone", true);
    }

    private CVector2F oldV2 = new CVector2F(0, 0);
    private CVector2F oldDeltaMap = new CVector2F(0, 0);
    private float yawMoved = 0;
    private int vl = 0;

    void run() {
        this.call(() -> {
            Location l = this.protocol.getLocation();
            CVector2F v2 = new CVector2F(l.getYaw(), l.getPitch());
            float deltaYaw = (float) RayUtils.scaleVal(Math.abs(v2.x - oldV2.x), 3);
            float deltaPitch = (float) RayUtils.scaleVal(Math.abs(v2.y - oldV2.y), 3);
            CVector2F deltaMap = new CVector2F(deltaYaw, deltaPitch);

            // for future
            //float jiffDeltaYaw = (float) RayUtils.scaleVal(Math.abs(deltaYaw - oldDeltaMap.x), 3);
            //float jiffDeltaPitch = (float) RayUtils.scaleVal(Math.abs(deltaPitch - oldDeltaMap.y), 3);
            {
                if (this.protocol.getVehicle() != null) return;
                this.yawMoved += (Math.abs(l.getPitch()) > 89) ? 0 : (deltaPitch == 0.0F) ? deltaYaw : -yawMoved;
                this.yawMoved = (float) RayUtils.scaleVal(yawMoved, 2);
                if (this.yawMoved > 45 && Math.abs(yawMoved % 360.0 - 1.0) < 1e-9) {
                    this.vl += 3;
                    if (this.vl > 14)
                        punish(3, "baritone[rotation], euler-y-total: " + this.yawMoved);
                }
                //player.getInstance().sendMessage(jiffDeltaYaw + " " + jiffDeltaPitch);
            }
            if (this.vl > 0) this.vl--;
            this.oldV2 = v2;
            this.oldDeltaMap = deltaMap;
        });
    }

    void teleport() {
        this.call(() -> this.yawMoved = 0);
    }

    private void punish(int v, String s) {
        this.vl += v;
        if (this.vl > 10) {
            cancel(s);
            this.vl -= 4;
        }
    }

}
