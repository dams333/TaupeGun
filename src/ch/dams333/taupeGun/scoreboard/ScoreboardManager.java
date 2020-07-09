package ch.dams333.taupeGun.scoreboard;

import ch.dams333.damsLib.ScoreboardSign;
import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    Map<Player, ScoreboardSign> scoreboards;

    public ScoreboardManager(TaupeGun main) {
        this.main = main;
        this.scoreboards = new HashMap<>();
    }

    TaupeGun main;

    public void updateScoreboard(int time) {

        int episode = time/1200 + 1;
        int episodeTime = time % 1200;

        Map<Player, ScoreboardSign> update = new HashMap<>();

        for(Player p : this.scoreboards.keySet()){
            ScoreboardSign scoreboardSign = this.scoreboards.get(p);
            scoreboardSign.setLine(1, "  " + ChatColor.RED + "Episode: " + ChatColor.WHITE + episode);
            scoreboardSign.setLine(2, "  " + ChatColor.RED + "Timer: " + ChatColor.WHITE + getTimeIntoString(episodeTime));
            if(episode == 1) {
                int taupeTime = 1200 - episodeTime;
                scoreboardSign.setLine(3, "  " + ChatColor.RED + "Taupes: " + ChatColor.WHITE + getTimeIntoString(taupeTime));
            }else{
                scoreboardSign.setLine(3, "  " + ChatColor.RED + "Taupes: " + ChatColor.GREEN + "Activé");
            }
            update.put(p, scoreboardSign);
        }

        scoreboards = update;

    }

    public void createScoreboard(Player p) {
        ScoreboardSign scoreboardSign = new ScoreboardSign(p, ChatColor.RED + "TaupeGun");
        scoreboardSign.create();

        scoreboardSign.setLine(0, ChatColor.GRAY + "--------------");
        scoreboardSign.setLine(1, "  " + ChatColor.RED + "Episode: " + ChatColor.WHITE + "1");
        scoreboardSign.setLine(2, "  " + ChatColor.RED + "Timer: " + ChatColor.WHITE + "00:00");
        scoreboardSign.setLine(3, "  " + ChatColor.RED + "Taupes: " + ChatColor.WHITE + "20:00");

        this.scoreboards.put(p, scoreboardSign);
    }


    private String getTimeIntoString(int seconds){
        int min = (seconds % 3600) / 60;
        int sec = seconds % 60;

        String secSTR = "";
        String minSTR = "";
        if(sec < 10){
            secSTR = "0" + String.valueOf(sec);
        }else{
            secSTR = String.valueOf(sec);
        }
        if(min < 10){
            minSTR = "0" + String.valueOf(min);
        }else{
            minSTR = String.valueOf(min);
        }

        return minSTR + ":" + secSTR;
    }
}
