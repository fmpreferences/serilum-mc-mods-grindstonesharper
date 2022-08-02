/*
 * This is the latest source code of Trample Everything.
 * Minecraft version: 1.19.1, mod version: 1.8.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.trampleeverything;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.trampleeverything.config.ConfigHandler;
import com.natamus.trampleeverything.events.TrampleEvent;
import com.natamus.trampleeverything.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CollectivePlayerEvents.PLAYER_TICK.register((ServerLevel world, ServerPlayer player) -> {
			TrampleEvent.onPlayerTick(world, player);
		});
	}
}
