package com.mullatoes.todoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mullatoes.todoapp.model.Task

class MyRecyclerAdapter(
    private var tasks: List<Task>, private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return MyViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}

interface TaskItemClickListener {
    fun onDeleteTaskClicked(taskId: Long)
}

class MyViewHolder(view: View, private val clickListener: TaskItemClickListener) :
    RecyclerView.ViewHolder(view) {

    fun bind(task: Task) {
        title.text = task.title
        description.text = task.description
        category.text = task.category
        priority.text = task.priority
        duedate.text = task.dueDate

        deleteTaskButton.setOnClickListener {
            clickListener.onDeleteTaskClicked(task.id)
        }
    }

    val title: TextView = view.findViewById(R.id.textViewTaskTitle)
    val description = view.findViewById<TextView>(R.id.textViewTaskDescription)
    val category = view.findViewById<TextView>(R.id.textViewTaskCategory)
    private val priority: TextView = view.findViewById(R.id.textViewTaskPriority)
    private val duedate: TextView = view.findViewById(R.id.textViewTaskDueDate)
    private val deleteTaskButton: Button = view.findViewById(R.id.taskDeleteButton)

}