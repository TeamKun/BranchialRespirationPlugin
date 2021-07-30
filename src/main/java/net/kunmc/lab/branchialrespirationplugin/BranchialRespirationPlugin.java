package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import net.kunmc.lab.branchialrespirationplugin.command.CommandHandler;
import net.kunmc.lab.branchialrespirationplugin.command.ConfigManager;

public class BranchialRespirationPlugin extends JavaPlugin
{
    private static BranchialRespirationPlugin INSTANCE;

    public static BranchialRespirationPlugin getInstance() 
    {
        return INSTANCE;
    }

    @Override
    public void onEnable() 
    {
        INSTANCE = this;

        saveDefaultConfig();
        FileConfiguration config = getConfig();

        ConfigManager obj_ConfigManager = new ConfigManager(config);
        PlayerManager obj_PlayerManager = new PlayerManager(this);
        AirManager obj_AirManager = new AirManager();

        CommandHandler commandHandler = new CommandHandler(obj_AirManager, obj_ConfigManager);
        getServer().getPluginCommand("erakokyu").setExecutor(commandHandler);
        getServer().getPluginCommand("erakokyu").setTabCompleter(commandHandler);

        getLogger().info("Branchial Respiration Plugin が導入されました。");
    }

    @Override
    public void onDisable() 
    {
        // Plugin shutdown logic
    }
}
