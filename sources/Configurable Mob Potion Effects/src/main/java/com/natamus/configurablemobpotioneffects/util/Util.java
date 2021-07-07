/*
 * This is the latest source code of Configurable Mob Potion Effects.
 * Minecraft version: 1.16.5, mod version: 1.3.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Configurable Mob Potion Effects ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.configurablemobpotioneffects.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.natamus.collective.functions.NumberFunctions;
import com.natamus.collective.functions.StringFunctions;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {
	private static String dirpath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "configurablemobpotioneffects";
	private static File dir = new File(dirpath);
	private static File permanentfile = new File(dirpath + File.separator + "permanenteffects.txt");
	private static File damagefile = new File(dirpath + File.separator + "ondamageeffects.txt");
	
	public static HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>> mobpermanent = new HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>>();
	public static HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>> mobdamage = new HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>>();
	
	public static void loadMobConfigFile() throws IOException, FileNotFoundException, UnsupportedEncodingException {
		mobpermanent = new HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>>();
		mobdamage = new HashMap<EntityType<?>, CopyOnWriteArrayList<EffectInstance>>();
		
		PrintWriter permanentwriter = null;
		PrintWriter damagewriter = null;
		if (!dir.isDirectory() || !permanentfile.isFile() || !damagefile.isFile()) {
			dir.mkdirs();
			
			if (!permanentfile.isFile()) {
				permanentwriter = new PrintWriter(dirpath + File.separator + "permanenteffects.txt", "UTF-8");
			}
			if (!damagefile.isFile()) {
				damagewriter = new PrintWriter(dirpath + File.separator + "ondamageeffects.txt", "UTF-8");
			}
		}
		else {
			String permanentcontent = new String(Files.readAllBytes(Paths.get(dirpath + File.separator + "permanenteffects.txt", new String[0])));
			for (String line : permanentcontent.split("\n")) {
				if (line.trim().endsWith(",")) {
					line = line.trim();
					line = line.substring(0, line.length() - 1).trim();
				}
				
				if (line.length() < 5) {
					continue;
				}
				
				if (!line.contains("' : '")) {
					continue;
				}
				
				String[] linespl = line.split("' : '");
				if (linespl.length < 2) {
					continue;
				}
				
				String entityrl = linespl[0].substring(1).trim();
				String potioneffects = linespl[1].trim();
				potioneffects = potioneffects.substring(0, potioneffects.length() - 1).trim();
				
				EntityType<?> entitytype = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityrl));
				if (entitytype == null) {
					continue;
				}
				
				mobpermanent.put(entitytype, parseEffectString(potioneffects));
			}
			
			String damagecontent = new String(Files.readAllBytes(Paths.get(dirpath + File.separator + "ondamageeffects.txt", new String[0])));
			for (String line : damagecontent.split("\n")) {
				if (line.trim().endsWith(",")) {
					line = line.trim();
					line = line.substring(0, line.length() - 1).trim();
				}
				
				if (line.length() < 5) {
					continue;
				}
				
				if (!line.contains("' : '")) {
					continue;
				}
				
				String[] linespl = line.split("' : '");
				if (linespl.length < 2) {
					continue;
				}
				
				String entityrl = linespl[0].substring(1).trim();
				String potioneffects = linespl[1].trim();
				potioneffects = potioneffects.substring(0, potioneffects.length() - 1).trim();

				EntityType<?> entitytype = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityrl));
				if (entitytype == null) {
					continue;
				}
				
				mobdamage.put(entitytype, parseEffectString(potioneffects));
			}
		}
		
		List<String> sortedpotions = new ArrayList<String>();
		List<String> sortedentities = new ArrayList<String>();
		HashMap<String, Effect> phm = new HashMap<String, Effect>();
		HashMap<String, EntityType<?>> ehm = new HashMap<String, EntityType<?>>();
		
		String emptypermanenteffects = "";
		String emptydamageeffects = "";
		
		if (permanentwriter != null || damagewriter != null) {
			for (Effect effect : ForgeRegistries.POTIONS) {
				String n = effect.getRegistryName().toString().toLowerCase();
				if (n.contains(":")) {
					n = n.split(":")[1];
				}
				
				sortedpotions.add(n);
				phm.put(n, effect);
			}
			for (EntityType<?> entitytype : ForgeRegistries.ENTITIES) {
				String n = entitytype.getRegistryName().toString().toLowerCase();
				if (n.contains(":")) {
					n = n.split(":")[1];
				}
				
				sortedentities.add(n);
				ehm.put(n, entitytype);
			}
			
			Collections.sort(sortedpotions);
			Collections.sort(sortedentities);
			
			for (String effectstring : sortedpotions) {
				Effect effect = phm.get(effectstring);
				
				if (emptypermanenteffects != "") {
					emptypermanenteffects += "|";
				}
				if (emptydamageeffects != "") {
					emptydamageeffects += "|";
				}
				
				ResourceLocation rl = effect.getRegistryName();
				
				emptypermanenteffects += rl.toString() + ",lvl:0";
				emptydamageeffects += rl.toString() + ",lvl:0,duration:5";
			}
		}
		
		boolean rerun = false;
		if (permanentwriter != null) {
			for (String entitytypestring : sortedentities) {
				EntityType<?> entitytype = ehm.get(entitytypestring);
				
				EntityClassification classification = entitytype.getCategory();
				if (!classification.equals(EntityClassification.MISC)) {
					ResourceLocation rl = entitytype.getRegistryName();
					permanentwriter.println("'" + rl.toString() + "'" + " : '" + emptypermanenteffects + "'," + "\n");
					
					mobpermanent.put(entitytype, new CopyOnWriteArrayList<EffectInstance>());
				}
			}
			
			permanentwriter.close();
			rerun = true;
		}
		
		if (damagewriter != null) {
			for (String entitytypestring : sortedentities) {
				EntityType<?> entitytype = ehm.get(entitytypestring);
				
				EntityClassification classification = entitytype.getCategory();
				if (!classification.equals(EntityClassification.MISC)) {
					ResourceLocation rl = entitytype.getRegistryName();
					damagewriter.println("'" + rl.toString() + "'" + " : '" + emptydamageeffects + "'," + "\n");
					
					mobdamage.put(entitytype, new CopyOnWriteArrayList<EffectInstance>());
				}
			}
			
			damagewriter.close();
			rerun = true;
		}

		if (rerun) {
			loadMobConfigFile();
		}
	}
	
	private static CopyOnWriteArrayList<EffectInstance> parseEffectString(String effectstring) {
		CopyOnWriteArrayList<EffectInstance> effectinstances = new CopyOnWriteArrayList<EffectInstance>();
		
		for (String effectpair : effectstring.split(StringFunctions.escapeSpecialRegexChars("|"))) {
			String[] epspl = effectpair.split(",");
			if (epspl.length < 2) {
				continue;
			}
			
			String effectrlstring = "";
			String lvlstring = "";
			String durationstring = "";
			for (String ep : epspl) {
				if (ep.contains("lvl:")) {
					lvlstring = ep.split(":")[1].trim();
				}
				else if (ep.contains("duration:")) {
					durationstring = ep.split(":")[1].trim();
				}
				else {
					effectrlstring = ep.trim();
				}
			}
			
			if (effectrlstring == "" || lvlstring == "") {
				continue;
			}
			
			if (durationstring == "") {
				durationstring = "0";
			}
			
			Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectrlstring));
			if (effect == null) {
				continue;
			}
			
			if (!NumberFunctions.isNumeric(lvlstring) || !NumberFunctions.isNumeric(durationstring)) {
				continue;
			}
			
			int level;
			int duration;
			try {
				level = Integer.parseInt(lvlstring);
				duration = Integer.parseInt(durationstring);
			}
			catch (NumberFormatException ex) {
				continue;
			}
			
			if (level == 0) {
				continue;
			}
			
			if (duration == 0) {
				duration = 100000000;
			}
			
			EffectInstance instance = new EffectInstance(effect, duration*20, level-1, true, true);
			effectinstances.add(instance);
		}
		
		return effectinstances;
	}
}
