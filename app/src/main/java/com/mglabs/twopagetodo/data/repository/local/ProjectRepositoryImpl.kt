package com.mglabs.twopagetodo.data.repository.local

import com.mglabs.twopagetodo.data.local.dao.ProjectDao
import com.mglabs.twopagetodo.data.local.entities.toDomain
import com.mglabs.twopagetodo.domain.model.Project
import com.mglabs.twopagetodo.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProjectRepositoryImpl : ProjectRepository {
    private val projectDao: ProjectDao = ProjectDao()

    override fun findAll(): Flow<List<Project>> {
        return flowOf(projectDao.findAll().map { it.toDomain() })
    }

    override suspend fun findById(projectId: Int): Project? {
        return projectDao.findById(projectId)?.toDomain()
    }
}