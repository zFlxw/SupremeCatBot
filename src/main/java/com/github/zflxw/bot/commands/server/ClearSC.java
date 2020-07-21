package com.github.zflxw.bot.commands.server;

import com.github.zflxw.bot.SupremeCatBot;
import com.github.zflxw.bot.interfaces.IServerCommand;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ClearSC implements IServerCommand {
    @Override
    public void performCommand(String command, Server server, User user, TextChannel textChannel, Message message, String[] args) {
        if (server.hasPermission(user, PermissionType.MANAGE_MESSAGES)) {
            if (args.length == 1) {
                int amount = Integer.parseInt(args[0]) + 1;
                int totalMessages = 0;
                try {
                    totalMessages = textChannel.getMessages(amount).get().size();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                message.delete();
                textChannel.getMessages(amount).thenAcceptAsync(MessageSet::deleteAll);
                textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendSuccess("Es wurden ``"+totalMessages+"`` gelöscht!"))
                        .thenAcceptAsync(msg -> server.getApi().getThreadPool().getScheduler().schedule((Runnable) msg::delete, 3, TimeUnit.SECONDS))
                        .exceptionally(ExceptionLogger.get());
            } else {
                textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendUsage("clear <number>"));
            }
        } else {
            textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendNoPermissionMessage("clear"));
        }
    }
}
