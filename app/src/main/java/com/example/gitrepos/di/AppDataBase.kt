package com.example.gitrepos.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gitrepos.model.data.Converters
import com.example.gitrepos.model.data.Item
import com.example.gitrepos.model.data.ItemsUpdateDao

@TypeConverters(Converters::class)
@Database(entities = [Item::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemsUpdateDao(): ItemsUpdateDao
}