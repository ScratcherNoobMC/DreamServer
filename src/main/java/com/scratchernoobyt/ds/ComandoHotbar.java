package com.scratchernoobyt.ds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Arrays;
import com.scratchernoobyt.noobicore.data.HotbarData;
import com.scratchernoobyt.noobicore.gestores.GestorHotbar;
import com.scratchernoobyt.noobicore.util.PlaceholderReplacer;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoHotbar implements CommandExecutor {
    private final JavaPlugin plugin;

    public ComandoHotbar(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();

        Player p = (Player) sender;

        if (!sender.hasPermission("ds.hotbar") && !GestorPrefijosYaml.tienePermiso(p, "ds.hotbar")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("crear")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
                return true;
            }
            Player jugador = (Player) sender;

            String nombre = args[1];
            String texto;
            int duracion = 3;

            try {
                duracion = Integer.parseInt(args[args.length - 1]);
                texto = String.join(" ", Arrays.copyOfRange(args, 2, args.length -1 ));
            }

            catch (NumberFormatException e) {
                texto = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            }

            texto = ChatColor.translateAlternateColorCodes('&', PlaceholderReplacer.aplicar((Player) sender, texto));

            GestorHotbar.crear(nombre, texto, duracion, jugador);
            sender.sendMessage(ChatColor.GREEN + "HotBar " + ChatColor.YELLOW + nombre + ChatColor.GREEN + " creado por " + ChatColor.YELLOW + duracion + ChatColor.GREEN + "s.");
            return true;
        }

        if (sub.equals("mostrar")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
                return true;
            }

            String nombre = args[1];
            String objetivo = args[2];

            if (!GestorHotbar.existe(nombre)) {
                sender.sendMessage(ChatColor.RED + "La hotbar " + ChatColor.GOLD + nombre + ChatColor.RED + " no existe.");
                return true;
            }

            HotbarData hotbar = GestorHotbar.obtener(nombre);
            String textoo = hotbar.getTexto();
            if (objetivo.equalsIgnoreCase("todos")) {
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    String rrr = PlaceholderReplacer.aplicar(jugador, textoo);
                    TextComponent texto = new TextComponent(rrr);
                    mostrarHotBar(jugador, texto, hotbar.getDuracion(), nombre);
                }
            }
            else {
                Player jugador = Bukkit.getPlayerExact(objetivo);
                if (jugador == null) {
                    sender.sendMessage(ChatColor.RED + "Jugador no encontrado.");
                    return true;
                }
                String rrr = PlaceholderReplacer.aplicar(jugador, textoo);
                TextComponent texto = new TextComponent(rrr);
                mostrarHotBar(jugador, texto, hotbar.getDuracion(), nombre);
            }
            return true;
        }

        if (sub.equals("eliminar")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
                return true;
            }

            GestorHotbar.eliminar(args[1]);
            sender.sendMessage(ChatColor.YELLOW + "HotBar eliminada.");
            return true;
        }
        return false;
    }

    private void mostrarHotBar(Player jugador, TextComponent texto, int duracion, String nombre) {
        new BukkitRunnable() {
            int tiempo = duracion;
            public void run() {
                if (tiempo <= 0) cancel();
                if (!GestorHotbar.existe(nombre)) cancel();
                jugador.spigot().sendMessage(ChatMessageType.ACTION_BAR, texto);
                tiempo--;
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }
}
