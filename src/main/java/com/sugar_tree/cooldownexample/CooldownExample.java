package com.sugar_tree.cooldownexample;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class CooldownExample extends JavaPlugin implements TabExecutor {

    static Map<UUID, Long> cooldown1Map = new HashMap<>();
    static double cooldown1 = 1.0 * 1000; //컴퓨터의 시간으로 계산(단위: 밀리초) (1초 = 1000밀리초)

    static Map<UUID, Integer> cooldown2Map = new HashMap<>();
    static int cooldown2 = 20; //마크 틱으로 계산(단위 틱) (1초 = 20틱)

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("test1").setExecutor(this);
        getCommand("test1").setTabCompleter(this);

        getCommand("test2").setExecutor(this);
        getCommand("test2").setTabCompleter(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("test1")) {
            if (!sender.isOp()) {
                sender.sendMessage(Bukkit.permissionMessage());
                return true;
            }
            if (sender instanceof Player p) {
                if (cooldown1Map.containsKey(p.getUniqueId())) { //Map에 UUID가 없으면 맵.get(Key);를 사용할때 오류 발생
                    if (!(cooldown1Map.get(p.getUniqueId()) < (System.currentTimeMillis() - cooldown1))) {
                        p.sendMessage(ChatColor.RED + "This Apility is on Cooldown (" + ((cooldown1Map.get(p.getUniqueId()) + cooldown1 - System.currentTimeMillis()) / 1000.0 /*1000.0으로 하면 소수점이 나옴*/) + "s)");
                    }
                    else {
                        cooldown1Map.put(p.getUniqueId(), System.currentTimeMillis());
                        p.sendMessage(ChatColor.GREEN + "test1 커맨드가 성공적으로 실행되었습니다.");
                    }
                }
                else {
                    cooldown1Map.put(p.getUniqueId(), System.currentTimeMillis());
                    p.sendMessage(ChatColor.GREEN + "test1 커맨드가 성공적으로 실행되었습니다.");
                }
            }
            return true;
        }

        else if (command.getName().equalsIgnoreCase("test2")) {
            if (!sender.isOp()) {
                sender.sendMessage(Bukkit.permissionMessage());
                return true;
            }
            if (sender instanceof Player p) {
                if (cooldown2Map.containsKey(p.getUniqueId())) {
                    if (!(cooldown2Map.get(p.getUniqueId()) < (getServer().getCurrentTick() - cooldown2))) {
                        p.sendMessage(ChatColor.RED + "This Apility is on Cooldown (" + ((cooldown2Map.get(p.getUniqueId()) + cooldown2 - getServer().getCurrentTick()) / 20.0 /*20.0으로 하면 소수점이 나옴*/) + "s)");
                    }
                    else {
                        cooldown2Map.put(p.getUniqueId(), getServer().getCurrentTick());
                        p.sendMessage(ChatColor.GREEN + "test2 커맨드가 성공적으로 실행되었습니다.");
                    }
                }
                else {
                    cooldown2Map.put(p.getUniqueId(), getServer().getCurrentTick());
                    p.sendMessage(ChatColor.GREEN + "test2 커맨드가 성공적으로 실행되었습니다.");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("test1")) {
            return new ArrayList<>();
        }

        else if (command.getName().equalsIgnoreCase("test2")) {
            return new ArrayList<>();
        }
        return null;
    }
}
