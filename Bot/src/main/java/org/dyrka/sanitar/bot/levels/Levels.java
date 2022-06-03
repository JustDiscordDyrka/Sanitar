package org.dyrka.sanitar.bot.levels;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.dyrka.sanitar.bot.levels.listener.LevelsListener;
import org.koin.java.KoinJavaComponent;

public class Levels {
    private JDA jda = KoinJavaComponent.get(JDA.class);

    public static final Levels instance = new Levels();

    public Levels() {
        jda.addEventListener(new LevelsListener());

        jda.getGuildById("621722954002333696").updateCommands()
                .addCommands(
                        Commands.slash("rank", "Заставляет бота показать твой уровень Санитара!")
                                .addOption(OptionType.INTEGER, "id", "Если ты хочешь узнать свой уровень, оставь это поле пустым!", false)
                )
                .queue();
    }

    public JDA getJda() {
        return jda;
    }
}
