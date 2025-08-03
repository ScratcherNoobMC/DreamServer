package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.scratchernoobyt.noobicore.gestores.GestorPermisos;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

import java.util.Map;

public class ComandoPerms implements CommandExecutor {
    private final GestorPermisos gestor;
    private final JavaPlugin plugin;

    public ComandoPerms(GestorPermisos gestor, JavaPlugin plugin) {
        this.gestor = gestor;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String etiqueta, String[] args) {
        FileConfiguration config = plugin.getConfig();

        Player xd = (Player) sender;

        if (!sender.hasPermission("ds.perms") && !GestorPrefijosYaml.tienePermiso(xd, "ds.perms")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        Player objetivo = Bukkit.getPlayer(args[1]);

        if (objetivo == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-nick-no-encontrado", "&cJugador no encontrado.")));
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "dar" -> {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
                    return true;
                }

                gestor.asignar(objetivo, args[2]);
                String mensajeRaw = config.getString("mensajes.permiso-asignado", "&aPermiso &e%perm% &aasignado a &e%player%&a.");
                mensajeRaw = mensajeRaw.replace("%perm%", args[2]);
                mensajeRaw = mensajeRaw.replace("%player%", objetivo.getName());
                String mensaje = ChatColor.translateAlternateColorCodes('&', mensajeRaw);
                sender.sendMessage(mensaje);
            }
            case "quitar" -> {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
                    return true;
                }

                gestor.quitar(objetivo, args[2]);
                String mensajeRaw = config.getString("mensajes.permiso-desasignado", "&cPermiso &5%perm% &cdesasignado de &5%player%&c.");
                mensajeRaw = mensajeRaw.replace("%perm%", args[2]);
                mensajeRaw = mensajeRaw.replace("%player%", objetivo.getName());
                String mensaje = ChatColor.translateAlternateColorCodes('&', mensajeRaw);
                sender.sendMessage(mensaje);
            }

            case "lista" -> {
                Map <String, Boolean> permisos = gestor.listar(objetivo);
                sender.sendMessage(ChatColor.GREEN + "Permisos de: " + objetivo.getName() + ":");
                if (permisos.isEmpty()) {
                    sender.sendMessage(ChatColor.GRAY + "(Sin permisos asignados)");
                }
                else {
                    permisos.forEach((perm, val) -> sender.sendMessage(ChatColor.GRAY + "- " + perm + ": " + (val ? "✔️" : "✖️")));
                }
            }
            default -> sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
        }

        return true;
    }
    
}
