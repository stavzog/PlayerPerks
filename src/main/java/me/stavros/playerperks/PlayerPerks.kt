package me.stavros.playerperks

import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume;

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
        Miner().load()
        Hunter().load()
        Hunter.addEmeraldRecipe()
    }
}

val scope = CoroutineScope(Dispatchers.Default)
val plugin = PlayerPerks.instance
val perksmap = hashMapOf<String, HashSet<String>>()
suspend fun <T> sync(task: () -> T): T = withTimeout(10000L) {
    suspendCancellableCoroutine { cont ->
        Bukkit.getScheduler().runTask(plugin, Runnable {
            runCatching(task).fold({ cont.resume(it) }, cont::resumeWithException)
        })
    }
}

fun Player.isIn(perk: String): Boolean {
    if (perksmap.get(perk)?.contains(this.name) == true) return true
    return false
}