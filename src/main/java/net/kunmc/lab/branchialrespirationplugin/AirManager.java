package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAirChangeEvent;

public class AirManager extends BukkitRunnable implements Listener {
    
    boolean task_cancel = false;

    public AirManager()
    {
    }

    @EventHandler
    public void onEntityAirChange​(EntityAirChangeEvent event)
    {
        // 水中時の酸素量設定
        event.setAmount(1000);
    }
    
    @Override
    public void run() 
    {
        // 地上時の酸素チェック処理

        if(this.task_cancel == true)
        {
            this.cancel();
        }
    }

    public void startAirManager(Plugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.task_cancel = false;
        this.runTaskTimer(plugin, 20, 20);
    }

    public void stopAirManager()
    {
        EntityAirChangeEvent.getHandlerList().unregister(this);
        this.task_cancel = true;
    }
}
