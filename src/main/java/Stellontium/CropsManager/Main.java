package Stellontium.CropsManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    FileConfiguration config;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        //使用可能か
        Bukkit.getPluginCommand("cm").setExecutor(new CropsManager(this));
        CropsManager.plantC = config.getBoolean("power.PlantCrops",false);

    }
}
