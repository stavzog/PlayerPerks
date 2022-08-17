package me.stavros.playerperks

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

class Hunter: Perk {
    override val name: String = "hunter"

    companion object {
        private val entities = hashSetOf(EntityType.ZOMBIE,EntityType.ZOMBIE_VILLAGER,EntityType.SKELETON,EntityType.CREEPER, EntityType.ENDERMAN,EntityType.SPIDER,EntityType.CAVE_SPIDER, EntityType.BLAZE, EntityType.COW,EntityType.SHEEP)

        fun doubleDrops(e: EntityDeathEvent) {
            if(!entities.contains(e.entityType)) return
            val drops = e.drops
            drops.forEach { e.entity.world.dropItem(e.entity.location, it) }
        }

        fun addEmeraldRecipe() {
            val emerald = ItemStack(Material.EMERALD)
            val key = NamespacedKey(plugin, "emerald")

            val emeraldRecipe = ShapedRecipe(key, emerald)

            emeraldRecipe.shape("b", "l")
            emeraldRecipe.setIngredient('b',Material.LAVA_BUCKET)
            emeraldRecipe.setIngredient('l', Material.LAPIS_LAZULI)

            Bukkit.addRecipe(emeraldRecipe)
        }
    }
}