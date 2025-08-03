package com.scratchernoobyt.ds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.*;

public class CompletarMundo implements TabCompleter {
    
    @Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            List<String> mundos = new ArrayList<>();

            for (World mundo : Bukkit.getWorlds()) {
                mundos.add(mundo.getName());
            }

            return mundos;
        }

        return Collections.emptyList();
    }
}
