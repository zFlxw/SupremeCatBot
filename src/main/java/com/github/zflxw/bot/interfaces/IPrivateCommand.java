package com.github.zflxw.bot.interfaces;

import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

import java.util.concurrent.ExecutionException;

public interface IPrivateCommand {
    public void performCommand(String command, User user, PrivateChannel privateChannel, Message message, String[] args);
}
