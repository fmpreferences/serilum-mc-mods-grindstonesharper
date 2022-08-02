/*
 * This is the latest source code of Beautified Chat Client.
 * Minecraft version: 1.19.1, mod version: 1.2.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.beautifiedchatclient.util;

import com.natamus.beautifiedchatclient.config.ConfigHandler;

import net.minecraft.ChatFormatting;

public class Util {
	public static ChatFormatting getColour(String word) {
		ChatFormatting colour = ChatFormatting.getById(ConfigHandler.GENERAL.chatOtherSymbolsColour.get());
		if (word.equalsIgnoreCase("timestamp")) {
			colour = ChatFormatting.getById(ConfigHandler.GENERAL.chatTimestampColour.get());
		}
		else if (word.equalsIgnoreCase("username")) {
			colour = ChatFormatting.getById(ConfigHandler.GENERAL.chatUsernameColour.get());
		}
		else if (word.equalsIgnoreCase("chatmessage")) {
			colour = ChatFormatting.getById(ConfigHandler.GENERAL.chatMessageColour.get());
		}
		
		return colour;
	}
}
