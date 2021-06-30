package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class BranchialRespirationPlugin extends JavaPlugin
{
    @Override
    public void onEnable() 
    {
        getLogger().info("Branchial Respiration Plugin が導入されました。");
    }

    @Override
    public void onDisable() 
    {
        // Plugin shutdown logic
    }
}
