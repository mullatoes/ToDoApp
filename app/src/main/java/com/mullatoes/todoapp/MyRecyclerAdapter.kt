package com.mullatoes.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mullatoes.todoapp.model.Task

class MyRecyclerAdapter(
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }
}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    //title, vategory, due date, priority, description,
    fun bind(task: Task) {
        title.text = task.title
        description.text = task.description
        category.text = task.category
        priority.text = task.priority
        duedate.text = task.dueDate
    }

    val title: TextView = view.findViewById(R.id.textViewTaskTitle)
    val description = view.findViewById<TextView>(R.id.textViewTaskDescription)
    val category = view.findViewById<TextView>(R.id.textViewTaskCategory)
    val priority = view.findViewById<TextView>(R.id.textViewTaskPriority)
    val duedate = view.findViewById<TextView>(R.id.textViewTaskDueDate)

}