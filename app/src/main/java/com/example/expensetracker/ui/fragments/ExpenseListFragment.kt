package com.example.expensetracker.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.ui.adapter.ExpenseAdapter
import com.example.expensetracker.ui.viewmodels.ExpenseViewModel

class ExpenseListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var adapter: ExpenseAdapter

    // ✅ Access shared ViewModel from MainActivity
    private val viewModel: ExpenseViewModel by lazy {
        (requireActivity() as MainActivity).expenseViewModel
    }

    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)

        tvTotal = view.findViewById(R.id.tvTotal)
        recyclerView = view.findViewById(R.id.rvExpenses)

        adapter = ExpenseAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observe the expense list from ViewModel
        viewModel.allExpenses.observe(viewLifecycleOwner) { expenseList ->
            adapter.updateList(expenseList)
            val total = expenseList.sumOf { it.amount }
            tvTotal.text = "Total: ₹ %.2f".format(total)
        }

        return view
    }
}
