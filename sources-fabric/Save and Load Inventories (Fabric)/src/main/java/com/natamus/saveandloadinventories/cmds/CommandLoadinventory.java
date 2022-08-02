/*
 * This is the latest source code of Save and Load Inventories.
 * Minecraft version: 1.19.1, mod version: 2.1.
 *
 * Please don't distribute without permission.
 * For all modding projects, feel free to visit the CurseForge page: https://curseforge.com/members/serilum/projects
 */

package com.natamus.saveandloadinventories.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective_fabric.functions.PlayerFunctions;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.saveandloadinventories.util.Util;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

public class CommandLoadinventory {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    	dispatcher.register(Commands.literal("loadinventory").requires((iCommandSender) -> iCommandSender.hasPermission(2))
			.then(Commands.argument("inventory-name", StringArgumentType.word())
			.executes((command) -> {
				return loadInventory(command);
			}))
			.then(Commands.argument("inventory-name", StringArgumentType.word())
			.then(Commands.argument("player-name", StringArgumentType.word())
			.executes((command) -> {
				return loadInventoryForPlayerName(command);
			})))
		);
    	dispatcher.register(Commands.literal("li").requires((iCommandSender) -> iCommandSender.hasPermission(2))
			.then(Commands.argument("inventory-name", StringArgumentType.word())
			.executes((command) -> {
				return loadInventory(command);
			}))
			.then(Commands.argument("inventory-name", StringArgumentType.word())
			.then(Commands.argument("player-name", StringArgumentType.word())
			.executes((command) -> {
				return loadInventoryForPlayerName(command);
			})))
		);
    }
    
    private static int loadInventory(CommandContext<CommandSourceStack> command) {
    	CommandSourceStack source = command.getSource();
    	
    	Player player;
		try {
			player = source.getPlayerOrException();
		}
		catch (CommandSyntaxException ex) {
			StringFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
			return 1;
		}
		
		String inventoryname = StringArgumentType.getString(command, "inventory-name").toLowerCase();
		if (inventoryname.trim() == "") {
			StringFunctions.sendMessage(source, "The inventory name '" + inventoryname + "' is invalid.", ChatFormatting.RED);
			return 0;
		}
		
		String gearstring = Util.getGearStringFromFile(inventoryname);
		if (gearstring == "") {
			StringFunctions.sendMessage(source, "Unable to load the content of the inventory with the name '" + inventoryname + "'.", ChatFormatting.RED);
			return 0;					
		}
		
		PlayerFunctions.setPlayerGearFromString(player, gearstring);
		StringFunctions.sendMessage(source, "Successfully loaded '" + inventoryname + "' to your inventory.", ChatFormatting.DARK_GREEN);
		return 1;
    }
    
    private static int loadInventoryForPlayerName(CommandContext<CommandSourceStack> command) {
    	CommandSourceStack source = command.getSource();
    	
    	Player player;
		try {
			player = source.getPlayerOrException();
		}
		catch (CommandSyntaxException ex) {
			StringFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
			return 1;
		}
		
		String inventoryname = StringArgumentType.getString(command, "inventory-name").toLowerCase();
		if (inventoryname.trim() == "") {
			StringFunctions.sendMessage(source, "The inventory name '" + inventoryname + "' is invalid.", ChatFormatting.RED);
			return 0;
		}
		
		String targetname = StringArgumentType.getString(command, "player-name").toLowerCase();
		Player target = PlayerFunctions.matchPlayer(player, targetname.toLowerCase());
		if (target == null) {
			StringFunctions.sendMessage(source, "Unable to find an online player with the name '" + targetname + "'.", ChatFormatting.RED);
			return 0;			
		}
		
		String gearstring = Util.getGearStringFromFile(inventoryname);
		if (gearstring == "") {
			StringFunctions.sendMessage(source, "Unable to load the content of the inventory with the name '" + inventoryname + "'.", ChatFormatting.RED);
			return 0;					
		}
		
		PlayerFunctions.setPlayerGearFromString(target, gearstring);
		StringFunctions.sendMessage(source, "Successfully loaded '" + inventoryname + "' to the inventory of '" + target.getName().getString() + "'.", ChatFormatting.DARK_GREEN);
		StringFunctions.sendMessage(target, "Your inventory has been replaced with the preset named '" + inventoryname + "'.", ChatFormatting.DARK_GREEN);
		return 1;
    }
}
