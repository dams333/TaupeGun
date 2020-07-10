package ch.dams333.taupeGun.events.interactions;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import ch.dams333.taupeGun.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    TaupeGun main;
    public ChatEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        e.setCancelled(true);

        if(!main.isState(GameState.GAME)){
            Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ChatColor.GOLD + " > " + ChatColor.GRAY + e.getMessage());
        }else{
            if(e.getMessage().startsWith("!")){
                Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ChatColor.GOLD + " > " + ChatColor.GRAY + e.getMessage().replaceFirst("!", ""));
            }else{
                for(Team team : main.teamsManager.getTeams()){
                    if(team.getPlayers().contains(e.getPlayer())){
                        for(Player p : team.getPlayers()){
                            p.sendMessage(ChatColor.GRAY + "[Equipe] " + ChatColor.WHITE + e.getPlayer().getName() + ChatColor.GOLD + " > " + ChatColor.GRAY + e.getMessage());
                        }
                    }
                }
            }
        }

    }

}
