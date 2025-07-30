package com.example.expensetracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.data.model.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): LiveData<List<Expense>>
}


