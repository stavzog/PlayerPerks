package me.stavros.playerperks

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

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

    @EventHandler
    fun prepareCraftItem(e: PrepareItemCraftEvent) {
        if(e.inventory.holder !is Player) return
        if(e.recipe == null) return
        val p = e.inventory.holder as Player
        when(e.recipe!!.result.type) {
            Material.EMERALD -> if(!p.isIn("hunter")) e.inventory.result = ItemStack(Material.AIR)
            else -> {
                return
            }
        }

    }
}