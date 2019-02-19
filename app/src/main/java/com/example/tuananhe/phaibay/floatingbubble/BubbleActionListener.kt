package com.example.tuananhe.phaibay.floatingbubble

interface BubbleActionListener {

    fun onBubbleStartMove()

    fun onBubbleIdle()

    fun onBubbleWantDismiss()

    fun onBubbleMove(x: Int, y: Int)
}
