/*
 * This is the latest source code of Entity Information.
 * Minecraft version: 1.19.1, mod version: 2.0.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.entityinformation;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.fabric.callbacks.CollectiveEntityEvents;
import com.natamus.entityinformation.cmds.CommandIst;
import com.natamus.entityinformation.events.EntityEvent;
import com.natamus.entityinformation.util.Reference;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		registerEvents();
		
		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}
	
	private void registerEvents() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CommandIst.register(dispatcher);
		});
		
		CollectiveEntityEvents.ON_LIVING_ATTACK.register((Level world, Entity entity, DamageSource damageSource, float damageAmount) -> {
			return EntityEvent.onEntityDamage(world, entity, damageSource, damageAmount);
		});
	}
}
