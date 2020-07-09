package ch.dams333.taupeGun.commands.admin;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    TaupeGun main;
    public StartCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (main.isState(GameState.PREGAME)) {
                float taupes = Float.parseFloat(String.valueOf(main.taupesPerTeam * main.teamsManager.getActivateTeams()));
                float taupesTeams = Float.parseFloat(String.valueOf(main.taupeTeams));
                if (taupes != 0 && taupesTeams != 0 && String.valueOf(taupes / taupesTeams).contains(".0")) {
                    // DESACTIVATE for devlopement test
                    //if(Bukkit.getOnlinePlayers().size() > taupes){
                    main.startGame();
                    return true;
                    //}
                    //p.sendMessage(ChatColor.RED + "Il y a " + main.taupesPerTeam * main.teamsManager.getActivateTeams() + " taupes dans la partie alors que seulement " + Bukkit.getOnlinePlayers().size() + " joueurs sont connectés");
                    //return true;
                }
                p.sendMessage(ChatColor.RED + "Impossible de lancer la partie. Il y a " + main.taupesPerTeam * main.teamsManager.getActivateTeams() + " taupes, réparties dans " + main.taupeTeams + " équipes");
                return true;
            }
            p.sendMessage(ChatColor.RED + "Ce n'est pas le moment de démarrer la partie");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}
