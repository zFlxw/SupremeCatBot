package com.github.zflxw.bot.commands.server;

import com.github.zflxw.bot.SupremeCatBot;
import com.github.zflxw.bot.interfaces.IServerCommand;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.concurrent.TimeUnit;

public class KickSC implements IServerCommand {
    @Override
    public void performCommand(String command, Server server, User user, TextChannel textChannel, Message message, String[] args) {
        if (server.hasPermission(user, PermissionType.KICK_MEMBERS)) {
            if (args.length >= 2) {
                if (!message.getMentionedUsers().isEmpty()) {
                    User target = message.getMentionedUsers().get(0);
                    String reason;
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        strBuilder.append(args[i]).append(" ");
                    }
                    reason = strBuilder.toString().trim();
                    if (!server.hasPermission(target, PermissionType.KICK_MEMBERS)) {
                        target.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendWarning("Du wurdest gekickt!", "Du wurdest wegen ``" + reason + "`` von dem **" + server.getName() + "** Discord gekickt!"));
                                /*.whenComplete((nothing, throwable) -> {
                                    server.kickUser(target, reason).exceptionally(ExceptionLogger.get());
                                    textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendSuccess("Der User **" + target.getName() + "** wurde erfolgreich gekickt!"));
                                })
                                .exceptionally(ExceptionLogger.get());*/
                    } else {
                        textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler!", "Du kannst den User **" + target.getMentionTag() + "** nicht kicken!"));
                    }
                } else {
                    textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler!", "Es wurde kein gepingter User gefunden!"));
                }
            } else {
                textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendUsage("kick <member> <reason>"))
                .thenAcceptAsync(msg -> server.getApi().getThreadPool().getScheduler().schedule((Runnable) msg::delete, 3, TimeUnit.SECONDS))
                .exceptionally(ExceptionLogger.get());
            }
        } else {
            textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendNoPermissionMessage("kick"));
        }
    }
}
