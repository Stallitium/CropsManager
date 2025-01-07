package Stellontium.CropsManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class U {
    //アイテム作成
    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    //アイテム渡す
    public static void addItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(),item);
        } else {
            player.getInventory().addItem(item);
        }
    }
}
