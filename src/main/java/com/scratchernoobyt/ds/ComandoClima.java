package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoClima implements CommandExecutor {
    private final JavaPlugin plugin;
    public ComandoClima(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player jugador = (Player) sender;

        if (!sender.hasPermission("ds.weather") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.weather")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        String mensajeIncorrecto = plugin.getConfig().getString("mensajes.comando-incorrecto", "&cComando incorrecto, porfavor use: /ds ayuda");
        mensajeIncorrecto = ChatColor.translateAlternateColorCodes('&', mensajeIncorrecto);

        if (args.length == 0) {
            sender.sendMessage(mensajeIncorrecto);
            return true;
        }

        String subcomando = args[0].toLowerCase();

        switch (subcomando) {
            case "soleado":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:weather clear");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-clima-soleado", "&aHas cambiado el clima a soleado.")));
                break;
            case "lluvioso":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:weather rain");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-clima-lluvioso", "&aHas cambiado el clima a lluvioso.")));
                break;
            case "tormentoso":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:weather thunder");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-clima-tormentoso", "&aHas cambiado el clima a tormentoso")));
        }
        return true;
    }
}
