package com.mlx.animatordemo.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by MLX on 2017/9/1.
 */
abstract class BaseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),View.OnClickListener{


    var mwidth=0
    var mheight=0

    init {
        setOnClickListener(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mwidth=w
        mheight=h
    }

}