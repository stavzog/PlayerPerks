package me.stavros.playerabilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayerExact;

public class Miner {

    public static Set<String> players = new HashSet<>();
    private static FileConfiguration config;
    private static Plugin plugin;
    private static boolean running = false;

    public Miner(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static boolean load() {

        if(!config.contains("miners")) return false;

        List<String> strplayers = config.getStringList("miners");
        for (String pl : strplayers) {
            players.add(pl);
        }

        return true;
    }

    public static boolean add(Player p) {
        players.add(p.getName());
        config.set("miners", new ArrayList<String>(players));
        plugin.saveConfig();

        return true;
    }

    public static void applyHaste(PlayerJoinEvent e) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, true, false));
    }

    public static void eatCoal(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!(p.getInventory().getItemInMainHand().getType() == Material.COAL)) return;
        if (running) return;

        p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, new ItemStack(Material.COAL));
        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 1f,1f);
        p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, new ItemStack(Material.COAL));
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f,1f);
        p.spawnParticle(Particle.ITEM_CRACK, p.getLocation(), 10, 0.2, 0.5, 0.2, new ItemStack(Material.COAL));

        p.getInventory().removeItem(new ItemStack(Material.COAL, 1));

        running = true;
        new BukkitRunnable() {

            @Override
            public void run() {
                running = false;
                cancel();
            }
        }.runTaskLater(plugin, 100);

    }

    public static void superBreak(BlockBreakEvent e) {
        if(!running) return;
        if(!(e.getPlayer().getInventory().getItemInMainHand().getType().name().contains("PICKAXE"))) return;

        Collection<ItemStack> drops = e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
        ItemStack items;
        if (e.getBlock().getType().equals(Material.STONE)) {
            items = new ItemStack(Material.COBBLESTONE);
        } else {
            items = new ItemStack(e.getBlock().getDrops().iterator().next().getType());
        }
        int num = 0;
        for(ItemStack i : drops) {
            num = num + i.getAmount();
        }
        items.setAmount(num);
        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), items);

    }
}
