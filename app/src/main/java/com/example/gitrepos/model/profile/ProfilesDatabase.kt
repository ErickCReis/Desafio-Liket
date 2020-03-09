package com.example.gitrepos.model.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ProfilesDatabase : RoomDatabase() {

    abstract fun profilesDao(): ProfilesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ProfilesDatabase? = null

        fun getDatabase(context: Context): ProfilesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfilesDatabase::class.java,
                        "profile_database"
                    )
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}