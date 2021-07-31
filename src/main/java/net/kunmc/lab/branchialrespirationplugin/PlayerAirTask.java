package net.kunmc.lab.branchialrespirationplugin;

import net.kunmc.lab.branchialrespirationplugin.command.ConfigManager;

import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.UUID;
import java.util.Set;

public class PlayerAirTask extends BukkitRunnable
{
    private PlayerManager obj_PlayerManager;
    private ConfigManager obj_ConfigManager;

    private int playerMaxAir;
    private double addTickDamage;
    private int decTickAir;
    private int addTickAir;

    public PlayerAirTask(PlayerManager obj_PlayerManager, ConfigManager obj_ConfigManager)
    {
        this.obj_PlayerManager = obj_PlayerManager;
        this.obj_ConfigManager = obj_ConfigManager;

        getParameter();
    }
    
    @Override
    public void run() 
    {
        // ログイン中のプレイヤーリスト取得
        Set<UUID> uuid_list = this.obj_PlayerManager.getPlayerList();

        for(UUID uuid : uuid_list)
        {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null)
            {
                PlayerAirManager(player);
            }
        }
    }

    // プレイヤーの酸素残量管理
    private void PlayerAirManager(Player player)
    {
        getParameter();
        
        boolean check = checkHeadInWater(player);
        int now_air = player.getRemainingAir();

        // プレイヤーの頭の位置が水中か判定
        if(check == false)
        {
            // プレイヤーの頭が水中にない場合
            int dec_air = (now_air - this.decTickAir) > 0 ? this.decTickAir : now_air;
            player.setRemainingAir(now_air - dec_air);

            if((now_air - dec_air) <= 0)
            {
                //プレイヤーの酸素値が0の場合ダメージを与える 
                player.damage(this.addTickDamage);
            }
        }
        else
        {
            // プレイヤーの頭が水中にある場合
            int dec_air = (this.playerMaxAir - now_air) > this.addTickAir ? this.addTickAir : (this.playerMaxAir - now_air);
            player.setRemainingAir(now_air + dec_air);
        }
    }

    // プレイヤーの頭の位置が水中か判定
    private boolean checkHeadInWater(Player player)
    {
        boolean result = false;
        Location pl_loc = player.getLocation();

        // プレイヤーが水泳中かチェック
        LivingEntity le = (LivingEntity)player;
        if(!le.isSwimming())
        {
            pl_loc.setY(pl_loc.getY() + 1.0);
        }

        // プレイヤーの頭の位置のブロック取得
        Block pl_head_block = pl_loc.getBlock();
        if(pl_head_block.getType() == Material.WATER)
        {
            result = true;
        }

        return result;
    }

    // パラメータをコンフィグから取得して更新
    private void getParameter()
    {
        this.addTickDamage = this.obj_ConfigManager.getDamage();
        this.decTickAir = this.obj_ConfigManager.getDecAir();
        this.addTickAir = this.obj_ConfigManager.getAddAir();
        this.playerMaxAir = obj_ConfigManager.getMaxAir();
    }
}