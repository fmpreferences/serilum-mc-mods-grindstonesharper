/*
 * This is the latest source code of Name Tag Tweaks.
 * Minecraft version: 1.17.x, mod version: 1.7.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Name Tag Tweaks ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.nametagtweaks.events;

import com.natamus.nametagtweaks.config.ConfigHandler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NameTagEvent {
	public static void mobItemDrop(Level world, Entity entity, DamageSource damageSource) {
		if (world.isClientSide) {
			return;
		}
		
		if (entity.hasCustomName()) {
			if (entity instanceof Slime) {
				Slime slime = (Slime)entity;
				int slimesize = slime.getSize();
				if (slimesize != 4) {
					return;
				}
			}
			
			ItemStack nametagstack = new ItemStack(Items.NAME_TAG, 1);
			if (ConfigHandler.droppedNameTagbyEntityKeepsNameValue.getValue()) {
				Component name = entity.getName();
				nametagstack.setHoverName(name);
			}
			nametagstack.setRepairCost(0);
			
			Vec3 evec = entity.position();
			ItemEntity ie = new ItemEntity(world, evec.x(), evec.y()+1, evec.z(), nametagstack);
			world.addFreshEntity(ie);
		}
	}
}