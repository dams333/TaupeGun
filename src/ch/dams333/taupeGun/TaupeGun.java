package ch.dams333.taupeGun;

import ch.dams333.damsLib.DamsLIB;
import ch.dams333.taupeGun.commands.admin.HealCommand;
import ch.dams333.taupeGun.commands.admin.StartCommand;
import ch.dams333.taupeGun.commands.players.ClaimCommand;
import ch.dams333.taupeGun.commands.players.CoordCommand;
import ch.dams333.taupeGun.commands.players.RevealCommand;
import ch.dams333.taupeGun.commands.players.TaupeCommand;
import ch.dams333.taupeGun.events.connexion.JoinEvent;
import ch.dams333.taupeGun.events.interactions.ChatEvent;
import ch.dams333.taupeGun.events.interactions.InventoryClickEvent;
import ch.dams333.taupeGun.events.interactions.ItemClickEvent;
import ch.dams333.taupeGun.events.status.DamageByEntityEvent;
import ch.dams333.taupeGun.events.status.DamageEvent;
import ch.dams333.taupeGun.events.status.FoodEvent;
import ch.dams333.taupeGun.events.status.MoveEvent;
import ch.dams333.taupeGun.gameState.GameState;
import ch.dams333.taupeGun.inventories.ConfigInventoriesGenerator;
import ch.dams333.taupeGun.inventories.TeamsInventoryGenerator;
import ch.dams333.taupeGun.kit.Kit;
import ch.dams333.taupeGun.kit.KitManager;
import ch.dams333.taupeGun.scoreboard.ScoreboardManager;
import ch.dams333.taupeGun.tasks.BorderTask;
import ch.dams333.taupeGun.tasks.GameTask;
import ch.dams333.taupeGun.teams.Team;
import ch.dams333.taupeGun.teams.TeamsManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ScoreboardManager scoreboardManager;
    public KitManager kitManager;

    public List<Player> inGame;
    public List<Player> revealed;

    public Map<Integer, List<Player>> taupesTeams;
    public Map<Player, Kit> kits;

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
        time = 0; //0

        inGame = new ArrayList<>();
        revealed = new ArrayList<>();

        taupesTeams = new HashMap<>();
        kits = new HashMap<>();

        API = (DamsLIB) getServer().getPluginManager().getPlugin("DamsLIB");

        this.configInventoriesGenerator = new ConfigInventoriesGenerator(this);
        this.teamsInventoryGenerator = new TeamsInventoryGenerator(this);
        this.teamsManager = new TeamsManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.kitManager = new KitManager(this);

        this.gameState = GameState.PREGAME;


        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(this), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(this), this);
        getServer().getPluginManager().registerEvents(new ItemClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);

        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("claim").setExecutor(new ClaimCommand(this));
        getCommand("t").setExecutor(new TaupeCommand(this));
        getCommand("reveal").setExecutor(new RevealCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("coord").setExecutor(new CoordCommand(this));
    }


    public void setState(GameState gameState){
        this.gameState = gameState;
    }
    public boolean isState(GameState gameState){
        return this.gameState == gameState;
    }


    public void joinSpectator(Player p) {
        p.teleport(new Location(Bukkit.getWorld("world"), 0, 150, 0));
        p.setGameMode(GameMode.SPECTATOR);
        p.setHealth(20);
        p.setFoodLevel(20);
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

    public void startGame() {
        setState(GameState.STARTING);
        for(Player p : Bukkit.getOnlinePlayers()){
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 255));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 255));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 999999, 255));
            scoreboardManager.createScoreboard(p);
            inGame.add(p);
        }
        for(Team team : teamsManager.getTeams()){
            if(team.isActivated()) {
                Bukkit.broadcastMessage(ChatColor.GRAY + "Téléportation de l'équipe " + team.getName());
                Location spawn = new Location(Bukkit.getWorld("world"), API.random(-800, 800), 255, API.random(-800, 800));
                spawn = transformSpawn(spawn);
                spawn.add(0, 1, 0);
                for (Player p : team.getPlayers()) {
                    p.teleport(spawn);
                }
            }
        }

        GameTask gameTask = new GameTask(this);
        gameTask.runTaskTimer(this, 20, 20);

        Bukkit.getWorld("world").setTime(0);
        WorldBorder worldBorder = Bukkit.getWorld("world").getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(2000);
        worldBorder.setWarningDistance(10);
        worldBorder.setDamageAmount(1);
        for(Player p : Bukkit.getOnlinePlayers()){
            for(PotionEffect potionEffect : p.getActivePotionEffects()){
                p.removePotionEffect(potionEffect.getType());
            }
            for(ItemStack it : this.startInventory){
                p.getInventory().addItem(it);
            }
        }
        setState(GameState.GAME);
    }

    private Location transformSpawn(Location loc){
        while (loc.getBlock().getType() == Material.AIR){
            loc.add(0, -1, 0);
        }
        if(loc.getBlock().getType() == Material.WATER){
            loc = new Location(Bukkit.getWorld("world"), API.random(-800, 800), 255, API.random(-800, 800));
            loc = transformSpawn(loc);
        }
        return loc;
    }

    public void selectTaupes() {

        List<Player> taupes = new ArrayList<>();

        for(Team team : this.teamsManager.getTeams()){
            if(team.isActivated()){
                int toSelect = this.taupesPerTeam;
                List<Player> teamPlayers = new ArrayList<>();
                teamPlayers.addAll(team.getPlayers());
                while (toSelect > 0){
                    int selected = API.random(0, teamPlayers.size() - 1);
                    API.titleManager.sendTitle(teamPlayers.get(selected), ChatColor.RED + "Vous êtes la taupe !", 30);
                    API.titleManager.sendSubTitle(teamPlayers.get(selected), ChatColor.GRAY + "Ne le dites à personne", 30);
                    Kit kit = kitManager.getRandomKit();

                    teamPlayers.get(selected).sendMessage(ChatColor.RED + "Vous obtenez le kit: " + ChatColor.BOLD + kit.getName() + ChatColor.RESET + "" + ChatColor.RED + ". /claim pour le récupérer");
                    this.kits.put(teamPlayers.get(selected), kit);

                    taupes.add(teamPlayers.get(selected));
                    teamPlayers.remove(teamPlayers.get(selected));
                    toSelect--;
                }
            }
        }

        for(Player p : this.inGame){
            if(!taupes.contains(p)){
                p.sendMessage(ChatColor.GRAY + "Vous n'êtes pas la taupe !");
            }
        }


        int needToCreateTeam = this.taupeTeams;
        int taupesPerTeam = taupes.size() / this.taupeTeams;

        int index = 1;

        while (needToCreateTeam > 0){
            int needToAdd = taupesPerTeam;
            List<Player> team = new ArrayList<>();
            while (needToAdd > 0){
                int selected = API.random(0, taupes.size() - 1);
                team.add(taupes.get(selected));
                taupes.remove(taupes.get(selected));
                needToAdd--;
            }
            this.taupesTeams.put(index, team);
            index++;
            needToCreateTeam--;
        }

    }

    public void reduceBorder() {
        BorderTask borderTask = new BorderTask(this);
        borderTask.runTaskTimer(this, 20, 20);
    }

    public boolean isTaupe(Player p) {
        for(int index : this.taupesTeams.keySet()){
            for(Player pl : this.taupesTeams.get(index)){
                if(pl.getUniqueId().equals(p.getUniqueId())){
                    return true;
                }
            }
        }
        return false;
    }

    public void sendTaupeMessage(Player p, String message) {
        int teamIndex = 0;
        global:
        for(int index : this.taupesTeams.keySet()){
            for(Player pl : this.taupesTeams.get(index)){
                if(pl.getUniqueId().equals(p.getUniqueId())){
                    teamIndex = index;
                    break global;
                }
            }
        }

        for(Player taupe : this.taupesTeams.get(teamIndex)){
            taupe.sendMessage(ChatColor.RED + "[" + p.getName() + "] " + ChatColor.GRAY + message);
        }
    }

    public boolean canReveal(Player p) {
        if(isTaupe(p)){
            if(!revealed.contains(p)){
                return true;
            }
        }
        return false;
    }

    public int getTaupeTeamNumber(Player p) {
        for(int number : this.taupesTeams.keySet()){
            if(this.taupesTeams.get(number).contains(p)){
                return number;
            }
        }
        return 0;
    }

    public int getTime() {
        return this.time;
    }


    int time;

    public void setTime(int time) {
        this.time = time;
    }

    public void kill(Player p) {
        Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.GRAY + " est mort !");
        p.setGameMode(GameMode.SPECTATOR);
        p.setHealth(20);
        p.setFoodLevel(20);
        for(ItemStack it : p.getInventory()){
            if(it != null){
                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), it);
            }
        }
        p.getInventory().clear();
        p.setDisplayName(ChatColor.GRAY + p.getName());
        p.setPlayerListName(ChatColor.GRAY + p.getName());
        this.inGame.remove(p);

        this.teamsManager.removePlayer(p);

        this.checkVictory();
    }

    public void killByPlayer(Player p, Player damager) {
        this.kill(p);
    }


    private void checkVictory() {
        for(int teamNumber : this.taupesTeams.keySet()){
            boolean win = true;
            for(Player p : this.inGame){
                if(!this.taupesTeams.get(teamNumber).contains(p)){
                    win = false;
                }
            }
            if(win){
                this.winTaupeTeam(teamNumber);
                return;
            }
        }
        for(Team team : this.teamsManager.getTeams()){
            if(team.isActivated()){
                if(team.getPlayers().size() > 0){
                    for(Team teamTest : this.teamsManager.getTeams()){
                        if(teamTest.isActivated() && !team.getName().equals(teamTest.getName())) {
                            if (teamTest.getPlayers().size() > 0) {
                                return;
                            }
                        }
                    }
                    for(Player p : team.getPlayers()){
                        if(isTaupe(p)){
                            return;
                        }
                    }
                    this.winTeam(team);
                    return;
                }
            }
        }
    }

    private void winTeam(Team team) {
        Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Victoire de l'équipe " + team.getChatColor() + team.getName() + ChatColor.GOLD + " !!!");
    }

    private void winTaupeTeam(int teamNumber) {
        Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Victoire de l'équipe " + ChatColor.RED + "Taupe " + teamNumber + "" + ChatColor.GOLD + " !!!");
    }
}
