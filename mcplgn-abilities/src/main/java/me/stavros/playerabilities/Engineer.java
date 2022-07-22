package me.stavros.playerabilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Engineer {

    public static Set<String> players = new HashSet<>();
    private static FileConfiguration config;
    private static Plugin plugin;

    public Engineer(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static boolean load() {

        if(!config.contains("engineers")) return false;

        List<String> strplayers = config.getStringList("engineers");
        for (String pl : strplayers) {
            players.add(pl);
        }

        return true;
    }

    public static boolean add(Player p) {
        players.add(p.getName());
        config.set("engineers", new ArrayList<String>(players));
        plugin.saveConfig();

        return true;
    }

}
