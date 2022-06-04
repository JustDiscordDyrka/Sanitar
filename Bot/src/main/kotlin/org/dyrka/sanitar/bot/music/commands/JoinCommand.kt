package org.dyrka.sanitar.bot.music.commands

import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.AudioManager
import org.koin.core.context.GlobalContext

fun joinCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("join") { event ->
        val channel: TextChannel = event.textChannel
        val self: Member = event.guild!!.selfMember
        val selfVoiceState: GuildVoiceState? = self.voiceState

        event.deferReply().queue()

        if (selfVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Я уже в голосовом канале!").queue()
            return@onCommand
        }

        val member: Member? = event.member
        val memberVoiceState: GuildVoiceState? = member!!.voiceState

        if (!memberVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Ты не в голосовом канале!").queue()
            return@onCommand
        }

        val audioManager: AudioManager = event.guild!!.audioManager
        val memberChannel: VoiceChannel = memberVoiceState.channel as VoiceChannel

        audioManager.openAudioConnection(memberChannel)
        event.interaction.hook.sendMessage(":white_check_mark: Присоединяюсь к " + memberChannel.name).queue()
    }

}