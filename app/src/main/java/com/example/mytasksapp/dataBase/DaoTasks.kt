package com.example.mytasksapp.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoTasks {
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertTask(taskItem: TaskItem)

@Update(onConflict = OnConflictStrategy.REPLACE)
fun updateTask(taskItem: TaskItem)

    @Query("SELECT * FROM tasks")
     fun getAllTasks(): List<TaskItem>

     @Query("SELECT * FROM tasks where id=:t_id")
     fun getOneTaskById(t_id:Int): TaskItem

//     @Query("Delete  FROM tasks where task_id=:id")
//     fun deleteTask(id:Int)

    @Delete
     fun deleteTask(taskItem: TaskItem)

}