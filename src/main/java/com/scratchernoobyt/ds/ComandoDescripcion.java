package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Particle;
import org.bukkit.Sound;
import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoDescripcion implements CommandExecutor {

    private final JavaPlugin plugin;

    public ComandoDescripcion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String etiqueta, String[] args) {
        FileConfiguration config = plugin.getConfig();
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-jugador", "&cNo eres un jugador >=/")));
            return true;
        }

        if (!jugador.hasPermission("ds.description") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.description")) {
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
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("mensajes.error-no-renombrable", "&cEste item no se puede renombrar.")));
            return true;
        }

        String textoLore = String.join(" ", args);
        textoLore = ChatColor.translateAlternateColorCodes('&', textoLore);

        List<String> lore = Arrays.asList(textoLore);
        meta.setLore(lore);
        enMano.setItemMeta(meta);

        String mensajeRaw = config.getString("mensajes.descripcion-aplicada", "&aLa descripcion de tu item ha cambiado a: &3%lore%&a.");
        mensajeRaw = mensajeRaw.replace("%lore%", textoLore);
        String mensajeTraducido = ChatColor.translateAlternateColorCodes('&', mensajeRaw);

        jugador.sendMessage(mensajeTraducido);
        jugador.getWorld().spawnParticle(
            Particle.GLOW,
            jugador.getLocation().add(0, 1.5, 0),
            30,
            0.5, 0.5, 0.5,
            0
        );
        jugador.playSound(jugador.getLocation(), Sound.ITEM_BOOK_PUT, 1.0f, 1.2f);
        return true;
    }
    
}
