package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoPuntoInicial implements CommandExecutor {
    private final JavaPlugin plugin;

    public ComandoPuntoInicial(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        Player jugador = (Player) sender;
        if (!jugador.hasPermission("ds.setspawn") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.setspawn")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }
        Location loc = jugador.getLocation();

        plugin.getConfig().set("puntoinicial.mundo", loc.getWorld().getName());
        plugin.getConfig().set("puntoinicial.x", loc.getX());
        plugin.getConfig().set("puntoinicial.y", loc.getY());
        plugin.getConfig().set("puntoinicial.z", loc.getZ());
        plugin.getConfig().set("puntoinicial.yaw", loc.getYaw());
        plugin.getConfig().set("puntoinicial.pitch", loc.getPitch());

        plugin.saveConfig();

        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.puntoinicial-guardado", "&aPunto incial guardado correctamente.")));
        
        return true;
    }
}
