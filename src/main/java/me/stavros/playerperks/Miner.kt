package me.stavros.playerperks

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Miner: Perk {
    override val name = "miner"

    companion object {
        var running = false;

        fun eatCoal(e: PlayerInteractEvent) {
            val p = e.player
            if(running) return;
            if(p.inventory.itemInMainHand.type != Material.COAL) return

            p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, ItemStack(Material.COAL));
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1f,1f);
            p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, ItemStack(Material.COAL));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f,1f);
            p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, ItemStack(Material.COAL));

            p.sendActionBar(Component.text("Super-Break Ability active for 4s"))
            running = true
            scope.launch {
                delay(4000)
                running = false
                cancel()
            }
        }

        fun superBreak(e: BlockBreakEvent) {
            if(!running) return
            if(e.player.inventory.itemInMainHand.type.name.contains("PICKAXE")) return

            val drops = e.block.getDrops(e.player.inventory.itemInMainHand)
            e.block.world.dropItem(e.block.location, drops.first())
        }
    }

}
