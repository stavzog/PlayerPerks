package me.stavros.playerabilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class EventListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if(Farmer.players.contains(e.getPlayer().getName())) Farmer.blockBreak(e);
        if(Miner.players.contains(e.getPlayer().getName())) Miner.superBreak(e);

        return;
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if(Farmer.players.contains(e.getPlayer().getName())) Farmer.blockPlace(e);

        return;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(Miner.players.contains(e.getPlayer().getName())) Miner.applyHaste(e);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(Miner.players.contains(e.getPlayer().getName())) Miner.eatCoal(e);
    }

    @EventHandler
    public void onEntityKilled(EntityDeathEvent e) {
        if (!(e.getEntity().getKiller() instanceof Player)) return;
        Player p = (Player) e.getEntity().getKiller();
        if(Hunter.players.contains(p.getName())) Hunter.doubleDrop(e);
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent e) {
        if(!(e.getInventory().getHolder() instanceof Player)) return;
        if (e.getRecipe() == null) return;
        Player p = (Player) e.getInventory().getHolder();
        if (e.getRecipe().getResult().equals(new ItemStack(Material.SLIME_BALL))) {
            if (!Engineer.players.contains(p.getName())) e.getInventory().setResult(new ItemStack(Material.AIR));
        } else if (e.getRecipe().getResult().equals(new ItemStack(Material.REDSTONE_ORE))) {
            if (!Engineer.players.contains(p.getName())) e.getInventory().setResult(new ItemStack(Material.AIR));
        } else if (e.getRecipe().getResult().equals(new ItemStack(Material.EMERALD))) {
            if (!Hunter.players.contains(p.getName())) e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

}
