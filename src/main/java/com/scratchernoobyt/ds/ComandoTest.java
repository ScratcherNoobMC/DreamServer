package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;

import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoTest implements CommandExecutor {

    private final DreamServer plugin;

    public ComandoTest(DreamServer plugin) {
        this.plugin = plugin;
    }
    
    private final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("ds.test") && !GestorPrefijosYaml.tienePermiso(player, "ds.test")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        double[] tps = Bukkit.getServer().getTPS();
        double currentTps = tps[0];

        int ping = player.getPing();

        long ramTotal = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long ramFree = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long ramUsed = ramTotal - ramFree;

        player.sendMessage(ChatColor.GREEN + "📊 Informacion del servidor:");
        player.sendMessage(ChatColor.YELLOW + "⚙️ TPS: " + ChatColor.WHITE + df.format(currentTps));
        player.sendMessage(ChatColor.YELLOW + "📡 PING: " + ChatColor.WHITE + ping + "ms");
        player.sendMessage(ChatColor.YELLOW + "🚀 RAM USADA: " + ChatColor.WHITE + ramUsed + ChatColor.YELLOW + "MB / " + ChatColor.WHITE + ramTotal + ChatColor.YELLOW + "MB");

        return true;
    }
}
