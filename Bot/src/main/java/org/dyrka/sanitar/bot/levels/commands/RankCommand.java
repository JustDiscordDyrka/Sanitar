package org.dyrka.sanitar.bot.levels.commands;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.dyrka.sanitar.bot.levels.Levels;
import org.dyrka.sanitar.bot.levels.generator.Rank;

public class RankCommand {
    public RankCommand(SlashCommandInteraction interaction) {
        Levels levelsMain = Levels.instance;

        //TODO: Implement Ars Api.

        new Rank()
                .setXp(1000L) //<- xp from database
                .setId(interaction.getUser().getIdLong())
                .sendGenerationTask();

        //interaction.deferReply().queue();

        interaction.deferReply(true).setContent("Функция в разработке");
    }
}
