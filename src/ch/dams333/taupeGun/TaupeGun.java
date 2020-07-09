package ch.dams333.taupeGun;

import ch.dams333.damsLib.DamsLIB;
import ch.dams333.taupeGun.events.connexion.JoinEvent;
import ch.dams333.taupeGun.events.interactions.InventoryClickEvent;
import ch.dams333.taupeGun.events.interactions.ItemClickEvent;
import ch.dams333.taupeGun.events.status.DamageEvent;
import ch.dams333.taupeGun.events.status.FoodEvent;
import ch.dams333.taupeGun.events.status.MoveEvent;
import ch.dams333.taupeGun.gameState.GameState;
import ch.dams333.taupeGun.inventories.ConfigInventoriesGenerator;
import ch.dams333.taupeGun.inventories.TeamsInventoryGenerator;
import ch.dams333.taupeGun.teams.TeamsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TaupeGun extends JavaPlugin {

    public int graceTime;
    public int pvpTime;
    public int borderTime;
    public int preBorder;
    public int reductionTime;
    public int postBorder;
    public List<ItemStack> startInventory;
    public int taupesPerTeam;
    public int taupeTeams;


    private GameState gameState;
    public DamsLIB API;

    public ConfigInventoriesGenerator configInventoriesGenerator;
    public TeamsInventoryGenerator teamsInventoryGenerator;
    public TeamsManager teamsManager;

    @Override
    public void onEnable(){

        graceTime = 60; //60
        pvpTime = 1200; //1200
        borderTime = 4800; // 4800
        preBorder = 1000; //1000
        postBorder = 20; //20
        reductionTime = 980; //980
        startInventory = new ArrayList<>();
        taupesPerTeam = 1; //1
        taupeTeams = 5; //5

        API = (DamsLIB) getServer().getPluginManager().getPlugin("DamsLIB");

        this.configInventoriesGenerator = new ConfigInventoriesGenerator(this);
        this.teamsInventoryGenerator = new TeamsInventoryGenerator(this);
        this.teamsManager = new TeamsManager(this);

        this.gameState = GameState.PREGAME;


        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(this), this);
        getServer().getPluginManager().registerEvents(new ItemClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(this), this);
    }


    public void setState(GameState gameState){
        this.gameState = gameState;
    }
    public boolean isState(GameState gameState){
        return this.gameState == gameState;
    }


    public void joinSpectator(Player p) {
    }

    public String getTimeIntoString(int seconds){
        int sec = seconds % 60;
        int hour = seconds / 60;
        int min = hour % 60;
        hour = hour/60;

        String secSTR = "";
        String minSTR = "";
        String hourSTR = "";
        if(sec < 10){
            secSTR = "0" + String.valueOf(sec);
        }else{
            secSTR = String.valueOf(sec);
        }
        if(min < 10){
            minSTR = "0" + String.valueOf(min);
        }else{
            minSTR = String.valueOf(min);
        }
        if(hour < 10){
            hourSTR = "0" + String.valueOf(hour);
        }else{
            hourSTR = String.valueOf(hour);
        }

        return hourSTR + ":" + minSTR + ":" + secSTR;
    }
}
