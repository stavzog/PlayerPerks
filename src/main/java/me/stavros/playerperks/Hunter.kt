package me.stavros.playerperks

import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDeathEvent

class Hunter: Perk {
    override val name: String = "hunter"

    companion object {
        private val entities = hashSetOf(EntityType.ZOMBIE,EntityType.ZOMBIE_VILLAGER,EntityType.SKELETON,EntityType.CREEPER, EntityType.ENDERMAN,EntityType.SPIDER,EntityType.CAVE_SPIDER, EntityType.BLAZE, EntityType.COW,EntityType.SHEEP)

        fun doubleDrops(e: EntityDeathEvent) {
            if(!entities.contains(e.entityType)) return
            val drops = e.drops
            drops.forEach { e.entity.world.dropItem(e.entity.location, it) }
        }
    }
}