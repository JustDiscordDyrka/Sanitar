package org.dyrka.sanitar.bot.music.commands

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import org.dyrka.sanitar.bot.music.lavaplayer.GuildMusicManager
import org.dyrka.sanitar.bot.music.lavaplayer.PlayerManager
import org.koin.core.context.GlobalContext


fun nowPlayingCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("now-playing") { event ->
        val channel: TextChannel = event.textChannel
        val self: Member = event.guild!!.selfMember
        val selfVoiceState: GuildVoiceState? = self.voiceState

        if (!selfVoiceState!!.inAudioChannel()) {
            event.reply(":x: Я не в голосовом канале!").queue()
            return@onCommand
        }

        val member = event.member!!
        val memberVoiceState: GuildVoiceState? = member.voiceState

        if (!memberVoiceState!!.inAudioChannel()) {
            event.reply(":x: Ты не в голосовом канале!").queue()
            return@onCommand
        }

        if (memberVoiceState.channel != selfVoiceState.channel) {
            event.reply(":x: Ты не в том же голосовом канале, что и я!").queue()
            return@onCommand
        }

        val musicManager: GuildMusicManager = PlayerManager.instance!!.getMusicManager(event.guild!!)
        val audioPlayer: AudioPlayer = musicManager.audioPlayer
        val track = audioPlayer.playingTrack

        if (track == null) {
            event.reply(":x: Сейчас ничего не играет!").queue()
            return@onCommand
        }

        val info = track.info

        event.reply(
            """
                :musical_note: Сейчас играет: `${info.title}`
                :bust_in_silhouette: Автор: `${info.author}`
                :link: Ссылка: ${info.uri}
                """.trimIndent()
        ).queue()
    }

}
