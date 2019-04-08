package com.example.tuananhe.phaibay.floatingbubble

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.signup.SignUpActivity

class BubbleView(
    private val mContext: Context,
    private val mWindowManager: WindowManager,
    private val mListener: BubbleActionListener?
) {
    private lateinit var mBubbleView: View

    object Orientation {
        val LEFT = -1;
        val RIGHT = 1;
    }


    var lastAction: Int = -1
    var initialX = 0
    var initialY = 0
    var initialTouchX = 0f
    var initialTouchY = 0f

    init {
        initBubbleView()
    }

    private fun initBubbleView() {

        mBubbleView =
            LayoutInflater.from(mContext).inflate(R.layout.layout_floating_bubble, null)
        //Create Layout param
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
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }

        //Specify position bubble
        params.gravity = Gravity.TOP or Gravity.START
        //Initially view will be added to top-left corner
        params.x = 0
        params.y = 0

        //Add bubble view to window
        mWindowManager.addView(mBubbleView, params)
        initTouchListener(params)
    }

    var orientation = 0
    var distanceX = 0
    var distanceY = 0

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener(params: WindowManager.LayoutParams) {
        //Get screen with
        val display = mWindowManager.defaultDisplay
        val screenWith = display.width
        var isMove = false

        mBubbleView.findViewById<ImageView>(R.id.image_bubble).setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    if (!isMove) {
                        val distanceToCenter = (screenWith - mBubbleView.width) / 2
                        if (params.x < screenWith / 2) {
                            orientation = Orientation.LEFT
                            mListener?.onBubbleStartMoveFromLeft(distanceToCenter)
                        } else {
                            orientation = Orientation.RIGHT
                            mListener?.onBubbleStartMoveFromRight(distanceToCenter)
                        }
                        isMove = true
                    }
                    lastAction = event.action
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val handler = Handler()
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                        // todo show control button
                    }

                    if (params.x < screenWith / 2) {
                        handler.post(object : Runnable {
                            override fun run() {
                                if (params.x > 0) {
                                    params.x -= 50
                                    mWindowManager.updateViewLayout(mBubbleView, params)
                                    handler.post(this)
                                } else {
                                    handler.removeCallbacks(this)
                                }
                            }
                        })
                    } else {
                        handler.post(object : Runnable {
                            override fun run() {
                                if (params.x < screenWith - mBubbleView.width) {
                                    params.x += 50
                                    mWindowManager.updateViewLayout(mBubbleView, params)
                                    handler.post(this)
                                } else {
                                    handler.removeCallbacks(this)
                                }
                            }
                        })
                    }
                    isMove = false
                    lastAction = event.action
                    mListener?.onBubbleIdle()
                    true
                }
                MotionEvent.ACTION_MOVE -> {

                    distanceX = ((event.rawX - initialTouchX) * 1.1).toInt()
                    distanceY = ((event.rawY - initialTouchY) * 1.1).toInt()

                    params.x = initialX + distanceX
                    params.y = initialY + distanceY

                    if (params.x < 0) {
                        params.x = 0
                    } else if (params.x > screenWith - mBubbleView.width) {
                        params.x = screenWith - mBubbleView.width
                    }
                    Log.d("BubbleView", " param.x " + params.x)
                    mWindowManager.updateViewLayout(mBubbleView, params)
                    mListener?.onBubbleMove(distanceX, distanceY)



                    lastAction = event.action
                    true
                }

                else -> false
            }
        }
    }

    fun removeBubbleView() {
        mWindowManager.removeView(mBubbleView)
    }

}