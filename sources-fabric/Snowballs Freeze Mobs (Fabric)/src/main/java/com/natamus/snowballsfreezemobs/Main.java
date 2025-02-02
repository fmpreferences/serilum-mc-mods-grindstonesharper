/*
 * This is the latest source code of Snowballs Freeze Mobs.
 * Minecraft version: 1.19.2, mod version: 2.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.snowballsfreezemobs;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.fabric.callbacks.CollectiveEntityEvents;
import com.natamus.snowballsfreezemobs.config.ConfigHandler;
import com.natamus.snowballsfreezemobs.events.SnowEvent;
import com.natamus.snowballsfreezemobs.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CollectiveEntityEvents.ON_LIVING_DAMAGE_CALC.register((Level world, Entity entity, DamageSource damageSource, float damageAmount) -> {
			return SnowEvent.onEntityHurt(world, entity, damageSource, damageAmount);
		});
		
		CollectiveEntityEvents.LIVING_TICK.register((Level world, Entity entity) -> {
			SnowEvent.onLivingUpdate(world, entity);
		});
	}
}
