package ch.dams333.taupeGun.events.status;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {
    TaupeGun main;
    public DamageEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent e){
        if(main.isState(GameState.PREGAME)){
            e.setCancelled(true);
        }
    }

}
