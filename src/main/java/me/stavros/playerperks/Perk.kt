package me.stavros.playerperks

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.HashSet

interface Perk {
    val name: String

    fun load() {
        val config = PlayerPerks.instance.config
        if(!config.contains(name)) return
        PlayerPerks.perksmap.get(name)?.addAll(config.getStringList(name))
    }

    infix fun add(player: String) {
        PlayerPerks.instance.logger.info(Bukkit.getPlayerExact(player)?.name ?: "noplayer")
        PlayerPerks.perksmap.get(name)?.add(Bukkit.getPlayerExact(player)?.name ?: return)
        PlayerPerks.instance.config.set(name, PlayerPerks.perksmap.get(name) ?: return)
    }
}