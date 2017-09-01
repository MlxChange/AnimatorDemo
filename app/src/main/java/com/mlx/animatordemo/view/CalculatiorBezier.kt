package com.example.mlx.myapplication.view

import android.graphics.Point
import android.graphics.PointF

/**
 * Created by MLX on 2017/8/31.
 */
fun PointF.CalculatiooBezier(t:Float,p0:PointF?,p1:PointF?,p2:PointF?):PointF{
    var p=PointF()
    var temp=1-t
    p.x= p0!!.x *temp*temp+2*temp*t*p1!!.x+p2!!.x*t*t
    p.y=p0.y*temp*temp+2*temp*t*p1.y+p2.y*t*t
    return p
}