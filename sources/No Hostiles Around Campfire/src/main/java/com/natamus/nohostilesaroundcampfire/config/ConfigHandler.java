/*
 * This is the latest source code of No Hostiles Around Campfire.
 * Minecraft version: 1.19.1, mod version: 4.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.nohostilesaroundcampfire.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> preventHostilesRadius;
		
		public final ForgeConfigSpec.ConfigValue<Boolean> burnHostilesAroundWhenPlaced;
		public final ForgeConfigSpec.ConfigValue<Double> burnHostilesRadiusModifier;
		
		public final ForgeConfigSpec.ConfigValue<Boolean> preventMobSpawnerSpawns;
		public final ForgeConfigSpec.ConfigValue<Boolean> campfireMustBeLit;
		public final ForgeConfigSpec.ConfigValue<Boolean> campfireMustBeSignalling;
		
		public final ForgeConfigSpec.ConfigValue<Boolean> enableEffectForNormalCampfires;
		public final ForgeConfigSpec.ConfigValue<Boolean> enableEffectForSoulCampfires;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			preventHostilesRadius = builder
					.comment("The radius around the campfire in blocks where hostile mob spawns will be blocked.")
					.defineInRange("preventHostilesRadius", 48, 1, 128);
			
			burnHostilesAroundWhenPlaced = builder
					.comment("If enabled, burns all hostile mobs around the campfire within the radius whenever a player places a campfire.")
					.define("burnHostilesAroundWhenPlaced", true);
			burnHostilesRadiusModifier = builder
					.comment("By default set to 0.5. This means that if the radius is 16, the campfire burns prior mobs in a radius of 8.")
					.defineInRange("burnHostilesRadiusModifier", 0.5, 0, 1.0);
			
			preventMobSpawnerSpawns = builder
					.comment("When enabled, the mob spawners spawns are also disabled when a campfire is within the radius.")
					.define("preventMobSpawnerSpawns", false);
			campfireMustBeLit = builder
					.comment("When enabled, the campfire only has an effect when the block is lit up.")
					.define("campfireMustBeLit", true);
			campfireMustBeSignalling = builder
					.comment("When enabled, the campfire only has an effect when the block is signalling.")
					.define("campfireMustBeSignalling", false);
			
			enableEffectForNormalCampfires = builder
					.comment("When enabled, the mod will work with normal campfires.")
					.define("enableEffectForNormalCampfires", true);
			enableEffectForSoulCampfires = builder
					.comment("When enabled, the mod will work with soul campfires.")
					.define("enableEffectForSoulCampfires", true);
			
			builder.pop();
		}
	}
}