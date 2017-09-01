package com.mlx.animatordemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mlx.animatordemo.activity.PathHeartActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun heart(v: View){
        startActivity(Intent(this,PathHeartActivity::class.java))
    }
}
