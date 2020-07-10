package ch.dams333.taupeGun.commands.players;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import ch.dams333.taupeGun.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordCommand implements CommandExecutor {
    TaupeGun main;
    public CoordCommand(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.isState(GameState.GAME)){
                if(args.length == 1){
                    String pseudo = args[0];
                    if(Bukkit.getPlayer(pseudo) != null){
                        Bukkit.getPlayer(pseudo).sendMessage(p.getDisplayName() + ChatColor.GOLD + " > " + getStringOfCoord(p));
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "Ce joueur n'est pas connecté");
                    return true;
                }else{
                    for(Team team : main.teamsManager.getTeams()){
                        if(team.getPlayers().contains(p)){
                            for(Player pl : team.getPlayers()){
                                pl.sendMessage(ChatColor.GRAY + "[Equipe] " + ChatColor.WHITE + p.getName() + ChatColor.GOLD + " > " + getStringOfCoord(p));
                            }
                        }
                    }
                }
                return true;
            }
            p.sendMessage(ChatColor.RED + "Ce n'est pas le moment de faire celà");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire celà");
        return false;
    }

    private String getStringOfCoord(Player p){
        return
                ChatColor.GOLD + "X: " + ChatColor.WHITE + Math.round(p.getLocation().getX()) + ChatColor.GRAY + " | " +
                ChatColor.GOLD + "Y: " + ChatColor.WHITE + Math.round(p.getLocation().getY()) + ChatColor.GRAY + " | " +
                ChatColor.GOLD + "Z: " + ChatColor.WHITE + Math.round(p.getLocation().getZ());
    }
}
