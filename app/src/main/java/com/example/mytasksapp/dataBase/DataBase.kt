package com.example.mytasksapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1)
 abstract  class DataBase :RoomDatabase(){
   abstract  fun taskDao():DaoTasks

}