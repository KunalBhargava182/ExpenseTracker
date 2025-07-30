package com.example.expensetracker.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.viewmodels.ExpenseViewModel

class SummaryFragment : Fragment() {

    private lateinit var tvSummaryDetails: TextView

    private val viewModel: ExpenseViewModel by lazy {
        (requireActivity() as MainActivity).expenseViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        tvSummaryDetails = view.findViewById(R.id.tvSummaryDetails)

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            displaySummary(expenses)
        }

        return view
    }

    private fun displaySummary(expenses: List<Expense>) {
        val grouped = expenses.groupBy { it.category }
        val summaryText = StringBuilder()

        var totalAmount = 0.0
        grouped.forEach { (category, list) ->
            val total = list.sumOf { it.amount }
            totalAmount += total
            summaryText.append("$category: ₹%.2f\n".format(total))
        }

        summaryText.append("\nTotal: ₹%.2f".format(totalAmount))
        tvSummaryDetails.text = summaryText.toString()
    }
}
