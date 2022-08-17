package me.stavros.playerperks

import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import kotlin.random.Random

class Farmer: Perk {
    override val name = "farmer"

    companion object {

        private val materials = mutableMapOf<Material, Material>().apply {
            this[Material.WHEAT] = Material.WHEAT
            this[Material.POTATOES] = Material.POTATO
            this[Material.BEETROOTS] = Material.BEETROOT
            this[Material.CARROTS] = Material.CARROT
            this[Material.NETHER_WART] = Material.NETHER_WART
        }

        fun doubleCrops(e: BlockBreakEvent) {
            if(!materials.containsKey(e.block.type)) return
            e.block.drops.forEach {
                e.block.world.dropItem(e.block.location, it)
            }
        }

        fun fastGrow(e: BlockPlaceEvent) {
            plugin.logger.info("block place")
            val chance = 0.25
            //if(Random.nextDouble() > chance) return;
            if (e.block.type !in materials.keys) return;
            plugin.logger.info("in list")
            val crop = e.block.blockData as Ageable
            scope.launch {
                repeat(5) {
                    if (crop.age == crop.maximumAge) cancel()
                    if (e.block.isEmpty) cancel()
                    yield()
                    delay(4000)
                    crop.age++
                    sync {
                        e.block.setBlockData(crop)
                    }
                }
                cancel()
            }
        }

    }
}