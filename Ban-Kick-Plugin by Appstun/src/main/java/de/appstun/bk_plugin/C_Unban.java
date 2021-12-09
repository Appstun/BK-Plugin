package de.appstun.bk_plugin;

import de.appstun.bk_plugin.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class C_Unban implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.isOp()) {
                if(args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    UUID uuid = target.getUniqueId();
                    delBannedPlayer(target, p, uuid);
                } else {
                    p.sendMessage(main.prefix + "§fBitte nutze §9§l/kick <[Spieler]>");
                }
            }
        } else {
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                UUID uuid = target.getUniqueId();
                delBannedPlayer2(target, sender, uuid);
            } else {
                sender.sendMessage(main.prefix + "§fBitte nutze §9§l/kick <[Spieler]>");
            }
        }
        return false;
    }

    public static void delBannedPlayer(OfflinePlayer name, Player p,UUID uuid) {
        File ordner = new File(main.ornderpath + "//Banned");
        File file = new File(main.ornderpath + "//Banned//" + uuid + ".yml");
        if (!ordner.exists()) {
            ordner.mkdir();
        }
        if (file.exists()) {
            file.delete();
            for(Player p_o : Bukkit.getOnlinePlayers()) {
                if (p_o.isOp()) {
                    p_o.sendMessage(main.prefix + "§l§4UNBANN §r§7>> §a" + name.getName() + " wurde von " + p.getName() + " entbannt.");
                }
            }
            Bukkit.getConsoleSender().sendMessage(main.prefix + "§l§4UNBANN §r§7>> §6" + name.getName() + " wurde von " + p.getName() + " entbannt.");
        } else {
            p.sendMessage(main.prefix + "Dieser Spieler ist nicht gebannt.");
            return;
        }
    }

    public static void delBannedPlayer2(OfflinePlayer name, CommandSender sender, UUID uuid) {
        File ordner = new File(main.ornderpath + "//Banned");
        File file = new File(main.ornderpath + "//Banned//" + uuid + ".yml");
        if (!ordner.exists()) {
            ordner.mkdirs();
        }
        if (file.exists()) {
            file.delete();
            Bukkit.getConsoleSender().sendMessage(main.prefix + "§l§4UNBANN §r§7>> §6" + name.getName() + " wurde von " + sender.getName() + " entbannt.");
            for(Player p_o : Bukkit.getOnlinePlayers()) {
                if (p_o.isOp()) {
                    p_o.sendMessage(main.prefix + "§l§4UNBANN §r§7>> §a" + name.getName() + " wurde von " + sender.getName() + " entbannt.");
                }
            }
        } else {
            sender.sendMessage(main.prefix + "Dieser Spieler ist nicht gebannt.");
            return;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabcomplete = new ArrayList<String>();
        Player p = (Player) sender;
        if(p.isOp()) {
            if (args.length == 1) {
                File ordner = new File(main.ornderpath + "//Banned");
                for (File file : ordner.listFiles()) {
                    for (OfflinePlayer offp : Bukkit.getOfflinePlayers()) {
                        if (offp.getUniqueId().toString().equals(file.getName().replace(".yml", ""))) {
                            File file2 = new File(main.ornderpath + "//Banned//" + offp.getUniqueId() + ".yml");
                            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file2);
                            String gebannter = cfg.getString("Gebannter");
                            tabcomplete.add(gebannter);
                        }
                    }
                }
            }
        }
        return tabcomplete;
    }
}
