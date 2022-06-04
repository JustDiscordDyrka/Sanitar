package org.dyrka.sanitar.bot.music.commands

import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import org.dyrka.sanitar.bot.music.lavaplayer.GuildMusicManager
import org.dyrka.sanitar.bot.music.lavaplayer.PlayerManager
import org.koin.core.context.GlobalContext

fun leaveCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("leave") { event ->
        val channel = event.textChannel
        val self: Member = event.guild!!.selfMember
        val selfVoiceState = self.voiceState

        event.deferReply().queue()

        if (!selfVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Я не в голосовом канале!").queue()
            return@onCommand
        }

        val member = event.member!!
        val memberVoiceState = member.voiceState

        if (!memberVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Ты не в голосовом канале!").queue()
            return@onCommand
        }

        if (memberVoiceState.channel != selfVoiceState.channel) {
            event.interaction.hook.sendMessage(":x: Ты не в том же голосовом канале, что и бот!").queue()
            return@onCommand
        }

        val guild: Guild = event.guild!!

        val musicManager: GuildMusicManager = PlayerManager.instance!!.getMusicManager(guild)

        musicManager.scheduler.repeating = false
        musicManager.scheduler.queue.clear()
        musicManager.audioPlayer.stopTrack()

        val audioManager = event.guild!!.audioManager

        audioManager.closeAudioConnection()

        event.interaction.hook.sendMessage(":white_check_mark: Вышел из голосового канала!").queue()
    }

}