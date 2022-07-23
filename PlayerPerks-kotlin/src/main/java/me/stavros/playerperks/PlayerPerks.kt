package me.stavros.playerperks

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class PlayerPerks : JavaPlugin() {
    companion object {
        lateinit var instance: Plugin
    }

    init {
        instance = this
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Kotlin Working")
        saveDefaultConfig()
        server.pluginManager.registerEvents(EventListener(), this)
        getCommand("perks")!!.setExecutor(PerksCommand)
        Farmer().load()
    }
}