package me.stavros.playerabilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayerExact;

public final class Farmer {

    public static Set<String> players = new HashSet<>();
    private static Material[] materials= {Material.WHEAT, Material.POTATOES, Material.BEETROOTS, Material.CARROTS, Material.NETHER_WART};
    private static FileConfiguration config;
    private static Plugin plugin;

    public Farmer(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static boolean load() {

        if(!config.contains("farmers")) return false;

        List<String> strplayers = config.getStringList("farmers");
        for (String pl : strplayers) {
            players.add(pl);
        }

        return true;
    }

    public static void blockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material mat = e.getBlock().getType();
        double percentage = 0.5;

        if(!Arrays.asList(materials).contains(block.getType())) return;
        if(new Random().nextDouble() > percentage) return;

        Ageable crop = (Ageable) block.getBlockData();
        if (!(crop.getAge() == crop.getMaximumAge())) return;

        ItemStack item = new ItemStack(Material.WHEAT);

        switch (block.getType()) {
            case WHEAT:
                item = new ItemStack(Material.WHEAT);
                break;
            case POTATOES:
                item = new ItemStack(Material.POTATO);
                break;
            case BEETROOTS:
                item = new ItemStack(Material.BEETROOT);
                break;
            case CARROTS:
                item = new ItemStack(Material.CARROT);
                break;
            case NETHER_WART:
                item = new ItemStack(Material.NETHER_WART);
        }

        item.setAmount(block.getDrops().size());
        block.getWorld().dropItem(block.getLocation(), item);
    }

    public static boolean add(Player p) {
        players.add(p.getName());
        config.set("farmers", new ArrayList<String>(players));
        plugin.saveConfig();

        return true;
    }

    public static void blockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Material mat = block.getType();
        double percentage = 0.50;

        if(new Random().nextDouble() > percentage) return;
        if(!Arrays.asList(materials).contains(block.getType())) return;

        grow(block);
    }

    private static boolean grow(Block block) {
        Ageable crop = (Ageable) block.getBlockData();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.isEmpty()) {
                    cancel();
                    return;
                }
                if(crop.getAge() == 4) {
                    cancel();
                    return;
                }
                if (crop.getAge() == (crop.getMaximumAge() - 1)) {
                    cancel();
                    return;
                }
                crop.setAge(crop.getAge() + 1);
                block.setBlockData(crop);

            }
        }.runTaskTimer(plugin, 1, 80);
        return true;
    }
}
