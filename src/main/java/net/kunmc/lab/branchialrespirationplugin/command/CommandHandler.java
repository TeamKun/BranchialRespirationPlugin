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
    private ConfigManager obj_ConfigManager;
    private boolean enable_flg = false;

    private final double MIN_TICK_DAMAGE = 1.0;
    private final double MAX_TICK_DAMAGE = 10.0;
    private final int MIN_TICK_DEC_AIR = 3;
    private final int MAX_TICK_DEC_AIR = 10;
    private final int MIN_TICK_ADD_AIR = 2;
    private final int MAX_TICK_ADD_AIR = 10;

    public CommandHandler(AirManager obj_AirManager, ConfigManager obj_ConfigManager)
    {
        this.obj_AirManager = obj_AirManager;
        this.obj_ConfigManager = obj_ConfigManager;
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
            else if(args[0].equals("set"))
            {
                setParameter(sender, args[1], args[2]);
                result = true;
            }
            else if(args[0].equals("info"))
            {
                getInfo(sender);
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
                return Stream.of("on", "off", "set", "info", "help")
                        .filter(e -> e.startsWith(args[0]))
                        .collect(Collectors.toList());
            case 2:
                if(args[0].equals("set"))
                {
                    return Stream.of("damage", "decair", "addair")
                            .filter(e -> e.startsWith(args[1]))
                            .collect(Collectors.toList());
                }
            case 3:
                if(args[1].equals("damage"))
                {
                    return Stream.of(MIN_TICK_DAMAGE + " ~ " + MAX_TICK_DAMAGE + " の間で指定してください")
                            .filter(e -> e.startsWith(args[2]))
                            .collect(Collectors.toList());
                }
                else if(args[1].equals("decair"))
                {
                    return Stream.of(MIN_TICK_DEC_AIR + " ~ " + MAX_TICK_DEC_AIR + " の間で指定してください")
                            .filter(e -> e.startsWith(args[2]))
                            .collect(Collectors.toList());
                }
                else if(args[1].equals("addair"))
                {
                    return Stream.of(MIN_TICK_ADD_AIR + " ~ " + MAX_TICK_ADD_AIR + " の間で指定してください")
                            .filter(e -> e.startsWith(args[2]))
                            .collect(Collectors.toList());
                }
            default:
                return Collections.emptyList();

        }
    }
    
    // 起動コマンド処理
    private void onEraKokyu(CommandSender sender)
    {
        if(this.enable_flg == false)
        {
            this.obj_AirManager.startAirManager(BranchialRespirationPlugin.getInstance());
            this.enable_flg = true;
            sender.sendMessage("えら呼吸プラグインが有効になりました");
        }
        else
        {
            sender.sendMessage("すでにプラグインが有効になっています");
        }
    }

    // 終了コマンド処理
    private void offEraKokyu(CommandSender sender)
    {
        if(this.enable_flg == true)
        {
            this.obj_AirManager.stopAirManager();
            this.enable_flg = false;
            sender.sendMessage("えら呼吸プラグインが無効になりました");
        }
        else
        {
            sender.sendMessage("すでにプラグインが無効になっています");
        }
    }

    // パラメータ設定
    private void setParameter(CommandSender sender, String cmd, String par)
    {
        if(cmd.equals("damage"))
        {
            try {
                double num = Double.parseDouble(par);
                if(num >= MIN_TICK_DAMAGE || num <= MAX_TICK_DAMAGE)
                {
                    this.obj_ConfigManager.setDamage(num);
                }
                else
                {
                    sender.sendMessage(MIN_TICK_DAMAGE + " ~ " + MAX_TICK_DAMAGE + " の間で指定してください");
                }
            } catch (Exception e) {
                sender.sendMessage("小数点で指定してください");
            }
        }
        else if(cmd.equals("decair"))
        {
            try {
                int num = Integer.parseInt(par);
                if(num >= MIN_TICK_DEC_AIR || num <= MAX_TICK_DEC_AIR)
                {
                    this.obj_ConfigManager.setDecAir(num);
                }
                else
                {
                    sender.sendMessage(MIN_TICK_DEC_AIR + " ~ " + MAX_TICK_DEC_AIR + " の間で指定してください");
                }
            } catch (Exception e) {
                sender.sendMessage("整数で指定してください");
            }
        }
        else if(cmd.equals("addair"))
        {
            try {
                int num = Integer.parseInt(par);
                if(num >= MIN_TICK_ADD_AIR || num <= MAX_TICK_ADD_AIR)
                {
                    this.obj_ConfigManager.setAddAir(num);
                }
                else
                {
                    sender.sendMessage(MIN_TICK_ADD_AIR + " ~ " + MAX_TICK_ADD_AIR + " の間で指定してください");
                }
            } catch (Exception e) {
                sender.sendMessage("整数で指定してください");
            }
        }
    }
    
    // 現在の設定中パラメータ確認
    private void getInfo(CommandSender sender)
    {
        sender.sendMessage("1Tickあたりのダメージ量 ： " + this.obj_ConfigManager.getDamage());
        sender.sendMessage("1Tickあたりの酸素減少量 ： " + this.obj_ConfigManager.getDecAir());
        sender.sendMessage("1Tickあたりの酸素増加量 ： " + this.obj_ConfigManager.getAddAir());
        sender.sendMessage("プレイヤーの最大酸素量 ： " + this.obj_ConfigManager.getMaxAir());
    }

    // コマンドヘルプ取得処理
    private void getHelp(CommandSender sender)
    {
        // コマンドのへプルを表示
        sender.sendMessage("erakokyu on [プラグインを有効化]");
        sender.sendMessage("erakokyu off [プラグインを無効化]");
        sender.sendMessage("erakokyu set damage (" + MIN_TICK_DAMAGE + " ~ " + MAX_TICK_DAMAGE + ") [酸素がなくなったときに受ける、1tick当たりのダメージ量]");
        sender.sendMessage("erakokyu set decair (" + MIN_TICK_DEC_AIR + " ~ " + MAX_TICK_DEC_AIR + ") [プレイヤーが地上にいるときの、酸素の減少量]");
        sender.sendMessage("erakokyu set addair (" + MIN_TICK_ADD_AIR + " ~ " + MAX_TICK_ADD_AIR + ") [プレイヤーが水中にいるときの、酸素の増加量]");
        sender.sendMessage("erakokyu info [現在の設定パラメータを確認]");
    }
}