/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.16.5, mod version: 1.1.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of The Vanilla Experience ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.thevanillaexperience.mods.zombieproofdoors.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ZombieProofDoorsZombieJoinEvent {
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent e) {
		World world = e.getWorld();
		if (world.isRemote) {
			return;
		}
		
		Entity entity = e.getEntity();
		if (entity instanceof ZombieEntity == false) {
			return;
		}
		
		ZombieEntity zombie = (ZombieEntity)entity;
		zombie.setBreakDoorsAItask(false);
	}
}