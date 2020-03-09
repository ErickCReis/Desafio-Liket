package com.example.gitrepos.model.profile

import androidx.room.*

@Dao
interface ProfilesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(profile: Profile)

    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile() : Profile
}