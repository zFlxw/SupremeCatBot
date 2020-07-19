package com.github.zflxw.bot.manager;

import com.github.zflxw.bot.commands.dm.PCommand_Help;
import com.github.zflxw.bot.commands.server.SCommand_Clear;
import com.github.zflxw.bot.interfaces.IServerCommand;
import com.github.zflxw.bot.interfaces.IPrivateCommand;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public ConcurrentHashMap<String, IServerCommand> commands;
    public ConcurrentHashMap<String, IPrivateCommand> privateCommands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();
        this.privateCommands = new ConcurrentHashMap<>();

        // Commands
        this.commands.put("clear", new SCommand_Clear());

        // Private Commands
        this.privateCommands.put("help", new PCommand_Help());
    }

    public boolean performServerCommand(String command, Server server, User user, TextChannel channel, Message message, String[] args) {
        IServerCommand serverCommand;
        if ((serverCommand = this.commands.get(command.toLowerCase())) != null) {
            serverCommand.performCommand(command, server, user, channel, message, args);
            return true;
        }
        return false;
    }

    public boolean performPrivateCommand(String command, User user, PrivateChannel channel, Message message, String[] args) {
        IPrivateCommand privateCommand;
        if ((privateCommand = this.privateCommands.get(command.toLowerCase())) != null) {
            privateCommand.performCommand(command, user, channel, message, args);
            return true;
        }
        return false;
    }
}
