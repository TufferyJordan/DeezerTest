package com.deezer.test.playerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deezer.test.interfaces.player.PlayerController
import com.deezer.test.interfaces.player.PlayerListener
import kotlinx.android.synthetic.main.fragment_player.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class PlayerFragment: Fragment(), PlayerListener {

    private val playerController: PlayerController by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.visibility = View.GONE
        playerController.registerListener(this)
        playerTitleText.isSelected = true

        playerPlayPauseButton.setOnClickListener {
            playerController.resumePause()
        }
        playerStopButton.setOnClickListener {
            playerController.stop()
        }
    }

    override fun onDestroyView() {
        playerController.unregisterListener(this)
        super.onDestroyView()
    }

    override fun onPlayPlayer() {
        view?.visibility = View.VISIBLE
        playerPlayPauseButton.setImageResource(R.drawable.ic_pause)
        playerTitleText.text = playerController.currentAlbumTitle
    }

    override fun onPausePlayer() {
        view?.visibility = View.VISIBLE
        playerPlayPauseButton.setImageResource(R.drawable.ic_play_arrow)
        playerTitleText.text = playerController.currentAlbumTitle
    }

    override fun onStopPlayer() {
        view?.visibility = View.GONE
    }

    companion object {
        fun newInstance() = PlayerFragment()
    }
}