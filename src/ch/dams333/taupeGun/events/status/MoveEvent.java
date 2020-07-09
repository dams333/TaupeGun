package ch.dams333.taupeGun.events.status;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    TaupeGun main;
    public MoveEvent(TaupeGun taupeGun) {
        this.main = taupeGun;

    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        if(main.isState(GameState.PREGAME)){
            if(e.getTo().getY() < 150 && !e.getPlayer().hasPermission("taupegun.configure")){
                e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 150, 0));
            }
        }
    }

}
