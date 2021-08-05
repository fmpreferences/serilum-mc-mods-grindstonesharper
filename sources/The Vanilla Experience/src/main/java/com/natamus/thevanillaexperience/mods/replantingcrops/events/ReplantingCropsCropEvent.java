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

package com.natamus.thevanillaexperience.mods.replantingcrops.events;

import java.util.HashMap;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.thevanillaexperience.mods.replantingcrops.config.ReplantingCropsConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ReplantingCropsCropEvent {
	private static HashMap<BlockPos, Item> checkreplant = new HashMap<BlockPos, Item>();
	private static HashMap<BlockPos, BlockState> cocoaStates = new HashMap<BlockPos, BlockState>();
	
	@SubscribeEvent
	public void onHarvest(BlockEvent.BreakEvent e) {
		World world = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (world == null) {
			return;
		}
		
		PlayerEntity player = e.getPlayer();
		if (player == null) {
			return;
		}
		
		if (ReplantingCropsConfigHandler.GENERAL.mustHoldHoeForReplanting.get()) {
			if (player.getMainHandItem().getItem() instanceof HoeItem == false) {
				if (player.getOffhandItem().getItem() instanceof HoeItem == false) {
					return;
				}
			}
		}
		
		if (player.isShiftKeyDown()) {
			return;
		}
		
		BlockPos hpos = e.getPos().immutable();
		BlockState state = world.getBlockState(hpos);
		Block block = state.getBlock();
		
		if (block.equals(Blocks.WHEAT)) {
			checkreplant.put(hpos, Items.WHEAT_SEEDS);
		}
		else if (block.equals(Blocks.CARROTS)) {
			checkreplant.put(hpos, Items.CARROT);
		}
		else if (block.equals(Blocks.POTATOES)) {
			checkreplant.put(hpos, Items.POTATO);
		}
		else if (block.equals(Blocks.BEETROOTS)) {
			checkreplant.put(hpos, Items.BEETROOT_SEEDS);
		}
		else if (block.equals(Blocks.NETHER_WART)) {
			checkreplant.put(hpos, Items.NETHER_WART);
		}
		else if (block.equals(Blocks.COCOA)) {
			cocoaStates.put(hpos, state);
			checkreplant.put(hpos, Items.COCOA_BEANS);
		}
	}
	
	@SubscribeEvent
	public void onHarvest(EntityJoinWorldEvent e) {
		World world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		Entity entity = e.getEntity();
		if (entity instanceof ItemEntity == false) {
			return;
		}
		
		BlockPos ipos = entity.blockPosition();
		if (!checkreplant.containsKey(ipos)) {
			return;
		}
		
		ItemEntity itementity = (ItemEntity)entity;
		ItemStack itemstack = itementity.getItem();
		Item item = itemstack.getItem();
		if (item.equals(Items.WHEAT_SEEDS)) {
			world.setBlockAndUpdate(ipos, Blocks.WHEAT.defaultBlockState());
		}
		else if (item.equals(Items.CARROT)) {
			world.setBlockAndUpdate(ipos, Blocks.CARROTS.defaultBlockState());
		}
		else if (item.equals(Items.POTATO)) {
			world.setBlockAndUpdate(ipos, Blocks.POTATOES.defaultBlockState());
		}
		else if (item.equals(Items.BEETROOT_SEEDS)) {
			world.setBlockAndUpdate(ipos, Blocks.BEETROOTS.defaultBlockState());
		}
		else if (item.equals(Items.NETHER_WART)) {
			world.setBlockAndUpdate(ipos, Blocks.NETHER_WART.defaultBlockState());
		}
		else if (item.equals(Items.COCOA_BEANS)) {
			if (!cocoaStates.containsKey(ipos)) {
				checkreplant.remove(ipos);
				return;
			}
			world.setBlockAndUpdate(ipos, cocoaStates.get(ipos).setValue(CocoaBlock.AGE, 0));
			cocoaStates.remove(ipos);
		}
		else {
			return;
		}
		
		checkreplant.remove(ipos);
		
		if (itemstack.getCount() > 1) {
			itemstack.shrink(1);
		}
		else {
			e.setCanceled(true);
		}
	}
}
