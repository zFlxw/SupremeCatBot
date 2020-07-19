package com.github.zflxw.bot.listener;

import com.github.zflxw.bot.SupremeCatBot;
import org.javacord.api.Javacord;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MessageListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        MessageAuthor author = event.getMessageAuthor();
        if (author.asUser().isPresent() && !author.asUser().get().isBot()) {
            if (event.getMessage().getContent().substring(SupremeCatBot.getInstance().PREFIX.length()).length() > 0) {
                Message message = event.getMessage();
                String rawMessage = message.getContent();
                String prefix = SupremeCatBot.getInstance().PREFIX;
                String command = rawMessage.substring(prefix.length()).split(" ")[0];

                // Has Arguments
                String[] args;
                if (rawMessage.length() >= (command.length() + prefix.length() + 1)) {
                    args = rawMessage.substring((prefix.length() + command.length() + 1)).split(" ");
                } else {
                    args = new String[0];
                }
                if (event.isServerMessage()) {
                    executeServerCommand(event, author, message, rawMessage, prefix, command, args);
                } else if (event.isPrivateMessage()) {
                    executePrivateCommand(event, author, message, rawMessage, prefix, command, args);
                }
            }
        }
    }

    private void executeServerCommand(MessageCreateEvent event, MessageAuthor author, Message message, String rawMessage, String prefix, String command, String[] args) {
        if (event.getServer().isPresent()) {
            Server server = event.getServer().get();
            TextChannel textChannel = event.getChannel();
            if (rawMessage.startsWith(prefix)) {
                if (author.asUser().isPresent() && (author.asUser().get() != event.getApi().getYourself())) {
                    User user = author.asUser().get();
                    if (!SupremeCatBot.getInstance().getCommandManager().performServerCommand(command, server, user, textChannel, message, args)) {
                        textChannel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler! Dieser Befehl existiert nicht"));
                    }
                }
            }
        }
    }

    private void executePrivateCommand(MessageCreateEvent event, MessageAuthor author, Message message, String rawMessage, String prefix, String command, String[] args) {
        if ((event.getPrivateChannel().isPresent() && event.getMessageAuthor().asUser().isPresent()) && (event.getMessageAuthor().asUser().get() != event.getApi().getYourself())) {
            PrivateChannel channel = event.getPrivateChannel().get();
            User user = author.asUser().get();
            if (rawMessage.startsWith(prefix)) {
                if (!SupremeCatBot.getInstance().getCommandManager().performPrivateCommand(command, user, channel, message, args)) {
                    channel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendError("Fehler! Dieser Befehl existiert nicht.", "Achtung: Du befindest dich in dem DM-Chat mit dem Bot. Nutze ``" + prefix + "help`` um eine Liste von allen verwendbaren Befehlen zu erhalten"));
                }
            } else {
                channel.sendMessage(SupremeCatBot.getInstance().getEmbedUtils().sendWarning("Achtung: DM-Chat!", "Du befindest dich in einem DM-Chat. Schreibe ``" + prefix + "help`` um eine Liste mit allen verwendbaren Befehlen zu erhalten"));
            }
        }
    }
}
