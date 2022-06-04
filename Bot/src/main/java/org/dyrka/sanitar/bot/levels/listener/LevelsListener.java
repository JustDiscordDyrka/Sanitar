package org.dyrka.sanitar.bot.levels.listener;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.dyrka.sanitar.bot.levels.commands.RankCommand;
import org.dyrka.sanitar.database.DataBaseAPI;
import org.jetbrains.annotations.NotNull;

public class LevelsListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch(event.getInteraction().getName()) {
            case "rank": new RankCommand(event.getInteraction());
        }
    }
}
