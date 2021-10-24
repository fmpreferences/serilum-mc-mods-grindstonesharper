/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.17.x, mod version: 1.48.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Collective ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.collective_fabric.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.natamus.collective_fabric.check.RegisterMod;
import com.natamus.collective_fabric.config.CollectiveConfigHandler;
import com.natamus.collective_fabric.data.GlobalVariables;
import com.natamus.collective_fabric.functions.BlockPosFunctions;
import com.natamus.collective_fabric.functions.EntityFunctions;
import com.natamus.collective_fabric.functions.SpawnEntityFunctions;
import com.natamus.collective_fabric.objects.SAMObject;
import com.natamus.collective_fabric.util.Reference;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CollectiveEvents {
	public static HashMap<ServerLevel, List<Entity>> entitiesToSpawn = new HashMap<ServerLevel, List<Entity>>();
	public static HashMap<ServerLevel, HashMap<Entity, Entity>> entitiesToRide = new HashMap<ServerLevel, HashMap<Entity, Entity>>();
	
	public static void onWorldLoad(ServerLevel serverworld) {
		entitiesToSpawn.put(serverworld, new ArrayList<Entity>());
		entitiesToRide.put(serverworld, new HashMap<Entity, Entity>());
	}
	
	public static void onWorldTick(ServerLevel world) {	
		ServerLevel serverworld = (ServerLevel)world;
		if (entitiesToSpawn.get(serverworld).size() > 0) {
			Entity tospawn = entitiesToSpawn.get(serverworld).get(0);
			
			serverworld.addFreshEntityWithPassengers(tospawn);
			
			if (entitiesToRide.get(world).containsKey(tospawn)) {
				Entity rider = entitiesToRide.get(world).get(tospawn);
				
				rider.startRiding(tospawn);
				
				entitiesToRide.get(world).remove(tospawn);
			}
			
			entitiesToSpawn.get(world).remove(0);
		}
	}
	
	public static void onEntityJoinLevel(Level world, Entity entity) {
		if (entity instanceof LivingEntity == false) {
			return;
		}
		
		if (RegisterMod.shouldDoCheck) {
			if (entity instanceof Player) {
				RegisterMod.joinWorldProcess(world, (Player)entity);
			}
		}
		
		if (entity.isRemoved()) {
			return;
		}
		
		if (GlobalVariables.samobjects.isEmpty()) {
			return;
		}
		
		Set<String> tags = entity.getTags();
		if (tags.contains(Reference.MOD_ID + ".checked")) {
			return;
		}
		entity.addTag(Reference.MOD_ID + ".checked");
		
		EntityType<?> entitytype = entity.getType();
		if (entitytype == null || !GlobalVariables.activesams.contains(entitytype)) {
			return;	
		}
		
		boolean isspawner = tags.contains(Reference.MOD_ID + ".fromspawner");
		
		List<SAMObject> possibles = new ArrayList<SAMObject>();
		Iterator<SAMObject> iterator = GlobalVariables.samobjects.iterator();
		while (iterator.hasNext()) {
			SAMObject samobject = iterator.next();
			if (samobject == null) {
				continue;
			}
			
			if (samobject.fromtype == null) {
				continue;
			}
			if (samobject.fromtype.equals(entitytype)) {
				if ((samobject.spawner && !isspawner) || (!samobject.spawner && isspawner)) {
					continue;
				}
				possibles.add(samobject);
			}
		}
		
		int size = possibles.size();
		if (size == 0) {
			return;
		}
		
		for (SAMObject sam : possibles) {
			double num = GlobalVariables.random.nextDouble();
			if (num > sam.chance) {
				continue;
			}
			
			Vec3 evec = entity.position();
			if (sam.surface) {
				if (!BlockPosFunctions.isOnSurface(world, evec)) {
					continue;
				}
			}
			
			Entity to = sam.totype.create(world);
			//to.setWorld(Level);
			to.setPos(evec.x, evec.y, evec.z);
			
			boolean ignoremainhand = false;
			if (sam.helditem != null) {
				if (to instanceof LivingEntity) {
					LivingEntity le = (LivingEntity)to;
					if (!le.getMainHandItem().getItem().equals(sam.helditem)) {
						le.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(sam.helditem, 1));
						ignoremainhand = true;
					}
				}
			}
			
			boolean ride = false;
			if (EntityFunctions.isHorse(to) && sam.rideable) {
				AbstractHorse ah = (AbstractHorse)to;
				ah.setTamed(true);
				to = ah;
				
				ride = true;
			}
			else {
				if (CollectiveConfigHandler.transferItemsBetweenReplacedEntities.getValue()) {
					EntityFunctions.transferItemsBetweenEntities(entity, to, ignoremainhand);
				}
			}
			
			if (world instanceof ServerLevel == false) {
				return;
			}
			
			ServerLevel serverworld = (ServerLevel)world;
			
			if (ride) {
				SpawnEntityFunctions.startRidingEntityOnNextTick(serverworld, to, entity); //entity.startRiding(to);
			}
			else {
				entity.remove(RemovalReason.DISCARDED);
			}
			
			to.addTag(Reference.MOD_ID + ".checked");
			SpawnEntityFunctions.spawnEntityOnNextTick(serverworld, to); //serverworld.addFreshEntityWithPassengers(to);

			break;
		}
	}
}