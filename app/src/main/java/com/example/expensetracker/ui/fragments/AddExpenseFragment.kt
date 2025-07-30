package com.example.expensetracker.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.expensetracker.MainActivity
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.viewmodels.ExpenseViewModel

class AddExpenseFragment : Fragment() {

    private lateinit var etTitle: EditText
    private lateinit var etAmount: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnSave: Button

    // ✅ Shared ViewModel from MainActivity
    private val viewModel: ExpenseViewModel by lazy {
        (requireActivity() as MainActivity).expenseViewModel
    }

    private val categories = arrayOf("Food", "Transport", "Bills", "Shopping", "Other")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)

        etTitle = view.findViewById(R.id.etTitle)
        etAmount = view.findViewById(R.id.etAmount)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        btnSave = view.findViewById(R.id.btnSave)

        spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        btnSave.setOnClickListener {
            saveExpense()
        }

        return view
    }

    private fun saveExpense() {
        val title = etTitle.text.toString().trim()
        val amountStr = etAmount.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()

        if (title.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "Enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(title = title, amount = amount, category = category)
        viewModel.insert(expense)

        Toast.makeText(requireContext(), "Expense Added", Toast.LENGTH_SHORT).show()
        etTitle.text.clear()
        etAmount.text.clear()
    }
}
