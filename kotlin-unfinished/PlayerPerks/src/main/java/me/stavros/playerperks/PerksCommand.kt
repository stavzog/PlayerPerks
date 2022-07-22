package me.stavros.playerperks

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object PerksCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        if (args[0] == "add" && args.size == 3) {
            if(args[1] in PlayerPerks.instance.config.getStringList(args[2])) sender.sendMessage("${ChatColor.RED}Player already has this perk")
            PlayerPerks.instance.logger.info(args[1])
            when (args[2]) {
                "farmer" -> Farmer().add(args[1])
            }
            sender.sendMessage("${ChatColor.GREEN}Success")
            Farmer().players.forEach{
                sender.sendMessage(it)
            }
            Bukkit.getPlayerExact(args[1])?.sendMessage("${ChatColor.GOLD} [Farming Committee] You are now a farmer")
            return true
        }

        return false
    }

}