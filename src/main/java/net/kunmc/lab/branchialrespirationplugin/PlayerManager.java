package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class PlayerManager implements Listener 
{
    private static PlayerManager INSTANCE;
    ArrayList<UUID> player_lists;

    public PlayerManager(Plugin plugin)
    {
        INSTANCE = this;
        this.player_lists = new ArrayList<>();
        
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        addPlayer();
    }

    public static PlayerManager getInstance() 
    {
        return INSTANCE;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) 
    {
        // ログイン時に全プレイヤーのUUIDを記録
        UUID pl_uuid = event.getPlayer().getUniqueId();
        this.player_lists.add(pl_uuid);
    }

    public ArrayList<UUID> getPlayerList()
    {
        return this.player_lists;
    }

    public void addPlayer()
    {
        // すでにログイン済みの全プレイヤーのUUIDを記録
        Collection<Player> players = new ArrayList<>( Bukkit.getOnlinePlayers() );

        for (Player player : players)
        {
            UUID pl_uuid = player.getUniqueId();
            this.player_lists.add(pl_uuid);
        }
    }

    public void deletePlayer(UUID uuid)
    {
        this.player_lists.remove(uuid);
    }
    
}
