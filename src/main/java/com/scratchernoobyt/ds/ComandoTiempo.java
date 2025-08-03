package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;


public class ComandoTiempo implements CommandExecutor {
    private final JavaPlugin plugin;
    public ComandoTiempo(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player gf = (Player) sender;

        if (!sender.hasPermission("ds.time") && !GestorPrefijosYaml.tienePermiso(gf, "ds.time")) {
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
            case "dia":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set day");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-dia", "&aHas cambiado el tiempo a dia.")));
                break;
            
            case "noche":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set night");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-noche", "&aHas cambiado el tiempo a noche.")));
                break;
            
            case "medianoche":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set 18000");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-medianoche", "&aHas cambiado el tiempo a medianoche.")));
                break;
            
            case "amanecer":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set 0");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-amanecer", "&aHas cambiado el tiempo a amanecer.")));
                break;
            
            case "mediodia":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set 6000");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-mediodia", "&aHas cambiado el tiempo a mediodia.")));
                break;
            
            case "atardecer":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:time set 12000");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.comando-tiempo-atardecer", "&aHas cambiado el tiempo a atardecer.")));
                break;
        }
        return true;
    }
}
