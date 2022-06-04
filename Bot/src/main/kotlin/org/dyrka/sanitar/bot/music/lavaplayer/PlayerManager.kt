package org.dyrka.sanitar.bot.music.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent

class PlayerManager {
    private val musicManagers: MutableMap<Long, GuildMusicManager>
    private val audioPlayerManager: AudioPlayerManager

    init {
        musicManagers = HashMap()
        audioPlayerManager = DefaultAudioPlayerManager()
        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    fun getMusicManager(guild: Guild): GuildMusicManager {
        return musicManagers.computeIfAbsent(guild.idLong) { guildId: Long? ->
            val guildMusicManager = GuildMusicManager(audioPlayerManager)
            guild.audioManager.sendingHandler = guildMusicManager.sendHandler
            guildMusicManager
        }
    }

    fun loadAndPlay(event: GenericCommandInteractionEvent, channel: TextChannel, trackUrl: String?) {
        val musicManager = getMusicManager(channel.guild)
        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                event.interaction.hook.sendMessage(
                    """
    :white_check_mark: Добавляю в очередь: `${track.info.title}`
    :bust_in_silhouette: Автор: `${track.info.author}`
    """.trimIndent()
                ).queue()
                musicManager.scheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                if (!playlist.isSearchResult) {
                    val tracks = playlist.tracks
                    event.interaction.hook.sendMessage(
                        """:white_check_mark: Добавляю в очередь: `${tracks.size} треков из плейлиста`
:film_frames: Плейлист: `${playlist.name}`"""
                    ).queue()
                    for (track in tracks) {
                        musicManager.scheduler.queue(track)
                    }
                    return
                }
                val track = playlist.tracks[0]
                musicManager.scheduler.queue(track)
                event.interaction.hook.sendMessage(
                    """
    :white_check_mark: Добавляю в очередь: `${track.info.title}`
    :bust_in_silhouette: Автор: `${track.info.author}`
    """.trimIndent()
                ).queue()
            }

            override fun noMatches() {
                event.interaction.hook.sendMessage(":x: Я ничего не нашёл!").queue()
            }

            override fun loadFailed(exception: FriendlyException) {
                event.interaction.hook.sendMessage(":x: Я не смог загрузить трек!").queue()
            }
        })
    }

    companion object {
        private var INSTANCE: PlayerManager? = null
        val instance: PlayerManager?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = PlayerManager()
                }
                return INSTANCE
            }
    }
}