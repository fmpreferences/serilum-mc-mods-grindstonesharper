/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.thevanillaexperience.mods.starterkit.events;

import com.natamus.collective.functions.PlayerFunctions;
import com.natamus.thevanillaexperience.mods.starterkit.util.Reference;
import com.natamus.thevanillaexperience.mods.starterkit.util.StarterKitUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class StarterKitFirstSpawnEvent {
	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		Entity entity = e.getEntity();
		if (entity instanceof Player == false) {
			return;
		}
		
		Player player = (Player)entity;
		if (PlayerFunctions.isJoiningWorldForTheFirstTime(player, Reference.MOD_ID)) {
			StarterKitUtil.setStarterKit(player);
		}
	}
}
