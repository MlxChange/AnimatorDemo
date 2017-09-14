package com.mlx.animatordemo.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View

/**
 * Created by MLX on 2017/9/13.
 */
class VipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),View.OnClickListener{
    override fun onClick(p0: View?) {
        mhandler.sendEmptyMessage(0)
    }


    var mwidth=0
    var mheight=0
    var mpaint:Paint
    var mCurrentValue=0f
    var mMoveSize=40f
    var mMoveSize2=90f

    lateinit var mValueAnimator:ValueAnimator

    var mhandler= @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message?) {
            mValueAnimator.start()
        }
    }

    init {
        mpaint=Paint()
        mpaint.color=Color.parseColor("#FB694F")
        mpaint.style=Paint.Style.FILL
        mpaint.strokeWidth=10f

        setOnClickListener(this)
        initAnimator()
    }

    private fun initAnimator() {
        mValueAnimator=ValueAnimator.ofFloat(0f,1f)
        mValueAnimator.repeatCount=ValueAnimator.INFINITE
        mValueAnimator.duration=5000
        mValueAnimator.addUpdateListener {
            mCurrentValue= it.animatedValue as Float
            invalidate()
        }

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mwidth=w
        mheight=h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate((mwidth/2).toFloat(), (mheight/2).toFloat())
        canvas?.scale(1f,-1f)
        mpaint.alpha= (100*(1-mCurrentValue)).toInt()
        for (i in 0..19){
            canvas?.rotate(18f)
            canvas?.drawRect(-4f,300f+mMoveSize*mCurrentValue,4f,332f+mMoveSize*mCurrentValue,mpaint)
        }
        canvas?.restore()
        canvas?.translate((mwidth/2).toFloat()+50, (mheight/2).toFloat()+50)
        canvas?.scale(1f,-1f)
        mpaint.alpha= (50*(1-mCurrentValue)).toInt()
        for (i in 0..19){
            canvas?.rotate(18f)
            canvas?.drawRect(-4f,300f+mMoveSize2*mCurrentValue,4f,332f+mMoveSize2*mCurrentValue,mpaint)
        }

    }

}