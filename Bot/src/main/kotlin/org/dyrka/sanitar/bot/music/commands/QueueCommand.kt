package org.dyrka.sanitar.bot.music.commands

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.minn.jda.ktx.events.onCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.requests.restaction.MessageAction
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

        if (queue.isEmpty()) {
            event.reply(":x: Очередь пуста!").queue()
            return@onCommand
        }

        event.reply("Смотрю очередь...").queue()

        val trackCount = queue.size.coerceAtMost(20)
        val trackList: ArrayList<AudioTrack?> = ArrayList(queue)

        val messageAction: MessageAction = channel.sendMessage(":white_check_mark: Очередь:\n")

        for (i in 0 until trackCount) {
            val track = trackList[i]
            val info = track!!.info
            messageAction.append('#')
                .append((i + 1).toString())
                .append(" `")
                .append(info.title)
                .append(" by ")
                .append(info.author)
                .append("` [`")
                .append(formatTime(track.duration))
                .append("`]\n")
        }


        if (trackList.size > trackCount) {
            messageAction.append("А также `")
                .append((trackList.size - trackCount).toString())
                .append("` других треков!")
        }

        messageAction.queue()
    }

}

private fun formatTime(timeInMillis: Long): String {
    val hours = timeInMillis / TimeUnit.HOURS.toMillis(1)
    val minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1)
    val seconds = timeInMillis % TimeUnit.SECONDS.toMillis(1) / TimeUnit.SECONDS.toMillis(1)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
