/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.2.
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

package com.natamus.thevanillaexperience.mods.areas.objects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AreaObject {
	public World world;
	public BlockPos location;
	public String areaname;
	public int radius;
	public String customrgb;
	
	public List<PlayerEntity> containsplayers;
	
	public AreaObject(World w, BlockPos l, String a, int r, String rgb) {
		world = w;
		location = l;
		areaname = a;
		radius = r;
		customrgb = rgb;
		containsplayers = new ArrayList<PlayerEntity>();
		
		if (AreasVariables.areasperworld.containsKey(world)) {
			AreasVariables.areasperworld.get(world).put(l, this);
		}
	}
}
