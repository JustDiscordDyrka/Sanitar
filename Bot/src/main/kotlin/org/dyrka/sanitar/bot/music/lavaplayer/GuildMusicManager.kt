package org.dyrka.sanitar.bot.music.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager(manager: AudioPlayerManager) {
    val audioPlayer: AudioPlayer
    val scheduler: TrackScheduler
    val sendHandler: AudioPlayerSendHandler

    init {
        audioPlayer = manager.createPlayer()
        scheduler = TrackScheduler(audioPlayer)
        audioPlayer.addListener(scheduler)
        sendHandler = AudioPlayerSendHandler(audioPlayer)
    }
}