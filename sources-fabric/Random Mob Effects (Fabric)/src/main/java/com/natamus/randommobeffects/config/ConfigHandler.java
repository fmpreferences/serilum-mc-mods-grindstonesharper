/*
 * This is the latest source code of Random Mob Effects.
 * Minecraft version: 1.19.1, mod version: 2.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.randommobeffects.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.natamus.randommobeffects.util.Reference;

import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;

public class ConfigHandler { 
	public static PropertyMirror<Integer> potionEffectLevel = PropertyMirror.create(ConfigTypes.INTEGER);
	public static PropertyMirror<Boolean> hideEffectParticles = PropertyMirror.create(ConfigTypes.BOOLEAN);
	public static PropertyMirror<Boolean> disableCreepers = PropertyMirror.create(ConfigTypes.BOOLEAN);

	private static final ConfigTree CONFIG = ConfigTree.builder() 
			.beginValue("potionEffectLevel", ConfigTypes.INTEGER, 1)
			.withComment("The default level of the effects the mod applies, by default 1.")
			.finishValue(potionEffectLevel::mirror)

			.beginValue("hideEffectParticles", ConfigTypes.BOOLEAN, false)
			.withComment("When enabled, hides the particles from the mobs with an effect.")
			.finishValue(hideEffectParticles::mirror)
			
			.beginValue("disableCreepers", ConfigTypes.BOOLEAN, true)
			.withComment("Creepers can create infinite lingering potion effects which is probably not what you want. When enabled, the mod doesn't give creepers a random effect.")
			.finishValue(disableCreepers::mirror)

			.build();

	private static void writeDefaultConfig(Path path, JanksonValueSerializer serializer) {
		try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
			FiberSerialization.serialize(CONFIG, s, serializer);
		} catch (IOException ignored) {}

	}

	public static void setup() {
		JanksonValueSerializer serializer = new JanksonValueSerializer(false);
		Path p = Paths.get("config", Reference.MOD_ID + ".json");
		writeDefaultConfig(p, serializer);

		try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
			FiberSerialization.deserialize(CONFIG, s, serializer);
		} catch (IOException | ValueDeserializationException e) {
			System.out.println("Error loading config");
		}
	}
}