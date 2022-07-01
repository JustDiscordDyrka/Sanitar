package org.dyrka.sanitar.bot.levels.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.dyrka.sanitar.bot.levels.generator.Rank;
import org.dyrka.sanitar.database.DataBaseAPI;

import java.util.Date;

public class RankCommand {
    public RankCommand(SlashCommandInteraction interaction) {
        Long userXp = DataBaseAPI.Companion.getInstance().getLevels().getLevel(interaction.getUser().getIdLong());

//      new Rank()
//                .setXp(1000L) //<- xp from database
//                .setId(interaction.getUser().getIdLong())
//                .sendGenerationTask();

//      interaction.deferReply().queue();

        interaction.reply(new MessageBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setTitle(interaction.getUser().getAsTag())
                        .setThumbnail(interaction.getUser().getAvatarUrl())
                        .addField("Уровень", "В разработке", true)
                        .addField("Опыт", String.valueOf(userXp), true)
                        .addField("К след. уровню", "В разработке", true)
                        .setFooter("Просто Дурка в Дискорде :D")
                        .setTimestamp(new Date().toInstant())
                        .build()
                ).build()
        ).queue();
    }
}
