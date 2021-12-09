package de.appstun.bk_plugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class E_OnJoin implements Listener {

    @EventHandler
    public void isBannedQuit(PlayerQuitEvent e) {
        String msg = e.getQuitMessage();
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        File file = new File(main.ornderpath + "//Banned//" + p.getUniqueId() + ".yml");
        if (!file.exists()) {
            e.setQuitMessage(msg);
        }
    }

    @EventHandler
    public void isBannedJoin(PlayerJoinEvent e) {
        String msg = e.getJoinMessage();
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        File file = new File(main.ornderpath + "//Banned//" + p.getUniqueId() + ".yml");
        if (file.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            String banner = cfg.getString("Banner");
            String message = cfg.getString("Begründung");
            String time = cfg.getString("Zeit");
            String date = cfg.getString("Datum");
            e.setJoinMessage(null);
            p.kickPlayer("§4§lBANN \n §r \n §r \n §cDu bist noch von §6" + banner + "\n§cseit dem §6" + date + " §cum §6" + time + " §cgebannt. \n §5Begründung: §r" + message);
            e.setJoinMessage(null);
            for(Player p_o : Bukkit.getOnlinePlayers()) {
                if (p_o.isOp()) {
                    p_o.sendMessage(main.prefix + "§eDer Gebannte Spieler " + p.getName() + " hat versucht zu joinen.");
                }
            }
        } else {
            e.setJoinMessage(msg);
        }
    }
}
