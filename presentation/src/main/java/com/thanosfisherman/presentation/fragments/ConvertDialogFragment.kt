package com.thanosfisherman.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.adapters.CurrenciesArrayAdapter
import kotlinx.android.synthetic.main.fragment_dialog_convert.*

class ConvertDialogFragment : AppCompatDialogFragment() {

    private lateinit var fragmentInteractor: FragmentInteractionListener

    private val amountParam: String by lazy {
        arguments?.getString("amount") ?: ""
    }

    companion object {
        fun newInstance(amount: String): ConvertDialogFragment {
            return ConvertDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("amount", amount)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_convert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val params = Array<String?>(2) { null }

        textTitle.text = getString(R.string.convert, amountParam)
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = CurrenciesArrayAdapter(requireContext(), currencies.toList())
        dropdownFrom.setAdapter(adapter)
        dropdownTo.setAdapter(adapter)

        dropdownFrom.setOnItemClickListener { parent, _, position, id -> params[0] = parent.getItemAtPosition(position) as String }
        dropdownTo.setOnItemClickListener { parent, _, position, id -> params[1] = parent.getItemAtPosition(position) as String }

        btnCancel.setOnClickListener { dialog?.hide() }
        btnSubmit.setOnClickListener {
            if (params.any { it == null }) {
                fragmentInteractor.messageFromChildToParent()
            } else {
                fragmentInteractor.messageFromChildToParent(params[0]!!, params[1]!!)
            }
            dialog?.hide()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionListener)
            fragmentInteractor = context
        else
            throw ClassCastException("Activity must implement FragmentInteractionListener")
    }

    override fun onStart() {
        super.onStart()
        correctProportions()
    }

    private fun correctProportions() {
        val lp = WindowManager.LayoutParams()
        dialog?.apply {
            lp.copyFrom(window?.attributes)
            // This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window?.attributes = lp
        }
    }
}