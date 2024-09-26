package com.mglabs.twopagetodo.domain.repository

import com.mglabs.twopagetodo.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun findAll(): Flow<List<Project>>
    suspend fun findById(projectId: Int): Project?
}