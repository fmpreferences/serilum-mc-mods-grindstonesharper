/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.17.x, mod version: 1.48.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Collective ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.collective_fabric.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.natamus.collective_fabric.fabric.callbacks.CollectiveCropEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	@Inject(method = "useOn", at = @At(value = "HEAD"), cancellable = true)
	public void BoneMealItem_useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> ci) {
		Level level = useOnContext.getLevel();
		BlockPos blockPos = useOnContext.getClickedPos();
		
		if (!CollectiveCropEvents.ON_BONE_MEAL_APPLY.invoker().onBoneMealApply(useOnContext.getPlayer(), level, blockPos, level.getBlockState(blockPos), useOnContext.getItemInHand())) {
			ci.setReturnValue(InteractionResult.PASS);
		}
	}
}