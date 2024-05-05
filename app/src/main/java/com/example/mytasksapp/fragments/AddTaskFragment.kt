package com.example.mytasksapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example.mytasksapp.MainActivity
import com.example.mytasksapp.R
import com.example.mytasksapp.dataBase.DataBase
import com.example.mytasksapp.dataBase.TaskItem
import com.example.mytasksapp.databinding.FragmentAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker


class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding
    val dataPicker = MaterialDatePicker.Builder.datePicker()
//    val dataPicker = MaterialDatePicker.Builder.datePicker().build()
    var selectedData: Long = 0
    lateinit var db: DataBase
    private val args  :AddTaskFragmentArgs by  navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_task, container, false)

        _binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)

        return binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //build DataBase
        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java,
            "db_tasks"

        ).allowMainThreadQueries().build()


        val task_id = args.taskId
//        Toast.makeText(requireContext(),"task_id=$task_id", Toast.LENGTH_SHORT).show()
       if (task_id !=-1){
           //chang title of tool bar
           (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update Task"
           val taskItem=db.taskDao().getOneTaskById(task_id)

           binding!!.AddTETitleInput.setText(taskItem.title)
           binding!!.AddTEBodyInput.setText(taskItem.details)
           binding!!.AddTERemindInput.setText(taskItem.getFullDateString())
           dataPicker.setSelection(taskItem.taskDate)
           binding!!.AddTERemindLayout.setEndIconOnClickListener {
               buildDatePicker()
           }
           updateTaskToDB(task_id)

       }else{

           // apply data picker
           binding!!.AddTERemindLayout.setEndIconOnClickListener {
               buildDatePicker()
           }
           addTaskToDB()
       }


    }

    private fun buildDatePicker(){
        dataPicker.build().apply {
            show(
                this@AddTaskFragment.requireActivity().supportFragmentManager,
                "data_picker"
            )

            // when user selected data or press ok
            addOnPositiveButtonClickListener {
                selectedData = it
                binding!!.AddTERemindInput.setText(this.headerText)
            }
        }
    }
    private fun addTaskToDB(){
        // insert data when user click
        binding!!.AddBtnSave.setOnClickListener {
            db.taskDao().insertTask(
                TaskItem(
                    0,
                    binding!!.AddTETitleInput.text.toString(),
                    binding!!.AddTEBodyInput.text.toString(),
                    selectedData
                )
            )

            // navigate to all task fragment
            val action = AddTaskFragmentDirections.actionAddTaskFragmentToAllTaskFragment()
            findNavController().navigate(action)

        }


    }
    private fun updateTaskToDB(t_id:Int){
        // update task when user click
        binding!!.AddBtnSave.setOnClickListener {
            db.taskDao().updateTask(
                TaskItem(
                    t_id,
                    binding!!.AddTETitleInput.text.toString(),
                    binding!!.AddTEBodyInput.text.toString(),
                    selectedData
                )
            )

            // navigate to all task fragment
            val action = AddTaskFragmentDirections.actionAddTaskFragmentToAllTaskFragment()
            findNavController().navigate(action)

        }


    }


}