package com.apolets.tabooar

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.ui_scoreboard.view.*

class ScoreboardView(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    : FrameLayout(context, attrs, defStyle) {

    init {
        inflate(context, R.layout.ui_scoreboard, this)

        btnStartGame.setOnClickListener {
            it.isEnabled = false
            onStartTapped?.invoke()
        }
    }

    var onStartTapped: (() -> Unit)? = null
    var score1: Int = 0
        set(value) {
            field = value
            txtTeam1Score.text = value.toString()
        }
    var score2: Int = 0
        set(value) {
            field = value
            txtTeam2Score.text = value.toString()
        }

    var time: Int = 30
    set(value) {
        field = value
        txtTime.text = value.toString()
    }

}
