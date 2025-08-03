package com.scratchernoobyt.ds;

import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoPrefijo implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoPrefijo(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();

        Player noob = (Player) sender;

        if (!sender.hasPermission("ds.prefijo") && !GestorPrefijosYaml.tienePermiso(noob, "ds.prefijo")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("set") && args.length >= 3) {
            Player jugador = Bukkit.getPlayerExact(args[1]);
            if (jugador == null) return mensaje(sender, "Jugador no encontrado");

            String prefijo = ChatColor.translateAlternateColorCodes('&', args[2]);

            GestorPrefijosYaml.asignarPrefijo(jugador, prefijo);
            return mensaje(sender, "Prefijo asignado a " + jugador.getName());
        }

        if (sub.equals("permiso") && args.length >= 3) {
            String prefijo = ChatColor.translateAlternateColorCodes('&', args[1]);
            String permiso = args[2];
            GestorPrefijosYaml.añadirPermisoPorPrefijo(prefijo, permiso);
            return mensaje(sender, "Permiso añadido a " + prefijo);
        }

        if (sub.equals("ver") && args.length >= 2) {
            Player jugador = Bukkit.getPlayerExact(args[1]);
            if (jugador == null) return mensaje(sender, "Jugador no encontrado.");

            String prefijo = GestorPrefijosYaml.obtenerPrefijo(jugador);
            return mensaje(sender, "Prefijo de " + jugador.getName() + ": " + ChatColor.AQUA + prefijo);
        }

        if (sub.equals("eliminar") && args.length >= 2) {
            Player jugador = Bukkit.getPlayerExact(args[1]);
            if (jugador == null) return mensaje(sender, "Jugador no encontrado");

            GestorPrefijosYaml.eliminarPrefijo(jugador);
            return mensaje(sender, "Prefijo eliminado de " + jugador.getName() + ".");
        }

        return mensaje(sender, "Subcomando desconocido.");
    }

    private boolean mensaje(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.YELLOW + msg);
        return true;
    }
}