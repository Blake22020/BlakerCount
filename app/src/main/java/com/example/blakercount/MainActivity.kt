package com.example.blakercount

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.*
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            click()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun click() {
        val count = findViewById<TextView>(R.id.count).text.toString().toInt()
        val index = count % 7
        val colors = listOf(
            "#FF0000",
            "#FFA500",
            "#FFFF00",
            "#00FF00",
            "#00FFFF",
            "#0000FF",
            "#8A2BE2"
        )

        val button = findViewById<Button>(R.id.button)

        button.setBackgroundColor(colors[index].toColorInt())

        increment()
        vibrate(250, 30)
    }



    private fun vibrate(milliseconds: Long, amplitude: Int) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (!vibrator.hasVibrator()) return

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(
                    milliseconds,
                    amplitude.coerceIn(1, 255)
                )
                vibrator.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(milliseconds)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun increment() {
        val count = findViewById<TextView>(R.id.count)

        var num = count.text.toString().toInt()
        num += 1
        count.text = num.toString()


    }

}