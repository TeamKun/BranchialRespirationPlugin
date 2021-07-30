package net.kunmc.lab.branchialrespirationplugin.command;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager
{
    private static ConfigManager INSTANCE;
    private FileConfiguration config;

    public static ConfigManager getInstance() 
    {
        return INSTANCE;
    }

    public ConfigManager(FileConfiguration config)
    {
        INSTANCE = this;
        this.config = config;
    }

    public void setDamage(double damage)
    {
        this.config.set("addTickDamage", damage);
    }

    public double getDamage()
    {
        return this.config.getDouble("addTickDamage");
    }

    public void setDecAir(int air)
    {
        this.config.set("decTickAir", air);
    }

    public int getDecAir()
    {
        return this.config.getInt("decTickAir");
    }

    public void setAddAir(int air)
    {
        this.config.set("addTickAir", air);
    }

    public int getAddAir()
    {
        return this.config.getInt("addTickAir");
    }

    public int getMaxAir()
    {
        return this.config.getInt("playerMaxAir");
    }
}

