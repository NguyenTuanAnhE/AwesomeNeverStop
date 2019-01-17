package com.example.tuananhe.phaibay.floatingbubble

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.example.tuananhe.phaibay.R


class BubbleService : Service() {

    private lateinit var mWindowManager: WindowManager
    private lateinit var mBubbleView: View

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        //Inflate bubble view from xml layout
        mBubbleView =
                LayoutInflater.from(this).inflate(R.layout.layout_floating_bubble, null)

        //Create WindowManager Layout Param to set up view
        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        } else {
            WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }

        //Specify position bubble
        params.gravity = Gravity.TOP or Gravity.START
        //Initially view will be added to top-left corner
        params.x = 0
        params.y = 0

        //Add BubbleView to WindowManager
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mBubbleView, params)

        //Control moving bubble on touch
        mBubbleView.findViewById<ImageView>(R.id.image_bubble).setOnTouchListener { v, event ->
            var lastAction: Int = -1
            var initialX = 0
            var initialY = 0
            var initialTouchX = 0f
            var initialTouchY = 0f
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    Log.d("aasasaas", "aaaaa ${params.x}")
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    lastAction = event.action
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                        //show control button
                    }
                    lastAction = event.action
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("aasasaas", "aaaaa ${params.x} aaaaaaa")
                    params.x = initialX + (event.rawX - initialTouchX).toInt()
                    params.y = initialY + (event.rawY - initialTouchY).toInt()

                    mWindowManager.updateViewLayout(mBubbleView, params)
                    lastAction = event.action
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager.removeView(mBubbleView)
    }

}
