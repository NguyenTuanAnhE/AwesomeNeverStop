package com.example.tuananhe.phaibay.floatingbubble

interface BubbleActionListener {

    fun onBubbleStartMoveFromLeft(bubbleToCenter: Int)

    fun onBubbleStartMoveFromRight(bubbleToCenter: Int)

    fun onBubbleIdle()

    fun onBubbleWantDismiss()

    fun onBubbleMove(distanceX: Int, distanceY: Int)

}
