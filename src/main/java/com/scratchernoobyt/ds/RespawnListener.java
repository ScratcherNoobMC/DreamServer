package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RespawnListener implements Listener {
    private final JavaPlugin plugin;

    public RespawnListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FileConfiguration config = plugin.getConfig();

        if (!config.contains("puntoinicial")) return;

        String mundoNombre = config.getString("puntoinicial.mundo");
        World mundo = Bukkit.getWorld(mundoNombre);
        if (mundo == null) return;

        double x = config.getDouble("puntoinicial.x");
        double y = config.getDouble("puntoinicial.y");
        double z = config.getDouble("puntoinicial.z");
        float yaw = (float) config.getDouble("puntoinicial.yaw");
        float pitch = (float) config.getDouble("puntoinicial.pitch");

        Location puntoInicial = new Location(mundo, x, y, z, yaw, pitch);
        event.setRespawnLocation(puntoInicial);
    }
}
