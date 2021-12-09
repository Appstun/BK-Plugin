package de.appstun.bk_plugin;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public final class main extends JavaPlugin {

    private static main plugin;
    private int banned_func;
    public static String ornderpath = "plugins//Ban-Kick-Plugin";
    public static String pluginname = "§7[ §6BK-Plugin §7] §r";
    public static String prefix = main.pluginname + "§r";
    public static String is_console = main.pluginname + "§cDu bist kein Spieler.";
    public static String no_perm = main.pluginname + "§cDu hast dafür keine Rechte.";
    public static String target_no_found = main.pluginname + "§cAngegebener Spieler nicht gefunden.";
    public static String target_isop = main.pluginname + "§cDen Spieler kannst du nicht nehmen.";
    public static String target_is_self = main.pluginname + "§cDu kannst dich nicht selbst verwenden.";

    public static main getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        FileConfiguration cfg = this.getConfig();
        if (!(cfg.getString("PluginPrefix") == null)) {
            prefix = cfg.getString("PluginPrefix");
            assert prefix != null;
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            prefix = prefix + " §r";
        }

        banned();

        Bukkit.getPluginManager().registerEvents(new E_OnJoin(), this);
        getCommand("ban").setExecutor(new C_Ban());
        getCommand("banlist").setExecutor(new C_Banlist());
        getCommand("kick").setExecutor(new C_Kick());
        getCommand("unban").setExecutor(new C_Unban());
        getCommand("unban").setTabCompleter(new C_Unban());
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(banned_func);
    }

    public void banned() {
        banned_func = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getPlugin(), new Runnable() {
            int count = 0;
            @Override
            public void run() {
                for (Player p_o : Bukkit.getOnlinePlayers()) {
                    File file = new File(main.ornderpath + "//Banned//" + p_o.getUniqueId() + ".yml");
                    if(file.exists()) {
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        String banner = cfg.getString("Banner");
                        String message = cfg.getString("Begründung");
                        String time = cfg.getString("Zeit");
                        String date = cfg.getString("Datum");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                p_o.kickPlayer("§4§lBANN \n §r \n §r \n §cDu bist noch von §6" + banner + "\n§cseit dem §6" + date + " §cum §6" + time + " §cgebannt. \n §5Begründung: §r" + message);
                            }
                        }.runTaskLater(main.getPlugin(main.class), 20);
                    }
                }
            }
        }, 0, 20*30);
    }
}
