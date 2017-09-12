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

/**
 * Created by MLX on 2017/9/11.
 */
class DropBall @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {
    override fun onClick(p0: View?) {
        mhandler.sendEmptyMessage(0)
    }

    val STATE_NONE=-1
    val STATE_DROP=0
    val STATE_RISE=1

    val BALL_RADIUS=60

    var DROP_HEIGHT=100f

    var mState=STATE_NONE

    var mhandler= @SuppressLint("HandlerLeak")
    object:Handler(){
        override fun handleMessage(msg: Message?) {
            when(mState){
                STATE_NONE->{
                    mState=STATE_DROP
                    mDropValueAnimator.start()
                }
                STATE_DROP->{


                }
            }
        }
    }

    var mpaint:Paint

    var mlistener= object:Animator.AnimatorListener{
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

    lateinit var mDropValueAnimator:ValueAnimator
    lateinit var mshadowValueAnimator:ValueAnimator


    var mCurrentValue=0f
    var mShadowValue=0f

    init {
        mpaint=Paint()
        mpaint.isDither=true
        mpaint.isAntiAlias=true
        mpaint.style=Paint.Style.FILL_AND_STROKE
        mpaint.color=Color.RED
        mpaint.strokeWidth=15f
        initAnimator()
    }

    private fun initAnimator() {
        mDropValueAnimator = ValueAnimator.ofFloat(0f,1f)
        mDropValueAnimator.duration=1000
        mDropValueAnimator.repeatCount=ValueAnimator.INFINITE
        mDropValueAnimator.repeatMode=ValueAnimator.REVERSE
        mDropValueAnimator.addUpdateListener {
            mCurrentValue= it.animatedValue as Float
            invalidate()
        }
        mDropValueAnimator.addListener(mlistener)

        mshadowValueAnimator = ValueAnimator.ofFloat(0f,1f)
        mshadowValueAnimator.duration=700
        mshadowValueAnimator.repeatCount=ValueAnimator.INFINITE
        mshadowValueAnimator.repeatMode=ValueAnimator.REVERSE
        mshadowValueAnimator.addUpdateListener {
            mShadowValue= it.animatedValue as Float
        }
        mshadowValueAnimator.addListener(mlistener)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        DROP_HEIGHT = (mheight/2+2*BALL_RADIUS).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when(mState){
            STATE_NONE->{
                canvas?.save()
                canvas?.drawOval((mwidth/2-BALL_RADIUS).toFloat(),100f, (mwidth/2+BALL_RADIUS).toFloat(), 220F,mpaint)
            }
            STATE_DROP->{
                val time = mDropValueAnimator.currentPlayTime % 1000
                val l = ((mDropValueAnimator.currentPlayTime / 1000) %2).toInt()
                if(time<300 && l==0){
                    if(mshadowValueAnimator.isRunning){
                        mshadowValueAnimator.cancel()
                    }
                    mpaint.color=Color.RED
                    canvas?.drawOval((mwidth/2-BALL_RADIUS).toFloat(),100f+DROP_HEIGHT*mCurrentValue, (mwidth/2+BALL_RADIUS).toFloat(), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                }
                if(time>=300 && l==0){
                    if(!mshadowValueAnimator.isRunning){
                        mshadowValueAnimator.start()
                    }
                    mpaint.color=Color.RED
                    if(mCurrentValue>=0.9f){
                        canvas?.drawOval(mwidth/2-BALL_RADIUS-100*(mCurrentValue-0.8f),100f+DROP_HEIGHT*mCurrentValue, mwidth/2+BALL_RADIUS + 50*(mCurrentValue-0.8f), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                    }else{
                        canvas?.drawOval((mwidth/2-BALL_RADIUS).toFloat(),100f+DROP_HEIGHT*mCurrentValue, (mwidth/2+BALL_RADIUS).toFloat(), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                    }
                    mpaint.alpha= (100*mShadowValue).toInt()
                    mpaint.color=Color.GRAY
                    canvas?.drawRect(mwidth/2-BALL_RADIUS*mShadowValue,225f+DROP_HEIGHT-1*mShadowValue,mwidth/2+BALL_RADIUS*mShadowValue,DROP_HEIGHT+225f+1*mShadowValue,mpaint)
                }
                if(time>=700 && l!=0){
                    if(mshadowValueAnimator.isRunning){
                        mshadowValueAnimator.cancel()
                    }
                    mpaint.color=Color.RED
                    canvas?.drawOval((mwidth/2-BALL_RADIUS).toFloat(),100f+DROP_HEIGHT*mCurrentValue, (mwidth/2+BALL_RADIUS).toFloat(), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                }
                if(time<=700 && l!=0){
                    if(!mshadowValueAnimator.isRunning){
                        mshadowValueAnimator.start()
                    }
                    mpaint.color=Color.RED
                    if(mCurrentValue>=0.9f){
                        canvas?.drawOval(mwidth/2-BALL_RADIUS-100*(mCurrentValue-0.8f),100f+DROP_HEIGHT*mCurrentValue, mwidth/2+BALL_RADIUS + 50*(mCurrentValue-0.8f), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                    }else{
                        canvas?.drawOval((mwidth/2-BALL_RADIUS).toFloat(),100f+DROP_HEIGHT*mCurrentValue, (mwidth/2+BALL_RADIUS).toFloat(), DROP_HEIGHT*mCurrentValue+220F,mpaint)
                    }
                    mpaint.alpha= (100*mShadowValue).toInt()
                    mpaint.color=Color.GRAY
                    canvas?.drawRect(mwidth/2-BALL_RADIUS*mShadowValue,225f+DROP_HEIGHT-1*mShadowValue/2,mwidth/2+BALL_RADIUS*mShadowValue,DROP_HEIGHT+225f+1*mShadowValue/2,mpaint)
                }
            }
            STATE_RISE->{

            }
        }
    }


}