package me.stavros.playerperks

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.HashSet

interface Perk {
    val name: String

    fun load() {
        perksmap.put(name, hashSetOf<String>())
        val config = plugin.config
        if(!config.contains(name)) return
        perksmap.get(name)?.addAll(config.getStringList(name))
    }

    infix fun add(player: String) {
        plugin.logger.info(Bukkit.getPlayerExact(player)?.name ?: "noplayer")
        perksmap.get(name)?.add(Bukkit.getPlayerExact(player)?.name ?: return)
        plugin.config.set(name, perksmap.get(name)?.toList())
        plugin.saveConfig()
    }
}