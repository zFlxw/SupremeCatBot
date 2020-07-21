package com.github.zflxw.bot.commands.dm;

import com.github.zflxw.bot.SupremeCatBot;
import com.github.zflxw.bot.interfaces.IPrivateCommand;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class HelpPC implements IPrivateCommand {
    @Override
    public void performCommand(String command, User user, PrivateChannel privateChannel, Message message, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        String description;

        ConcurrentHashMap<String, IPrivateCommand> privateCommands = SupremeCatBot.getInstance().getCommandManager().privateCommands;
        privateCommands.forEach((key, value) -> stringBuilder.append("**• ").append(key).append("**"));

        description = stringBuilder.toString().trim();

        try {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Command Overview")
                    .setThumbnail("https://cdn.discordapp.com/attachments/722499400953233470/734451884001198100/List.png")
                    .setColor(Color.GREEN)
                    .setFooter("Made by Flxw with <3", user.getApi().getUserById("315574841031786496").get().getAvatar())
                    .setTimestamp(Instant.now())
                    .setDescription(description);

            privateChannel.sendMessage(builder);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
