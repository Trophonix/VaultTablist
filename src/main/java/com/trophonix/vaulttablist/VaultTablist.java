package com.trophonix.vaulttablist;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultTablist extends JavaPlugin implements Listener {

    private static VaultTablist instance;

    @Getter private VaultHook vaultHook;

    private Tablist tablist;

    @Override
    public void onEnable() {
        instance = this;
        vaultHook = new VaultHook();
        if (!vaultHook.initialize()) {
            getLogger().warning("Failed to hook into Vault!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        tablist = new Tablist();
        tablist.reload();
        Bukkit.getOnlinePlayers().forEach(tablist::sendScoreboard);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getScheduler().runTaskTimer(this, tablist::reload, 0, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                tablist.reload();
                sender.sendMessage(ChatColor.YELLOW + "Reloaded tablist!");
                return true;
            }
        }
        sender.sendMessage(ChatColor.YELLOW + "Use" + ChatColor.GOLD + " /" + label + " reload " + ChatColor.YELLOW + "to reload the tablist!");
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        tablist.loadPlayer(event.getPlayer());
        tablist.addPlayer(event.getPlayer());
        tablist.sendScoreboard(event.getPlayer());
    }

    public static VaultTablist get() {
        return instance;
    }

}
