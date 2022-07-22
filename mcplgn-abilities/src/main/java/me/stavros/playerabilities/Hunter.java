package me.stavros.playerabilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Hunter {

    public static Set<String> players = new HashSet<>();
    private static FileConfiguration config;
    private static Plugin plugin;
    private static EntityType[] entities = {EntityType.ZOMBIE,EntityType.ZOMBIE_VILLAGER,EntityType.SKELETON,EntityType.CREEPER, EntityType.ENDERMAN,EntityType.SPIDER,EntityType.CAVE_SPIDER, EntityType.BLAZE, EntityType.COW,EntityType.SHEEP};

    public Hunter(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static boolean load() {

        if(!config.contains("hunters")) return false;

        List<String> strplayers = config.getStringList("hunters");
        for (String pl : strplayers) {
            players.add(pl);
        }

        return true;
    }

    public static boolean add(Player p) {
        players.add(p.getName());
        config.set("hunters", new ArrayList<String>(players));
        plugin.saveConfig();

        return true;
    }

    public static void doubleDrop(EntityDeathEvent e) {
        if(!Arrays.asList(entities).contains(e.getEntityType())) return;
        Collection<ItemStack> drops = e.getDrops();
        List<ItemStack> toDrop = new ArrayList<>();

        for(ItemStack i : drops) {
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
        }
    }
}
