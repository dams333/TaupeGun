package ch.dams333.taupeGun.events.interactions;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickEvent implements Listener {
    TaupeGun main;
    public InventoryClickEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void click(org.bukkit.event.inventory.InventoryClickEvent e){
        if(main.isState(GameState.PREGAME)){
            Player p = (Player) e.getWhoClicked();
            if(e.getCurrentItem() != null) {
                ItemStack it = e.getCurrentItem();
                if (e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration")) {
                    e.setCancelled(true);
                    if (e.getCurrentItem() != null) {
                        if (e.getCurrentItem().getType() == Material.CLOCK) {
                            main.configInventoriesGenerator.openTimersInventory(e.getWhoClicked());
                        }
                        if (e.getCurrentItem().getType() == Material.WHITE_BANNER) {
                            main.configInventoriesGenerator.openTeamsInventory(e.getWhoClicked());
                        }
                        if (e.getCurrentItem().getType() == Material.BARRIER) {
                            main.configInventoriesGenerator.openBorderInventory(e.getWhoClicked());
                        }
                        if (e.getCurrentItem().getType() == Material.CHEST) {
                            main.configInventoriesGenerator.openStartInventoryInventory(e.getWhoClicked());
                        }
                        if (e.getCurrentItem().getType() == Material.WITHER_ROSE) {
                            main.configInventoriesGenerator.openTaupesInventory(e.getWhoClicked());
                        }
                    }
                }
                if (e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Timers")) {
                    e.setCancelled(true);

                    if (it.getType() == Material.ARROW) {
                        main.configInventoriesGenerator.openBaseInventory(p);
                    }
                    if (it.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Temps avant la fin de l'invincibilité:")) {
                        main.configInventoriesGenerator.openModifyTimerInventory(p, "grace");
                    }
                    if (it.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Temps avant le PVP:")) {
                        main.configInventoriesGenerator.openModifyTimerInventory(p, "pvp");
                    }
                    if (it.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Temps avant la réduction de la bordure:")) {
                        main.configInventoriesGenerator.openModifyTimerInventory(p, "border");
                    }
                }
                if(e.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "Configuration > Equipes")){
                    e.setCancelled(true);
                    if (it.getType() == Material.ARROW) {
                        main.configInventoriesGenerator.openBaseInventory(p);
                    }else if(it.getType() != Material.BLACK_STAINED_GLASS_PANE){
                        main.teamsManager.switchTeam(it.getItemMeta().getDisplayName());
                        main.configInventoriesGenerator.openTeamsInventory(p);
                    }
                }
                if(e.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "Configuration > Timers > ")) {
                    e.setCancelled(true);

                    if (it.getType() == Material.ARROW) {
                        main.configInventoriesGenerator.openTimersInventory(p);
                    }

                    if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Timers > Invincibilité")){
                        if(it.getItemMeta().getDisplayName().contains("- 1min")){
                            if(main.graceTime > 60){
                                main.graceTime = main.graceTime - 60;
                            }else{
                                main.graceTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 30sec")){
                            if(main.graceTime > 30){
                                main.graceTime = main.graceTime - 30;
                            }else{
                                main.graceTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 10sec")){
                            if(main.graceTime > 10){
                                main.graceTime = main.graceTime - 10;
                            }else{
                                main.graceTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 10sec")){
                            main.graceTime = main.graceTime + 10;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 30sec")){
                            main.graceTime = main.graceTime + 30;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 1min")){
                            main.graceTime = main.graceTime + 60;
                        }
                        if(it.getType() != Material.CLOCK && it.getType() != Material.ARROW){
                            main.configInventoriesGenerator.openModifyTimerInventory(p, "grace");
                        }
                    }
                    if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Timers > PVP")){
                        if(it.getItemMeta().getDisplayName().contains("- 20min")){
                            if(main.pvpTime > 1200){
                                main.pvpTime = main.pvpTime - 1200;
                            }else{
                                main.pvpTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 10min")){
                            if(main.pvpTime > 600){
                                main.pvpTime = main.pvpTime - 600;
                            }else{
                                main.pvpTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 1min")){
                            if(main.pvpTime > 60){
                                main.pvpTime = main.pvpTime - 60;
                            }else{
                                main.pvpTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 1min")){
                            main.pvpTime = main.pvpTime + 60;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 10min")){
                            main.pvpTime = main.pvpTime + 600;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 20min")){
                            main.pvpTime = main.pvpTime + 1200;
                        }
                        if(it.getType() != Material.CLOCK && it.getType() != Material.ARROW){
                            main.configInventoriesGenerator.openModifyTimerInventory(p, "pvp");
                        }
                    }
                    if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Timers > Bordure")){
                        if(it.getItemMeta().getDisplayName().contains("- 30min")){
                            if(main.borderTime > 1800){
                                main.borderTime = main.borderTime - 1800;
                            }else{
                                main.borderTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 15min")){
                            if(main.borderTime > 900){
                                main.borderTime = main.borderTime - 900;
                            }else{
                                main.borderTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("- 5min")){
                            if(main.borderTime > 300){
                                main.borderTime = main.borderTime - 300;
                            }else{
                                main.borderTime = 0;
                            }
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 5min")){
                            main.borderTime = main.borderTime + 300;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 15min")){
                            main.borderTime = main.borderTime + 900;
                        }
                        if(it.getItemMeta().getDisplayName().contains("+ 30min")){
                            main.borderTime = main.borderTime + 1800;
                        }
                        if(it.getType() != Material.CLOCK && it.getType() != Material.ARROW){
                            main.configInventoriesGenerator.openModifyTimerInventory(p, "border");
                        }
                    }
                }
                if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Bordure")){
                    e.setCancelled(true);

                    if(it.getType() == Material.ARROW){
                        main.configInventoriesGenerator.openBaseInventory(p);
                    }else{

                        if(it.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bordure avant la réduction")){
                            if(e.getAction() == InventoryAction.PICKUP_ALL){
                                main.preBorder = main.preBorder + 50;
                            }
                            if(e.getAction() == InventoryAction.PICKUP_HALF){
                                if(main.preBorder < 50){
                                    main.preBorder = 0;
                                }else{
                                    main.preBorder = main.preBorder - 50;
                                }
                            }
                        }
                        if(it.getType() == Material.CLOCK){
                            if(e.getAction() == InventoryAction.PICKUP_ALL){
                                main.reductionTime = main.reductionTime + 30;
                            }
                            if(e.getAction() == InventoryAction.PICKUP_HALF){
                                if(main.reductionTime < 30){
                                    main.reductionTime = 0;
                                }else{
                                    main.reductionTime = main.reductionTime - 30;
                                }
                            }
                        }
                        if(it.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Bordure après la réduction")){
                            if(e.getAction() == InventoryAction.PICKUP_ALL){
                                main.postBorder = main.postBorder + 50;
                            }
                            if(e.getAction() == InventoryAction.PICKUP_HALF){
                                if(main.postBorder <= 50){
                                    if(main.postBorder < 5) {
                                        main.postBorder = 0;
                                    }else{
                                        main.postBorder = main.postBorder - 5;
                                    }
                                }else{
                                    main.postBorder = main.postBorder - 50;
                                }
                            }
                        }

                        main.configInventoriesGenerator.openBorderInventory(p);
                    }
                }
                if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Inventaire de départ")){
                    if(it.getType() == Material.BLACK_STAINED_GLASS_PANE || it.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Configuration de la partie")){
                        e.setCancelled(true);
                    }else if(it.getType() == Material.ARROW && it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Revenir en arrière [Sauvegarder]")){
                        e.setCancelled(true);
                        List<ItemStack> items = new ArrayList<>();
                        for(int i = 0; i < 27; i++){
                            if(e.getInventory().getContents()[i] != null && e.getInventory().getContents()[i].getType() != Material.AIR){
                                items.add(e.getInventory().getContents()[i]);
                            }
                        }
                        main.startInventory = items;
                        main.configInventoriesGenerator.openBaseInventory(p);
                    }
                }
                if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Configuration > Taupes")){
                    e.setCancelled(true);
                    if(it.getType() == Material.ARROW){
                        main.configInventoriesGenerator.openBaseInventory(p);
                    }else {
                        if (it.getType() == Material.WITHER_ROSE) {
                            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                                main.taupesPerTeam = main.taupesPerTeam + 1;
                            }
                            if (e.getAction() == InventoryAction.PICKUP_HALF) {
                                if (main.taupesPerTeam > 1) {
                                    main.taupesPerTeam = main.taupesPerTeam - 1;
                                }
                            }
                        }
                        if (it.getType() == Material.WHITE_BANNER) {
                            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                                main.taupeTeams = main.taupeTeams + 1;
                            }
                            if (e.getAction() == InventoryAction.PICKUP_HALF) {
                                if (main.taupeTeams > 1) {
                                    main.taupeTeams = main.taupeTeams - 1;
                                }
                            }
                        }
                        main.configInventoriesGenerator.openTaupesInventory(p);
                    }
                }
                if(e.getView().getTitle().equals(ChatColor.DARK_GREEN + "Choisir une équipe")){
                    e.setCancelled(true);
                    main.teamsManager.chooseTeam(it.getItemMeta().getDisplayName(), p);
                    p.closeInventory();

                    ItemStack it2 = main.teamsManager.getTeamBanner(p);
                    ItemMeta m = it2.getItemMeta();
                    m.setDisplayName(ChatColor.GOLD + "Sélecteur d'équipe");
                    it2.setItemMeta(m);
                    p.getInventory().setItem(8, it2);
                }
            }
        }
    }

}
