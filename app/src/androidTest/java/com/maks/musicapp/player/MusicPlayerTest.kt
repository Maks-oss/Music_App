package com.maks.musicapp.player

import android.media.MediaPlayer
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.utils.music_player.MusicPlayer
import com.maks.musicapp.utils.music_player.MusicPlayerImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MusicPlayerTest {

    private lateinit var musicPlayer: MusicPlayer
    private var trackMinutes = 0
    private lateinit var mediaPlayer: MediaPlayer

    @Before
    fun setup() {
        mediaPlayer =
            MediaPlayer.create(InstrumentationRegistry.getInstrumentation().context, Uri.parse(FakeDataProvider.provideFakeAudio()))
        UiThreadStatement.runOnUiThread {
            musicPlayer = MusicPlayerImpl(mediaPlayer, onTick = {
                trackMinutes = mediaPlayer.currentPosition
                if (mediaPlayer.currentPosition == mediaPlayer.duration) {
                    trackMinutes = 0
                }
            })
        }
    }

    @Test
    fun musicPlayerPlayMethodShouldPlayAudio() {
        musicPlayer.play()
        Thread.sleep(1000)
        assertThat(trackMinutes).isGreaterThan(0)
    }
    @Test
    fun musicPlayerPauseMethodShouldPauseAudio() {
        musicPlayer.play()
        Thread.sleep(2000)
        val currentMinutes = trackMinutes
        musicPlayer.pause()
        Thread.sleep(2000)
        assertThat(trackMinutes).isEqualTo(currentMinutes)
    }
    @Test
    fun musicPlayerDurationMethodShouldReturnAudioDuration() = assertThat(musicPlayer.duration()).isEqualTo(mediaPlayer.duration)

    @Test
    fun musicPlayerSeekToMethodShouldSeekToAudioMinute() {
        musicPlayer.seekTo(1f)
        assertThat(mediaPlayer.currentPosition).isEqualTo(1)
    }

}