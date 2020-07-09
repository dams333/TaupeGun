package ch.dams333.taupeGun.commands.players;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClaimCommand implements CommandExecutor {
    TaupeGun main;
    public ClaimCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.kits.keySet().contains(p)){
                for(ItemStack it : main.kits.get(p).getItems()){
                    p.getInventory().addItem(it);
                }
                main.kits.remove(p);
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Vous avez reçu votre kit");
                return true;
            }
            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas éxecuter cette commande");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire celà");
        return false;
    }
}
