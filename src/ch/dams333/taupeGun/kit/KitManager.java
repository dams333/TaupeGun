package ch.dams333.taupeGun.kit;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private List<Kit> kits;

    public KitManager(TaupeGun main) {
        this.main = main;
        kits = new ArrayList<>();
        setupCreeperKit();
        setupPotionKit();
        setupChevalierKit();
        setupArcherKit();
        setupBlazeKit();
    }

    private void setupBlazeKit() {

        List<ItemStack> items = new ArrayList<>();

        items.add(main.API.itemStackManager.create(Material.BLAZE_ROD, 3));

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta m = (EnchantmentStorageMeta) book.getItemMeta();
        m.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
        book.setItemMeta(m);
        items.add(book);

        this.kits.add(new Kit("Blaze", items));

    }

    private void setupArcherKit() {
        List<ItemStack> items = new ArrayList<>();


        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta m = (EnchantmentStorageMeta) book.getItemMeta();
        m.addStoredEnchant(Enchantment.ARROW_DAMAGE, 3, true);
        book.setItemMeta(m);
        items.add(book);

        ItemStack book2 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta m2 = (EnchantmentStorageMeta) book2.getItemMeta();
        m2.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, true);
        book2.setItemMeta(m2);
        items.add(book2);

        this.kits.add(new Kit("Archer", items));
    }

    private void setupChevalierKit() {
        List<ItemStack> items = new ArrayList<>();

        items.add(main.API.itemStackManager.create(Material.DIAMOND_HORSE_ARMOR));
        items.add(main.API.itemStackManager.create(Material.SADDLE));

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta m = (EnchantmentStorageMeta) book.getItemMeta();
        m.addStoredEnchant(Enchantment.DAMAGE_ALL, 3, true);
        book.setItemMeta(m);
        items.add(book);

        this.kits.add(new Kit("Chevalier", items));
    }

    private void setupPotionKit() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack potionS = new ItemStack(Material.SPLASH_POTION);
        PotionMeta sM = (PotionMeta) potionS.getItemMeta();
        sM.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0), true);
        sM.setDisplayName("Potion de slowness");
        sM.setColor(Color.PURPLE);
        potionS.setItemMeta(sM);
        items.add(potionS);

        ItemStack potionW = new ItemStack(Material.SPLASH_POTION);
        PotionMeta wM = (PotionMeta) potionW.getItemMeta();
        wM.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
        wM.setDisplayName("Potion de weakness");
        wM.setColor(Color.PURPLE);
        potionW.setItemMeta(wM);
        items.add(potionW);

        ItemStack potionP = new ItemStack(Material.SPLASH_POTION);
        PotionMeta pM = (PotionMeta) potionP.getItemMeta();
        pM.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 200, 0), true);
        pM.setDisplayName("Potion de poison");
        pM.setColor(Color.GREEN);
        potionP.setItemMeta(pM);
        items.add(potionP);

        this.kits.add(new Kit("Potion", items));
    }

    private void setupCreeperKit() {
        List<ItemStack> items = new ArrayList<>();

        items.add(main.API.itemStackManager.create(Material.CREEPER_SPAWN_EGG));
        items.add(main.API.itemStackManager.create(Material.FLINT_AND_STEEL));
        items.add(main.API.itemStackManager.create(Material.TNT, 5));

        this.kits.add(new Kit("Creeper", items));
    }

    TaupeGun main;


    public Kit getRandomKit(){
        return this.kits.get(main.API.random(0, this.kits.size() - 1));
    }

}
