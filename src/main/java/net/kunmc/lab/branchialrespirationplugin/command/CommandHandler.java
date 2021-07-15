package net.kunmc.lab.branchialrespirationplugin.command;

import net.kunmc.lab.branchialrespirationplugin.AirManager;
import net.kunmc.lab.branchialrespirationplugin.BranchialRespirationPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private AirManager obj_AirManager;

    public CommandHandler(AirManager obj_AirManager)
    {
        this.obj_AirManager = obj_AirManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) 
    {
        Boolean result = false;


        if(args.length == 0)
        {
            sender.sendMessage("不正なコマンドです");
            sender.sendMessage("[erakokyu help] でコマンドを確認してください");
        }
        else if (command.getName().equalsIgnoreCase("erakokyu")) 
        {
            if(args[0].equals("on"))
            {
                onEraKokyu(sender);
                result = true;
            }
            else if(args[0].equals("off"))
            {
                offEraKokyu(sender);
                result = true;
            }
            else if(args[0].equals("help"))
            {
                getHelp(sender);
                result = true;
            }
            else
            {
                sender.sendMessage("不正なコマンドです");
                sender.sendMessage("[erakokyu help] でコマンドを確認してください");
            }
        }
        
        return result;
    }

    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) 
    {
        switch(args.length)
        {
            case 1:
                return Stream.of("on", "off", "help")
                        .filter(e -> e.startsWith(args[0]))
                        .collect(Collectors.toList());

        }

        return Collections.emptyList();
    }
    
    // 起動コマンド処理
    private void onEraKokyu(CommandSender sender)
    {
        this.obj_AirManager.startAirManager(BranchialRespirationPlugin.getInstance());

        sender.sendMessage("えら呼吸プラグインが有効になりました");
    }

    // 終了コマンド処理
    private void offEraKokyu(CommandSender sender)
    {
        this.obj_AirManager.stopAirManager();
        
        sender.sendMessage("えら呼吸プラグインが無効になりました");
    }
    
    // コマンドヘルプ取得処理
    private void getHelp(CommandSender sender)
    {
        // コマンドのへプルを表示
        sender.sendMessage("erakokyu on [プラグインを有効化]");
        sender.sendMessage("erakokyu off [プラグインを無効化]");
    }
}