package net.kunmc.lab.branchialrespirationplugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
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

    public void deletePlayer(UUID uuid)
    {
        this.player_lists.remove(uuid);
    }
    
}