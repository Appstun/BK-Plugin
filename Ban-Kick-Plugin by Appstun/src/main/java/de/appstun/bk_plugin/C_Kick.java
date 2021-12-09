package de.appstun.bk_plugin;


import de.appstun.bk_plugin.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class C_Kick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp()) {
                if (args.length == 0) {
                    p.sendMessage(main.prefix + "§fBitte nutze §9§l/kick <[Spieler]> §9§l§o<[Begründung]>");
                    return true;
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(p == target) {
                        p.sendMessage(main.target_is_self);
                        return true;
                    }
                    if(target.isOp()) {
                        p.sendMessage(main.target_isop);
                        return true;
                    }
                    if (target == null) {
                        p.sendMessage(main.target_no_found);
                        return true;
                    } else {
                        target.kickPlayer("§4§lKICK \n §r \n §r \n §cDu wurdest von §6" + p.getName() + " §cgekickt.");
                        for (Player p_o : Bukkit.getOnlinePlayers()) {
                            if (p_o.isOp()) {
                                p_o.sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " §cwurde von §6" + p.getName() + " §cgekickt.");
                            }
                        }
                        Bukkit.getConsoleSender().sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " wurde von " + p.getName() + " gekickt.");
                    }
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(p == target) {
                        p.sendMessage(main.target_is_self);
                        return true;
                    }
                    if(target.isOp()) {
                        p.sendMessage(main.target_isop);
                        return true;
                    }
                    if (target == null) {
                        p.sendMessage(main.target_no_found);
                        return true;
                    } else {
                        String m = args[1];
                        for (int i = 2; i < args.length; i++) {
                            m = m + " " + args[i];
                        }
                        target.kickPlayer("§4§lKICK \n §r \n §r \n §cDu wurdest von §6" + p.getName() + " §cgekickt. \n §5Begründung: §r" + m);
                        for (Player p_o : Bukkit.getOnlinePlayers()) {
                            if (p_o.isOp()) {
                                p_o.sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " §cwurde von §6" + p.getName() + " §cwegen §e'" + m + "' §cgekickt.");
                            }
                        }
                        Bukkit.getConsoleSender().sendMessage("KICK = " + target.getName() + " wurde von " + p.getName() + " wegen '" + m + "' gekickt.");
                    }
                }
            } else {
                p.sendMessage(main.no_perm);
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(main.prefix + "§fBitte nutze §9§l/kick <[Spieler]> §9§l§o<[Begründung]>");
                return true;
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(main.target_no_found);
                    return true;
                } else {
                    target.kickPlayer("§4§lKICK \n §r \n §r \n §cDu wurdest von §6CONSOLE §cgekickt.");
                    for (Player p_o : Bukkit.getOnlinePlayers()) {
                        if (p_o.isOp()) {
                            p_o.sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " §cwurde von §6CONSOLE §cgekickt.");
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " wurde von CONSOLE gekickt.");
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(main.target_no_found);
                    return true;
                } else {
                    String m = args[1];
                    for (int i = 2; i < args.length; i++) {
                        m = m + " " + args[i];
                    }
                    target.kickPlayer("§4§lKICK \n §r \n §r \n §cDu wurdest von §6CONSOLE §cgekickt. \n §5Begründung: §r" + m);
                    for (Player p_o : Bukkit.getOnlinePlayers()) {
                        if (p_o.isOp()) {
                            p_o.sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " §cwurde von §6CONSOLE §cwegen " + m +  " gekickt.");
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(main.prefix + "§4§lKICK §r§7>> §6" + target.getName() + " wurde von CONSOLE wegen " + m + " gekickt.");
                }
            }
        }
        return false;
    }
}
