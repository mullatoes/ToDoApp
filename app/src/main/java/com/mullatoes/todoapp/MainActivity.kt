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
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullatoes.todoapp.databinding.ActivityMainBinding
import com.mullatoes.todoapp.model.Task

class MainActivity : AppCompatActivity() {

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
        Task(5, "Pay Bills", "Pay monthly utility bills.", "High", "2023-09-28", "Finance")
    )


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            setBackgroundColor(Color.YELLOW)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyRecyclerAdapter(taskList)
        }

//        binding.floatingActionButton.setOnClickListener {
//            println("FAB Clicked! ${taskList.size}")
//            // val newTask = "Swimming"
//            val task = Task(
//                6,
//                "Meeting with Client",
//                "Discuss project details with the client.",
//                "High",
//                "2023-09-24",
//                "Work"
//            )
//
//            addTask(task)
//
//            println("FAB Clicked After Adding! ${taskList.size}")
//            println("Task title: ${task.title}")
//        }

        val overlayLayout = LayoutInflater.from(this)
            .inflate(R.layout.add_task_overlay, null)

        val addTaskLayout = overlayLayout.findViewById<View>(R.id.addTaskLayout)
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        binding.floatingActionButton.setOnClickListener {
            windowManager.addView(overlayLayout, layoutParams)
        }

        val saveButton = overlayLayout.findViewById<Button>(R.id.buttonSaveTask)
        val taskTitleEditText = overlayLayout.findViewById<EditText>(R.id.eTTitle)
        val taskDescriptionEditText = overlayLayout.findViewById<EditText>(R.id.eTDescription)
        val taskPriorityEditText = overlayLayout.findViewById<EditText>(R.id.eTPriority)
        val taskDueDateEditText = overlayLayout.findViewById<EditText>(R.id.eTDueDate)
        val taskCategoryEditText = overlayLayout.findViewById<EditText>(R.id.eTCategory)

        saveButton.setOnClickListener {

            val taskTitle = taskTitleEditText.text.toString()
            val taskDescription = taskDescriptionEditText.text.toString()
            val priority = taskPriorityEditText.text.toString()
            val dueDate = taskDueDateEditText.text.toString()
            val category = taskCategoryEditText.text.toString()

            val newId = Math.random().toLong()

            val newTask = Task(newId, taskTitle, taskDescription, priority, dueDate, category)
            taskList.add(newTask)

            binding.recyclerView.adapter?.notifyDataSetChanged()

            windowManager.removeView(overlayLayout)
        }
    }

    private fun addTask(task: Task) {
        taskList.add(task)
    }
}