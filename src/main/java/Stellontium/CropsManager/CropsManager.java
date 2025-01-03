package Stellontium.CropsManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class CropsManager implements Listener {
    public static boolean plantC = false;
    public static ItemStack edamame;
    public CropsManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);

        createEdamame();

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (plantC) {
            Block block = e.getBlock();
            Material cType = block.getType();
            //作物の場合その作物を植える
            if (cType == Material.WHEAT || cType == Material.POTATOES || cType == Material.BEETROOTS || cType == Material.CARROTS) {
                Ageable ageable = (Ageable) e.getBlock().getBlockData();
                //成長が最大値か
                if (ageable.getAge() != ageable.getMaximumAge()) {
                    if (ageable.getAge() == ageable.getMaximumAge()-1) {
                        e.setCancelled(true);
                        block.setType(cType);
                        block.getWorld().dropItemNaturally(block.getLocation(),edamame);
                    }
                    return;
                }
                e.setCancelled(true);
                //自然な破壊
                block.breakNaturally();
                block.setType(cType);
                //幸運付きの場合おまけを
                ItemStack mainhand = e.getPlayer().getInventory().getItemInMainHand();
                if (mainhand.getType() != Material.AIR) {
                    if (mainhand.getEnchantmentLevel(Enchantment.FORTUNE) == 0) {

                    } else {
                        block.getWorld().dropItemNaturally(block.getLocation(),new ItemStack(Material.EMERALD,mainhand.getEnchantmentLevel(Enchantment.FORTUNE)));
                    }
                }
            }
        }
    }

    //食べ物とされるアイテムを食べたとき
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().isSimilar(edamame)) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER,200,1));
            e.getPlayer().sendMessage("えだまめ生で食べちゃった");
        }
    }

    //成長を1戻す
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getPlayer().isOp()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material cType = e.getClickedBlock().getType();
                if (cType == Material.WHEAT || cType == Material.POTATOES || cType == Material.BEETROOTS || cType == Material.CARROTS) {
                    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                        Ageable ageable = (Ageable) e.getClickedBlock().getBlockData();
                        if (ageable.getAge() == 0) {
                            e.getPlayer().sendMessage("成長値はもう0よ");
                            return;
                        }
                        ageable.setAge(ageable.getAge()-1);
                        e.getClickedBlock().setBlockData(ageable);
                        e.getPlayer().sendMessage("成長を戻しました");
                    }
                    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {
                        Ageable ageable = (Ageable) e.getClickedBlock().getBlockData();
                        ageable.setAge(ageable.getMaximumAge()-1);
                        e.getClickedBlock().setBlockData(ageable);
                        e.getPlayer().sendMessage("成長を最大値-1にしました");
                    }
                }
            }
        }
    }

    void createEdamame() {
        edamame = U.createItem(Material.POISONOUS_POTATO,"えだまめ", Arrays.asList("成長しきる前に収穫される物を代表して…","えだまめ生で食べちゃだめだよ"));
        ItemMeta meta = edamame.getItemMeta();
        FoodComponent fc = meta.getFood();
        fc.setCanAlwaysEat(true);
        fc.setNutrition(0);
        fc.setSaturation(1);
        meta.setFood(fc);
        edamame.setItemMeta(meta);
    }
}
