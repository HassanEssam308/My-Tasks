package com.example.mytasksapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example.mytasksapp.R
import com.example.mytasksapp.dataBase.DataBase
import com.example.mytasksapp.dataBase.TaskItem
import com.example.mytasksapp.databinding.FragmentTaskDetailsBinding


class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding
    lateinit var db: DataBase


    private val args : TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_task_details, container, false)
        _binding = FragmentTaskDetailsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(
            requireContext(),
            DataBase::class.java,
            "db_tasks"

        ).allowMainThreadQueries().build()

        val task_id = args.taskId
       val taskItem=db.taskDao().getOneTaskById(task_id)
        binding!!.DetailsTVTextTitle.text=taskItem.title
        binding!!.DetailsTVTextDetails.text=taskItem.details
        binding!!.DetailsTVTextRemind.text=taskItem.getFullDateString()

        binding!!.DetailsFRFloatingBtnAdd.setOnClickListener{
            val  action= TaskDetailsFragmentDirections.actionTaskDetailsFragmentToAddTaskFragment(task_id)
            findNavController().navigate(action)
        }
    }
}