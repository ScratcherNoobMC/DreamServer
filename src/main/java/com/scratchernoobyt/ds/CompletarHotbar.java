package com.scratchernoobyt.ds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class CompletarHotbar implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("crear", "eliminar", "mostrar");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("crear")) {
                return Arrays.asList("<nombre>");
            }

            else if (args[0].equalsIgnoreCase("eliminar")) {
                return Arrays.asList("<nombre>");
            }

            else if (args[0].equalsIgnoreCase("mostrar")) {
                return Arrays.asList("<nombre>");
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("crear")) {
                return Arrays.asList("<texto>");
            }

            else if (args[0].equalsIgnoreCase("mostrar")) {
                return Arrays.asList("<jugador>", "todos");
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("crear")) {
                return Arrays.asList("<duracion(s)>");
            }
        }

        return Collections.emptyList();
    }
    
}
