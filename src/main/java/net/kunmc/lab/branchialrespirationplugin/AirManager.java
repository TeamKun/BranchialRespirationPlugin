package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAirChangeEvent;

import org.bukkit.entity.*;


public class AirManager implements Listener {
    
    private PlayerAirTask obj_PlayerAirTask;

    public AirManager()
    {
    }

    @EventHandler
    public void onEntityAirChange​(EntityAirChangeEvent event)
    {
        // プレイヤー以外のエンティティは何もしない
        EntityType type = event.getEntity().getType();
        if(type != EntityType.PLAYER)
        {
            return;
        }

        // 水中時の酸素量設定
        event.setAmount(event.getAmount());
    }

    // プラグインの有効化
    public void startAirManager(Plugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.obj_PlayerAirTask = new PlayerAirTask(PlayerManager.getInstance());
        this.obj_PlayerAirTask.runTaskTimer(plugin, 20, 20);
    }

    // プラグインの無効化
    public void stopAirManager()
    {
        EntityAirChangeEvent.getHandlerList().unregister(this);
        this.obj_PlayerAirTask.cancel();
    }
}
