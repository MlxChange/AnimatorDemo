package com.mlx.animatordemo.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View

/**
 * Created by MLX on 2017/9/14.
 */
class BallLoading @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {
    override fun onClick(p0: View?) {

    }

    var mpaint:Paint

    var mCurrentValue=0f

    lateinit var mValueAnimator:ValueAnimator

    var mhandler= @SuppressLint("HandlerLeak")
    object:Handler(){
        override fun handleMessage(msg: Message?) {

        }
    }

    var mlistener=object: Animator.AnimatorListener{
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            mhandler.sendEmptyMessage(0)
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    }

    init {
        mpaint=Paint()
        mpaint.style=Paint.Style.FILL
        mpaint.isAntiAlias=true
        mpaint.strokeWidth=10f

        initanimator()
    }

    private fun initanimator() {
        mValueAnimator= ValueAnimator.ofFloat(0f,1f)
        mValueAnimator.duration=1000
        mValueAnimator.repeatCount=ValueAnimator.INFINITE
        mValueAnimator.addUpdateListener {
            mCurrentValue=it.animatedValue as Float
            invalidate()
        }
        mValueAnimator.addListener(mlistener)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }


}