package com.github.zflxw.bot;

import com.github.zflxw.bot.listener.MessageListener;
import com.github.zflxw.bot.manager.CommandManager;
import com.github.zflxw.bot.util.EmbedUtils;
import com.github.zflxw.bot.util.Secrets;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.user.UserStatus;
import org.javacord.api.util.logging.ExceptionLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SupremeCatBot {
    public final String PREFIX = "$";

    private static SupremeCatBot instance;
    private CommandManager commandManager;
    private EmbedUtils embedUtils;
    public static DiscordApi api;

    public static void main(String[] args) {
        new SupremeCatBot();
    }

    public SupremeCatBot() {
        System.setProperty("file.encoding", "UTF-8");
        initClasses();
        new DiscordApiBuilder()
                .setToken(Secrets.TOKEN)
                .setRecommendedTotalShards().join()
                .loginAllShards()
                .forEach(shardFuture -> shardFuture
                        .thenAcceptAsync(SupremeCatBot::onShardLogin)
                        .exceptionally(ExceptionLogger.get())
                );

    }

    public static SupremeCatBot getInstance() { return instance; }
    public CommandManager getCommandManager() { return commandManager; }
    public EmbedUtils getEmbedUtils() { return embedUtils; }

    private void initClasses() {
        instance = this;
        this.commandManager = new CommandManager();
        this.embedUtils = new EmbedUtils();
    }

    private static void onShardLogin(DiscordApi discordApi) {
        System.out.println("Logged in on Shard: " + discordApi.getCurrentShard());
        discordApi.addMessageCreateListener(new MessageListener());

        discordApi.updateActivity("test");

        System.out.println("You can create the Bot by using the following Link: " + discordApi.createBotInvite());
        shutdown(discordApi);
    }

    private static void shutdown(DiscordApi api) {
        new Thread(() -> {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit")) {
                        if (api != null) {
                            api.updateStatus(UserStatus.OFFLINE);
                            api.disconnect();
                            System.out.println("Bot offline");
                        }
                        reader.close();
                    }
                }
            } catch (IOException ignored) {}
        }).start();
    }
}
