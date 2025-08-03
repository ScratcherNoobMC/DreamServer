package com.scratchernoobyt.ds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.*;

public class CompletarPrefijo implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("set", "ver", "eliminar", "permiso");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                List<String> jugadores = new ArrayList<>();
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    jugadores.add(jugador.getName());
                }
                return jugadores;
            }

            else if (args[0].equalsIgnoreCase("ver")) {
                return Arrays.asList("<jugador>");
            }

            else if (args[0].equalsIgnoreCase("eliminar")) {
                List<String> jugadores = new ArrayList<>();
                for (Player jugador : Bukkit.getOnlinePlayers()) {
                    jugadores.add(jugador.getName());
                }
                return jugadores;
            }

            else if (args[0].equalsIgnoreCase("permiso")) {
                return Arrays.asList("<prefijo>");
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                return Arrays.asList("<prefijo>");
            }

            else if (args[0].equalsIgnoreCase("permiso")) {
                return Arrays.asList("<permiso>");
            }
        }

        return Collections.emptyList();
    }
    
}
