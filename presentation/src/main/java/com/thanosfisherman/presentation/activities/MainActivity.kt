package com.thanosfisherman.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thanosfisherman.presentation.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DesquaredCalc)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sliding_pane.sliderFadeColor = resources.getColor(android.R.color.transparent)
        sliding_pane.openPane()
    }
}
