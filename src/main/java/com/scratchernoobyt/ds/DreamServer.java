package com.scratchernoobyt.ds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.Runnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect;
import org.bukkit.Color;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import com.scratchernoobyt.noobicore.gestores.GestorPermisos;


public class DreamServer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            getLogger().severe("+===========================================================+");
            getLogger().severe("Se ha detectado un /reload. Este plugin no es compatible.");
            getLogger().severe("Esto puede causar errores o cuelges aleatorios.");
            getLogger().severe("Por favor, si quiere recargar use: /ds recargar");
            getLogger().severe("El plugin sera desactivado por seguridad.");
            getLogger().severe("+===========================================================+");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        getLogger().info("+-----------------DreamServer----------------+");
        getLogger().info("  · Version: " + getConfig().getString("version"));
        getLogger().info("  · Autor: ScratcherNoobYT                    ");
        getLogger().info("+-----------------Iniciando------------------+");
        getLogger().info("  ⚙️Cargando configuracion...                 ");
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("  ✅Configuracion cargada.                     ");
        getLogger().info("  📡Cargando comandos...                       ");
        GestorPermisos gestor = new GestorPermisos(this);
        this.getCommand("perms").setExecutor(new ComandoPerms(gestor, this));
        this.getCommand("ds").setExecutor(new ComandoDS(this));
        this.getCommand("tiempo").setExecutor(new ComandoTiempo(this));
        this.getCommand("clima").setExecutor(new ComandoClima(this));
        this.getCommand("puntoinicial").setExecutor(new ComandoPuntoInicial(this));
        this.getCommand("inicio").setExecutor(new ComandoInicio(this));
        this.getCommand("mundo").setExecutor(new ComandoMundo(this));
        this.getCommand("test").setExecutor(new ComandoTest(this));
        this.getCommand("cama").setExecutor(new ComandoCama(this));
        this.getCommand("punto").setExecutor(new ComandoPunto(this));
        this.getCommand("reglas").setExecutor(new ComandoReglas(this));
        this.getCommand("renombrar").setExecutor(new ComandoRenombrar(this));
        this.getCommand("descripcion").setExecutor(new ComandoDescripcion(this));
        this.getCommand("nombre").setExecutor(new ComandoNombre(this));
        this.getCommand("brillo").setExecutor(new ComandoBrillo(this));
        this.getCommand("hotbar").setExecutor(new ComandoHotbar(this));
        this.getCommand("prefijo").setExecutor(new ComandoPrefijo(this));
        getLogger().info("  ✅Comandos cargados.                         ");
        getLogger().info("  🔈Escuchando eventos...                      ");
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new RespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new TNTListener(this), this);
        getServer().getPluginManager().registerEvents(new CrystalListener(this), this);
        getServer().getPluginManager().registerEvents(new CreeperListener(this), this);
        getServer().getPluginManager().registerEvents(new RemplazoChatListener(), this);
        getLogger().info("  ✅Eventos escuchados.                        ");
        getLogger().info("  ✏️Aplicando configuracion...                ");
        if (getConfig().getBoolean("antilag.activado", true)) {
            int intervalo = getConfig().getInt("antilag.segundos", 120);
            long intervaloTicks = 20L * intervalo;

            Bukkit.getScheduler().runTaskTimer(this, () -> {
                int contador = 0;
                for (World world : Bukkit.getWorlds()) {
                    for (org.bukkit.entity.Entity entity : world.getEntities()) {
                        if (entity instanceof org.bukkit.entity.Item || entity instanceof org.bukkit.entity.ExperienceOrb) {
                            entity.remove();
                            contador++;
                        }
                    }
                }
                if (contador > 0) {
                    getLogger().info("⚙️ Antilag: Se eliminaron " + contador + " entidades innecesarias");
                }
            }, intervaloTicks, intervaloTicks);
        }
        if (getConfig().getBoolean("antilag.monitorearping", true)) {
            iniciarMonitoreoLag();
        }
        getLogger().info("  ✅Configuracion aplicada.                    ");
        getLogger().info("  📋Cargando reglas del servidor...            ");
        recargarReglas();
        getLogger().info("  ✅Reglas cargadas.                           ");
        getLogger().info("  🛡️Cargando DreamShield...                   ");
        getServer().getPluginManager().registerEvents(new FiltroPalabrasAvanzado(this), this);
        getLogger().info("  ✅DreamShield cargado.                       ");
        getLogger().info("  ✔️Cargando auto-completar....               ");
        this.getCommand("mundo").setTabCompleter(new CompletarMundo());
        this.getCommand("clima").setTabCompleter(new CompletarClima());
        this.getCommand("tiempo").setTabCompleter(new CompletarTiempo());
        this.getCommand("renombrar").setTabCompleter(new CompletarRenombrar());
        this.getCommand("descripcion").setTabCompleter(new CompletarDescripcion());
        this.getCommand("prefijo").setTabCompleter(new CompletarPrefijo());
        this.getCommand("perms").setTabCompleter(new CompletarPerms());
        this.getCommand("punto").setTabCompleter(new CompletarPunto());
        this.getCommand("nombre").setTabCompleter(new CompletarNombre());
        this.getCommand("hotbar").setTabCompleter(new CompletarHotbar());
        this.getCommand("ds").setTabCompleter(new CompletarDS());
        getLogger().info("  ✅Auto-Completar cargado."                   );
        getLogger().info("+--------------------------------------------+");
        getLogger().info("🎉DreamServer ha sido activado correctamente.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Guardando archivo de configuracion...");
        saveConfig();
        getLogger().info("DreamServer ha sido desactivado correctamente.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Boolean entradaHabilitada = getConfig().getBoolean("mensajes.activar-entrada", true);
        Boolean usarDefault = getConfig().getBoolean("mensajes.entrada-default", false);
        String mensajeEntrada = getConfig().getString("mensajes.mensaje-entrada", "&f&l[&a&l+&f&l] &r&6%player%");
        Boolean tituloHabilitado = getConfig().getBoolean("mensajes.activar-titulo", true);
        String mensajeTitulo = getConfig().getString("mensajes.mensaje-titulo", "&a¡Bienvenid@!");
        String mensajeSubtitulo = getConfig().getString("mensajes.mensaje-subtitulo", "&e%player%&5, estas listo.");
        mensajeEntrada = mensajeEntrada.replace("%player%", p.getName());
        mensajeSubtitulo = mensajeSubtitulo.replace("%player%", p.getName());
        mensajeEntrada = ChatColor.translateAlternateColorCodes('&', mensajeEntrada);
        mensajeTitulo = ChatColor.translateAlternateColorCodes('&', mensajeTitulo);
        mensajeSubtitulo = ChatColor.translateAlternateColorCodes('&', mensajeSubtitulo);

        if (getConfig().getBoolean("efectos.cohete-bienvenida", true)) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder()
                        .withColor(Color.AQUA)
                        .withFade(Color.LIME)
                        .with(FireworkEffect.Type.BALL_LARGE)
                        .flicker(true)
                        .trail(true)
                        .build());
                meta.setPower(1);
                firework.setFireworkMeta(meta);
                if (getConfig().getBoolean("efectos.sonido-bienvenida", true)) {
                    p.playSound(p.getLocation(), "entity.player.levelup", 1.0f, 1.0f);
                }
            }, 20L);
        }

        if (tituloHabilitado) {
            p.sendTitle(mensajeTitulo, mensajeSubtitulo, 10, 70, 20);
        }
        
        if (!entradaHabilitada && !usarDefault) {
            event.setJoinMessage(null);
        }

        else if (!usarDefault && entradaHabilitada) {
            event.setJoinMessage(mensajeEntrada);
        }

        else if (usarDefault && entradaHabilitada) {
            event.setJoinMessage(null);
            getLogger().severe("No se ha podido enviar el mensaje de entrada porque hay dos tipos de mensajes habilitados. Por favor revise config.yml");
            return;
        }

        else if (usarDefault && !entradaHabilitada) {
            return;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Boolean salidaHabilitada = getConfig().getBoolean("mensajes.activar-salida", true);
        Boolean usarDefault = getConfig().getBoolean("mensajes.salida-default", false);
        String mensajeSalida = getConfig().getString("mensajes.mensaje-salida", "&f&l[&4&l-&f&l] &r&6%player%");
        mensajeSalida = mensajeSalida.replace("%player%", p.getName());
        mensajeSalida = ChatColor.translateAlternateColorCodes('&', mensajeSalida);

        if (!salidaHabilitada && !usarDefault) {
            event.setQuitMessage(null);
        }

        else if (!usarDefault && salidaHabilitada) {
            event.setQuitMessage(mensajeSalida);
        }

        else if (usarDefault && salidaHabilitada) {
            event.setQuitMessage(null);
            getLogger().severe("No se ha podido enviar el mensaje de salida porque hay dos tipos de mensajes habilitados. Por favor revise config.yml");
            return;
        }

        else if (usarDefault && !salidaHabilitada) {
            return;
        }
    }

    public void iniciarMonitoreoLag() {
        Plugin plugin = this;

        Bukkit.getScheduler().runTaskTimer(plugin, new java.lang.Runnable() {
            @Override
            public void run() {
                final long start = System.currentTimeMillis();

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run(){
                        long lag = System.currentTimeMillis() - start;
                        if (lag > 50) {
                            Bukkit.broadcastMessage(ChatColor.RED + "[DreamServer] Tu servidor va muy lag, tienes mucho ping: " + lag + "ms. Por favor arregla eso!");
                            getLogger().warning("Lag detectado: " + lag + "ms");
                        }
                    }
                }); 
            }
        }, 0L, 20L);
    }

    public void checkearMemoria() {
        long max = Runtime.getRuntime().maxMemory();
        long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double usageRatio = (double) used / max;

        if (usageRatio > 0.85) {
            getLogger().warning(ChatColor.RED + "⚠️Memoria baja, se activara el modo optimizado.");
            for (World world : Bukkit.getWorlds()) {
                for (org.bukkit.entity.Entity entity : world.getEntities()) {
                    if (entity instanceof org.bukkit.entity.Item || entity instanceof org.bukkit.entity.ExperienceOrb) {
                        entity.remove();
                    }
                }
            }
            int radio = 5;

            for (World mundo : Bukkit.getWorlds()) {
                for (Player jugador : mundo.getPlayers()) {
                    int centroX = jugador.getLocation().getChunk().getX();
                    int centroZ = jugador.getLocation().getChunk().getZ();

                    for (int x = -radio; x <= radio; x++) {
                        for (int z = -radio; z <= radio; z++) {
                            int chunkX = centroX + x;
                            int chunkZ = centroZ + z;

                            if (mundo.isChunkLoaded(chunkX, chunkZ)) {
                                boolean descargado = mundo.unloadChunk(chunkX, chunkZ, true);
                            }
                        }
                    }
                }
            }
        }
    }

    public class TNTListener implements Listener {

        private final JavaPlugin plugin;

        public TNTListener(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onTNTExplode(EntityExplodeEvent event) {
            Entity entidad = event.getEntity();
            if (entidad instanceof TNTPrimed tnt) {
                boolean permitirTNT = plugin.getConfig().getBoolean("explosivos.activar-tnt");

                if (!permitirTNT) {
                    event.setCancelled(true);
                    Bukkit.getLogger().info("🛡️ Explosión de TNT bloqueada por configuración.");
                    
                    String mensaje = plugin.getConfig().getString("mensajes.no-tnt", "&cNo esta permitido lanzar TNT.");
                    String mensajeTraducido = ChatColor.translateAlternateColorCodes('&', mensaje);

                    for (Player jugador : tnt.getWorld().getPlayers()) {
                        if (jugador.getLocation().distance(tnt.getLocation()) < 10) {
                            jugador.sendMessage(mensajeTraducido);
                            jugador.playSound(jugador.getLocation(), org.bukkit.Sound.BLOCK_ANVIL_BREAK, 1.0f, 0.8f);
                            DustOptions opcionesPolvo = new DustOptions(Color.RED, 1.5F);
                            jugador.spawnParticle(Particle.DUST, tnt.getLocation(), 50, 2, 2, 2, 1, opcionesPolvo);
                        }
                    }
                }
            }
        }
    }

    public class CrystalListener implements Listener {

        private final JavaPlugin plugin;

        public CrystalListener(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onCrystalDamage(EntityDamageEvent event) {
            if (event.getEntity() instanceof EnderCrystal crystal) {
                boolean permitirCristal = plugin.getConfig().getBoolean("explosivos.activar-cristales", true);

                if (!permitirCristal) {
                    event.setCancelled(true);
                    crystal.remove();

                    String mensajeRaw = plugin.getConfig().getString("mensajes.no-cristales", "&cNo esta permitido explotar cristales.");
                    String mensajeTraducido = ChatColor.translateAlternateColorCodes('&', mensajeRaw);

                    DustOptions polvoFucsia = new DustOptions(Color.FUCHSIA, 1.5F);

                    for (Player jugador : crystal.getWorld().getPlayers()) {
                        if (jugador.getLocation().distance(crystal.getLocation()) < 10) {
                            jugador.sendMessage(mensajeTraducido);
                            jugador.playSound(jugador.getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0f, 0.8f);
                            jugador.spawnParticle(Particle.DUST, crystal.getLocation(), 50, 2, 2, 2, 1, polvoFucsia);
                        }
                    }
                }

            }
        }
    }

    public class CreeperListener implements Listener {
        private final JavaPlugin plugin;

        public CreeperListener(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onCreeperExplode(EntityExplodeEvent event) {
            Entity entidad = event.getEntity();

            if (entidad instanceof Creeper creeper) {
                boolean permitirCreeper = plugin.getConfig().getBoolean("explosivos.activar-creepers", true);

                if (!permitirCreeper) {
                    event.setCancelled(true);
                    creeper.remove();
                    
                    String mensajeRaw = plugin.getConfig().getString("mensajes.no-creepers", "&cNo esta permitido explotar creepers.");
                    String mensajeTraducido = ChatColor.translateAlternateColorCodes('&', mensajeRaw);

                    DustOptions polvoVerde = new DustOptions(Color.LIME, 1.5f);

                    for (Player jugador : creeper.getWorld().getPlayers()) {
                        if (jugador.getLocation().distance(creeper.getLocation()) < 10) {
                            jugador.sendMessage(mensajeTraducido);
                            jugador.playSound(jugador.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 0.8f);
                            jugador.spawnParticle(Particle.DUST, creeper.getLocation(), 50, 2, 2, 2, 1, polvoVerde);
                        }
                    }
                }
            }
        }
    }

    public void recargarReglas() {
        File archivoReglas = new File(getDataFolder(), "reglas.txt");

        if (!archivoReglas.exists()) {
            try {
                getDataFolder().mkdirs();
                archivoReglas.createNewFile();

                try (PrintWriter writer = new PrintWriter(archivoReglas)) {
                    writer.println("&eReglas del servidor:");
                    writer.println("&f1. No hacer trampas.");
                    writer.println("&f2. Respeta a los demas.");
                    writer.println("&f3. No usar hacks.");
                    writer.println("&f4. Diviertete con responsabilidad.");
                }

                getLogger().info("Archivos reglas.txt creado correctamente.");
            } catch (IOException e) {
                getLogger().severe("No se pudo crear reglas.txt");
                e.printStackTrace();
            }
        } else {
            getLogger().info("📜 reglas.txt ya existe y no se ha sobrescrito.");
        }
    }

}
