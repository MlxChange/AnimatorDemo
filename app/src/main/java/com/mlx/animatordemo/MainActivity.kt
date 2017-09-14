package com.mlx.animatordemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mlx.animatordemo.activity.BallLoadingActivity
import com.mlx.animatordemo.activity.DropBallActivity
import com.mlx.animatordemo.activity.PathHeartActivity
import com.mlx.animatordemo.activity.VipActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun heart(v: View){
        startActivity(Intent(this,PathHeartActivity::class.java))
    }
    fun dropBall(v: View){
        startActivity(Intent(this,DropBallActivity::class.java))
    }
    fun Vip(v: View){
        startActivity(Intent(this,VipActivity::class.java))
    }
    fun ballLoading(v:View){
        startActivity(Intent(this,BallLoadingActivity::class.java))
    }
}
