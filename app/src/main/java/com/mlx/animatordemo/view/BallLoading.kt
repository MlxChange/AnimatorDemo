package com.mlx.animatordemo.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ViewAnimator

/**
 * Created by MLX on 2017/9/14.
 */
class BallLoading @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {
    override fun onClick(p0: View?) {
        mhandle.sendEmptyMessage(0)
    }

    var mpaint:Paint

    var STATE_RIGHT_TO_LEFT1=0
    var STATE_RIGHT_TO_LEFT2=1
    var STATE_LEFT_TO_RIGHT1=2
    var STATE_LEFT_TO_RIGHT2=3
    var STATE_NONE=-1
    var mState=STATE_NONE


    var mRadius=100f
    var mMoveSize=2*mRadius
    var mCurrentValue=0f
    var mScaleSize=mRadius/2

    lateinit var mValueAnimatorRTL1:ValueAnimator
    lateinit var mValueAnimatorRTL2:ValueAnimator

    var mlistener=object:Animator.AnimatorListener{
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            mhandle.sendEmptyMessage(0)
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }

    }

    var mhandle= @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message?) {
            when(mState){
                STATE_NONE->{
                    mState=STATE_RIGHT_TO_LEFT1
                    mValueAnimatorRTL1.start()
                }
                STATE_RIGHT_TO_LEFT1->{
                    mState=STATE_RIGHT_TO_LEFT2
                    mValueAnimatorRTL2.start()
                }
                STATE_RIGHT_TO_LEFT2->{
                    mState=STATE_LEFT_TO_RIGHT1
                    mValueAnimatorRTL1.start()
                }
                STATE_LEFT_TO_RIGHT1->{
                    mState=STATE_LEFT_TO_RIGHT2
                    mValueAnimatorRTL2.start()
                }
                STATE_LEFT_TO_RIGHT2->{
                    mState=STATE_NONE
                    this.sendEmptyMessageDelayed(0,200)
                }
            }
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
        mValueAnimatorRTL1 = ValueAnimator.ofFloat(0f,1f)
        mValueAnimatorRTL1.duration=100
        mValueAnimatorRTL1.addUpdateListener {
            mCurrentValue=it.animatedValue as Float
            invalidate()
        }
        mValueAnimatorRTL1.interpolator= LinearInterpolator()
        mValueAnimatorRTL1.addListener(mlistener)
        mValueAnimatorRTL2 = ValueAnimator.ofFloat(1f,0f)
        mValueAnimatorRTL2.duration=100
        mValueAnimatorRTL2.addUpdateListener {
            mCurrentValue=it.animatedValue as Float
            invalidate()
        }
        mValueAnimatorRTL2.interpolator=LinearInterpolator()
        mValueAnimatorRTL2.addListener(mlistener)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate((mwidth/2).toFloat(), (mheight/2).toFloat())
        canvas?.scale(1f,-1f)
        when(mState){
            STATE_NONE->{
                mpaint.color=Color.GREEN
                canvas?.drawOval(mRadius,mRadius,mRadius*3,-mRadius,mpaint)
                mpaint.color=Color.YELLOW
                canvas?.drawOval(-mRadius*3,mRadius,-mRadius,-mRadius,mpaint)
            }
            STATE_RIGHT_TO_LEFT1->{
                mpaint.color=Color.GREEN
                canvas?.drawOval(mRadius-mMoveSize*mCurrentValue+mScaleSize*mCurrentValue,mRadius-mScaleSize*mCurrentValue,mRadius*3-mMoveSize*mCurrentValue-mScaleSize*mCurrentValue,-mRadius+mScaleSize*mCurrentValue,mpaint)
                mpaint.color=Color.YELLOW
                canvas?.drawOval(-mRadius*3+mMoveSize*mCurrentValue-mScaleSize*mCurrentValue,mRadius+mScaleSize*mCurrentValue,-mRadius+mMoveSize*mCurrentValue+mScaleSize*mCurrentValue,-mRadius-mScaleSize*mCurrentValue,mpaint)
            }
            STATE_RIGHT_TO_LEFT2->{
                mpaint.color=Color.GREEN
                canvas?.drawOval(mRadius-mMoveSize+mScaleSize*mCurrentValue-mMoveSize*(1-mCurrentValue),mRadius-mScaleSize*mCurrentValue,mRadius*3-mMoveSize-mScaleSize*mCurrentValue-mMoveSize*(1-mCurrentValue),-mRadius+mScaleSize*mCurrentValue,mpaint)
                mpaint.color=Color.YELLOW
                canvas?.drawOval(-mRadius*3+mMoveSize-mScaleSize*mCurrentValue+mMoveSize*(1-mCurrentValue),mRadius+mScaleSize*mCurrentValue,-mRadius+mMoveSize+mScaleSize*mCurrentValue+mMoveSize*(1-mCurrentValue),-mRadius-mScaleSize*mCurrentValue,mpaint)
            }
            STATE_LEFT_TO_RIGHT1->{
                mpaint.color=Color.YELLOW
                canvas?.drawOval(mRadius-mMoveSize*mCurrentValue+mScaleSize*mCurrentValue,mRadius-mScaleSize*mCurrentValue,mRadius*3-mMoveSize*mCurrentValue-mScaleSize*mCurrentValue,-mRadius+mScaleSize*mCurrentValue,mpaint)
                mpaint.color=Color.GREEN
                canvas?.drawOval(-mRadius*3+mMoveSize*mCurrentValue-mScaleSize*mCurrentValue,mRadius+mScaleSize*mCurrentValue,-mRadius+mMoveSize*mCurrentValue+mScaleSize*mCurrentValue,-mRadius-mScaleSize*mCurrentValue,mpaint)
            }
            STATE_LEFT_TO_RIGHT2->{
                mpaint.color=Color.YELLOW
                canvas?.drawOval(mRadius-mMoveSize+mScaleSize*mCurrentValue-mMoveSize*(1-mCurrentValue),mRadius-mScaleSize*mCurrentValue,mRadius*3-mMoveSize-mScaleSize*mCurrentValue-mMoveSize*(1-mCurrentValue),-mRadius+mScaleSize*mCurrentValue,mpaint)
                mpaint.color=Color.GREEN
                canvas?.drawOval(-mRadius*3+mMoveSize-mScaleSize*mCurrentValue+mMoveSize*(1-mCurrentValue),mRadius+mScaleSize*mCurrentValue,-mRadius+mMoveSize+mScaleSize*mCurrentValue+mMoveSize*(1-mCurrentValue),-mRadius-mScaleSize*mCurrentValue,mpaint)
            }
        }
    }

}