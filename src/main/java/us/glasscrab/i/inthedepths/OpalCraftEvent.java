package us.glasscrab.i.inthedepths;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OpalCraftEvent implements Listener {
    private final Manager manager;

    public OpalCraftEvent(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void opalCombine(PlayerSwapHandItemsEvent e){
        if(e.getMainHandItem() == null) return;
        if(e.getOffHandItem() == null) return;
        if(!manager.isUpgradeable(e.getOffHandItem().getType())) return;
        if(!e.getMainHandItem().getType().equals(Material.ECHO_SHARD)) return;
        if(e.getMainHandItem().getItemMeta() == null) return;
        if(!e.getMainHandItem().getItemMeta().hasCustomModelData()) return;
        if(e.getMainHandItem().getItemMeta().getCustomModelData() != 1) return;

        MiniMessage miniMessage = MiniMessage.miniMessage();

        ItemStack netheriteItem = e.getOffHandItem();
        ItemMeta meta = netheriteItem.getItemMeta();
        ItemStack opal = e.getMainHandItem();

        if(manager.containsUpgradeableEnchant(e.getOffHandItem().getEnchantments())){
            for(Enchantment ench : e.getOffHandItem().getEnchantments().keySet()){
                if(manager.getUpgradeableEnchantmentList().contains(ench)){
                    if(meta.getEnchantLevel(ench) == ench.getMaxLevel() + 1){
                        e.getPlayer().sendActionBar(miniMessage.deserialize("<red>This item already has an opal inset!</red>"));
                        e.setCancelled(true);
                        return;
                    }
                    e.getMainHandItem().setAmount(e.getMainHandItem().getAmount() - 1);
                    meta.addEnchant(ench, ench.getMaxLevel() + 1, true);
                }
            }
        }

        else if(e.getOffHandItem().getEnchantments().size() == 0 || !manager.containsUpgradeableEnchant(e.getOffHandItem().getEnchantments())){
            e.getPlayer().sendActionBar(miniMessage.deserialize("<red>This item is inert, it cannot accept an opal!"));
            e.setCancelled(true);
            return;
        }

        List<Component> itemLore;
        if(meta.lore() == null){
            itemLore = new ArrayList<>();
        }
        else{
            itemLore = meta.lore();
        }

        itemLore.add(miniMessage.deserialize("<aqua>♢</aqua><gray>Charged Opal</gray><aqua>♢</aqua>"));
        meta.lore(itemLore);
        netheriteItem.setItemMeta(meta);

        e.setMainHandItem(netheriteItem);
        e.setOffHandItem(opal);

        e.getPlayer().sendActionBar(miniMessage.deserialize("<aqua>Opal inset into tool!</aqua>"));

        //VV this only works if you're playing on 1.19.4 VV
        e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_AMETHYST_BLOCK_FALL, SoundCategory.PLAYERS, 1,1);

    }
}
