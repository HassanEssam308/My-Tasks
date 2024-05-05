package com.example.mytasksapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasksapp.dataBase.TaskItem
import com.example.mytasksapp.databinding.RowViewOneTaskBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class TasksAdapter(
    private val listOfTasks: List<TaskItem>
) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var onClickListenerOfThreeDote: OnClickListener? = null

 /*   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.RowView_TV_Title)
        val details = itemView.findViewById<TextView>(R.id.RowView_TV_Details)
        val remind = itemView.findViewById<TextView>(R.id.RowView_TV_Remind)
        val dayString = itemView.findViewById<TextView>(R.id.RowView_TV_DayString)
        val dayNum = itemView.findViewById<TextView>(R.id.RowView_TV_DayNum)
        val month = itemView.findViewById<TextView>(R.id.RowView_TV_Month)


    }*/

class ViewHolder(binding: RowViewOneTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    val threeDote =binding.RowViewIVThreeDote
    val title = binding.RowViewTVTitle
    val details = binding.RowViewTVDetails
    val remind = binding.RowViewTVRemind
    val dayString = binding.RowViewTVDayString
    val dayNum = binding.RowViewTVDayNum
    val month = binding.RowViewTVMonth


}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
/*           LayoutInflater.from(parent.context)
               .inflate(R.layout.row_view_one_task, parent, false)*/
            RowViewOneTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfTasks[position]
        holder.title.text = item.title
        holder.details.text = item.details
        holder.remind.text=item.getFullDateString()
        holder.dayString.text=item.getDay().substring(0,3)
        holder.dayNum.text=item.getDate()
        holder.month.text=item.getMonth().substring(0,3)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
        holder.threeDote.setOnClickListener{
            if (onClickListenerOfThreeDote!=null){
                onClickListenerOfThreeDote!!.onClick(position, item)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setOnClickListenerOnThreeDote(onClickListener: OnClickListener) {
        this.onClickListenerOfThreeDote = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, item: TaskItem)
    }
}