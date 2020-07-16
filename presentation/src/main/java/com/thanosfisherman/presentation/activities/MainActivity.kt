package com.thanosfisherman.presentation.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.thanosfisherman.domain.common.CalcResultState
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.reveal
import com.thanosfisherman.presentation.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.display_panel.*
import kotlinx.android.synthetic.main.grid_numbers.*
import kotlinx.android.synthetic.main.grid_operators.*
import kotlinx.android.synthetic.main.slide_grid_advanced.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DesquaredCalc)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sliding_pane.sliderFadeColor = ContextCompat.getColor(applicationContext, android.R.color.transparent)
        sliding_pane.openPane()

        initPads()
        mainViewModel.stateResult.onEach { setDisplayState(it) }.launchIn(lifecycleScope)
    }

    private fun setDisplayState(calcResultState: CalcResultState<String>) {
        when (calcResultState) {
            is CalcResultState.ClearAll -> {
                clearAllTextsWithAnimation()
            }
            is CalcResultState.SuccessEquals -> {
                val currentTextBelow = (switcher_below.currentView as TextView).text.toString()
                switcher_below.setText(calcResultState.data)
                switcher_above.setText(currentTextBelow)
            }
            is CalcResultState.SuccessDigit -> {
                switcher_below.setCurrentText(calcResultState.data)
            }
            is CalcResultState.Error -> {
                switcher_above.setCurrentText(calcResultState.errorMsg)
            }
        }
    }

    override fun onClick(v: View?) {
        val btnText = (v as Button).text.toString()
        val padType = PadType.getPadType(btnText)
        mainViewModel.addExpression(padType)
    }

    private fun initPads() {
        btn_decimal.setOnClickListener(this)
        btn_equals.setOnClickListener(this)

        btn_0.setOnClickListener(this)
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)

        btn_div.setOnClickListener(this)
        btn_minus.setOnClickListener(this)
        btn_multiply.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        btn_del.setOnClickListener(this)

        btn_percent.setOnClickListener(this)
        btn_power.setOnClickListener(this)
        btn_root.setOnClickListener(this)
        btn_pi.setOnClickListener(this)
        btn_e.setOnClickListener(this)
        btn_log.setOnClickListener(this)
        btn_ln.setOnClickListener(this)
        btn_left_paren.setOnClickListener(this)
        btn_right_paren.setOnClickListener(this)

        btn_del.setOnLongClickListener {
            mainViewModel.addExpression(PadType.CLEAR_ALL)
            return@setOnLongClickListener true
        }
    }

    private fun clearAllTextsWithAnimation() {

        if (ViewCompat.isAttachedToWindow(window.decorView)) {
            btn_del.reveal(switcher_below, window, R.color.colorAccent, object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    switcher_above.setCurrentText("")
                    switcher_below.setCurrentText("")
                }
            })
        }
    }
}
