package me.stavros.playerabilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayerExact;

public class PerksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("perks")) {
            if(args.length <= 0) return false;
            if (args[0].equalsIgnoreCase("add")) {
                if(getPlayerExact(args[1]) == null) {
                    player.sendMessage(ChatColor.RED+"Player cannot be found or is not online!");
                    return false;
                }
                switch(args[2]) {
                    case "farmer":
                        if(Farmer.players.contains(args[1])) {
                            player.sendMessage(ChatColor.GOLD+"Player already is a farmer");
                        }
                        Farmer.add(Bukkit.getPlayerExact(args[1]));
                        player.sendMessage(ChatColor.GREEN+"Success");
                        Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GOLD+"[Farming Committee] You are now a farmer!");
                        break;
                    case "miner":
                        if(Miner.players.contains(args[1])) {
                            player.sendMessage(ChatColor.GOLD+"Player already is a miner!");
                        }
                        Miner.add(Bukkit.getPlayerExact(args[1]));
                        player.sendMessage(ChatColor.GREEN+"Success");
                        Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GOLD+"[Mining Committee] You are now a miner!");
                        break;
                    case "engineer":
                        if(Engineer.players.contains(args[1])) {
                            player.sendMessage(ChatColor.GOLD+"Player already is an engineer!");
                        }
                        Engineer.add(Bukkit.getPlayerExact(args[1]));
                        player.sendMessage(ChatColor.GREEN+"Success");
                        Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GOLD+"[Engineering Committee] You are now an engineer!");
                        break;
                    case "hunter":
                        if(Hunter.players.contains(args[1])) {
                            player.sendMessage(ChatColor.GOLD+"Player already is a hunter!");
                        }
                        Hunter.add(Bukkit.getPlayerExact(args[1]));
                        player.sendMessage(ChatColor.GREEN+"Success");
                        Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GOLD+"[Hunting Committee] You are now a hunter!");
                        break;
                }
                return true;
            }

            else if (args[0].equalsIgnoreCase("lfarmers")) {
                getLogger().info("lfarmers");
                player.sendMessage("Farmers");
                Farmer.players.forEach((e) -> { player.sendMessage(ChatColor.GOLD+e); });
                player.sendMessage("Miners");
                Miner.players.forEach((e) -> { player.sendMessage(ChatColor.GOLD+e); });
                player.sendMessage("Engineers");
                Engineer.players.forEach((e) -> { player.sendMessage(ChatColor.GOLD+e); });
                player.sendMessage("Hunters");
                Hunter.players.forEach((e) -> { player.sendMessage(ChatColor.GOLD+e); });

                return true;
            }

        }

        return false;
    }
}
