package com.scratchernoobyt.ds;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;


public class ComandoCama implements CommandExecutor  {

    private final JavaPlugin plugin;

    public ComandoCama(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.bed") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.bed")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        Location cama = jugador.getBedSpawnLocation();

        if (cama != null) {
            jugador.teleport(cama);
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.teleport-cama", "&aTeletransportando a tu cama...")));
        }
        else {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-cama", "&cNo tienes cama.")));
        }
        return true;
    }
}
