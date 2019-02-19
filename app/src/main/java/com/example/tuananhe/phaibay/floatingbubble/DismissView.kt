package com.example.tuananhe.phaibay.floatingbubble

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.example.tuananhe.phaibay.R
import com.example.tuananhe.phaibay.util.BubbleUtil

class DismissView(private val mContext: Context, private val mWindowManager: WindowManager) :
    FrameLayout(mContext), BubbleActionListener {

    private lateinit var mFrameLayout: FrameLayout
    private lateinit var mDismissView: View
    private lateinit var mParams: WindowManager.LayoutParams
    private lateinit var mDisplay: Display
    private lateinit var mScreenPoint: Point
    var initialX = 0
    var initialY = 0

    private var mDismissWidth: Int = 0

    init {
        initDismissView()
    }

    private fun initDismissView() {
        mDismissView = LayoutInflater.from(mContext).inflate(R.layout.layout_dismiss_bubble, null)
        mFrameLayout = FrameLayout(mContext)
        mDisplay = mWindowManager.defaultDisplay
        mScreenPoint = Point()
        mDisplay.getSize(mScreenPoint)

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
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }

        mDismissWidth = BubbleUtil.convertDipToPixel(resources, 64f)
        mParams.gravity = Gravity.START or Gravity.BOTTOM
        mParams.x = (mScreenPoint.x - mDismissWidth) / 2
        mParams.y = 0

        mDismissView.visibility = GONE
        mFrameLayout.addView(mDismissView)
        mWindowManager.addView(mFrameLayout, mParams)
        initialX = mParams.x
        initialY = mParams.y
    }

    override fun onBubbleStartMove() {
        mParams.y = mScreenPoint.y / 6
        mDismissView.visibility = View.VISIBLE
        val hide = AnimationUtils.loadAnimation(mContext, R.anim.swipe_bottom)
        mDismissView.startAnimation(hide)
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
        initialX = (mScreenPoint.x - mDismissWidth) / 2
        initialY = mParams.y

    }

    override fun onBubbleIdle() {
        mParams.y = 0
        mParams.x = (mScreenPoint.x - mDismissWidth) / 2
        mDismissView.visibility = GONE
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
    }

    override fun onBubbleWantDismiss() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBubbleMove(x: Int, y: Int) {

        mParams.x = initialX + x / 10
        mParams.y = initialY + y / 10
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
    }

}