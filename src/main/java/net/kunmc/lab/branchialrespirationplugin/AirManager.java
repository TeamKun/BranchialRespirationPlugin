package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAirChangeEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.UUID;


public class AirManager extends BukkitRunnable implements Listener {
    
    private PlayerManager obj_PlayerManager;
    private boolean task_cancel = false;

    private final int PLAYER_MAX_AIR = 300;
    private final double ADD_TICK_DAMAGE = 2.0;
    private final int DEC_TICK_AIR = 20;

    public AirManager(PlayerManager obj_PlayerManager)
    {
        this.obj_PlayerManager = obj_PlayerManager;
    }

    @EventHandler
    public void onEntityAirChange​(EntityAirChangeEvent event)
    {
        Entity entity = event.getEntity();
        EntityType type = entity.getType();

        // プレイヤー以外のエンティティは何もしない
        if(type != EntityType.PLAYER)
        {
            return;
        }
        
        // プレイヤーの頭の位置が水中か判定
        boolean check = checkHeadInWater((Player)entity);
        if(check == false)
        {
            return;
        }

        // 水中時の酸素量設定
        event.setAmount(this.PLAYER_MAX_AIR);
    }
    
    @Override
    public void run() 
    {
        if(this.task_cancel == true)
        {
            this.cancel();
            return;
        }

        // ログイン中のプレイヤーリスト取得
        ArrayList<UUID> uuid_list = this.obj_PlayerManager.getPlayerList();

        for(UUID uuid : uuid_list)
        {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null)
            {
                PlayerAirManager(player);
            }
        }
    }

    // プラグインの有効化
    public void startAirManager(Plugin plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.task_cancel = false;
        this.runTaskTimer(plugin, 20, 20);
    }

    // プラグインの無効化
    public void stopAirManager()
    {
        EntityAirChangeEvent.getHandlerList().unregister(this);
        this.task_cancel = true;
    }

    // プレイヤーの頭の位置が水中か判定
    private boolean checkHeadInWater(Player player)
    {
        boolean result = false;

        Location pl_loc = player.getLocation();
        pl_loc.setY(pl_loc.getY() + 1.0);

        Block pl_head_block = pl_loc.getBlock();
        Material type = pl_head_block.getType();

        if(type == Material.WATER)
        {
            result = true;
        }

        return result;
    }

    // プレイヤーの酸素残量管理
    private void PlayerAirManager(Player player)
    {
        boolean check = checkHeadInWater(player);
        int now_air = player.getRemainingAir();

        // プレイヤーの頭の位置が水中か判定
        if(check == false)
        {
            // プレイヤーの頭が水中にない場合
            if(now_air > 0)
            {
                // プレイヤーの酸素値設定
                player.setRemainingAir(now_air - this.DEC_TICK_AIR);
            }
            else
            {
                //プレイヤーの酸素値が0の場合ダメージを与える 
                player.damage(this.ADD_TICK_DAMAGE);
            }
        }
        else
        {
            // プレイヤーの頭が水中にある場合
            if(now_air < this.PLAYER_MAX_AIR)
            {
                player.setRemainingAir(now_air + this.DEC_TICK_AIR);
            }
        }
    }
}
