package com.mullatoes.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullatoes.todoapp.databinding.ActivityMainBinding
import com.mullatoes.todoapp.model.Task

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var taskTitleEditText: EditText
    private lateinit var taskDescriptionEditText: EditText
    private lateinit var taskPriorityEditText: EditText
    private lateinit var taskDueDateEditText: EditText
    private lateinit var taskCategoryEditText: EditText

    private lateinit var binding: ActivityMainBinding

    // var tasks = mutableListOf("Praying", "Writing Documentation", "Coding", "Eating", "Netflixing")

    val taskList = mutableListOf(
        Task(
            1,
            "Complete Project Proposal",
            "Prepare and submit the project proposal for review.",
            "High",
            "2023-09-30",
            "Work"
        ),
        Task(
            2,
            "Grocery Shopping",
            "Buy groceries for the week.",
            "Medium",
            "2023-09-20",
            "Personal"
        ),
        Task(3, "Exercise", "Go for a run or hit the gym.", "Low", "2023-09-22", "Health"),
        Task(
            4,
            "Read a Book",
            "Read a chapter of your current book.",
            "Medium",
            "2023-09-25",
            "Personal"
        ),
        Task(
            5,
            "Pay Bills",
            "Pay monthly utility bills.",
            "High",
            "2023-09-28",
            "Finance"
        )
    )


    private lateinit var adapter: MyRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = MyRecyclerAdapter(taskList, this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyRecyclerAdapter(taskList, this@MainActivity)
        }

        binding.floatingActionButton.setOnClickListener {
            launchAddItemDialog()
        }

        val priorityLevels = arrayOf("All", "High", "Medium", "Low")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, priorityLevels
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnerPriorityFilter.adapter = adapter

        binding.spinnerPriorityFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedPriority = priorityLevels[position]
                    filterTasksByPriority(selectedPriority)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Do nothing if nothing is selected
                }

            }

    }

    private fun filterTasksByPriority(selectedPriority: String) {

        val filteredTasks = if (selectedPriority == "All") {
            taskList
        } else {
            taskList.filter { it.priority == selectedPriority }
        }

        adapter.updateTasks(filteredTasks)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun launchAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_item_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
            .setTitle("Enter Task Details")
            .setPositiveButton("Save") { dialog, which ->

                val taskTitleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
                val taskDescriptionEditText =
                    dialogView.findViewById<EditText>(R.id.editTextDescription)
                val taskPriorityEditText = dialogView.findViewById<EditText>(R.id.editTextPriority)
                val taskDueDateEditText = dialogView.findViewById<EditText>(R.id.editTextDueDate)
                val taskCategoryEditText = dialogView.findViewById<EditText>(R.id.editTextCategory)

                taskDueDateEditText.setOnClickListener {
                    println("Clicked Due Date")
                }

                val taskTitle = taskTitleEditText.text.toString()
                val taskDescription = taskDescriptionEditText.text.toString()
                val priority = taskPriorityEditText.text.toString()
                val dueDate = taskDueDateEditText.text.toString()
                val category = taskCategoryEditText.text.toString()

                val newId = Math.random().toLong()
                val newTask = Task(newId, taskTitle, taskDescription, priority, dueDate, category)
                taskList.add(newTask)

                binding.recyclerView.adapter?.notifyDataSetChanged()


                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->

                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun addTask(task: Task) {
        taskList.add(task)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteTaskClicked(taskId: Long) {

        println("Task Id: $taskId")

        val taskToRemove = taskList.find { it.id == taskId }

        if (taskToRemove != null) {
            taskList.remove(taskToRemove)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

    }
}