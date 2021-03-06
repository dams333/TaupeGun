package ch.dams333.taupeGun.events.connexion;

import ch.dams333.taupeGun.TaupeGun;
import ch.dams333.taupeGun.gameState.GameState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class JoinEvent implements Listener {
    TaupeGun main;
    public JoinEvent(TaupeGun taupeGun) {
        this.main = taupeGun;
    }

    @EventHandler
    public void join(PlayerJoinEvent e){

        for(Player pl : main.inGame){
            if(pl.getUniqueId().equals(e.getPlayer())){
                main.inGame.remove(pl);
                main.inGame.add(e.getPlayer());
                break;
            }
        }

        global:
        for(int taupeTeamNumber : main.taupesTeams.keySet()){
            for(Player pl : main.taupesTeams.get(taupeTeamNumber)){
                if(pl.getUniqueId().equals(e.getPlayer())){
                    List<Player> team = main.taupesTeams.get(taupeTeamNumber);
                    team.remove(pl);
                    team.add(pl);
                    main.taupesTeams.put(taupeTeamNumber, team);
                    break global;
                }
            }
        }

        for(Player pl : main.kits.keySet()){
            if(pl.getUniqueId().equals(e.getPlayer().getUniqueId())){
                main.kits.put(e.getPlayer(), main.kits.get(pl));
                main.kits.remove(pl);
                break;
            }
        }

        main.teamsManager.reco(e.getPlayer());
        main.scoreboardManager.reco(e.getPlayer());

        if(main.isState(GameState.PREGAME)){
            if(new Location(Bukkit.getWorld("world"), 0, 149, 0).getBlock().getType() != Material.BARRIER){
                for(int x = - 50; x <= 50; x++){
                    for(int z = - 50; z <= 50; z++){
                        new Location(Bukkit.getWorld("world"), x, 149, z).getBlock().setType(Material.BARRIER);
                    }
                }
            }
            Player p = e.getPlayer();
            p.teleport(new Location(Bukkit.getWorld("world"), 0, 150, 0));
            p.getInventory().clear();
            p.updateInventory();
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setGameMode(GameMode.ADVENTURE);
            if(p.hasPermission("taupegun.configure")){
                p.getInventory().setItem(4, main.API.itemStackManager.create(Material.BOOK, ChatColor.GOLD + "Configuration de la partie"));
                p.getInventory().setHeldItemSlot(4);
                p.setGameMode(GameMode.CREATIVE);
            }

            if(main.teamsManager.isInTeam(p)){
                ItemStack it = main.teamsManager.getTeamBanner(p);
                ItemMeta m = it.getItemMeta();
                m.setDisplayName(ChatColor.GOLD + "Sélecteur d'équipe");
                it.setItemMeta(m);
                p.getInventory().setItem(8, it);
            }else{
                ItemStack it = new ItemStack(Material.WHITE_BANNER);
                ItemMeta m = it.getItemMeta();
                m.setDisplayName(ChatColor.GOLD + "Sélecteur d'équipe");
                it.setItemMeta(m);
                p.getInventory().setItem(8, it);
            }

        }else{
            e.setJoinMessage(null);
            main.joinSpectator(e.getPlayer());
        }
    }

}
