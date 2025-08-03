package com.scratchernoobyt.ds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

import com.scratchernoobyt.noobicore.gestores.GestorPrefijosYaml;

public class ComandoDS implements CommandExecutor  {

    private final DreamServer plugin;

    public ComandoDS(DreamServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player jugador = (Player) sender;

        if (!sender.hasPermission("ds.principalcommand") && !GestorPrefijosYaml.tienePermiso(jugador, "ds.principalcommand")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mensajes.error-permisos", "&cNo tienes permisos para esto!")));
            return true;
        }

        String mensajeIncorrecto = plugin.getConfig().getString("mensajes.comando-incorrecto", "&cComando incorrecto, porfavor use: /ds ayuda");
        mensajeIncorrecto = ChatColor.translateAlternateColorCodes('&', mensajeIncorrecto);

        if (args.length == 0) {
            sender.sendMessage(mensajeIncorrecto);
            return true;
        }

        String subcomando = args[0].toLowerCase();

        switch (subcomando) {
            case "ayuda":
                sender.sendMessage(ChatColor.AQUA + "Comandos disponibles:");
                sender.sendMessage(ChatColor.GREEN + "/ds ayuda: Muestra los comandos");
                sender.sendMessage(ChatColor.GREEN + "/ds recargar: Recarga el plugin");
                sender.sendMessage(ChatColor.GREEN + "/ds version: Verifica la version del plugin");
                sender.sendMessage(ChatColor.GREEN + "/tiempo: cambia el tiempo mediante los parametros: dia|noche|atardecer|amanecer|mediodia|medianoche");
                sender.sendMessage(ChatColor.GREEN + "/clima: cambia el clima mediante los parametros: soleado|lluvioso|tormentoso");
                sender.sendMessage(ChatColor.GREEN + "/puntoinicial: Selecciona donde van a aparecer los jugadores al morir.");
                sender.sendMessage(ChatColor.GREEN + "/inicio: Teltransportate al punto inicial.");
                sender.sendMessage(ChatColor.GREEN + "/mundo: Permite cambiar de mundo con solo introducir el nombre");
                sender.sendMessage(ChatColor.GREEN + "/test: Muestra el estado del servidor.");
                sender.sendMessage(ChatColor.GREEN + "/cama: Teletransportate a tu cama.");
                sender.sendMessage(ChatColor.GREEN + "/punto: Gestiona puntos: <crear|eliminar|nombre>");
                sender.sendMessage(ChatColor.GREEN + "/reglas: Mira las reglas definidas en el archivo reglas.txt");
                sender.sendMessage(ChatColor.GREEN + "/renombrar: Renombra el objeto en tu mano con /renombrar <nombre>");
                sender.sendMessage(ChatColor.GREEN + "/descripcion: Cambia la descripcion del objeto en tu mano con /descripcion <texto>");
                sender.sendMessage(ChatColor.GREEN + "/nombre: Cambia tu nombre (Solo estético) con /nombre <nombre>");
                sender.sendMessage(ChatColor.GREEN + "/brillo: Haz brillar al objeto en tu mano");
                sender.sendMessage(ChatColor.GREEN + "/perms: Gestiona permisos por jugador con /perms <dar|quitar|lista> <nick> [permiso]");
                sender.sendMessage(ChatColor.GREEN + "/hotbar: Muy largo, usa /ds hotbar");
                TextComponent mensaje = new TextComponent("Siguiente pagina");
                mensaje.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                mensaje.setBold(true);
                mensaje.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ds ayuda2"));
                mensaje.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click Izquierdo para siguiente pagina!")));
                sender.spigot().sendMessage(mensaje);
                break;
            case "recargar":
                plugin.reloadConfig();
                plugin.recargarReglas();
                sender.sendMessage(ChatColor.GREEN + "Configuracion recargada!");
                break;
            case "version":
                sender.sendMessage(ChatColor.RED + "+----------------+");
                sender.sendMessage(ChatColor.GOLD + "   " + plugin.getConfig().getString("version"));
                sender.sendMessage(ChatColor.RED + "+----------------+");
                break;
            case "hotbar":
                sender.sendMessage(ChatColor.YELLOW + "+-----------------HotBar-----------------------+");
                sender.sendMessage(ChatColor.GOLD + "/hotbar crear <nombre> <texto> [duracion]");
                sender.sendMessage(ChatColor.GOLD + "/hotbar eliminar <nombre>");
                sender.sendMessage(ChatColor.GOLD + "/hotbar mostrar <nombre> <jugador/todos>");
                sender.sendMessage(ChatColor.YELLOW + "+----------------------------------------------+");
                break;
            case "ayuda2":
                sender.sendMessage(ChatColor.GREEN + "/prefijo: <set|ver|eliminar|permiso> <jugador> [prefijo]");
                sender.sendMessage(ChatColor.GREEN + "/npc: <crear|eliminar> <id> [skinJugador]");
                break;
        }

        return true;
    }
}