package ch.dams333.taupeGun.commands.players;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TaupeCommand implements CommandExecutor {
    TaupeGun main;
    public TaupeCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.isTaupe(p)){
                StringBuilder sb = new StringBuilder();
                for(String arg : args){
                    sb.append(arg + " ");
                }
                main.sendTaupeMessage(p, sb.toString());
                return true;
            }
            p.sendMessage(ChatColor.RED + "Vous n'êtes pas la taupe !");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}
