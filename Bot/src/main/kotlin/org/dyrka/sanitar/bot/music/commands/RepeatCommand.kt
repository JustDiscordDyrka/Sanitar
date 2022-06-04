package org.dyrka.sanitar.bot.music.commands

import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.TextChannel
import org.dyrka.sanitar.bot.music.lavaplayer.GuildMusicManager
import org.dyrka.sanitar.bot.music.lavaplayer.PlayerManager
import org.koin.core.context.GlobalContext

fun repeatCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("repeat") { event ->
        val channel: TextChannel = event.textChannel
        val self: Member = event.guild!!.selfMember
        val selfVoiceState: GuildVoiceState? = self.voiceState

        event.deferReply().queue()

        if (!selfVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Я не в голосовом канале!").queue()
            return@onCommand
        }

        val member = event.member!!
        val memberVoiceState: GuildVoiceState? = member.voiceState

        if (!memberVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Ты не в голосовом канале!").queue()
            return@onCommand
        }

        if (memberVoiceState.channel!! != selfVoiceState.channel) {
            event.interaction.hook.sendMessage(":x: Ты не в том же голосовом канале, что и бот!").queue()
            return@onCommand
        }

        val musicManager: GuildMusicManager = PlayerManager.instance!!.getMusicManager(event.guild!!)

        val newRepeating: Boolean = !musicManager.scheduler.repeating

        musicManager.scheduler.repeating = newRepeating

        event.interaction.hook.sendMessage(":cyclone: Установлен режим: `" + (if (newRepeating) "повторение" else "обычный") + "`")
            .queue()
    }

}
