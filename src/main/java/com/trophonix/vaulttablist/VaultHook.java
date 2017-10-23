package com.trophonix.vaulttablist;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class VaultHook {

    private Chat chat;
    private Permission permission;

    public boolean initialize() {
        RegisteredServiceProvider<Chat> chatRsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (chatRsp == null) return false;
        chat = chatRsp.getProvider();

        RegisteredServiceProvider<Permission> permissionRsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        if (permissionRsp == null) return false;
        permission = permissionRsp.getProvider();

        return chat != null && permission != null;
    }

}
