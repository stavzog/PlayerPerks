package me.stavros.playerperks

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

class EventListener: Listener {

    @EventHandler
    fun blockPlace(e: BlockPlaceEvent) {
        if(e.player.isIn("farmer")) Farmer.fastGrow(e)
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        if (e.player.isIn("miner")) Miner.superBreak(e)
    }

    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
        if (e.player.isIn("miner")) Miner.eatCoal(e)
    }

}