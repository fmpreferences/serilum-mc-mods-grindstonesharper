/*
 * This is the latest source code of Save and Load Inventories.
 * Minecraft version: 1.19.1, mod version: 2.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.saveandloadinventories.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.saveandloadinventories.util.Util;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.ChatFormatting;

public class CommandListinventories {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    	dispatcher.register(Commands.literal("listinventories").requires((iCommandSender) -> iCommandSender.hasPermission(2))
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				
				StringFunctions.sendMessage(source, "Saved inventories: " + Util.getListOfInventories() + ".", ChatFormatting.DARK_GREEN);
				return 1;
			})
		);
    }
}
