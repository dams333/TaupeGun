package ch.dams333.taupeGun.events.status;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvent implements Listener {
    TaupeGun main;
    public FoodEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void entityDamage(FoodLevelChangeEvent e){
        if(main.isState(GameState.PREGAME)){
            e.setCancelled(true);
        }
    }
}
