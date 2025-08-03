package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Particle;
import org.bukkit.Sound;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;


public class ComandoRenombrar implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoRenombrar(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String etiqueta, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.rename") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.rename")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        if (args.length == 0) {
            jugador.sendMessage(ChatColor.RED + "Uso incorrecto: Porfavor use /ds ayuda");
            return true;
        }

        ItemStack enMano = jugador.getInventory().getItemInMainHand();

        if (enMano == null || enMano.getType() == Material.AIR) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-material", "&cNo tienes ningun material en la mano.")));
            return true;
        }

        ItemMeta meta = enMano.getItemMeta();

        if (meta == null) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-renombrable", "&cEste item no se puede renombrar")));
            return true;
        }

        String nuevoNombre = String.join(" ", args);
        nuevoNombre = ChatColor.translateAlternateColorCodes('&', nuevoNombre);

        meta.setDisplayName(nuevoNombre);
        enMano.setItemMeta(meta);

        String mensajeRaw = config.getString("mensajes.renombrado", "&aTu item ha sido renombrado a: &5%display_name%&a.");
        mensajeRaw = mensajeRaw.replace("%display_name%", nuevoNombre);
        String mensajeTraducido = ChatColor.translateAlternateColorCodes('&', mensajeRaw);
        jugador.sendMessage(mensajeTraducido);
        jugador.getWorld().spawnParticle(
            Particle.ENCHANT,
            jugador.getLocation().add(0, 1.5, 0),
            30,
            0.5, 0.5, 0.5,
            0
        );
        jugador.playSound(jugador.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.2f);
        return true;
    }
}
