/*
 * This is the latest source code of Passive Endermen.
 * Minecraft version: 1.19.2, mod version: 3.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.passiveendermen.fabric.mixin;

import com.natamus.collective_fabric.functions.ConfigFunctions;
import com.natamus.passiveendermen.util.Reference;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EnderMan.class, priority = 1001)
public class EnderManMixin {
    private final boolean preventEndermenFromTeleporting = ConfigFunctions.getDictValues(Reference.MOD_ID).get("preventEndermenFromTeleporting").equals("true");

    @Inject(method = "teleport(DDD)Z", at = @At(value = "HEAD"), cancellable = true)
    private void teleport(double p_32544_, double p_32545_, double p_32546_, CallbackInfoReturnable<Boolean> cir) {
        if (preventEndermenFromTeleporting) {
            cir.setReturnValue(false);
        }
    }
}
