package ch.dams333.taupeGun.events.interactions;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemClickEvent implements Listener {
    TaupeGun main;
    public ItemClickEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void click(PlayerInteractEvent e){
        if(e.getItem() != null){
            if(main.isState(GameState.PREGAME)){
                if(e.getItem().getType() == Material.BOOK){
                    main.configInventoriesGenerator.openBaseInventory(e.getPlayer());
                }
                if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Sélecteur d'équipe")){
                    e.setCancelled(true);
                    main.teamsInventoryGenerator.baseInventory(e.getPlayer());
                }
            }
        }
    }

}
