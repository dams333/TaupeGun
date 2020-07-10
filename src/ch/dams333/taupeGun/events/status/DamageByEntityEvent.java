package ch.dams333.taupeGun.events.status;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityEvent implements Listener {
    TaupeGun main;
    public DamageByEntityEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent e){
        if(!e.isCancelled()){
            if(e.getEntity() instanceof Player){
                if(e.getDamager() instanceof Player){
                    if(((Player) e.getEntity()).getHealth() <= e.getFinalDamage()){
                        e.setCancelled(true);
                        main.killByPlayer((Player) e.getEntity(), (Player)e.getDamager());
                    }
                }else if(e.getDamager() instanceof Arrow){
                    if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                        if (((Player) e.getEntity()).getHealth() <= e.getFinalDamage()) {
                            Player shooted = (Player) e.getEntity();
                            Player shooter = (Player) ((Arrow) e.getDamager()).getShooter();
                            e.setCancelled(true);
                            main.killByPlayer(shooted, shooter);
                        }
                    }
                }else{
                    if(((Player) e.getEntity()).getHealth() <= e.getFinalDamage()){
                        e.setCancelled(true);
                        main.kill((Player) e.getEntity());
                    }
                }
            }
        }
    }

}
