package ch.dams333.taupeGun.commands.admin;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    TaupeGun main;
    public HealCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                for(Player pl : Bukkit.getOnlinePlayers()){
                    pl.setHealth(20);
                }
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Tout le monde a été soigné !");
                return true;
            }
            if(args.length == 1){
                String pseudo = args[0];
                if(Bukkit.getPlayer(pseudo) != null){
                    Bukkit.getPlayer(pseudo).setHealth(20);
                    p.sendMessage(ChatColor.LIGHT_PURPLE + pseudo + " a été soigné !");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "Ce jouer n'est pas connecté");
                return true;
            }
            p.sendMessage(ChatColor.RED + "/heal [pseudo]");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}
