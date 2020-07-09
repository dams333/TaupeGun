package ch.dams333.taupeGun.inventories;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ConfigInventoriesGenerator {
    TaupeGun main;
    public ConfigInventoriesGenerator(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    public void openBaseInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Configuration");

        inv.setItem(2, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Timers"));
        inv.setItem(6, main.API.itemStackManager.create(Material.WHITE_BANNER, ChatColor.LIGHT_PURPLE + "Equipes"));
        inv.setItem(13, main.API.itemStackManager.create(Material.BARRIER, ChatColor.DARK_RED + "Bordure du monde"));
        inv.setItem(20, main.API.itemStackManager.create(Material.CHEST, ChatColor.BLUE + "Inventaire de départ"));
        inv.setItem(24, main.API.itemStackManager.create(Material.WITHER_ROSE, ChatColor.RED + "Taupes"));


        p.openInventory(inv);
    }

    public void openTimersInventory(HumanEntity p) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Timers");

        inv.setItem(1, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant la fin de l'invincibilité:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.graceTime))));
        inv.setItem(3, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant le PVP:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.pvpTime))));
        inv.setItem(5, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant la réduction de la bordure:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.borderTime))));
        inv.setItem(8, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière"));

        p.openInventory(inv);
    }

    public void openTeamsInventory(HumanEntity p) {

        int activate = main.teamsManager.getActivateTeams();
        int teams = main.teamsManager.getTeams().size();

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Configuration > Equipes (" + activate + "/" + teams + ")");
        int slot = 0;
        for(Team team : main.teamsManager.getTeams()){
            if(!team.isActivated()){
                inv.addItem(main.API.itemStackManager.create(team.getBanner(), team.getChatColor() + team.getName()));
            }else{
                ItemStack it = main.API.itemStackManager.create(team.getBanner(), team.getChatColor() + team.getName());
                ItemMeta m = it.getItemMeta();
                m.setLore(Arrays.asList(ChatColor.GREEN + "Activée"));
                it.setItemMeta(m);
                inv.addItem(it);
            }
            slot = slot + 1;
            if(team.getChatColor().equals(ChatColor.GRAY)){
                inv.setItem(slot, main.API.itemStackManager.create(Material.BLACK_STAINED_GLASS_PANE, " "));
                slot = slot + 1;
            }
        }
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière"));
        p.openInventory(inv);
    }

    public void openBorderInventory(HumanEntity p) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Bordure");

        inv.setItem(1, main.API.itemStackManager.create(Material.BARRIER, ChatColor.GOLD + "Bordure avant la réduction", Arrays.asList(ChatColor.GRAY + "+" + main.preBorder + " / -" + main.preBorder, ChatColor.GREEN + "Clique gauche pour augmenter", ChatColor.RED + "Clique droit pour diminuer")));
        inv.setItem(3, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps de réduction", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.reductionTime) + "(" + (Math.round(Math.round(main.preBorder - main.postBorder)/main.reductionTime)) + "blocks/sec)", ChatColor.GREEN + "Clique gauche pour augmenter", ChatColor.RED + "Clique droit pour diminuer")));
        inv.setItem(5, main.API.itemStackManager.create(Material.BARRIER, ChatColor.GOLD + "Bordure après la réduction", Arrays.asList(ChatColor.GRAY + "+" + main.postBorder + " / -" + main.postBorder, ChatColor.GREEN + "Clique gauche pour augmenter", ChatColor.RED + "Clique droit pour diminuer")));

        inv.setItem(8, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière"));
        p.openInventory(inv);
    }

    public void openStartInventoryInventory(HumanEntity p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_GREEN + "Configuration > Inventaire de départ");

        for(ItemStack it : main.startInventory){
            inv.addItem(it);
        }

        for(int i = 27; i <35; i++){
            inv.setItem(i, main.API.itemStackManager.create(Material.BLACK_STAINED_GLASS_PANE, " "));
        }
        inv.setItem(35, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière [Sauvegarder]"));

        p.openInventory(inv);
    }

    public void openTaupesInventory(HumanEntity p) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Taupes");

        inv.setItem(2, main.API.itemStackManager.create(Material.WITHER_ROSE, ChatColor.GOLD + "Taupes par team: " + main.taupesPerTeam, Arrays.asList(ChatColor.GREEN + "Clique gauche pour augmenter", ChatColor.RED + "Clique droit pour réduire")));
        inv.setItem(6, main.API.itemStackManager.create(Material.WHITE_BANNER, ChatColor.GOLD + "Teams de taupes: " + main.taupeTeams, Arrays.asList(ChatColor.GREEN + "Clique gauche pour augmenter", ChatColor.RED + "Clique droit pour réduire")));

        inv.setItem(8, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière"));
        p.openInventory(inv);
    }


    public void openModifyTimerInventory(Player p, String grace) {

        Inventory inv = null;
        if(grace.equals("grace")){
            inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Timers > Invincibilité");
            inv.setItem(0, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 1min"));
            inv.setItem(1, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 30sec"));
            inv.setItem(2, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 10sec"));
            inv.setItem(3, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant la fin de l'invincibilité:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.graceTime))));
            inv.setItem(4, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 10sec"));
            inv.setItem(5, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 30sec"));
            inv.setItem(6, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 1min"));
        }
        if(grace.equals("pvp")){
            inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Timers > PVP");
            inv.setItem(0, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 20min"));
            inv.setItem(1, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 10min"));
            inv.setItem(2, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 1min"));
            inv.setItem(3, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant le PVP:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.pvpTime))));
            inv.setItem(4, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 1min"));
            inv.setItem(5, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 10min"));
            inv.setItem(6, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 20min"));
        }
        if(grace.equals("border")){
            inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Configuration > Timers > Bordure");
            inv.setItem(0, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 30min"));
            inv.setItem(1, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 15min"));
            inv.setItem(2, main.API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "- 5min"));
            inv.setItem(3, main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + "Temps avant la réduction de la bordure:", Arrays.asList(ChatColor.GRAY + main.getTimeIntoString(main.borderTime))));
            inv.setItem(4, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 5min"));
            inv.setItem(5, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 15min"));
            inv.setItem(6, main.API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "+ 30min"));
        }


        inv.setItem(8, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Revenir en arrière"));
        p.openInventory(inv);
    }
}
