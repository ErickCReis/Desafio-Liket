package com.example.gitrepos.model.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Items (@PrimaryKey(autoGenerate = true) var id: Int?,
                  @ColumnInfo(name = "items_name") var name: String,
                  @ColumnInfo(name = "items_stars") var stars: Int) {

    override fun toString(): String {
        return "Name: $name - Stars: $stars"
    }
}