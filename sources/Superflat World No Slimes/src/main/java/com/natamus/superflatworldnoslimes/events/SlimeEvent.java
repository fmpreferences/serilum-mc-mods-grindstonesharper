/*
 * This is the latest source code of Superflat World No Slimes.
 * Minecraft version: 1.16.5, mod version: 1.3.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Superflat World No Slimes ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.superflatworldnoslimes.events;

import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class SlimeEvent {
	@SubscribeEvent
	public void onWorldJoin(EntityJoinWorldEvent e) {
		World world = e.getWorld();
		if (world.isRemote) {
			return;
		}
		
		ServerWorld serverworld = (ServerWorld)world;
		
		if (serverworld.getServer().getServerConfiguration().getDimensionGeneratorSettings().func_236228_i_()) {
			if (e.getEntity() instanceof SlimeEntity) {
				e.setCanceled(true);
			}
		}
	}
}