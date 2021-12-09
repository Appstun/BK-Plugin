package de.appstun.bk_plugin;

import de.appstun.bk_plugin.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class C_Ban implements CommandExecutor {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMANY);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.isOp()) {
                if(args.length == 1 || args.length == 0) {
                    p.sendMessage(main.prefix + "§fBitte nutze §9§l/ban <[Spieler]> <[Begründung]>");
                } else {
                    OfflinePlayer targetoff = Bukkit.getOfflinePlayer(args[0]);
                    Player targeton = targetoff.getPlayer();
                    if(p == targetoff) {
                        p.sendMessage(main.target_is_self);
                        return true;
                    }
                    if(targetoff.isOp()) {
                        p.sendMessage(main.target_isop);
                        return true;
                    }
                    String m = args[1];
                    for (int i = 2; i < args.length; i++) {
                        m = m + " " + args[i];
                    }
                    try {
                        setBannedPlayer(targetoff.getUniqueId(), p, targetoff, targeton, m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if(args.length == 1 || args.length == 0) {
                sender.sendMessage(main.prefix + "§fBitte nutze §6§l/ban <[Spieler]> <[Begründung]>");
            } else {
                OfflinePlayer targetoff = Bukkit.getOfflinePlayer(args[0]);
                Player targeton = targetoff.getPlayer();
                String m = args[1];
                for (int i = 2; i < args.length; i++) {
                    m = m + " " + args[i];
                }
                try {
                    setBannedPlayer2(targetoff.getUniqueId(), sender, targetoff, targeton, m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void setBannedPlayer(UUID uuid, Player p, OfflinePlayer targetoff, Player targeton, String message) throws IOException {
        ZonedDateTime zoneNow = ZonedDateTime.now(TimeZone.getTimeZone("Europe/Berlin").toZoneId());
        String time = TIME_FORMATTER.format(zoneNow);
        String date = DATE_FORMATTER.format(zoneNow);
        File ordner = new File(main.ornderpath + "//Banned");
        File file = new File(main.ornderpath + "//Banned//" + uuid + ".yml");
        if (!ordner.exists()) {
            ordner.mkdir();
        }
        if (!file.exists()) {
            file.createNewFile();
        } else {
            p.sendMessage(main.prefix + "§cDieser Spieler ist schon gebannt!");
            return;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Gebannter", targetoff.getName());
        cfg.set("Banner", p.getName());
        cfg.set("Begründung", message);
        cfg.set("Zeit", time);
        cfg.set("Datum", date);
        cfg.save(file);
        for(Player p_o : Bukkit.getOnlinePlayers()) {
            if (p_o.isOp()) {
                p_o.sendMessage(main.prefix + "§l§4BANN §r§7>> §a" + targetoff.getName() + " wurde wegen '" + message + "' von " + p.getName() + " gebannt.");
            }
        }
        Bukkit.getConsoleSender().sendMessage(main.prefix + "§4§lBANN §r§7>> §6" + targetoff.getName() + " wurde wegen '" + message + "' von " + p.getName() + " gebannt.");
        if(targetoff.isOnline()) {
            targeton.kickPlayer("§4§lBANN \n §r \n §r \n §cDu wurdest von §6" + p.getName() + " §cgebannt. \n §5Begründung: §r" + message);
        }
    }
    public static void setBannedPlayer2(UUID uuid, CommandSender sender, OfflinePlayer targetoff, Player targeton, String message) throws IOException {
        ZonedDateTime zoneNow = ZonedDateTime.now(TimeZone.getTimeZone("Europe/Berlin").toZoneId());
        String time = TIME_FORMATTER.format(zoneNow);
        String date = DATE_FORMATTER.format(zoneNow);
        File ordner = new File(main.ornderpath + "//Banned");
        File file = new File(main.ornderpath + "//Banned//" + uuid + ".yml");
        if (!ordner.exists()) {
            ordner.mkdir();
        }
        if (!file.exists()) {
            file.createNewFile();
        } else {
            sender.sendMessage(main.prefix + "§c§cDieser Spieler ist schon gebannt!");
            return;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Gebannter", targetoff.getName());
        cfg.set("Banner", "CONSOLE");
        cfg.set("Begründung", message);
        cfg.set("Zeit", time);
        cfg.set("Datum", date);
        cfg.save(file);
        for(Player p_o : Bukkit.getOnlinePlayers()) {
            if (p_o.isOp()) {
                p_o.sendMessage(main.prefix + "§l§4BANN §r§7>> §a" + targetoff.getName() + " wurde wegen '" + message + "' von CONSOLE gebannt.");
            }
        }
        Bukkit.getConsoleSender().sendMessage(main.prefix + "§4§lBANN §r§7>> §6" + targetoff.getName() + " wurde wegen '" + message + "' von CONSOLE gebannt.");
        if(targetoff.isOnline()) {
            targeton.kickPlayer("§4§lBANN \n §r \n §r \n §cDu wurdest von §6CONSOLE §cgebannt. \n §5Begründung: §r" + message);
        }
    }
}
