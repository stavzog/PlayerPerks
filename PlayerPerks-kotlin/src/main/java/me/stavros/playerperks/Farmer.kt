package me.stavros.playerperks

import kotlinx.coroutines.*
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.event.block.BlockPlaceEvent
import kotlin.random.Random

class Farmer: Perk {
    override val name = "farmer"
    override val players = hashSetOf<String>()

    companion object {

        private val materials = mutableMapOf<Material, Material>().apply {
            this[Material.WHEAT] = Material.WHEAT
            this[Material.POTATOES] = Material.POTATO
            this[Material.BEETROOTS] = Material.BEETROOT
            this[Material.CARROTS] = Material.CARROT
            this[Material.NETHER_WART] = Material.NETHER_WART
        }

        fun fastGrow(e: BlockPlaceEvent) {
            PlayerPerks.instance.logger.info("block place")
            val chance = 0.25
            //if(Random.nextDouble() > chance) return;
            if (e.block.type !in materials.keys) return;
            PlayerPerks.instance.logger.info("in list")
            val crop = e.block.blockData as Ageable
            runBlocking {
                launch {
                    while (crop.age < 6) {
                        if (crop.age == crop.maximumAge) cancel()
                        if (e.block.isEmpty) cancel()
                        PlayerPerks.instance.logger.info("growing")
                        yield()
                        delay(4000)
                        crop.setAge(crop.age + 1)
                        e.block.setBlockData(crop)
                    }
                }
            }
        }

    }
}