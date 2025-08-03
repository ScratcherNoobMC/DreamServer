package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoInicio implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoInicio(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ds.start") && !GestorPrefijosYaml.tienePermiso(player, "ds.start")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (!config.contains("puntoinicial")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-inicio", "&cNo se ha establecido un punto de inicio aun.")));
            return true;
        }

        String mundoNombre = config.getString("puntoinicial.mundo");
        double x = config.getDouble("puntoinicial.x");
        double y = config.getDouble("puntoinicial.y");
        double z = config.getDouble("puntoinicial.z");
        float yaw = (float) config.getDouble("puntoinicial.yaw");
        float pitch = (float) config.getDouble("puntoinicial.pitch");

        World mundo = Bukkit.getWorld(mundoNombre);
        if (mundo == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-mundo", "&cEl mundo proporcionado no existe.")));
            return true;
        }

        Location loc = new Location(mundo, x, y, z, yaw, pitch);
        player.teleport(loc);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.tp-inicio", "&aHas sido teletransportado a inicio.")));
        return true;
    }
}