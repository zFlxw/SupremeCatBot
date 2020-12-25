package com.github.zflxw.bot.commands.server;

import com.github.zflxw.bot.SupremeCatBot;
import com.github.zflxw.bot.interfaces.IServerCommand;
import org.javacord.api.entity.Region;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.DefaultMessageNotificationLevel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.VerificationLevel;
import org.javacord.api.entity.user.User;

public class CreateGuildSC implements IServerCommand {
    @Override
    public void performCommand(String command, Server server, User user, TextChannel textChannel, Message message, String[] args) {
        if (server.hasPermission(user, PermissionType.ADMINISTRATOR)) {
            if (args.length == 2) {
                // create <name>
                server.getApi().createServerBuilder()
                        .setName("RichiHD | Entbannung")
                        .setRegion(Region.EU_WEST)
                        .setVerificationLevel(VerificationLevel.LOW)
                        .setDefaultMessageNotificationLevel(DefaultMessageNotificationLevel.ALL_MESSAGES)
                        .create();
            }
        } else {
            textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendNoPermissionMessage("create"));
        }
    }
}
