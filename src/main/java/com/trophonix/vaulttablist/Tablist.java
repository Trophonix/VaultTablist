package com.trophonix.vaulttablist;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


public class Tablist {

    private Scoreboard scoreboard;

    public Tablist() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void reload() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayer);
    }

    public void loadPlayer(Player player) {
        Chat chat = VaultTablist.get().getVaultHook().getChat();
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) team = scoreboard.registerNewTeam(player.getName());
        String prefix = chat.getPlayerPrefix(player);
        if (prefix != null && !prefix.isEmpty()) {
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        } else {
            team.setPrefix("");
        }
        String suffix = chat.getPlayerSuffix(player);
        if (suffix != null && !suffix.isEmpty()) {
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
        } else {
            team.setSuffix("");
        }
        team.addEntry(player.getName());
    }

    public void addPlayer(Player player) {
        Team team = scoreboard.getTeam(player.getName());
        team.addEntry(player.getName());
    }

    public void sendScoreboard(Player player) {
        player.setScoreboard(scoreboard);
    }

}
