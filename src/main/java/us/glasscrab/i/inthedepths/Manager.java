package us.glasscrab.i.inthedepths;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Manager {
    private static Manager manager;
    private final List<Material> upgradeableItems = new ArrayList<>();
    public Manager() {
        manager = this;
    }

    public boolean isUpgradeable(Material material) {
        // Netherite Armor:
        upgradeableItems.add(Material.NETHERITE_HELMET);
        upgradeableItems.add(Material.NETHERITE_CHESTPLATE);
        upgradeableItems.add(Material.NETHERITE_LEGGINGS);
        upgradeableItems.add(Material.NETHERITE_BOOTS);

        // Netherite Tools:
        upgradeableItems.add(Material.NETHERITE_PICKAXE);
        upgradeableItems.add(Material.NETHERITE_AXE);
        upgradeableItems.add(Material.NETHERITE_SHOVEL);
        upgradeableItems.add(Material.NETHERITE_HOE);
        upgradeableItems.add(Material.NETHERITE_SWORD);

        // Miscellaneous:
        upgradeableItems.add(Material.FISHING_ROD);
        upgradeableItems.add(Material.MACE);
        upgradeableItems.add(Material.BOW);
        upgradeableItems.add(Material.CROSSBOW);

        return upgradeableItems.contains(material);
    }

    public Set<Enchantment> getUpgradeableEnchantmentList(){
        Set<Enchantment> upgradeableEnchantmentList = new HashSet<>();
        // Weapons
        upgradeableEnchantmentList.add(Enchantment.SHARPNESS);
        upgradeableEnchantmentList.add(Enchantment.SMITE);
        upgradeableEnchantmentList.add(Enchantment.BANE_OF_ARTHROPODS);
        upgradeableEnchantmentList.add(Enchantment.WIND_BURST);
        upgradeableEnchantmentList.add(Enchantment.POWER);
        upgradeableEnchantmentList.add(Enchantment.QUICK_CHARGE);

        // Tools
        upgradeableEnchantmentList.add(Enchantment.EFFICIENCY);
        upgradeableEnchantmentList.add(Enchantment.LURE);

        // Armour
        upgradeableEnchantmentList.add(Enchantment.PROTECTION);
        upgradeableEnchantmentList.add(Enchantment.BLAST_PROTECTION);
        upgradeableEnchantmentList.add(Enchantment.FIRE_PROTECTION);
        upgradeableEnchantmentList.add(Enchantment.PROJECTILE_PROTECTION);

        return upgradeableEnchantmentList;
    }

    public boolean containsUpgradeableEnchant (Map<Enchantment,Integer> enchantments){
        Set<Enchantment> upgradeableToolEnchantmentList = getUpgradeableEnchantmentList();
        for(Enchantment e : enchantments.keySet()){
            if(upgradeableToolEnchantmentList.contains(e)){
                return true;
            }
        }
        return false;
    }

    public void dropOpal(Item droppedItem){
        droppedItem.getWorld().dropItem(droppedItem.getLocation(), this.makeOpal());
    }

    public ItemStack makeOpal() {
        List<String> lore = new ArrayList<>();
        ItemStack item = new ItemStack(Material.ECHO_SHARD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Charged Opal");
        lore.add(ChatColor.GRAY + "A shimmering jewel that can be inset into powerful items.");
        meta.setLore(lore);

        lore.clear();

        meta.setCustomModelData(1);

        item.setItemMeta(meta);

        return item;
    }

    public static Manager getManager() {
        return manager;
    }
}
