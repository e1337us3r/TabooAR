package com.apolets.tabooar

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.ui_word_card.view.*

class WordCardView(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    : FrameLayout(context, attrs, defStyle) {

    init {
        inflate(context, R.layout.ui_word_card, this)

        btnFaul.setOnClickListener {
            onFaulTapped?.invoke()
        }
        btnPass.setOnClickListener {
            onPassTapped?.invoke()
        }
        btnCorrect.setOnClickListener {
            onCorrectTapped?.invoke()
        }


    }

    var onFaulTapped: (() -> Unit)? = null
    var onPassTapped: (() -> Unit)? = null
    var onCorrectTapped: (() -> Unit)? = null


    var word: Word = Word("WORD", arrayOf("BANNED WORD","BANNED WORD","BANNED WORD"))
        set(value) {
            field = value

            txtWord.text = value.word
            txtBannedWord1.text = value.bannedWords[0]
            txtBannedWord2.text = value.bannedWords[1]
            txtBannedWord3.text = value.bannedWords[2]

        }


}
