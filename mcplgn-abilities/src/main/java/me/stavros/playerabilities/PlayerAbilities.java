package me.stavros.playerabilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAbilities extends JavaPlugin {

    public static ShapelessRecipe slimeRecipe;
    public static ShapelessRecipe redstoneRecipe;
    public static ShapedRecipe emeraldRecipe;


    @Override
    public void onEnable() {
        // Plugin startup logicew
        new Farmer(this);
        new Miner(this);
        new Engineer(this);
        new Hunter(this);
        this.getServer().getPluginManager().registerEvents(new EventListener(), this );
        this.saveDefaultConfig();
        Farmer.load();
        Miner.load();
        Engineer.load();
        Hunter.load();
        this.getCommand("perks").setExecutor(new PerksCommand());

        //slime recipe
        ItemStack slime = new ItemStack(Material.SLIME_BALL);

        NamespacedKey key = new NamespacedKey(this, "slime_ball");
        slimeRecipe = new ShapelessRecipe(key, slime);

        slimeRecipe.addIngredient(1, Material.GOLD_INGOT);
        slimeRecipe.addIngredient(1, Material.CLAY_BALL);
        slimeRecipe.addIngredient(1, Material.EMERALD);

        Bukkit.addRecipe(slimeRecipe);

        //redstone recipe
        ItemStack redstone = new ItemStack(Material.REDSTONE_ORE);

        NamespacedKey key2 = new NamespacedKey(this, "redstone_ore");
        redstoneRecipe = new ShapelessRecipe(key2, redstone);

        redstoneRecipe.addIngredient(9, Material.GRANITE);

        Bukkit.addRecipe(redstoneRecipe);

        //emerald recipe
        ItemStack emerald = new ItemStack(Material.EMERALD);

        NamespacedKey key3 = new NamespacedKey(this, "emerald");
        emeraldRecipe = new ShapedRecipe(key3, emerald);

        emeraldRecipe.shape("i","e");
        emeraldRecipe.setIngredient('i', Material.LAVA_BUCKET);
        emeraldRecipe.setIngredient('e', Material.LAPIS_LAZULI);

        Bukkit.addRecipe(emeraldRecipe);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
