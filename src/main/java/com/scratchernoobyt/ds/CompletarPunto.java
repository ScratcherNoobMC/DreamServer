package com.scratchernoobyt.ds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class CompletarPunto implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("crear", "eliminar", "punto");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("crear")) {
                return Arrays.asList("<nombre>");
            }
            
            else if (args[0].equalsIgnoreCase("eliminar")) {
                return Arrays.asList("<punto>");
            }
        }

        return Collections.emptyList();
    }
}
