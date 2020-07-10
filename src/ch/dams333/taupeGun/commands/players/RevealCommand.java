package ch.dams333.taupeGun.commands.players;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RevealCommand implements CommandExecutor {
    TaupeGun main;
    public RevealCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.canReveal(p)){
                main.revealed.add(p);
                Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " se révèle être une taupe !!!");

                p.setDisplayName(ChatColor.RED + "[Taupe " + main.getTaupeTeamNumber(p) + "] " + p.getName());
                p.setPlayerListName(ChatColor.RED + "[Taupe " + main.getTaupeTeamNumber(p) + "] " + p.getName());

                p.getInventory().addItem(main.API.itemStackManager.create(Material.GOLDEN_APPLE));

                return true;
            }
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire celà");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}
