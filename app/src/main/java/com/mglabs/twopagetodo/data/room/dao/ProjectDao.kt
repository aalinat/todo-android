package com.mglabs.twopagetodo.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mglabs.twopagetodo.data.room.entities.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects")
    fun getProjects(): Flow<List<Project>>
    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun findById(projectId: Int): Project
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(project: Project)
}