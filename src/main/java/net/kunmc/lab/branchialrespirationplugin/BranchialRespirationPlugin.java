package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.kunmc.lab.branchialrespirationplugin.command.CommandHandler;

public class BranchialRespirationPlugin extends JavaPlugin
{
    private static BranchialRespirationPlugin INSTANCE;
    private PlayerManager obj_PlayerManager;
    private AirManager obj_AirManager;

    public static BranchialRespirationPlugin getInstance() 
    {
        return INSTANCE;
    }

    @Override
    public void onEnable() 
    {
        INSTANCE = this;
        this.obj_PlayerManager = new PlayerManager(this);
        this.obj_AirManager = new AirManager(this.obj_PlayerManager);

        CommandHandler commandHandler = new CommandHandler(obj_AirManager);
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
