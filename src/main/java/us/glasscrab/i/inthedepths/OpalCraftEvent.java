package us.glasscrab.i.inthedepths;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
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

        ItemStack netheriteItem = e.getOffHandItem();
        ItemMeta meta = netheriteItem.getItemMeta();
        ItemStack opal = e.getMainHandItem();

        if(manager.containsUpgradeableToolEnchant(e.getOffHandItem().getEnchantments())){
            for(Enchantment ench : e.getOffHandItem().getEnchantments().keySet()){
                if(manager.getUpgradeableToolEnchantmentList().contains(ench)){
                    if(meta.getEnchantLevel(ench) == 6){
                        String message = ChatColor.RED + "This item already has an opal inset!";
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                        e.setCancelled(true);
                        return;
                    }
                    e.getMainHandItem().setAmount(e.getMainHandItem().getAmount() - 1);
                    meta.addEnchant(ench, 6, true);
                }
            }
        }

        else if(manager.containsUpgradeableArmorEnchant(e.getOffHandItem().getEnchantments())){
            for(Enchantment ench : e.getOffHandItem().getEnchantments().keySet()){
                if(manager.getUpgradeableArmorEnchantmentList().contains(ench)){
                    if(meta.getEnchantLevel(ench) == 5){
                        String message = ChatColor.RED + "This item already has an opal inset!";
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                        e.setCancelled(true);
                        return;
                    }
                    e.getMainHandItem().setAmount(e.getMainHandItem().getAmount() - 1);
                    meta.addEnchant(ench, 5, true);
                }
            }
        }

        else if(e.getOffHandItem().getEnchantments().size() == 0 || !manager.containsUpgradeableToolEnchant(e.getOffHandItem().getEnchantments()) || !manager.containsUpgradeableArmorEnchant(e.getOffHandItem().getEnchantments())){
            String message = ChatColor.RED + "This item is inert, it cannot accept an opal!";
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            e.setCancelled(true);
            return;
        }

        List<String> itemLore;
        if(meta.getLore() == null){
            itemLore = new ArrayList<>();
        }
        else{
            itemLore = meta.getLore();
        }

        itemLore.add(ChatColor.AQUA + "♢" + ChatColor.GRAY + "Charged Opal" + ChatColor.AQUA + "♢");
        meta.setLore(itemLore);
        netheriteItem.setItemMeta(meta);

        e.setMainHandItem(netheriteItem);
        e.setOffHandItem(opal);

        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "Opal inset into tool!"));

        //VV this only works if you're playing on 1.19.4 VV
        e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_AMETHYST_BLOCK_FALL, SoundCategory.PLAYERS, 1,1);

    }
}
