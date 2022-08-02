/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.thevanillaexperience.mods.randomshulkercolours.events;

import java.util.List;
import java.util.Set;

import com.natamus.collective.data.GlobalVariables;
import com.natamus.thevanillaexperience.mods.randomshulkercolours.util.Reference;
import com.natamus.thevanillaexperience.mods.randomshulkercolours.util.RandomShulkerColoursUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.DataItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RandomShulkerColoursShulkerEvent {
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onShulkerSpawn(EntityJoinWorldEvent e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		Entity entity = e.getEntity();
		if (entity instanceof Shulker == false) {
			return;
		}
		
		String shulkertag = Reference.MOD_ID + ".coloured";
		Set<String> tags = entity.getTags();
		if (tags.contains(shulkertag)) {
			return;
		}
		
		if (RandomShulkerColoursUtil.possibleColours == null) {
			return;
		}
		if (RandomShulkerColoursUtil.possibleColours.size() < 1) {
			return;
		}
		
		Shulker shulker = (Shulker)entity;
		
		int randomindex = GlobalVariables.random.nextInt(RandomShulkerColoursUtil.possibleColours.size());
		final DyeColor randomcolour = RandomShulkerColoursUtil.possibleColours.get(randomindex);
		if (randomcolour != null) {
			int dyeid = randomcolour.getId();

			SynchedEntityData datamanager = shulker.getEntityData();
			
			EntityDataAccessor<Byte> paramater = null;
			
			List<DataItem<?>> dataentries = datamanager.packDirty();
			for (DataItem<?> entry : dataentries) {
				EntityDataAccessor<?> key = entry.getAccessor();
				Object value = entry.getValue();
				if (value.toString().equals("16")) {
					paramater = (EntityDataAccessor<Byte>)key;
				}
			}
			
			if (paramater != null) {
				datamanager.set(paramater, (byte)dyeid);
			}
		}
		
		shulker.addTag(shulkertag);
	}
}
