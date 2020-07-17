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
import com.thanosfisherman.domain.common.CalcResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.enums.PadType
import com.thanosfisherman.domain.models.ConversionResultModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.extensions.reveal
import com.thanosfisherman.presentation.common.utils.FragmentUtils
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.fragments.ConvertDialogFragment
import com.thanosfisherman.presentation.fragments.FragmentInteractionListener
import com.thanosfisherman.presentation.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.display_panel.*
import kotlinx.android.synthetic.main.grid_numbers.*
import kotlinx.android.synthetic.main.grid_operators.*
import kotlinx.android.synthetic.main.slide_grid_advanced.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), View.OnClickListener, FragmentInteractionListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DesquaredCalc)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sliding_pane.sliderFadeColor = ContextCompat.getColor(applicationContext, android.R.color.transparent)
        sliding_pane.openPane()
        progressConvert.visibility = View.GONE
        initPads()
        observe(mainViewModel.liveCalculateStateResult, ::getDisplayState)
        observe(mainViewModel.liveNetworkStateResult, ::getExchangeState)
    }

    private fun getExchangeState(resultModel: NetworkResultState<ConversionResultModel>) {
        when (resultModel) {
            is NetworkResultState.Loading -> {
                progressConvert.visibility = View.VISIBLE
            }
            is NetworkResultState.Success -> {
                progressConvert.visibility = View.GONE
                val currentTextBelow = "${(switcher_below.currentView as TextView).text} ${resultModel.data.fromCurrency}"
                switcher_below.setText("${resultModel.data.result} ${resultModel.data.toCurrency}")
                switcher_above.setText(currentTextBelow)
                Timber.i("SUCCESS ${resultModel.data.fromCurrency} ${resultModel.data.toCurrency} ${resultModel.data.result}")
            }
            is NetworkResultState.Error -> {
                progressConvert.visibility = View.GONE
                switcher_above.setCurrentText("ERROR ${resultModel.error}")
                Timber.i("ERROR ${resultModel.error}")
            }
        }
    }

    private fun getDisplayState(calcResultState: CalcResultState<String>) {
        progressConvert.visibility = View.GONE
        val currentTextBelow = (switcher_below.currentView as TextView).text.toString()
        when (calcResultState) {
            is CalcResultState.ClearAll -> {
                clearAllTextsWithAnimation()
            }
            is CalcResultState.SuccessEquals -> {

                switcher_below.setText(calcResultState.data)
                switcher_above.setText(currentTextBelow)
            }
            is CalcResultState.SuccessDigit -> {
                switcher_below.setCurrentText(calcResultState.data)
            }
            is CalcResultState.ShowConversionDialog -> {
                FragmentUtils.showDialog(ConvertDialogFragment.newInstance(currentTextBelow), supportFragmentManager)
            }
            is CalcResultState.Error -> {
                switcher_above.setCurrentText("ERROR ${calcResultState.errorMsg}")
            }
        }
    }

    override fun onClick(v: View?) {
        val btnText = (v as Button).text.toString()
        val padType = PadType.getPadType(btnText)
        mainViewModel.addExpression(padType)
    }

    private fun initPads() {
        btn_currency.setOnClickListener(this)
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

    override fun messageFromChildToParent(vararg values: String) {
        if (values.isEmpty()) {
            RapidSnack.error(window.decorView)
            return
        }
        val currentTextBelow = (switcher_below.currentView as TextView).text.toString()
        mainViewModel.exchange(values[0], values[1], currentTextBelow)
    }
}
