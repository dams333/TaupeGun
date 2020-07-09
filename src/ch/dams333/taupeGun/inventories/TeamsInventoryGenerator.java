package ch.dams333.taupeGun.inventories;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamsInventoryGenerator {

    TaupeGun main;

    public TeamsInventoryGenerator(TaupeGun main) {
        this.main = main;
    }

    public void baseInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Choisir une équipe");

        for(Team team : main.teamsManager.getTeams()){
            if(team.isActivated()) {
                ItemStack it = main.API.itemStackManager.create(team.getBanner(), team.getChatColor() + team.getName());
                inv.addItem(it);
            }
        }
        p.openInventory(inv);

        p.openInventory(inv);
    }
}
