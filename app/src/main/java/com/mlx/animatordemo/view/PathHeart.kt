package com.mlx.animatordemo.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.mlx.animatordemo.R
import org.xmlpull.v1.XmlPullParser

/**
 * Created by MLX on 2017/9/1.
 */
class PathHeart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseView(context, attrs, defStyleAttr) {
    override fun onClick(p0: View?) {
    }

    var mimg:Bitmap

    var mpaint:Paint

    init {
        mpaint=Paint()
        mpaint.color=Color.RED
        mValueAnimator= ValueAnimator.ofFloat(0f,1f)

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}