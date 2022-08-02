/*
 * This is the latest source code of Surface Mushrooms.
 * Minecraft version: 1.19.1, mod version: 1.8.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.surfacemushrooms;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.surfacemushrooms.events.MushroomBlockEvent;
import com.natamus.surfacemushrooms.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CollectiveBlockEvents.BLOCK_RIGHT_CLICK.register((Level world, Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) -> {
			return MushroomBlockEvent.onMushroomPlace(world, player, hand, pos, hitVec);
		});
	}
}
