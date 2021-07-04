package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.kunmc.lab.branchialrespirationplugin.command.CommandHandler;

public class BranchialRespirationPlugin extends JavaPlugin
{
    private static BranchialRespirationPlugin INSTANCE;

    // public static BranchialRespirationPlugin getInstance() {
    //     return INSTANCE;
    // }

    @Override
    public void onEnable() 
    {
        INSTANCE = this;

        CommandHandler commandHandler = new CommandHandler();
        getServer().getPluginCommand("erakokyu").setExecutor(commandHandler);
        getServer().getPluginCommand("erakokyu").setTabCompleter(commandHandler);

        AirManager obj_air_manager = new AirManager();

        getLogger().info("Branchial Respiration Plugin が導入されました。");
    }

    @Override
    public void onDisable() 
    {
        // Plugin shutdown logic
    }
}
