package me.stavros.playerperks

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerInteractEvent

class EventListener: Listener {

    @EventHandler
    fun blockPlace(e: BlockPlaceEvent) {
        if(e.player.isIn("farmer")) Farmer.fastGrow(e)
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        if (e.player.isIn("miner")) Miner.superBreak(e)
        if(e.player.isIn("farmer")) Farmer.doubleCrops(e)
    }

    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
        if (e.player.isIn("miner")) Miner.eatCoal(e)
    }

    @EventHandler
    fun entityDeath(e: EntityDeathEvent) {
        if(e.entity.killer !is Player) return
        if((e.entity.killer as Player).isIn("hunter")) Hunter.doubleDrops(e)
    }
}