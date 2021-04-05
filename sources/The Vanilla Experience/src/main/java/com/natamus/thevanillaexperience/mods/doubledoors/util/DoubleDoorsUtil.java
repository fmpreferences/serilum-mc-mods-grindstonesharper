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

package com.natamus.thevanillaexperience.mods.doubledoors.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.state.properties.BlockStateProperties;

public class DoubleDoorsUtil {
	public static boolean isDoorBlock(BlockState blockstate) {
		Block block = blockstate.getBlock();
		if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceGateBlock) {
		//if (blockstate.has(BlockStateProperties.OPEN)) {
			return true;
		}
		return false;
	}
	
	public static boolean isPressureBlock(BlockState blockstate) {
		Block block = blockstate.getBlock();
		if (block instanceof PressurePlateBlock || block instanceof WoodButtonBlock || block instanceof StoneButtonBlock) {
			if (blockstate.get(BlockStateProperties.POWERED)) {
				return true;
			}
		}
		return false;
	}
}