package com.github.zflxw.bot.interfaces;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public interface IServerCommand {
    // Die performCommand Methode, die ich bereits in der JDA gezeigt habe
    public void performCommand(String command, Server server, User user, TextChannel textChannel, Message message, String[] args);
}
