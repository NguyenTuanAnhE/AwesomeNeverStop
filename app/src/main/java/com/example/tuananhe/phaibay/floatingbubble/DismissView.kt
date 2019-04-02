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
    private var rateCenter = 0f
    private var limitLeft = 0
    private var limitRight = 0
    private var limitTop = 0
    private var limitBottom = 0

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

    override fun onBubbleIdle() {
        mParams.y = 0
        mParams.x = (mScreenPoint.x - mDismissWidth) / 2
        mDismissView.visibility = GONE
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
    }

    override fun onBubbleWantDismiss() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBubbleMove(distanceX: Int, distanceY: Int) {
        mParams.x = (initialX + distanceX * rateCenter).toInt()
        mParams.y = initialY + distanceY
        Log.d("DimissView", "distance " + (distanceX * rateCenter))
        if (mParams.x < limitLeft) {
            mParams.x = limitLeft
        } else if (mParams.x > limitRight) {
            mParams.x = limitRight
        }
        if (mParams.y > limitTop) {
            mParams.y = limitTop
        } else if (mParams.y < limitBottom) {
            mParams.y = limitBottom
        }
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
    }

    override fun onBubbleStartMoveFromLeft(bubbleToCenter: Int) {
        limitLeft = mScreenPoint.x / 4
        rateCenter = ((mScreenPoint.x / 2.toFloat() - limitLeft) / bubbleToCenter)
        dismissStartMove(limitLeft)
    }

    override fun onBubbleStartMoveFromRight(bubbleToCenter: Int) {
        limitRight = 3 * mScreenPoint.x / 4 - mDismissView.width
        rateCenter = ((limitRight - mScreenPoint.x / 2.toFloat()) / bubbleToCenter)
        dismissStartMove(limitRight)
    }

    private fun dismissStartMove(x: Int) {
        limitTop = mScreenPoint.y / 4
        limitBottom = mScreenPoint.y / 7

        mParams.x = x
        mParams.y = limitBottom
        mDismissView.visibility = View.VISIBLE
        val hide = AnimationUtils.loadAnimation(mContext, R.anim.swipe_bottom)
        mDismissView.startAnimation(hide)
        mWindowManager.updateViewLayout(mFrameLayout, mParams)
        initialX = mParams.x
        initialY = mParams.y
    }

}

/**  DX = (screenWidth - D.width)/2 - screenWidth/3 : khoảng cách từ diểm bắt đàu D tới tâm
 *   BX = (screenWidth - B.width)/2
 *  Bx = B.x - Bx : khaoảng cách di chuyển của B theo chiều x, By tương tự
 *  Bx ban đầu bẳng B.width
 *- ban đầu B đang ở vị trí x=0, D đang ở vị trí (screnWidth - D.width)/2
 *      + khi bắt đầu di chuyển B, D di chuyển tới vị trí x = screenWithd/3, y= screenHeight/6
 *      + D đang cách tâm một khoảng DX => Dx = (float)Bx/BX *DX
 *       D.x =D.x + Dx
 *
 *- ban đầu B đang ở vị trí x= screenWidth - B.width, D đang ở vị trí (screnWidth - D.width)/2
 *      +Bx = Bx - B.x,  Bx ban đầu bẳng screenWidth - B.width
 *      + khi bắt đầu di chuyển B, D di chuyển tới vị trí x = 2*screenWithd/3 - B.width, y= screenHeight/6
 *      + D đang cách tâm một khoảng DX => Dx = (float)Bx/BX *DX
 *       D.x =D.x + Dx
 */
