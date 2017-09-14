package com.mlx.animatordemo.activity

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.mlx.animatordemo.R

class VipActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip)
    }


    fun start(v: View){
        var drable=(v as ImageView).background as AnimationDrawable
        drable.start()
    }
}
