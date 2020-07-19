package com.github.zflxw.bot.util;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class EmbedUtils {

    public EmbedBuilder sendError(String title) {
        return new EmbedBuilder()
                .setTitle("<:error:734014570565337148> " + title)
                .setColor(Color.RED);
    }

    public EmbedBuilder sendError(String title, String desc) {
        return new EmbedBuilder()
                .setTitle("<:error:734014570565337148> " + title)
                .setColor(Color.RED)
                .setDescription(desc);
    }

    public EmbedBuilder sendNoPermissionMessage(String command) {
        return new EmbedBuilder()
                .setTitle("<:error:734014570565337148> Fehler! Du hast keine Rechte dazu, den Befehl ``" + command + "`` auszuführen!")
                .setColor(Color.RED);
    }

    public EmbedBuilder sendUsage(String usage) {
        return new EmbedBuilder()
                .setTitle(":warning: Warnung! Falsche Syntax")
                .setColor(Color.ORANGE)
                .setDescription("Nutze ``"+usage+"``!");
    }

    public EmbedBuilder sendWarning(String title, String desc) {
        return new EmbedBuilder()
                .setTitle(":warning: Warnung! " + title)
                .setColor(Color.ORANGE)
                .setDescription(desc);
    }

    public EmbedBuilder sendSuccess(String command, String desc) {
        return new EmbedBuilder()
                .setTitle("<:success:734037912794169405> Erfolg!")
                .setColor(Color.GREEN)
                .setDescription(desc);
    }
}
