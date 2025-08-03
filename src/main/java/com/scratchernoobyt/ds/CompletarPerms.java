package com.scratchernoobyt.ds;

import org.bukkit.command.TabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;


public class CompletarPerms implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("dar", "quitar", "lista");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("dar")) {
                List<String> jugadores = new ArrayList<>();
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    jugadores.add(jugador.getName());
                }
                return jugadores;
            }
            else if (args[0].equalsIgnoreCase("quitar")) {
                List<String> jugadores = new ArrayList<>();
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    jugadores.add(jugador.getName());
                }
                return jugadores;
            }

            else if (args[0].equalsIgnoreCase("lista")) {
                List<String> jugadores = new ArrayList<>();
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    jugadores.add(jugador.getName());
                }
                return jugadores;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("dar")) {
                return Arrays.asList("<permiso>");
            }

            else if (args[0].equalsIgnoreCase("quitar")) {
                return Arrays.asList("<permiso>");
            }
        }

        return Collections.emptyList();
    }
    
}
