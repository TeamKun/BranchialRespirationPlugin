package net.kunmc.lab.branchialrespirationplugin.command;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager extends JavaPlugin 
{
    private FileConfiguration config;

    public ConfigManager()
    {
        saveDefaultConfig();
        this.config = getConfig();
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
        return this.config.getInt("maxAir");
    }
}

