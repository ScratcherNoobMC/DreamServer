package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.regex.Pattern;

public class FiltroPalabrasAvanzado implements Listener {

    private final JavaPlugin plugin;

    public FiltroPalabrasAvanzado(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent evento) {
        FileConfiguration config = plugin.getConfig();

        if (!config.getBoolean("bloqueo-palabras.activado")) return;

        String mensaje = normalizarTexto(limpiarTexto(evento.getMessage()));
        List <String> lista = config.getStringList("bloqueo-palabras.lista");

        for (String prohibida : lista) {
            String regex = generarRegexVariaciones(prohibida);
            if (Pattern.compile(regex).matcher(mensaje).find()) {
                evento.setCancelled(true);
                String advertencia = config.getString(
                    "bloqueo-palabras.mensaje",
                    "&c¡Lenguaje no permitido!"
                );
                evento.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', advertencia));
                return;
            }
        }
    }

    private String generarRegexVariaciones(String palabra) {
        StringBuilder regex = new StringBuilder();
        for (char letra : palabra.toLowerCase().toCharArray()) {
            regex.append(Pattern.quote(String.valueOf(letra))).append("[^a-zA-Z0-9]{0,2}?");
        }
        return regex.toString();
    }

    private String normalizarTexto(String texto) {
        return texto
            .toLowerCase()
            .replace("0", "o")
            .replace("1", "i")
            .replace("3", "e")
            .replace("2", "s")
            .replace("4", "a")
            .replace("5", "s")
            .replace("6", "g")
            .replace("7", "t")
            .replace("@", "a");
    }

    private String limpiarTexto(String texto) {
        return texto
            .toLowerCase()
            .replaceAll("[^a-z0-9@]", "");
    }
}
