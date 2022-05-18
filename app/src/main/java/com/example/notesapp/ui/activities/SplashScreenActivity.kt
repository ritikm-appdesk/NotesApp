package com.example.notesapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.WifiConfiguration.AuthAlgorithm.SHARED
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val counter = getSharedPreferences("shared", Context.MODE_PRIVATE).getInt("counter",0)
       if(counter >0){
           Handler().postDelayed({
               val intent = Intent(this, MainActivity::class.java)
               startActivity(intent)
               finish()
           }, 3000)
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