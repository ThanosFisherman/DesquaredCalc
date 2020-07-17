package com.thanosfisherman.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.thanosfisherman.presentation.R
import kotlinx.android.synthetic.main.dropdown_menu_popup_item.view.*

class CurrenciesArrayAdapter(context: Context, private val items: List<String>) :
    ArrayAdapter<String>(context, 0, items) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return filter_results_to_string
    }

    private val filter_results_to_string = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = items
            results.count = items.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultValue as String
        }
    }

    private fun getCustomView(pos: Int, contertView: View?, parent: ViewGroup): View {
        var view = contertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dropdown_menu_popup_item, parent, false)
        }
        view?.txtItem?.text = items[pos]

        return view as View
    }
}