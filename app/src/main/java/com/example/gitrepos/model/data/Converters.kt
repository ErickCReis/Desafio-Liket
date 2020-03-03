package com.example.gitrepos.model.data

import androidx.room.TypeConverter
import com.example.gitrepos.model.Owner
import com.google.gson.Gson

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromOwner(value: Owner): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toOwner(value: String): Owner {
            return Gson().fromJson(value, Owner::class.java)
        }
    }
}