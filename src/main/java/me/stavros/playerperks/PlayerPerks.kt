package me.stavros.playerperks

import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume;

class PlayerPerks : JavaPlugin() {
    companion object {
        lateinit var instance: Plugin
        val perksmap = hashMapOf<String, HashSet<String>>()
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

suspend fun <T> sync(task: () -> T): T = withTimeout(10000L) {
    suspendCancellableCoroutine { cont ->
        Bukkit.getScheduler().runTask(PlayerPerks.instance, Runnable {
            runCatching(task).fold({ cont.resume(it) }, cont::resumeWithException)
        })
    }
}