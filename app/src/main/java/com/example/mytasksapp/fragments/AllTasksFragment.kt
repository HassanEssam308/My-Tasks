package com.example.mytasksapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mytasksapp.R
import com.example.mytasksapp.TasksAdapter
import com.example.mytasksapp.TasksDataClass
import com.example.mytasksapp.dataBase.DataBase
import com.example.mytasksapp.dataBase.TaskItem
import com.example.mytasksapp.databinding.FragmentAddTaskBinding
import com.example.mytasksapp.databinding.FragmentAllTasksBinding


class AllTasksFragment : Fragment() {
    private lateinit var recyclerView_ShowTasks: RecyclerView

    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding
    lateinit var db: DataBase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_all_tasks, container, false)

        _binding = FragmentAllTasksBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java,
            "db_tasks"

        ).allowMainThreadQueries().build()

        val listOfTasks: List<TaskItem> = db.taskDao().getAllTasks()

        if (listOfTasks.isNotEmpty()) {

            binding!!.AllTasksFRTVNoData.isGone = true
            recyclerView_ShowTasks = binding!!.AllTasksFRRecViewTasks
            recyclerView_ShowTasks.layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            var tasksAdapter = TasksAdapter(listOfTasks)
            recyclerView_ShowTasks.adapter = tasksAdapter

            tasksAdapter.setOnClickListener(
                object : TasksAdapter.OnClickListener {
                    override fun onClick(position: Int, item: TaskItem) {
                        val action = AllTasksFragmentDirections
                            .actionAllTaskFragmentToTaskDetailsFragment(item.id)
                        findNavController().navigate(action)
                        /* Toast.makeText(
                             requireContext(),
                             "position =$position ,TITLE =${item.title} ",
                             Toast.LENGTH_SHORT
                         ).show()*/
                    }

                }
            )
            tasksAdapter.setOnClickListenerOnThreeDote(
                object : TasksAdapter.OnClickListener {
                    override fun onClick(position: Int, item: TaskItem) {
                        deleteTask(item)
                    }

                }
            )

        } else {
            binding!!.AllTasksFRTVNoData.isVisible = true
            binding!!.AllTasksFRRecViewTasks.isGone = true

        }

        // navigate to Add Task Fragment by floating Button
        binding!!.AllTasksFRFloatingBtnAdd.setOnClickListener {
            val action = AllTasksFragmentDirections
                .actionAllTaskFragmentToAddTaskFragment()
            findNavController().navigate(action)
        }

        /*       var tasksList = arrayListOf(
                    TasksDataClass(1, "one", "one", "saturday"),
                    TasksDataClass(2, "two", "two", "Sunday"),
                    TasksDataClass(3, "three", "three", "Monday"),
                    TasksDataClass(4, "four", "four", "Tuesday"),
                    TasksDataClass(5, "five", "five", "Wednesday"),
                    TasksDataClass(6, "six", "six", "Thursday"),
                    TasksDataClass(7, "seven", "seven", "friday"),

                    )*/
    }

    fun deleteTask(item: TaskItem) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Delete Task")
        alertDialogBuilder.setMessage("Are you sure you want to Delete this Task?")

        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, i: Int ->
            db.taskDao().deleteTask(item)
            findNavController().navigate(findNavController().currentDestination!!.id)
            Toast.makeText(
                requireContext(),
                "Task is Removed",
                Toast.LENGTH_SHORT
            ).show()
        }
        alertDialogBuilder.setNegativeButton(
            "Cancel"
        ) { dialogInterface: DialogInterface, i: Int -> }
        alertDialogBuilder.create().show()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

