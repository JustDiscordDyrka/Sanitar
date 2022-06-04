package org.dyrka.sanitar.bot.music.commands

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.requests.restaction.MessageAction
import net.dv8tion.jda.api.requests.restaction.WebhookMessageAction
import org.dyrka.sanitar.bot.music.lavaplayer.GuildMusicManager
import org.dyrka.sanitar.bot.music.lavaplayer.PlayerManager
import org.koin.core.context.GlobalContext
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit


fun queueCommandHandler() {

    val jda = GlobalContext.get().get<JDA>()

    jda.onCommand("queue") { event ->
        val channel: TextChannel = event.textChannel
        val musicManager: GuildMusicManager = PlayerManager.instance!!.getMusicManager(event.guild!!)
        val queue: BlockingQueue<AudioTrack?> = musicManager.scheduler.queue

        event.deferReply().queue()

        if (queue.isEmpty()) {
            event.interaction.hook.sendMessage(":x: Очередь пуста!").queue()
            return@onCommand
        }


        val trackCount = queue.size.coerceAtMost(20)
        val trackList: ArrayList<AudioTrack?> = ArrayList(queue)

        val answer = buildString {
            append(":white_check_mark: Очередь:\n")

            for (i in 0 until trackCount) {
                val track = trackList[i]
                val info = track!!.info

                append("#${i + 1} `${info.title}` by `${info.author}` `[${formatTime(track.duration)}]`\n")
            }


            if (trackList.size > trackCount) {
                append("А также `${trackList.size - trackCount}` других треков!")
            }
        }

        event.interaction.hook.sendMessage(answer).queue()
    }

}

private fun formatTime(timeInMillis: Long): String {
    val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
    val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
    val seconds = timeInMillis % TimeUnit.SECONDS.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
