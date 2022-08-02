/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.thevanillaexperience.mods.betterbeaconplacement.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BetterBeaconPlacementConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Boolean> breakBeaconBaseBlocks;
		public final ForgeConfigSpec.ConfigValue<Boolean> dropReplacedBlockTopBeacon;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			breakBeaconBaseBlocks = builder
					.comment("If enabled, drops all beacon base blocks when the beacon itself is broken.")
					.define("breakBeaconBaseBlocks", true);
			dropReplacedBlockTopBeacon = builder
					.comment("If enabled, when a mineral block replaces a normal block that block is dropped on top of the beacon.")
					.define("dropReplacedBlockTopBeacon", true);
			
			builder.pop();
		}
	}
}