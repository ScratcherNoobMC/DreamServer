package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoMundo implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoMundo(JavaPlugin plugin) {
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

        if (!player.hasPermission("ds.world") && !GestorPrefijosYaml.tienePermiso(player, "ds.world")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Uso incorrecto, porfavor use: /ds ayuda");
            return true;
        }

        String nombreMundo = args[0];
        World mundo = Bukkit.getWorld(nombreMundo);

        if (mundo == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-mundo", "&cEl mundo proporcionado no existe.")));
            return true;
        }

        player.teleport(mundo.getSpawnLocation());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.tp-mundo", "&aHas sido teletransportado al mundo.")));
        return true;
    }
}