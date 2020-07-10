package ch.dams333.taupeGun.events.status;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.entity.Player;
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
        if(main.isState(GameState.GAME)){
            if(main.getTime() < main.graceTime){
                if(e.getEntity() instanceof Player) {
                    e.setCancelled(true);
                }
            }else{
                if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                    if(e.getEntity() instanceof Player){
                        if(((Player) e.getEntity()).getHealth() <= e.getFinalDamage()){
                            e.setCancelled(true);
                            main.kill((Player)e.getEntity());
                        }
                    }
                }
            }
        }
    }

}
