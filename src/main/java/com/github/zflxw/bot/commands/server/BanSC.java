package com.github.zflxw.bot.commands.server;

import com.github.zflxw.bot.SupremeCatBot;
import com.github.zflxw.bot.interfaces.IServerCommand;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.concurrent.TimeUnit;

public class BanSC implements IServerCommand {
    @Override
    public void performCommand(String command, Server server, User user, TextChannel textChannel, Message message, String[] args) {
        if (server.hasPermission(user, PermissionType.BAN_MEMBERS)) {
            if (args.length >= 3) {
                if (!message.getMentionedUsers().isEmpty()) {
                    User target = message.getMentionedUsers().get(0);
                    try {
                        int deleteMessages = Integer.parseInt(args[1]);
                        String reason;
                        StringBuilder strBuilder = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            strBuilder.append(args[i]).append(" ");
                        }
                        reason = strBuilder.toString().trim();
                        if (!server.hasPermission(target, PermissionType.BAN_MEMBERS)) {
                            target.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Du wurdest gebannt!", "Du wurdest wegen ``" + reason + "`` von dem **" + server.getName() + "** Discord gebannt! Nutze ``" + SupremeCatBot.getInstance().PREFIX + "apply`` um einen Entbannungsantrag zu schreiben!"))
                                    .whenComplete((nothing, throwable) -> {
                                        server.banUser(target, deleteMessages, reason).exceptionally(ExceptionLogger.get());
                                        textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendSuccess("Der User **" + target.getName() + "** wurde erfolgreich gebannt!"));
                                    })
                                    .exceptionally(ExceptionLogger.get());
                        } else {
                            textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler!", "Du kannst den User **" + target.getMentionTag() + "** nicht bannen!"));
                        }
                    } catch (NumberFormatException exception) {
                        textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Syntaxfehler", "Du musst eine Zahl als zweites Argument angeben!"));
                    }
                } else {
                    textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler!", "Es wurde kein gepingter User gefunden!"));
                }
            } else {
                textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendUsage("ban <member> <deleteMessages> <reason>"))
                        .thenAcceptAsync(msg -> server.getApi().getThreadPool().getScheduler().schedule((Runnable) msg::delete, 3, TimeUnit.SECONDS))
                        .exceptionally(ExceptionLogger.get());
            }
        } else {
            textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendNoPermissionMessage("ban"));
        }
    }
}
