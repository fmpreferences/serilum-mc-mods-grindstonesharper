/*
 * This is the latest source code of Random Mob Effects.
 * Minecraft version: 1.16.5, mod version: 1.2.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Random Mob Effects ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.randommobeffects.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> potionEffectLevel;
		public final ForgeConfigSpec.ConfigValue<Boolean> hideEffectParticles;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			potionEffectLevel = builder
					.comment("The default level of the effects the mod applies, by default 1.")
					.defineInRange("potionEffectLevel", 1, 1, 50);
			hideEffectParticles = builder
					.comment("When enabled, hides the particles from the mobs with an effect.")
					.define("hideEffectParticles", false);
			
			builder.pop();
		}
	}
}