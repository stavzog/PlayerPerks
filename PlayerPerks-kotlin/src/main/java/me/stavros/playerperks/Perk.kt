package me.stavros.playerperks

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.HashSet

interface Perk {
    val name: String
    val players: HashSet<String>

    fun load() {
        val config = PlayerPerks.instance.config
        if (!config.contains(name)) return
        players.addAll(config.getStringList(name))
    }

    infix fun add(player: String) {
        PlayerPerks.instance.logger.info(Bukkit.getPlayerExact(player)?.name ?: "noplayer")
        players.add(Bukkit.getPlayerExact(player)?.name ?: return)
        PlayerPerks.instance.config.set(name, listOf(players))
    }
}