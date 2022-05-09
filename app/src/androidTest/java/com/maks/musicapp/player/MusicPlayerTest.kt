package com.maks.musicapp.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.maks.musicapp.fakedata.FakeDataProvider
import com.maks.musicapp.utils.player.MusicPlayer
import com.maks.musicapp.utils.player.MusicPlayerImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MusicPlayerTest {
    lateinit var musicPlayer: MusicPlayer
    var trackMinutes = 0
    @Before
    fun setup(){
        val mediaPlayer = MediaPlayer.create(InstrumentationRegistry.getInstrumentation().context, Uri.parse(FakeDataProvider.provideFakeAudio()))
        musicPlayer = MusicPlayerImpl(mediaPlayer, onTick = {
            trackMinutes = mediaPlayer.currentPosition
            if (mediaPlayer.currentPosition == mediaPlayer.duration){
                trackMinutes = 0
            }
        })
    }
    @Test
    fun musicPlayerPlayMethodShouldPlayAudio(){
        musicPlayer.play()
        assertThat(trackMinutes).isGreaterThan(0)
    }
}