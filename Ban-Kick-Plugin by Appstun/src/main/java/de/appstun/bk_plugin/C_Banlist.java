package de.appstun.bk_plugin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class C_Banlist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.isOp()) {
                int banned_num = 0;
                File ordner = new File(main.ornderpath + "//Banned");
                p.sendMessage(main.prefix + "§aAlle Gebannten Spieler:");
                for (File file : ordner.listFiles()) {
                    for (OfflinePlayer offp : Bukkit.getOfflinePlayers()) {
                        if(offp.getUniqueId().toString().equals(file.getName().replace(".yml", ""))) {
                            banned_num += 1;
                            File file2 = new File(main.ornderpath + "//Banned//" + offp.getUniqueId() + ".yml");
                            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file2);
                            String gebannter = cfg.getString("Gebannter");
                            String banner = cfg.getString("Banner");
                            String message = cfg.getString("Begründung");
                            String time = cfg.getString("Zeit");
                            String date = cfg.getString("Datum");
                            p.sendMessage(main.prefix + "§6" + gebannter + " §7~ UUID: " + offp.getUniqueId());
                            p.sendMessage(main.prefix + "§1     Banner: §9" + banner + " §1Begründung: §9" + message);
                            p.sendMessage(main.prefix + "§1     Zeit: §9" + time + " §1Datum: §9" + date);
                        }
                    }
                }
                if (banned_num > 0) {
                    p.sendMessage(main.prefix + "§7Es sind insgesamt " + banned_num + " Spieler gebannt.");
                } else {
                    p.sendMessage(main.prefix + "§cEs gibt noch keine gebannten Spieler");
                }
            }
        } else {
            sender.sendMessage(main.is_console);
        }
        return false;
    }
}
