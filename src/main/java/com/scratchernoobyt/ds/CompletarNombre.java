package com.scratchernoobyt.ds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class CompletarNombre implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("<nombre>");
        }

        return Collections.emptyList();
    }
}
