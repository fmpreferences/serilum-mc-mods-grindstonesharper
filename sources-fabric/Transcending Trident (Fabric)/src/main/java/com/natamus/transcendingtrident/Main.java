/*
 * This is the latest source code of Transcending Trident.
 * Minecraft version: 1.19.2, mod version: 2.7.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.transcendingtrident;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.transcendingtrident.config.ConfigHandler;
import com.natamus.transcendingtrident.events.TridentEvent;
import com.natamus.transcendingtrident.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			return TridentEvent.onItem(player, world, hand);
		});
	}
}
