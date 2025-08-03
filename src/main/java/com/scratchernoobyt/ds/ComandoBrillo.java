package com.scratchernoobyt.ds;

import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Color;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoBrillo implements CommandExecutor {

    public final JavaPlugin plugin;

    public ComandoBrillo(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.glow") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.glow")) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        ItemStack item = jugador.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-material", "&cNo tienes ningun material en la mano.")));
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-tener-brillo", "&cEste item no puede tener brillo.")));
            return true;
        }

        boolean tieneBrillo = item.containsEnchantment(Enchantment.UNBREAKING);

        if (tieneBrillo) {
            meta.removeEnchant(Enchantment.UNBREAKING);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.brillo_off")));
        }

        else {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.brillo_on", "&aBrillo activado")));
        }

        item.setItemMeta(meta);

        DustOptions jeje = new DustOptions(Color.fromRGB(255, 105, 180), 1.0F);
        jugador.getWorld().spawnParticle(
            Particle.DUST,
            jugador.getLocation().add(0, 1, 0),
            20,
            0.3, 0.3, 0.3,
            0,
            jeje
        );
        jugador.playSound(jugador.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 1.2f);
        return true;
    }
    
}
