package Stellontium.CropsManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    FileConfiguration config;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        //使用可能か
        CropsManager.plantC = config.getBoolean("power.PlantCrops",false);
        new CropsManager(this);
    }
}
