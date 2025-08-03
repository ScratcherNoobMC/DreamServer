package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Particle;
import org.bukkit.Sound;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoNombre implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoNombre(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String etiqueta, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.name") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.name")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length == 0) {
            jugador.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        String nuevoNombreRaw = String.join(" ", args);
        String nuevoNombre = ChatColor.translateAlternateColorCodes('&', nuevoNombreRaw);

        jugador.setDisplayName(nuevoNombre);
        jugador.setPlayerListName(nuevoNombre);

        jugador.setCustomName(nuevoNombre);
        jugador.setCustomNameVisible(true);

        String mensajeRaw = config.getString("mensajes.tag-cambiado", "&aTu nombre ahora es: %name%");
        mensajeRaw = mensajeRaw.replace("%name%", nuevoNombre);
        String mensaje = ChatColor.translateAlternateColorCodes('&', nuevoNombre);
        jugador.sendMessage(mensaje);
        jugador.getWorld().spawnParticle(Particle.POOF, jugador.getLocation().add(0, 1.5, 0), 20, 0.3, 0.5, 0.3);
        jugador.playSound(jugador.getLocation(), Sound.BLOCK_NETHERRACK_BREAK, 1.0f, 1.0f);

        return true;
    }
    
}
