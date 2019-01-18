package com.example.tuananhe.phaibay.floatingbubble

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager

class BubbleService : Service() {

    private lateinit var mWindowManager: WindowManager
    private lateinit var mBubbleView: BubbleView
    private lateinit var mDismissView: DismissView


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mDismissView = DismissView(this, mWindowManager)
        mBubbleView = BubbleView(this, mWindowManager, mDismissView)

    }

    override fun onDestroy() {
        super.onDestroy()
        mBubbleView.removeBubbleView()

    }

}
