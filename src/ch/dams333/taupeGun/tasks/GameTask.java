package ch.dams333.taupeGun.tasks;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    TaupeGun main;
    int time;

    public GameTask(TaupeGun main) {
        this.main = main;
        time = 0; //0
    }

    @Override
    public void run() {
        main.setTime(time);
        main.scoreboardManager.updateScoreboard(time);

        if(time == main.graceTime){
            Bukkit.broadcastMessage(ChatColor.GOLD + "Fin de la ériode d'invinvibilité");
        }
        if(time == main.pvpTime){
            Bukkit.broadcastMessage(ChatColor.RED + "Le PVP est désormais activé !!!");
        }

        if(time == 1140){
            Bukkit.broadcastMessage(ChatColor.RED + "Sélection des taupes dans 1 minute");
        }
        if(time % 1190 == 0){
            Bukkit.broadcastMessage(ChatColor.GRAY + "Fin de l'épisode dans 10 secondes");
        }
        if(time % 1200 == 0){
            int episode = time/1200 + 1;
            if(episode != 1) {
                Bukkit.broadcastMessage(ChatColor.GRAY + "----- " + ChatColor.RED + "Début de l'épisode " + episode + ChatColor.GRAY + " -----");
            }
        }

        if(time == 1200){
            main.selectTaupes();
            Bukkit.broadcastMessage(ChatColor.RED + "Les taupes ont été sélectionnées");
        }

        if(time == main.borderTime){
            main.reduceBorder();
            Bukkit.broadcastMessage(ChatColor.RED + "La bordure commence à réduire");
        }

        time++;
    }
}
