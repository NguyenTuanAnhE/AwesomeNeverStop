package com.example.tuananhe.phaibay.floatingbubble

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tuananhe.phaibay.floatingbubble.BubbleReceiver.Action.BUBBLE_STARTED

class BubbleReceiver : BroadcastReceiver() {

    lateinit var activity: Activity

    object Action {
        const val BUBBLE_STARTED = "BUBBLE_STARTED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action == null) return
        when (intent.action) {
            BUBBLE_STARTED -> {
                activity.finish()
            }
        }
    }

}