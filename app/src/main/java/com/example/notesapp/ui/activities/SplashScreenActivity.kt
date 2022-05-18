package com.example.notesapp.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val counter = getSharedPreferences("shared", Context.MODE_PRIVATE).getInt("counter",0)
       if(counter == 0){
           ani.postDelayed({
               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
               finish()
           }, 3000)
           Log.d("taget",counter.toString())
           getSharedPreferences("shared",Context.MODE_PRIVATE).edit().apply {
               putInt("counter",counter+1)
               apply()
           }
       }else{
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
           finish()
       }
    }
}