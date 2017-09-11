package com.mlx.animatordemo.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.mlx.myapplication.view.CalculationBezier
import com.mlx.animatordemo.R
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import java.util.*


@RequiresApi(Build.VERSION_CODES.KITKAT)
/**
 * Created by MLX on 2017/9/1.
 */
class PathHeart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(p0: View?) {
        mState=STATE_NONE
        mHandler.sendEmptyMessage(0)
    }




    lateinit var mlistener:Animator.AnimatorListener
    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when(mState){
                STATE_NONE->{
                    mRandom=Random().nextInt(2)
                    mState=STATE_SCALE
                    mScaleAnimator.start()
                }
                STATE_SCALE->{
                    mState=STATE_PATH
                    mPathAnimator.start()
                }
                STATE_PATH->{
                    mState=STATE_NONE
                    mAlphapaint=Paint()
                    mAlphapaint.color=Color.RED
                    mPathAnimator.cancel()
                    handleMessage(msg)
                }
            }
        }
    }
    var mimg:Bitmap
    var mAlphapaint:Paint

    var mState=-1

    private val STATE_SCALE=0
    private val STATE_PATH=1
    private val STATE_NONE=-1



    val LEFT=0

    var mRandom:Int

    var mAlpha=1f
    var mAlphaAnimator:ValueAnimator
    var mPathAnimator:ValueAnimator
    var mCurrentValue=0f
    var mpath:Path

    lateinit var mstartPoint:PointF
    lateinit var mFirstEndPoint:PointF
    lateinit var mFirstCollerPoint:PointF
    lateinit var mSecondEndPoint:PointF
    lateinit var mSecondCollerPoint:PointF



    init {
        initListener()

        mRandom=Random().nextInt(1)

        mAlphapaint=Paint()
        mpath=Path()
        mScaleAnimator = ValueAnimator.ofFloat(0f,1f)
        mScaleAnimator.interpolator=AccelerateInterpolator()
        mScaleAnimator.duration = 600
        mScaleAnimator.addUpdateListener {
            mCurrentValue=it.animatedValue as Float
            invalidate()
        }
        mScaleAnimator.addListener(mlistener)
        mPathAnimator = ValueAnimator.ofFloat(0f,1f)
        mPathAnimator.interpolator=AccelerateInterpolator()
        mPathAnimator.duration = 2000
        mPathAnimator.addUpdateListener {
            mCurrentValue=it.animatedValue as Float
            invalidate()
        }
        mPathAnimator.addListener(mlistener)
        mAlphaAnimator =ValueAnimator.ofFloat(1f,0f)
        mAlphaAnimator.duration=1000
        mAlphaAnimator.interpolator=LinearInterpolator()
        mAlphaAnimator.addUpdateListener {
            mAlpha= it.animatedValue as Float
        }
        mimg=BitmapFactory.decodeResource(context.resources,R.drawable.heart)
    }

    private fun initListener() {


        mlistener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator) {
                // getHandle发消息通知动画状态更
                mHandler.sendEmptyMessage(0)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mstartPoint=PointF((mwidth/2).toFloat(), (mheight-mimg.height/2).toFloat())

        mFirstCollerPoint=PointF(100f, 600f)
        mFirstEndPoint =PointF(700f,200f)

        mSecondCollerPoint=PointF(100f+w/2, 600f)
        mSecondEndPoint=PointF(700f-w/2,200f)

    }

    @SuppressLint("DrawAllocation")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mRandom==0){
            mAlphapaint.colorFilter = PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
        }else{
            mAlphapaint.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)

        }
        when(mState){
            STATE_SCALE->{
                canvas?.save()
                canvas?.translate(mstartPoint.x,mstartPoint.y)
                canvas?.scale(mCurrentValue,mCurrentValue)
                canvas?.drawBitmap(mimg,-(mimg.width/2).toFloat(),-(mimg.height/2).toFloat(),mAlphapaint)
                canvas?.restore()
            }
            STATE_PATH->{
                mpath.moveTo(mstartPoint.x,mstartPoint.y)
                val bezier:PointF
                if(mRandom==LEFT){
                    mpath.quadTo(mFirstCollerPoint.x,mFirstCollerPoint.y, mFirstEndPoint.x, mFirstEndPoint.y)
                    bezier = PointF().CalculationBezier(mCurrentValue, mstartPoint, mFirstCollerPoint, mFirstEndPoint)
                }else{
                    mpath.quadTo(mSecondCollerPoint.x,mSecondCollerPoint.y, mSecondEndPoint.x, mSecondEndPoint.y)
                    bezier = PointF().CalculationBezier(mCurrentValue, mstartPoint, mSecondCollerPoint, mSecondEndPoint)
                }
                if(mPathAnimator.currentPlayTime>1000){
                    if(!mAlphaAnimator.isRunning){
                        mAlphaAnimator.start()
                    }
                    if(mAlpha<1){
                        mAlphapaint.alpha= (mAlpha*100).toInt()
                    }
                }
                canvas?.drawBitmap(mimg,bezier.x-mimg.width/2,bezier.y-mimg.height/2,mAlphapaint)
            }
        }
    }

}