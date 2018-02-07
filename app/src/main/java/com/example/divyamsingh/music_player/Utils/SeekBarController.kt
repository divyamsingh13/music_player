package com.example.divyamsingh.music_player.Utils

/**
 * Created by Divyam Singh on 1/23/2018.
 */
import android.widget.SeekBar
import com.example.divyamsingh.music_player.Fragments.SongPlayingFragment

class SeekBarController : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        if (SongPlayingFragment.Statified.mediaPlayer == null)
            return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        if (p0?.progress!! < SongPlayingFragment.Statified.mediaPlayer!!.duration) {
            SongPlayingFragment.Statified.mediaPlayer!!.seekTo(p0?.progress!!)
        } else {
            SongPlayingFragment.Statified.mediaPlayer?.seekTo((SongPlayingFragment.Statified.mediaPlayer?.duration)!!.toInt())
        }
    }
}