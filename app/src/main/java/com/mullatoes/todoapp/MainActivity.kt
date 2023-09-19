package com.mullatoes.todoapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mullatoes.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var tasks = mutableListOf("Praying", "Writing Documentation", "Coding", "Eating", "Netflixing")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            setBackgroundColor(Color.YELLOW)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MyRecyclerAdapter(tasks)
        }

        binding.floatingActionButton.setOnClickListener {
            println("FAB Clicked! ${tasks.size}")
            val newTask = "Swimming"
            addTask(newTask)

            println("FAB Clicked After Adding! ${tasks.size}")
        }

    }

    private fun addTask(task: String) {
        tasks.add(task)
    }
}