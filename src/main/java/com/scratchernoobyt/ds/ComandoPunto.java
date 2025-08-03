package com.scratchernoobyt.ds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoPunto implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoPunto(JavaPlugin plugin) {
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

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        String action = args[0].toLowerCase();

        if (action.equals("crear") && args.length == 2) {
            if (!player.hasPermission("ds.createpoint") && !GestorPrefijosYaml.tienePermiso(player, "ds.createpoint")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
                return true;
            }

            String nombre = args[1];

            Location loc = player.getLocation();

            player.sendMessage(ChatColor.GOLD + "Guardando la ubicacion de " + ChatColor.YELLOW + nombre + ChatColor.GOLD + "...");

            config.set("puntos." + nombre + ".world", loc.getWorld().getName());
            config.set("puntos." + nombre + ".x", loc.getX());
            config.set("puntos." + nombre + ".y", loc.getY());
            config.set("puntos." + nombre + ".z", loc.getZ());
            config.set("puntos." + nombre + ".yaw", loc.getYaw());
            config.set("puntos." + nombre + ".pitch", loc.getPitch());
            plugin.saveConfig();

            player.sendMessage(ChatColor.GREEN + "Punto '" + nombre + "' creado con éxito.");
            return true;

        }

        else if (action.equals("eliminar") && args.length == 2) {

            if (!player.hasPermission("ds.deletepoint") && !GestorPrefijosYaml.tienePermiso(player, "ds.deletepoint")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
                return true;
            }
            String nombre = args[1];

            if (config.contains("puntos." + nombre)) {
                config.set("puntos." + nombre, null);
                plugin.saveConfig();
                player.sendMessage(ChatColor.DARK_BLUE + "Punto '" + nombre + "' eliminado.");
            }
            else {
                player.sendMessage(ChatColor.RED + "El punto '" + nombre + "' no existe.");
            }
            return true;
        }
        else if (args.length == 1) {

            if (!player.hasPermission("ds.point") && !GestorPrefijosYaml.tienePermiso(player, "ds.point")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
                return true;
            }
            String nombre = args[0];

            if (config.contains("puntos." + nombre)) {
                String world = config.getString("puntos." + nombre + ".world");
                double x = config.getDouble("puntos." + nombre + ".x");
                double y = config.getDouble("puntos." + nombre + ".y");
                double z = config.getDouble("puntos." + nombre + ".z");
                float yaw = (float) config.getDouble("puntos." + nombre + ".yaw");
                float pitch = (float) config.getDouble("puntos." + nombre + ".pitch");
                if (plugin.getServer().getWorld(world) == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-mundo", "&cEl mundo proporcionado no existe.")));
                    return true;
                }

                Location loc = new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
                player.teleport(loc);
                //Preparacion de mensaje
                String mensaje = config.getString("mensajes.tp-punto", "&aTe has teletransportado a %point%");
                mensaje = mensaje.replace("%point%", nombre);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje));
            }

            else {
                String mensaje = config.getString("mensajes.error-punto", "&cNo existe %point%");
                mensaje = mensaje.replace("%point%", nombre);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje));
            }
            return true;
        }

        else {
            player.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor usa /ds ayuda");
            return true;
        }
    }
    
}
