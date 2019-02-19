package com.example.tuananhe.phaibay.floatingbubble

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.ImageView
import com.example.tuananhe.phaibay.R

class BubbleView(
    private val mContext: Context,
    private val mWindowManager: WindowManager,
    private val mListener: BubbleActionListener?
) {
    private lateinit var mBubbleView: View

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

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener(params: WindowManager.LayoutParams) {
        //Get screen with
        val display = mWindowManager.defaultDisplay
        val screenWith = display.width

        mBubbleView.findViewById<ImageView>(R.id.image_bubble).setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    lastAction = event.action
                    mListener?.onBubbleStartMove()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                        // todo show control button
                    }

                    lastAction = event.action
                    if (params.x < screenWith / 2) {
                        while (params.x > 0) {
                            params.x -= 10
                            mWindowManager.updateViewLayout(mBubbleView, params)
                        }
                    } else {
                        while (params.x < screenWith) {
                            params.x += 10
                            mWindowManager.updateViewLayout(mBubbleView, params)
                        }
                    }
                    mListener?.onBubbleIdle()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + ((event.rawX - initialTouchX) * 1.1).toInt()
                    params.y = initialY + ((event.rawY - initialTouchY) * 1.1).toInt()
                    mWindowManager.updateViewLayout(mBubbleView, params)
                    mListener?.onBubbleMove(
                        (event.rawX - initialTouchX).toInt(),
                        (event.rawY - initialTouchY).toInt()
                    )
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