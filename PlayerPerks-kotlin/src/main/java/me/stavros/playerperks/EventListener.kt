package me.stavros.playerperks

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class EventListener: Listener {

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        if(e.player.name in Farmer().players) Farmer.fastGrow(e)
    }

}