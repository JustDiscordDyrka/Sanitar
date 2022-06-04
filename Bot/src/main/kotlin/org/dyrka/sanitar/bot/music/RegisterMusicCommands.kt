package org.dyrka.sanitar.bot.music

import dev.minn.jda.ktx.interactions.commands.option
import dev.minn.jda.ktx.interactions.commands.slash
import dev.minn.jda.ktx.interactions.commands.updateCommands
import net.dv8tion.jda.api.JDA
import org.dyrka.sanitar.bot.music.commands.*
import org.koin.core.context.GlobalContext

fun registerMusicCommands() {

    val jda = GlobalContext.get().get<JDA>()

    val dyrka = jda.getGuildById("621722954002333696")

    dyrka!!.updateCommands {

        slash("join", "Эта команда заставляет бота присоединиться к голосовому каналу")
        slash("leave", "Эта команда заставляет бота покинуть голосовой канал")
        slash("play", "Эта команда заставляет бота проигрывать трек в голосовом канале") {
            option<String>("track", "Поисковый запрос или ссылка на трек", true)
        }
        slash("queue", "Эта команда показывает очередь треков")
        slash("now-playing", "Эта команда показывает текущий трек")
        slash("repeat", "Эта команда заставляет трек повторяться до бесконечности")
        slash("skip", "Эта команда пропускает трек")
        slash("stop", "Эта команда останавливает трек")
        slash("volume", "Эта команда изменяет громкость трека") {
            option<Int>("volume", "Громкость трека", true)
        }
        slash("pause", "Эта команда приостанавливает трек")

    }.queue()

    joinCommandHandler()
    leaveCommandHandler()
    playCommandHandler()
    queueCommandHandler()
    nowPlayingCommandHandler()
    repeatCommandHandler()
    skipCommandHandler()
    stopCommandHandler()
    volumeCommandHandler()
    pauseCommandHandler()

}