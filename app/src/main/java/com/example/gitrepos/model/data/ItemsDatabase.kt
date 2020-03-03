package com.example.gitrepos.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
 abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items_database"
                )
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}