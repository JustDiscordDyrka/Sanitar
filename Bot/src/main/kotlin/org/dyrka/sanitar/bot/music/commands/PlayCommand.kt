package org.dyrka.sanitar.bot.music.commands

import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Member
import org.dyrka.sanitar.bot.music.lavaplayer.PlayerManager
import org.koin.core.context.GlobalContext
import java.net.URI
import java.net.URISyntaxException

fun playCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("play") { event ->
        val channel = event.textChannel

        event.deferReply().queue()

        if (event.options.isEmpty()) {
            event.interaction.hook.sendMessage(":x: Ты не указал ссылку на музыку или файл!").queue()
            return@onCommand
        }

        val self: Member = event.guild!!.selfMember
        val selfVoiceState = self.voiceState

        if (!selfVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(" :x: Я не в голосовом канале!").queue()
            return@onCommand
        }

        val member = event.member!!
        val memberVoiceState = member.voiceState

        if (!memberVoiceState!!.inAudioChannel()) {
            event.interaction.hook.sendMessage(":x: Ты не в голосовом канале!").queue()
            return@onCommand
        }

        if (memberVoiceState.channel != selfVoiceState.channel) {
            event.interaction.hook.sendMessage(":x: Ты не в том же голосовом канале, что и я!").queue()
            return@onCommand
        }

        var link: String = event.getOption("track")!!.asString
        if (!isUrl(link)) {
            link = "ytsearch:$link"
        }
        PlayerManager.instance!!.loadAndPlay(event, channel, link)
    }

}

private fun isUrl(url: String): Boolean {
    return try {
        URI(url)
        true
    } catch (e: URISyntaxException) {
        false
    }
}