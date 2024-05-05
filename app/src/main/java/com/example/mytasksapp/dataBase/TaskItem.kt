package com.example.mytasksapp.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//@Parcelize
@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "task_name") val title: String,
    @ColumnInfo(name = "task_details") val details: String,
    @ColumnInfo(name = "task_date") val taskDate: Long

//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "taskName")  val title: String,
//    @ColumnInfo(name = "taskDetails") val details: String,
//    @ColumnInfo(name = "taskDate") val taskDate: Long
) {
    fun getDay(): String {

        return SimpleDateFormat("EEEE").format(Date(taskDate))

    }


    fun getDate(): String {

        return SimpleDateFormat("dd").format(Date(taskDate))

    }


// get first 3 chars of the month

    fun getMonth(): String {

        return SimpleDateFormat("MMMM").format(Date(taskDate))

    }


    fun getFullDateString(): String {

        val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)

        return format.format(Date(taskDate))


    }


}

