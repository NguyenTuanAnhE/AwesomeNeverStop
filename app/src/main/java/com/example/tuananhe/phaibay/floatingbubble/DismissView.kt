package com.example.tuananhe.phaibay.floatingbubble

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import com.example.tuananhe.phaibay.R

class DismissView(private val mContext: Context, private val mWindowManager: WindowManager) :
    BubbleActionListener {


    private lateinit var mDismissView: View
    private lateinit var mParams: WindowManager.LayoutParams
    private lateinit var mDisplay: Display

    init {
        initDismissView()
    }

    private fun initDismissView() {
        mDismissView = LayoutInflater.from(mContext).inflate(R.layout.layout_dismiss_bubble, null)

        mDisplay = mWindowManager.defaultDisplay

        mParams = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
            )
        }

        mParams.gravity = Gravity.BOTTOM
//        mParams.x = (mDisplay.width + 64) / 2
        mParams.y = mDisplay.height + 64
        Log.d("dákjda", "asdadas ${mParams.y}  asdas ${mParams.x}")

        mWindowManager.addView(mDismissView, mParams)
    }

    override fun onBubbleMove() {
        while (mParams.y > mDisplay.height / 4) {
            mParams.y -= 10
            Log.d("dákjda", "asdadas ${mParams.y}  asdas ${mParams.x} ok")
            mWindowManager.updateViewLayout(mDismissView, mParams)
        }
    }

    override fun onBubbleIdle() {
        while (mParams.y > mDisplay.height + 64) {
            mParams.y += 10
            Log.d("dákjda", "asdadas ${mParams.y}  asdas ${mParams.x} ok")
            mWindowManager.updateViewLayout(mDismissView, mParams)
        }
    }

    override fun onBubbleWantDismiss() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}