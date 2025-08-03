package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoReglas implements CommandExecutor {
    private final JavaPlugin plugin;

    public ComandoReglas(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = plugin.getConfig();

        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.rules") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.rules")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        File archivo = new File(plugin.getDataFolder(), "reglas.txt");

        if (!archivo.exists()) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-reglas", "&cNo se encontro el archivo reglas.txt")));
            return true;
        }
        
        try {
            List<String> lineas = Files.readAllLines(archivo.toPath());
            for (String linea : lineas) {
                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', linea));
            }
        }

        catch(IOException e) {
            jugador.sendMessage("&4ERROR");
            e.printStackTrace();
        }

        return true;

    }
    
}
