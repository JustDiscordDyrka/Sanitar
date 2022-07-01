package org.dyrka.sanitar.bot.levels.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.dyrka.sanitar.bot.levels.commands.RankCommand;
import org.dyrka.sanitar.database.DataBaseAPI;
import org.dyrka.sanitar.database.level.LevelDataBase;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LevelsListener extends ListenerAdapter {
    Cache<Long, Long> messageCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch(event.getInteraction().getName()) {
            case "rank": new RankCommand(event.getInteraction());
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(!event.isFromGuild())
            return;

        if(messageCache.asMap().containsKey(event.getAuthor().getIdLong()))
            return;

        Integer[] possibleXps = {15, 25};

        Integer gainedXp = possibleXps[new Random().nextInt(possibleXps.length)];

        LevelDataBase levels = DataBaseAPI.Companion.getInstance().getLevels();

        levels.setLevel(event.getAuthor().getIdLong(), levels.getLevel(event.getAuthor().getIdLong()) + gainedXp);

        messageCache.put(event.getAuthor().getIdLong(), event.getAuthor().getIdLong());
    }
}
