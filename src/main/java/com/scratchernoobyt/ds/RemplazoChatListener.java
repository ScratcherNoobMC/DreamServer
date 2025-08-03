package com.scratchernoobyt.ds;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;
import com.scratchernoobyt.noobicore.util.PlaceholderReplacer;

public class RemplazoChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent evento) {
        Player jugador = evento.getPlayer();
        String mensaje = evento.getMessage();

        String mensajeProcesado = PlaceholderReplacer.aplicar(jugador, mensaje);

        evento.setMessage(mensajeProcesado);
    }
    
}
